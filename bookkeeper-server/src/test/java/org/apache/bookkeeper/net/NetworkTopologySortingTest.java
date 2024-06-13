package org.apache.bookkeeper.net;

import org.apache.bookkeeper.net.common.PopulatedNetworkTopology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NetworkTopologySortingTest extends PopulatedNetworkTopology {
    private final List<Distanced<NodeBase>> expectedSortedOrder = new ArrayList<>();
    private final NodeBase sourceNode;
    private final NodeBase[] nodesToBeSorted = new NodeBase[nodes.length];

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {
                        Arrays.asList(
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 8),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",10),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 8),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 8),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",8),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",0)),
                        "/europe/it/lombardia/milano/rack-1/bookie-5"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",8),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",10),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 2),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 4),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 0),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",6),
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12)),
                        "/europe/it/lazio/roma/rack-1/bookie-1"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 2),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 2),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 0),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",8),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",6),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",10)),
                        "/europe/it/lazio/roma/rack-2/bookie-3"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",10),
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 6),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 6),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 6),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",0),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",8)),
                        "/europe/it/lazio/frosinone/rack-1/bookie-4"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 1),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 0),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 2),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",6),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",8),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",10),
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12)),
                        "/europe/it/lazio/roma/rack-1/bookie-2"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 12),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 12),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 12),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",12),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",12),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",12),
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",0)),
                        "/america/us/california/paloalto/rack-2/bookie-7"
                },
                {
                        Arrays.asList(
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-1", 10),
                                new Distanced<>("/europe/it/lazio/roma/rack-1/bookie-2", 10),
                                new Distanced<>("/europe/it/lazio/roma/rack-2/bookie-3", 10),
                                new Distanced<>("/europe/it/lazio/frosinone/rack-1/bookie-4",10),
                                new Distanced<>("/europe/it/lombardia/milano/rack-1/bookie-5",10),
                                new Distanced<>("/europe/de/region-1/berlino/rack-1/bookie-6",0),
                                new Distanced<>("/america/us/california/paloalto/rack-2/bookie-7",12)),
                        "/europe/de/region-1/berlino/rack-1/bookie-6"
                },
        });
    }

    public NetworkTopologySortingTest(List<Distanced<String>> distancedPaths, String sourceNode) {
        this.sourceNode = (NodeBase) networkTopology.getNode(sourceNode);
        for (Distanced<String> distancedPath : distancedPaths) {
            this.expectedSortedOrder.add(
                    new Distanced<>((NodeBase)
                            networkTopology.getNode(distancedPath.data),
                            distancedPath.dist));
        }

        this.expectedSortedOrder.sort(Comparator.comparingInt(nA -> nA.dist));
        System.arraycopy(nodes, 0, nodesToBeSorted, 0, nodesToBeSorted.length);
    }

    @Test
    public void testSortingOrder() {
        networkTopology.pseudoSortByDistance(sourceNode, nodesToBeSorted);

        /*
        for(NodeBase node : nodesToBeSorted) {
            System.out.println(node);
        }

        System.out.println("======");
        */

        for(int i = 0; i < expectedSortedOrder.size(); ++i) {
            assertTrue(properSortedCheck(i));
        }
    }

    private boolean properSortedCheck(int index) {
        if(expectedSortedOrder.get(index).data == nodesToBeSorted[index]) {
            return true;
        }

        int distance = expectedSortedOrder.get(index).dist;
        int direction = 1;
        boolean alreadyChangedDirection = false;

        for(int j = index + 1;; j += direction) {
            if(j < 0) {
                return false;
            }

            if(j == expectedSortedOrder.size()) {
                alreadyChangedDirection = true;
                direction = -1;
                continue;
            }

            if(distance == expectedSortedOrder.get(j).dist && nodesToBeSorted[index] == expectedSortedOrder.get(j).data) {
                return true;
            }

            if(distance != expectedSortedOrder.get(j).dist) {
                if(alreadyChangedDirection) {
                    return false;
                }
                alreadyChangedDirection = true;
                direction = -1;
            }
        }
    }

    public static final class Distanced<T> {
        private final T data;
        private final int dist;

        public Distanced(T data, int dist) {
            this.data = data;
            this.dist = dist;
        }
    }
}