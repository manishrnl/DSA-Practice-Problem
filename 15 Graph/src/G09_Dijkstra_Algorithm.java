import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <b>Problem:</b> Given a weighted, directed graph and a source vertex, find
 * the shortest distance from the source to every other vertex.
 * <p>
 * <b>Approach — Dijkstra's algorithm:</b> repeatedly pull the
 * closest-not-yet-finalized vertex from a min-heap ({@link PriorityQueue},
 * ordered by distance via {@link Pair}), lock in its shortest distance, and
 * "relax" each outgoing edge — updating a neighbour's distance if reaching it
 * through the current vertex is cheaper than what's currently known. As with
 * {@code G08_Min_Spanning_Tree}, this uses lazy deletion: stale queue entries
 * for an already-finalized vertex are simply skipped rather than removed
 * in place. Requires non-negative edge weights. O(E log E) time.
 * </p>
 * <p>
 * <b>Representation:</b> {@code adjacencyList.get(u)} is a list of
 * {@code [neighbourVertex, edgeWeight]} pairs, one per outgoing edge from
 * {@code u}. 0-indexed vertices ({@code 0} to {@code numberOfVertex - 1}).
 * Vertices unreachable from the source keep their initial distance of
 * {@code Integer.MAX_VALUE}.
 * </p>
 */
public class G09_Dijkstra_Algorithm {

    /**
     * (vertex, distance) queue entry, ordered by distance so the priority
     * queue behaves as a min-heap on shortest-known distance.
     */
    public static class Pair implements Comparable<Pair> {
        int vertex;
        int weight;

        Pair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair that) {
            return this.weight - that.weight;
        }
    }

    /**
     * Runs Dijkstra's algorithm from {@code sourceNode} and returns the
     * shortest distance to every vertex.
     *
     * @param numberOfVertex total vertices, {@code 0} to
     *                       {@code numberOfVertex - 1}
     * @param sourceNode     the vertex to compute distances from
     * @param adjacencyList  the graph; see class doc for representation
     * @return array where index {@code v} holds the shortest distance from
     * {@code sourceNode} to {@code v}, or {@code Integer.MAX_VALUE} if
     * {@code v} is unreachable
     */
    public static int[] dijkstra(int numberOfVertex, int sourceNode, ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList) {
        boolean[] isVisited = new boolean[numberOfVertex];
        int[] answer = new int[numberOfVertex];
        Arrays.fill(answer, Integer.MAX_VALUE); // every vertex starts "unreachable" until relaxed
        answer[sourceNode] = 0;

        PriorityQueue<Pair> queue = new PriorityQueue<>();
        queue.add(new Pair(sourceNode, 0));

        while (!queue.isEmpty()) {
            Pair curr = queue.poll();
            int u = curr.vertex;

            if (isVisited[u]) {
                continue; // stale entry — u's shortest distance was already finalized
            }
            isVisited[u] = true;

            for (ArrayList<Integer> edge : adjacencyList.get(u)) {
                int vertex = edge.get(0);
                int weight = edge.get(1);

                if (!isVisited[vertex] && answer[vertex] > answer[u] + weight) {
                    answer[vertex] = answer[u] + weight;
                    queue.add(new Pair(vertex, answer[vertex]));
                }
            }
        }

        return answer;
    }

    /**
     * Adds a directed, weighted edge from {@code node1} to {@code node2}, as a
     * {@code [node2, weight]} pair in {@code node1}'s neighbour list.
     *
     * @param adjacencyList the adjacency list; {@code node1} must already
     *                      have an initialized (possibly empty) list
     * @param node1         source vertex
     * @param node2         destination vertex
     * @param weight        edge weight (must be non-negative for Dijkstra to
     *                      give correct results)
     */
    public static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList, int node1, int node2, int weight) {
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(node2);
        edge.add(weight);
        adjacencyList.get(node1).add(edge);
    }

    /**
     * Sample graph — shortest distances from vertex 0 are
     * {@code [0, 3, 1, 4]}: to vertex 1, going 0-&gt;2-&gt;1 (1+2=3) beats the
     * direct edge 0-&gt;1 (4); to vertex 3, going 0-&gt;2-&gt;1-&gt;3 (1+2+1=4)
     * beats 0-&gt;2-&gt;3 (1+5=6).
     * <pre>
     *      4
     *   0 ---&gt; 1
     *   |      ^ \
     *  1|     2|  1
     *   v      |   \v
     *   2 ----------&gt; 3
     *          5
     * </pre>
     */
    public static void main(String[] args) {
        ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList = new ArrayList<>();
        int numberOfVertex = 4,sourceNode=0;
        for (int i = 0; i < numberOfVertex; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        addEdge(adjacencyList, 0, 1, 4);
        addEdge(adjacencyList, 0, 2, 1);
        addEdge(adjacencyList, 2, 1, 2);
        addEdge(adjacencyList, 1, 3, 1);
        addEdge(adjacencyList, 2, 3, 5);

        int[] distances = dijkstra(numberOfVertex, sourceNode, adjacencyList);
        System.out.print("\nShortest distances from vertex "+sourceNode+ " is -> " + Arrays.toString(distances));
    }
}