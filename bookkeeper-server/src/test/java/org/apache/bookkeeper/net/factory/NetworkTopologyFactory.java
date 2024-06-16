package org.apache.bookkeeper.net.factory;

import org.apache.bookkeeper.net.NetworkTopology;
import org.apache.bookkeeper.net.Node;

public interface NetworkTopologyFactory {
    NetworkTopology createNetworkTopology();
    Node createNode(String bookieName, String networkLocation);

    static NetworkTopologyFactory defaultFactory() {
        return new DefaultNetworkTopologyFactory();
    }
}
