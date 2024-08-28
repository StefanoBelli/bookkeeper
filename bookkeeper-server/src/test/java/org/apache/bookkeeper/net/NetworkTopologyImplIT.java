package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplIT extends Common.PopulatedNetworkTopology {
    public enum ExpectedResult {
        IAE,
        FALSE,
        TRUE
    }

    private final Node n1;
    private final Node n2;
    private final ExpectedResult expectedResult;

    public NetworkTopologyImplIT(Node n1, Node n2, ExpectedResult expectedResult) {
        this.n1 = n1;
        this.n2 = n2;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        Node nonExistingNode = Common.buildNode("/europe/it/lazio/roma/rack-1","bookie-14");

        return Arrays.asList(new Object[][] {
                //{null, nodes[0], ExpectedResult.IAE},
                //{nodes[0], null, ExpectedResult.IAE},
                //{nonExistingNode, nodes[0], ExpectedResult.IAE},
                //{nodes[0], nonExistingNode, ExpectedResult.IAE},
                //{null, nonExistingNode, ExpectedResult.IAE},
                //{nonExistingNode, null, ExpectedResult.IAE},
                //{nonExistingNode, nonExistingNode, ExpectedResult.IAE},
                {nodes[0], nodes[1], ExpectedResult.TRUE},
                {nodes[1], nodes[2], ExpectedResult.FALSE}
        });
    }

    @Test
    public void testIsOnSameRack() {
        if(expectedResult == ExpectedResult.IAE) {
            assertThrows(IllegalArgumentException.class, () -> networkTopology.isOnSameRack(n1, n2));
        } else {
            assertEquals(expectedResult == ExpectedResult.TRUE, networkTopology.isOnSameRack(n1, n2));
        }
    }
}
