package org.apache.bookkeeper.net;


import org.apache.bookkeeper.net.common.PopulatedNetworkTopology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NetworkTopologyDistanceTest extends PopulatedNetworkTopology {

    private final int expectedDistance;
    private final NodeBase nodeA;
    private final NodeBase nodeB;

    public NetworkTopologyDistanceTest(int expectedDistance, String nodeAPath, String nodeBPath) {
        this.expectedDistance = expectedDistance;
        this.nodeA = (NodeBase) networkTopology.getNode(nodeAPath);
        this.nodeB = (NodeBase) networkTopology.getNode(nodeBPath);
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {0, "/europe/it/lazio/roma/rack-1/bookie-1", "/europe/it/lazio/roma/rack-1/bookie-1"},
                {2, "/europe/it/lazio/roma/rack-1/bookie-1", "/europe/it/lazio/roma/rack-1/bookie-2"},
                {4, "/europe/it/lazio/roma/rack-1/bookie-1", "/europe/it/lazio/roma/rack-2/bookie-3"},
                {6, "/europe/it/lazio/roma/rack-1/bookie-1", "/europe/it/lazio/frosinone/rack-1/bookie-4"},
                {8, "/europe/it/lazio/roma/rack-1/bookie-1","/europe/it/lombardia/milano/rack-1/bookie-5"},
                {10, "/europe/it/lazio/roma/rack-1/bookie-1","/europe/de/region-1/berlino/rack-1/bookie-6"},
                {12, "/europe/it/lazio/roma/rack-1/bookie-1", "/america/us/california/paloalto/rack-2/bookie-7"}
        });
    }

    @Test
    public void testDistance() {
        assertEquals(expectedDistance, networkTopology.getDistance(nodeA, nodeB));
    }
}
