package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplAddTest {
    public enum ExpectedResult {
        NO_CHANGES,
        NODE_ADDED,
        EXCEPTION_THROWN
    }

    private final ExpectedResult expectedResult;
    private final Node node;

    public NetworkTopologyImplAddTest(ExpectedResult expectedResult, Node node) {
        this.expectedResult = expectedResult;
        this.node = node;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {ExpectedResult.NO_CHANGES, null},
                {ExpectedResult.NODE_ADDED, Common.buildNode("/rack-1","bookie-1") },
                {ExpectedResult.EXCEPTION_THROWN, Common.buildNode("/", "bookie-2")},
                {ExpectedResult.NODE_ADDED, Common.buildNode("/dc-1/rack-1", "bookie-3")}
        });
    }

    @Test
    public void testAdd() {
        NetworkTopologyImpl sut = new NetworkTopologyImpl();
        if(expectedResult == ExpectedResult.EXCEPTION_THROWN) {
            assertThrows(IllegalArgumentException.class, () -> sut.add(node));
        } else {
            sut.add(node);
        }

        Node gotNode = node != null ? sut.getNode(node.getNetworkLocation() + "/" + node.getName()) : null;
        int numOfRacks = sut.getNumOfRacks();
        int numOfLeaves = sut.getNumOfLeaves();

        if(expectedResult == ExpectedResult.NO_CHANGES || expectedResult == ExpectedResult.EXCEPTION_THROWN) {
            assertNull(gotNode);
            assertEquals(0, numOfRacks);
            assertEquals(0, numOfLeaves);
        } else {
            assertNotNull(gotNode);
            assertEquals(node, gotNode);
            assertEquals(1, numOfRacks);
            assertEquals(1, numOfLeaves);
        }
    }
}
