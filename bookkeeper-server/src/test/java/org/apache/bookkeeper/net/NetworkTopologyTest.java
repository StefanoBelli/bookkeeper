package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NetworkTopologyTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {null},
                {"/zone-a/region-a/rack-1/node-1"},
                {"/region-a/rack-1/node-1"},
                {"/rack-1/node-1"},
                {"/node-2"},
                {"/"},
                {""}
        });
    }

    private final String singlePath;

    public NetworkTopologyTest(String singlePath) {
        this.singlePath = singlePath;
    }

    @Test
    public void testSingleNodeAdd() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        final NodeBase node = singlePath != null ? new NodeBase(singlePath) : null;

        if(singlePath != null && singlePath.indexOf("/") == singlePath.lastIndexOf("/")) {
            assertThrows(IllegalArgumentException.class, () -> networkTopology.add(node));
        } else {
            networkTopology.add(node);
            int expNum = node != null ? 1 : 0;
            assertEquals(expNum, networkTopology.getNumOfLeaves());
            assertEquals(expNum, networkTopology.getNumOfRacks());
            if(singlePath != null) {
                int lastSlashIdx = singlePath.lastIndexOf("/");
                assertEquals(node, networkTopology.getNode(singlePath));
                List<Node> nodesInRack = networkTopology.getDatanodesInRack(
                        singlePath.substring(0, lastSlashIdx));
                if(nodesInRack.size() != 1) {
                    fail();
                }
                assertEquals(node, nodesInRack.get(0));

                int prevEnd = -1;
                do {
                    prevEnd = singlePath.indexOf("/", prevEnd + 1);
                    String path = singlePath.substring(0, prevEnd == -1 ? lastSlashIdx : prevEnd);
                    Set<Node> nodes = networkTopology.getLeaves(path);
                    assertTrue(nodes.size() == 1 && nodes.contains(node));
                } while(prevEnd != lastSlashIdx);
            }
        }
    }
}
