package org.apache.bookkeeper.net;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class NetworkTopologyPrepopulatedNodeAddTest {
    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {ExpectedTestResult.NODE_ADDED, "/country-1/dc-1/rack-1/node-2", "/country-1/dc-1/rack-1/node-1"},
                {ExpectedTestResult.NODE_ADDED, "/country-1/dc-1/rack-1/node-2", "/country-1/dc-1/rack-2/node-1"},
                {ExpectedTestResult.NODE_ADDED, "/country-1/dc-1/rack-1/node-2", "/country-1/dc-2/rack-1/node-1"},
                {ExpectedTestResult.NODE_ADDED, "/country-1/dc-1/rack-1/node-2", "/country-2/dc-1/rack-1/node-1"},
                {ExpectedTestResult.EXCEPTION_THROWN, "/dc-2/rack-1/node-1", "/dc-2/rack-1/node-1/bookie-1"},
                {ExpectedTestResult.EXCEPTION_THROWN, "/dc-1/rack-1/node-1", "/rack-1/node-1"},
                {ExpectedTestResult.EXCEPTION_THROWN, "/dc-1/rack-1/node-1", "/dc-1/rack-1/subrack-2/node-1"},
                //{ExpectedTestResult.EXCEPTION_THROWN, "/europe/it/lazio/dc-rome/rack-1/node-1", "/europe/it/lazio/dc-rome/rack-1/node-1"}
        });
    }

    private final ExpectedTestResult expectedTestResult;
    private final String preExistantNodePath;
    private final String nodePathToAdd;

    private NetworkTopologyImpl networkTopology;

    public NetworkTopologyPrepopulatedNodeAddTest(
            ExpectedTestResult expectedTestResult, String preExistantNodePath, String nodePathToAdd) {

        this.expectedTestResult = expectedTestResult;
        this.preExistantNodePath = preExistantNodePath;
        this.nodePathToAdd = nodePathToAdd;
    }

    @Before
    public void populateNetworkTopology() {
        networkTopology = new NetworkTopologyImpl();
        networkTopology.add(new NodeBase(preExistantNodePath));
    }

    @Test
    public void testNodeAdding() throws Throwable {
        NodeBase addedNode = new NodeBase(nodePathToAdd);
        ThrowingRunnable addingLambda = () -> networkTopology.add(addedNode);

        if(expectedTestResult == ExpectedTestResult.NODE_ADDED) {
            addingLambda.run();
            assertEquals(2, networkTopology.getNumOfLeaves());
            assertEquals(addedNode, networkTopology.getNode(nodePathToAdd));
        } else {
            assertThrows(NetworkTopologyImpl.InvalidTopologyException.class, addingLambda);
        }
    }
}

enum ExpectedTestResult {
    NODE_ADDED,
    EXCEPTION_THROWN
}
