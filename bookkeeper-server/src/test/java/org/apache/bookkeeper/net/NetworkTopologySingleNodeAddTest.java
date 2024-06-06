package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NetworkTopologySingleNodeAddTest {
    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {null},
                {new NodeWithItsArg(null)},
                {new NodeWithItsArg("/zone-a/region-a/rack-1/node-1")},
                {new NodeWithItsArg("/region-a/rack-1/node-1")},
                {new NodeWithItsArg("/rack-1/node-1")},
                {new NodeWithItsArg("/node-2")},
                {new NodeWithItsArg("/")},
                {new NodeWithItsArg("")}
        });
    }

    private final NodeWithItsArg nodeWithItsArg;

    public NetworkTopologySingleNodeAddTest(NodeWithItsArg nodeWithItsArg) {
        this.nodeWithItsArg = nodeWithItsArg;
    }

    @Test
    public void testSingleNodeAdd() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        final NodeBase singleNode = nodeWithItsArg != null ? nodeWithItsArg.createNodeBase() : null;
        final String singlePath = nodeWithItsArg != null ? nodeWithItsArg.getPath() : null;

        if(
                (singleNode != null && singlePath == null) ||
                (singlePath != null && singlePath.indexOf("/") == singlePath.lastIndexOf("/"))
        ) {

            assertThrows(IllegalArgumentException.class, () -> networkTopology.add(singleNode));
        } else {
            networkTopology.add(singleNode);
            int expNum = singleNode != null ? 1 : 0;
            assertEquals(expNum, networkTopology.getNumOfLeaves());
            assertEquals(expNum, networkTopology.getNumOfRacks());
            if(singlePath != null) {
                assertEquals(singleNode, networkTopology.getNode(singlePath));

                int lastSlashIdx = singlePath.lastIndexOf("/");
                List<Node> nodesInRack = networkTopology.getDatanodesInRack(
                        singlePath.substring(0, lastSlashIdx));
                if(nodesInRack.size() != 1) {
                    fail();
                }
                assertEquals(singleNode, nodesInRack.get(0));

                int prevEnd = -1;
                do {
                    prevEnd = singlePath.indexOf("/", prevEnd + 1);
                    String path = singlePath.substring(0, prevEnd == -1 ? lastSlashIdx : prevEnd);
                    Set<Node> nodes = networkTopology.getLeaves(path);
                    assertTrue(nodes.size() == 1 && nodes.contains(singleNode));
                } while(prevEnd != lastSlashIdx);
            }
        }
    }


    public static final class NodeWithItsArg {
        private final String path;
        private NodeWithItsArg(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public NodeBase createNodeBase() {
            return new NodeBase(path);
        }
    }
}
