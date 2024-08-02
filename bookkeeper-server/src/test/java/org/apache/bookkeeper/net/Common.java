package org.apache.bookkeeper.net;

public final class Common {
    public static Node buildNode(String netLoc, String bookie) {
        return new BookieNode(BookieId.parse(bookie), netLoc);
    }

    public static Node getMyNode(NetworkTopologyImpl nti, Node n) {
        return nti.getNode(n.getNetworkLocation() + "/" + n.getName());
    }
}
