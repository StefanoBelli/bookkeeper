package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplSortingWithUnusualValuesTest extends Common.PopulatedNetworkTopology {
    private final Node readerNode;
    private final Node[] nodesToBeSorted;

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][]{
                //{null, null},
                {null, new Node[]{null}},
                {null, new Node[]{networkTopology.getNode("/europe/it/lazio/roma/rack-1/bookie-1")}},
                //{networkTopology.getNode("/europe/it/lazio/roma/rack-1/bookie-1"), null},
                {networkTopology.getNode("/europe/it/lazio/roma/rack-1/bookie-1"), new Node[]{null}}
        });
    }

    public NetworkTopologyImplSortingWithUnusualValuesTest(Node readerNode, Node[] nodesToBeSorted) {
        this.readerNode = readerNode;
        this.nodesToBeSorted = nodesToBeSorted;
    }

    @Test
    public void testSortingWithUnusualValues() {
        networkTopology.pseudoSortByDistance(readerNode, nodesToBeSorted);
        assertTrue(true);
    }
}
