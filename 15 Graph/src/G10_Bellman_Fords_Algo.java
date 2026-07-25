import java.util.*;

/**
 * <b>Problem:</b> Given a weighted, directed graph (edges may have negative
 * weight) and a source vertex, find the shortest distance from the source to
 * every other vertex — or detect that no shortest distance exists because a
 * reachable negative-weight cycle lets you loop forever, driving the "shortest"
 * path to negative infinity.
 * <p>
 * <b>Approach — Bellman-Ford:</b> unlike Dijkstra, this doesn't rely on
 * greedily finalizing the closest vertex, so it tolerates negative weights.
 * Instead, it simply relaxes <em>every</em> edge, {@code numberOfVertex - 1}
 * times over. Why that many: the longest possible shortest path (without
 * cycles) uses at most {@code numberOfVertex - 1} edges, and each full pass
 * over all edges guarantees at least one more correct edge gets "locked in"
 * to the shortest-path tree, so {@code numberOfVertex - 1} passes are always
 * enough for a cycle-free graph. A {@code numberOfVertex}-th pass is then run
 * purely as a check: if any edge can still be relaxed after that, some
 * negative cycle must be feeding it — a valid shortest path is impossible.
 * O(V &middot; E) time.
 * </p>
 * <p>
 * <b>Representation:</b> {@code edges} is a flat edge list, where each
 * {@code edges[i]} is {@code [source, destination, weight]}. This is simpler
 * than an adjacency list here since Bellman-Ford relaxes every edge every
 * pass regardless of vertex — it never needs to look up "this vertex's
 * neighbours" specifically.
 * </p>
 */
public class G10_Bellman_Fords_Algo {

    /**
     * Runs Bellman-Ford from {@code sourceVertex}.
     *
     * @param edges          flat edge list; each entry is
     *                       {@code [source, destination, weight]}
     * @param numberOfVertex total vertices, {@code 0} to
     *                       {@code numberOfVertex - 1}
     * @param sourceVertex   the vertex to compute distances from
     * @return array where index {@code v} holds the shortest distance from
     * {@code sourceVertex} to {@code v} ({@code Integer.MAX_VALUE} if
     * unreachable), or {@code null} if the graph contains a negative-weight
     * cycle reachable from the source, making shortest distances undefined
     */
    public static int[] bellmanFord(int[][] edges, int numberOfVertex, int sourceVertex) {
        int[] distance = new int[numberOfVertex];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[sourceVertex] = 0;

        // Relax every edge, numberOfVertex - 1 times over
        for (int i = 0; i < numberOfVertex - 1; i++) {
            for (int[] edge : edges) {
                int source = edge[0];
                int destination = edge[1];
                int weight = edge[2];

                if (distance[source] != Integer.MAX_VALUE && distance[source] + weight < distance[destination]) {
                    distance[destination] = distance[source] + weight;
                }
            }
        }
//      Minimum distance is calculated. now checking if -ve cycle is present by iterating it one times only (One more full pass). If present then we cant get minimum distances
//      Since well get more min distances with each loop , thus this doesn't make sense, and we will simply return cant calculate min distances = false
        for (int[] edge : edges) {
            int source = edge[0];
            int destination = edge[1];
            int weight = edge[2];

            if (distance[source] != Integer.MAX_VALUE && distance[source] + weight < distance[destination]) {
                return null; // negative cycle detected — distances are not well-defined
            }
        }

        return distance;
    }

    /**
     * Appends a directed, weighted edge {@code [node1, node2, weight]} to the
     * edge list.
     *
     * @param edges  the growable edge list to append to
     * @param node1  source vertex
     * @param node2  destination vertex
     * @param weight edge weight (negative weights are allowed here, unlike
     *               Dijkstra)
     */
    public static void addEdge(List<int[]> edges, int node1, int node2, int weight) {
        edges.add(new int[]{node1, node2, weight});
    }

    /**
     * Demonstrates {@link #bellmanFord} on two graphs: one with a negative
     * edge but no negative cycle (distances computed successfully), and the
     * same graph with one extra edge added that closes a negative cycle
     * (returns {@code null}).
     * <pre>
     *        4         4
     *   0 -------&gt; 1 -------&gt; 3
     *   |          |
     *  5|        -3|
     *   v          v
     *   2 &lt;--------+
     * </pre>
     */
    public static void main(String[] args) {
        int numberOfVertex = 4;

        // --- Graph with a negative edge but no negative cycle ---
        List<int[]> edgeList = new ArrayList<>();
        addEdge(edgeList, 0, 1, 4);
        addEdge(edgeList, 0, 2, 5);
        addEdge(edgeList, 1, 2, -3);
        addEdge(edgeList, 1, 3, 4);
        int[][] edges = edgeList.toArray(new int[0][]);
        System.out.println("Calculating Shortest distance for Graph 1 : ");
        int[] distances = bellmanFord(edges, numberOfVertex, 0);
        System.out.println(distances != null
                ? "Shortest distances from vertex 0 -> " + Arrays.toString(distances)
                : "Negative cycle detected — distances undefined");

        // --- Same graph plus an edge closing a negative cycle: 1 -> 2 -> 1 sums to -3 + (-2) = -5 ---
        List<int[]> cyclicEdgeList = new ArrayList<>(edgeList);
        addEdge(cyclicEdgeList, 2, 1, -2);
        int[][] cyclicEdges = cyclicEdgeList.toArray(new int[0][]);

        System.out.println("\n\nCalculating Shortest distance for Graph 2 : ");
        int[] cyclicResult = bellmanFord(cyclicEdges, numberOfVertex, 0);
        System.out.println(cyclicResult != null
                ? "Shortest distances from vertex 0 -> " + Arrays.toString(cyclicResult)
                : "Negative cycle detected — distances undefined");
    }
}