package org.apache.bookkeeper.net;

import org.junit.BeforeClass;

public final class Common {
    private Common() {}

    public static class PopulatedNetworkTopology {
        protected PopulatedNetworkTopology() {}

        protected static final Node[] nodes = {
                buildNode("/europe/it/lazio/roma/rack-1","bookie-1"),
                buildNode("/europe/it/lazio/roma/rack-1","bookie-2"),
                buildNode("/europe/it/lazio/roma/rack-2","bookie-3"),
                buildNode("/europe/it/lazio/frosinone/rack-3","bookie-4"),
                buildNode("/europe/it/lombardia/milano/rack-4","bookie-5"),
                buildNode("/europe/de/region-1/berlino/rack-5","bookie-6"),
                buildNode("/america/us/california/paloalto/rack-6","bookie-7"),
        };

        protected static final NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();

        @BeforeClass
        public static void populateNetworkTopology() {
            for(Node node : nodes) {
                networkTopology.add(node);
            }
        }
    }

    public static Node buildNode(String netLoc, String bookie) {
        return new BookieNode(BookieId.parse(bookie), netLoc);
    }

    public static Node getMyNode(NetworkTopologyImpl nti, Node n) {
        return nti.getNode(n.getNetworkLocation() + "/" + n.getName());
    }
}
