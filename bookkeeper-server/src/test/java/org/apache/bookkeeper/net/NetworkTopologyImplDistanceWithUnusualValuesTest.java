package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplDistanceWithUnusualValuesTest extends Common.PopulatedNetworkTopology {
    public interface NodeBuilder {
        Node build(String path);
    }

    private static final class InTopologyNodeBuilder implements NodeBuilder {
        @Override
        public Node build(String path) {
            return networkTopology.getNode(path);
        }
    }

    private static final class NonInTopologyNodeBuilder implements NodeBuilder {
        @Override
        public Node build(String path) {
            if(path == null) {
                return null;
            }

            int lastSlashIdx = path.lastIndexOf('/');
            String netLoc = path.substring(0, lastSlashIdx);
            String bookie = path.substring(lastSlashIdx + 1);

            return Common.buildNode(netLoc, bookie);
        }
    }

    private final int expectedDistance;
    private final Node nodeA;
    private final Node nodeB;

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {0, null, null,
                        new NonInTopologyNodeBuilder(), new NonInTopologyNodeBuilder()}
                /*{0, "/europe/it/lazio/frosinone/rack-3/bookie-4", null,
                        new InTopologyNodeBuilder(), new NonInTopologyNodeBuilder()},
                {0, "/asia/ru/region-1/moscow/rack-2/bookie-2", null,
                        new NonInTopologyNodeBuilder(), new NonInTopologyNodeBuilder()},
                {0, null, "/europe/it/lazio/frosinone/rack-3/bookie-4",
                        new NonInTopologyNodeBuilder(), new InTopologyNodeBuilder()},
                {0, null, "/asia/ru/region-1/moscow/rack-2/bookie-2",
                        new NonInTopologyNodeBuilder(), new NonInTopologyNodeBuilder()},
                {Integer.MAX_VALUE, "/asia/ru/region-1/moscow/rack-2/bookie-2", "/europe/it/lazio/roma/rack-1/bookie-1",
                        new NonInTopologyNodeBuilder(), new InTopologyNodeBuilder()},
                {Integer.MAX_VALUE, "/europe/it/lazio/roma/rack-1/bookie-1", "/asia/ru/region-1/moscow/rack-2/bookie-2",
                        new InTopologyNodeBuilder(), new NonInTopologyNodeBuilder()},
                {Integer.MAX_VALUE, "/asia/cn/region-1/beijing/rack-1/bookie-1", "/asia/ru/region-1/moscow/rack-2/bookie-2",
                        new NonInTopologyNodeBuilder(), new NonInTopologyNodeBuilder()}*/
        });
    }

    public NetworkTopologyImplDistanceWithUnusualValuesTest(int expectedDistance, String nodeAPath, String nodeBPath,
                                                            NodeBuilder nodeABuilder, NodeBuilder nodeBBuilder) {
        this.expectedDistance = expectedDistance;
        this.nodeA = nodeABuilder.build(nodeAPath);
        this.nodeB = nodeBBuilder.build(nodeBPath);
    }

    @Test
    public void testDistance() {
        assertEquals(expectedDistance, networkTopology.getDistance(nodeA, nodeB));
    }
}
