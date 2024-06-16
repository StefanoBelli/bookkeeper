package org.apache.bookkeeper.net;

import org.apache.bookkeeper.net.factory.NetworkTopologyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

@RunWith(Parameterized.class)
public class NetworkTopologySingleNodeAddTest {
    private final String bookieName;
    private final String location;

    public NetworkTopologySingleNodeAddTest(String bookieName, String location) {
        this.bookieName = bookieName;
        this.location = location;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {}
        });
    }

    @Test
    public void testSingleNodeAdd() {
        NetworkTopologyFactory networkTopologyFactory = NetworkTopologyFactory.defaultFactory();
        NetworkTopology networkTopology = networkTopologyFactory.createNetworkTopology();
        Node node = networkTopologyFactory.createNode(bookieName, location);

        networkTopology.add(node);
    }
}