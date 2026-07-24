import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * <b>Problem:</b> Given a weighted, undirected, connected graph, find the
 * total edge weight of its Minimum Spanning Tree (MST) — the cheapest set of
 * edges that connects all vertices with no cycles.
 * <p>
 * <b>Approach — Prim's algorithm:</b> grow one tree from vertex {@code 0},
 * repeatedly pulling in the cheapest edge that connects the tree to a new
 * vertex. A min-heap ({@link PriorityQueue}, ordered by weight via
 * {@link Pair}) always surfaces that cheapest edge next in O(log E). Instead
 * of a decrease-key heap, stale entries for already-visited vertices are left
 * in the queue and simply skipped when popped ("lazy deletion") — simpler to
 * implement, at the cost of the queue holding up to O(E) entries instead of
 * O(V). Overall: O(E log E) time.
 * </p>
 * <p>
 * <b>Representation:</b> {@code adjacencyList.get(u)} is a list of
 * {@code [neighbourVertex, edgeWeight]} pairs. 0-indexed vertices
 * ({@code 0} to {@code numberOfVertex - 1}); the graph must be connected,
 * since the algorithm always starts at vertex {@code 0} and never checks for
 * leftover unvisited vertices.
 * </p>
 */
public class G08_Min_Spanning_Tree {

    /**
     * (vertex, weight) queue entry, ordered by weight so the priority queue
     * behaves as a min-heap on edge cost.
     */
    public static class Pair implements Comparable<Pair> {
        int vertex;
        int weight;

        public Pair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair that) {
            return this.weight - that.weight;
        }
    }

    /**
     * Runs Prim's algorithm and returns the MST's total weight.
     *
     * @param adjacencyList  the graph; see class doc for representation
     * @param numberOfVertex total vertices, {@code 0} to
     *                       {@code numberOfVertex - 1}
     * @return total MST weight reachable from vertex {@code 0}
     */
    public static int spanningTree(ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList, int numberOfVertex) {
        boolean[] isVisited = new boolean[numberOfVertex];
//        Since we are using Min-Heap Priority Queue, it sorts nodes (ascending order) and keeps the smallest node which has least weight at top
//        Thus while popping out data from queue, we will always get smaller node whose weight is small
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        queue.add(new Pair(0, 0));
        int answer = 0;

        while (!queue.isEmpty()) {
            Pair curr = queue.remove();
            int u = curr.vertex;
            if (isVisited[u]) {
                continue; // stale entry — u was already added to the tree via a cheaper edge
            }
            answer += curr.weight;
            isVisited[u] = true;

            for (ArrayList<Integer> edge : adjacencyList.get(u)) {
                int vertex = edge.get(0);
                int weight = edge.get(1);

                if (!isVisited[vertex]) {
                    queue.add(new Pair(vertex, weight));
                }
            }
        }

        return answer;
    }

    /**
     * Adds an undirected, weighted edge by recording it in both vertices'
     * neighbour lists as {@code [otherVertex, weight]} pairs.
     * <p>
     * A direct write like {@code adjacencyList.get(node1).get(node2).add(weight)}
     * won't work here — {@code get(node2)} would fail with an out-of-bounds
     * exception, since each vertex's neighbour list only grows as edges are
     * added, and Java generics don't allow arbitrary index assignment into an
     * {@code ArrayList}. So the {@code [neighbour, weight]} pair is built
     * separately first, then appended.
     * </p>
     *
     * @param adjacencyList the adjacency list; both vertices must already
     *                      have an initialized (possibly empty) list
     * @param node1         first vertex
     * @param node2         second vertex
     * @param weight        edge weight between them
     */
    public static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList, int node1, int node2, int weight) {
        ArrayList<Integer> edgeToNode2 = new ArrayList<>();
        edgeToNode2.add(node2);
        edgeToNode2.add(weight);
        adjacencyList.get(node1).add(edgeToNode2);

        ArrayList<Integer> edgeToNode1 = new ArrayList<>();
        edgeToNode1.add(node1);
        edgeToNode1.add(weight);
        adjacencyList.get(node2).add(edgeToNode1);
    }

    /**
     * Sample graph — MST keeps edges 0-1, 1-2, 3-0 (total weight 7), dropping
     * the costliest edge 2-3 (weight 5):
     * <pre>
     *     0 --1-- 1
     *     |       |
     *     4       2
     *     |       |
     *     3 --5-- 2
     * </pre>
     */
    public static void main(String[] args) {
        int numberOfVertex = 4;
        ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertex; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        addEdge(adjacencyList, 0, 1, 1);
        addEdge(adjacencyList, 1, 2, 2);
        addEdge(adjacencyList, 2, 3, 5);
        addEdge(adjacencyList, 3, 0, 4);

        int mstWeight = spanningTree(adjacencyList, numberOfVertex);
        System.out.println("Total weight of Minimum Spanning Tree -> " + mstWeight);
    }
}