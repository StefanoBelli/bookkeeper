package org.apache.bookkeeper.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public final class NetworkTopologyImplRemoveTest {

    public enum ExpectedResult {
        NO_ADD_SO_NO_CHANGES,
       PREVIOUSLY_ADDED_NODE_REMOVED
    }

    private final ExpectedResult expectedResult;
    private final Node node;

    public NetworkTopologyImplRemoveTest(ExpectedResult expectedResult, Node node) {
        this.expectedResult = expectedResult;
        this.node = node;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {ExpectedResult.NO_ADD_SO_NO_CHANGES, null},
                {ExpectedResult.PREVIOUSLY_ADDED_NODE_REMOVED, Common.buildNode("/rack-1","bookie-1")},
                {ExpectedResult.NO_ADD_SO_NO_CHANGES, Common.buildNode("/rack-1", "bookie-2")}
        });
    }

    @Test
    public void testRemove() {
        NetworkTopologyImpl sut = new NetworkTopologyImpl();

        if(expectedResult == ExpectedResult.NO_ADD_SO_NO_CHANGES) {
            noChangesChecks(sut, node);
            sut.remove(node);
            noChangesChecks(sut, node);
        } else {
            sut.add(node);
            preChangesChecks(sut, node);
            sut.remove(node);
            postChangesChecks(sut, node);
        }
    }

    private static void baseCheck(int expected, NetworkTopologyImpl sut, Runnable nodeCheck) {
        assertEquals(expected, sut.getNumOfLeaves());
        assertEquals(expected, sut.getNumOfRacks());
        nodeCheck.run();
    }

    private static void baseEmptyChecks(NetworkTopologyImpl sut, Node node) {
        baseCheck(0, sut, () -> assertNull(node != null ? Common.getMyNode(sut, node) : null));
    }

    private static void preChangesChecks(NetworkTopologyImpl sut, Node node) {
        baseCheck(1, sut, () -> assertEquals(node, Common.getMyNode(sut,node)));
    }

    private static void postChangesChecks(NetworkTopologyImpl sut, Node node) {
        baseEmptyChecks(sut, node);
    }

    private static void noChangesChecks(NetworkTopologyImpl sut, Node node) {
        baseEmptyChecks(sut, node);
    }

}
