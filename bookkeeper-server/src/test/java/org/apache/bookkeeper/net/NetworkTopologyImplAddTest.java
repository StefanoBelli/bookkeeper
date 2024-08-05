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
    private final Node nodeToAdd;

    public NetworkTopologyImplAddTest(ExpectedResult expectedResult, Node nodeToAdd) {
        this.expectedResult = expectedResult;
        this.nodeToAdd = nodeToAdd;
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

        assertNull(nodeToAdd != null ? Common.getMyNode(sut, nodeToAdd) : null);
        assertEquals(0, sut.getNumOfRacks());
        assertEquals(0, sut.getNumOfLeaves());

        if(expectedResult == ExpectedResult.EXCEPTION_THROWN) {
            assertThrows(IllegalArgumentException.class, () -> sut.add(nodeToAdd));
        } else {
            sut.add(nodeToAdd);
        }

        Node gotNode = nodeToAdd != null ? Common.getMyNode(sut, nodeToAdd) : null;
        int numOfRacks = sut.getNumOfRacks();
        int numOfLeaves = sut.getNumOfLeaves();

        if(expectedResult == ExpectedResult.NO_CHANGES || expectedResult == ExpectedResult.EXCEPTION_THROWN) {
            if(nodeToAdd != null) {
                assertNotEquals(nodeToAdd, gotNode);
            }
            assertEquals(0, numOfRacks);
            assertEquals(0, numOfLeaves);
        } else {
            assertNotNull(gotNode);
            assertEquals(nodeToAdd, gotNode);
            assertEquals(1, numOfRacks);
            assertEquals(1, numOfLeaves);
        }
    }
}
