package org.apache.bookkeeper.net.factory;

import org.apache.bookkeeper.net.NetworkTopology;
import org.apache.bookkeeper.net.NetworkTopologyImpl;
import org.apache.bookkeeper.net.Node;
import org.apache.bookkeeper.net.BookieNode;
import org.apache.bookkeeper.net.BookieId;

public final class DefaultNetworkTopologyFactory implements NetworkTopologyFactory{

    @Override
    public NetworkTopology createNetworkTopology() {
        return new NetworkTopologyImpl();
    }

    @Override
    public Node createNode(String bookieName, String networkLocation) {
        return new BookieNode(BookieId.parse(bookieName), networkLocation);
    }
}
