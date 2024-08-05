package org.apache.bookkeeper.net;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplPrepopulatedAddTest {

    public enum ExpectedResult {
        NO_CHANGES_ALREADY_EXISTANT,
        NODE_ADDED,
        NODE_ADDED_WITH_RACK,
        EXCEPTION_THROWN
    }

    private NetworkTopologyImpl sut;
    private int numOfPreexistingNodes;
    private int numOfPreexistingRacks;

    private final ExpectedResult expectedResult;
    private final Node nodeToAdd;

    public NetworkTopologyImplPrepopulatedAddTest(ExpectedResult expectedResult, Node nodeToAdd) {
        this.expectedResult = expectedResult;
        this.nodeToAdd = nodeToAdd;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {ExpectedResult.EXCEPTION_THROWN, Common.buildNode("/rack-1","bookie-1")},
                {ExpectedResult.EXCEPTION_THROWN, new NodeBase("/dc-2")},
                {ExpectedResult.EXCEPTION_THROWN, new NodeBase("/dc-3/rack-2")},
                {ExpectedResult.EXCEPTION_THROWN, Common.buildNode("/region-1/dc-1/rack-1", "bookie-1")},
                {ExpectedResult.EXCEPTION_THROWN, Common.buildNode("/dc-1/rack-1/bookie-1", "another-bookie")},
                {ExpectedResult.NO_CHANGES_ALREADY_EXISTANT, Common.buildNode("/dc-1/rack-1", "bookie-1")},
                {ExpectedResult.NODE_ADDED, Common.buildNode("/dc-3/rack-4", "bookie-7")},
                {ExpectedResult.NODE_ADDED_WITH_RACK, Common.buildNode("/dc-3/rack-5", "bookie-8")},
                {ExpectedResult.NODE_ADDED_WITH_RACK, Common.buildNode("/dc-4/rack-6", "bookie-9")},
                {ExpectedResult.NODE_ADDED_WITH_RACK, Common.buildNode("/dc-4/rack-6", "bookie-7")},
        });
    }

    @Before
    public void populateTopology() {
        Node[] populatingNodes = new Node[] {
                Common.buildNode("/dc-1/rack-1", "bookie-1"),
                Common.buildNode("/dc-1/rack-1", "bookie-2"),
                Common.buildNode("/dc-2/rack-2", "bookie-3"),
                Common.buildNode("/dc-2/rack-2", "bookie-4"),
                Common.buildNode("/dc-3/rack-3", "bookie-5"),
                Common.buildNode("/dc-3/rack-4", "bookie-6")
        };

        sut = new NetworkTopologyImpl();
        for(Node node : populatingNodes) {
            sut.add(node);
        }

        numOfPreexistingNodes = 6;
        numOfPreexistingRacks = 4;
    }

    @Test
    public void testAddWhenNonEmptyTopology() {
        assertEquals(numOfPreexistingNodes, sut.getNumOfLeaves());
        assertEquals(numOfPreexistingRacks, sut.getNumOfRacks());

        if(expectedResult == ExpectedResult.EXCEPTION_THROWN) {
            assertThrows(NetworkTopologyImpl.InvalidTopologyException.class, () -> sut.add(nodeToAdd));
        } else {
            sut.add(nodeToAdd);
        }

        if(expectedResult == ExpectedResult.EXCEPTION_THROWN || expectedResult == ExpectedResult.NO_CHANGES_ALREADY_EXISTANT) {
            if(expectedResult == ExpectedResult.EXCEPTION_THROWN) {
                assertNotEquals(nodeToAdd, Common.getMyNode(sut, nodeToAdd));
            } else {
                checkNodeRetrievalOk(sut, nodeToAdd);
            }
            assertEquals(numOfPreexistingRacks, sut.getNumOfRacks());
            assertEquals(numOfPreexistingNodes, sut.getNumOfLeaves());
        } else {
            checkNodeRetrievalOk(sut, nodeToAdd);
            assertEquals(numOfPreexistingNodes + 1, sut.getNumOfLeaves());
            assertEquals(
                    numOfPreexistingRacks + (expectedResult == ExpectedResult.NODE_ADDED ? 0 : 1),
                    sut.getNumOfRacks());
        }
    }

    private static void checkNodeRetrievalOk(NetworkTopologyImpl sut, Node nodeToAdd) {
        Node justRetrievedNode = Common.getMyNode(sut, nodeToAdd);
        assertNotNull(justRetrievedNode);
        assertEquals(nodeToAdd, justRetrievedNode);
    }

}
