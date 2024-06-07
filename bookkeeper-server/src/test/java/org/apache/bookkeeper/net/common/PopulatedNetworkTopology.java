package org.apache.bookkeeper.net.common;

import org.apache.bookkeeper.net.NetworkTopologyImpl;
import org.apache.bookkeeper.net.NodeBase;
import org.junit.BeforeClass;

public class PopulatedNetworkTopology {
    protected PopulatedNetworkTopology() {}

    protected static final NodeBase[] nodes = {
            new NodeBase("/europe/it/lazio/roma/rack-1/bookie-1"),
            new NodeBase("/europe/it/lazio/roma/rack-1/bookie-2"),
            new NodeBase("/europe/it/lazio/roma/rack-2/bookie-3"),
            new NodeBase("/europe/it/lazio/frosinone/rack-1/bookie-4"),
            new NodeBase("/europe/it/lombardia/milano/rack-1/bookie-5"),
            new NodeBase("/europe/de/region-1/berlino/rack-1/bookie-6"),
            new NodeBase("/america/us/california/paloalto/rack-2/bookie-7"),
    };

    protected static final NetworkTopologyImpl networkTopology = new NetworkTopologyImpl();

    @BeforeClass
    public static void populateNetworkTopology() {
        for(NodeBase node : nodes) {
            networkTopology.add(node);
        }
    }
}
