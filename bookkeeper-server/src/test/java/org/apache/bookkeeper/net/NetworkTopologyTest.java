package org.apache.bookkeeper.net;

import org.apache.bookkeeper.net.NetworkTopology;
import org.apache.bookkeeper.net.NetworkTopologyImpl;
import org.apache.bookkeeper.net.NodeBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkTopologyTest {

    @Test
    public void testNetworkTopology() {
        NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();
        Node n = new NodeBase("/base");
        n.setNetworkLocation("/netloc");
        networkTopology.add(n);

        Node k = new NodeBase("/base1");
        k.setNetworkLocation("/netloc");
        networkTopology.add(k);

        Node child = new NodeBase("/base2");
        child.setNetworkLocation("/mynetloc");
        child.setParent(k);
        networkTopology.add(child);

        System.out.println(networkTopology.getNumOfLeaves());
        assertEquals(networkTopology.getNode("/netloc/base"), n);
        System.out.println(networkTopology.getNumOfRacks());
        System.out.println(networkTopology.getLeaves("/mynetloc"));
    }
}
