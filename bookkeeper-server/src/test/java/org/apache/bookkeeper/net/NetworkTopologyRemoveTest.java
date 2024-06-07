package org.apache.bookkeeper.net;


import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class NetworkTopologyRemoveTest {

    @Test
    public void testNullNodeRemoval() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        networkTopology.remove(null);
        assertEquals(0, networkTopology.getNumOfLeaves());
    }

    @Ignore
    @Test
    public void testNodeWithNullPathRemoval() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        networkTopology.remove(new NodeBase(null));
        assertEquals(0, networkTopology.getNumOfLeaves());
    }

    @Test
    public void testExistantNodeRemoval() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        networkTopology.add(new NodeBase("/country/datacenter/rack/bookie"));
        networkTopology.remove(new NodeBase("/country/datacenter/rack/bookie"));

        assertEquals(0, networkTopology.getNumOfLeaves());
        assertNull(networkTopology.getNode("/country/datacenter/rack/bookie"));
    }

    @Test
    public void testNonExistantNodeRemoval() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        networkTopology.add(new NodeBase("/country/datacenter/rack/bookie"));
        networkTopology.remove(new NodeBase("/country/datacenter/rack/bookienode"));

        assertEquals(1, networkTopology.getNumOfLeaves());
        assertNotNull(networkTopology.getNode("/country/datacenter/rack/bookie"));
        assertNull(networkTopology.getNode("/country/datacenter/rack/bookienode"));
    }
}
