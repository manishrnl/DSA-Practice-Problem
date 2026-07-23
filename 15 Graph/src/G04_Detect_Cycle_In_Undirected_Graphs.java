import java.util.ArrayList;

/**
 * Detects whether an undirected graph contains a cycle, using Depth-First
 * Search (DFS).
 * <p>
 * The key idea: in an undirected graph, every edge you traverse into a
 * neighbour also creates a "back edge" from that neighbour to you (since edges
 * are bidirectional). So simply seeing an already-visited neighbour is not
 * enough to declare a cycle — that neighbour might just be the vertex you came
 * from. A true cycle exists only when DFS reaches an already-visited vertex
 * that is <b>not</b> the immediate parent in the current traversal.
 * </p>
 * <p>
 * The graph may be disconnected, so the outer loop in {@link #isCyclePresent}
 * starts a fresh DFS from every unvisited vertex to make sure every component
 * gets checked.
 * </p>
 */
public class G04_Detect_Cycle_In_Undirected_Graphs {

    /**
     * Determines whether the given undirected graph contains at least one
     * cycle, checking every connected component.
     *
     * @param totalVertex   the total number of vertices in the graph
     * @param adjacencyList the graph represented as an adjacency list, where
     *                      {@code adjacencyList.get(v)} contains all vertices
     *                      directly connected to vertex {@code v}
     * @return {@code true} if any cycle exists anywhere in the graph;
     * {@code false} if the graph (including all its components) is acyclic
     */
    public static boolean isCyclePresent(int totalVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[totalVertex];
        for (int i = 0; i < totalVertex; i++) {
            if (!isVisited[i]) {
                if (dfs(i, adjacencyList, isVisited, -1))
                    return true;
            }
        }
        return false;
    }

    /**
     * Recursive DFS helper that explores from {@code currentVertex}, looking
     * for a cycle within its connected component.
     * <p>
     * For each neighbour: if it hasn't been visited yet, recurse into it,
     * remembering {@code currentVertex} as its parentNode. If it has already been
     * visited, this only signals a cycle when that neighbour is <b>not</b> the
     * vertex we just came from — otherwise we'd be flagging the same edge we
     * used to arrive here as a false cycle.
     * </p>
     *
     * @param currentVertex the vertex currently being visited
     * @param adjacencyList the graph represented as an adjacency list
     * @param isVisited     tracks which vertices have been visited across the
     *                      whole search (shared across components, not reset
     *                      per component)
     * @param parentNode    the vertex we arrived from on this DFS path, or
     *                      {@code -1} if {@code currentVertex} is the root of
     *                      its DFS tree (has no parentNode yet)
     * @return {@code true} if a cycle is found anywhere in this vertex's
     * connected component; {@code false} otherwise
     */
    public static boolean dfs(int currentVertex, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] isVisited, int parentNode) {
        isVisited[currentVertex] = true;

        for (Integer neighbour : adjacencyList.get(currentVertex)) {
            if (!isVisited[neighbour]) {
                if (dfs(neighbour, adjacencyList, isVisited, currentVertex))
                    return true;
            }
//            If node is visited but parentNode != neighbor , means cycle found
            else if (parentNode != neighbour) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an undirected edge between two vertices in the graph by inserting
     * each vertex into the other's adjacency list.
     *
     * @param adjacencyList the adjacency list representing the graph; both
     *                      {@code node1} and {@code node2} must already have an
     *                      initialized (possibly empty) list at their index
     * @param node1         the first vertex
     * @param node2         the second vertex
     */
    private static void addEdge(ArrayList<ArrayList<Integer>> adjacencyList, int node1, int node2) {
        adjacencyList.get(node1).add(node2);
        adjacencyList.get(node2).add(node1);
    }

    /**
     * Builds an {@code NumberOfVertices}-vertex graph with no edges yet (an adjacency list of
     * {@code NumberOfVertices} empty lists), ready for {@link #addEdge} calls.
     *
     * @param NumberOfVertices number of vertices
     * @return a fresh adjacency list with {@code NumberOfVertices} empty neighbour lists
     */
    private static ArrayList<ArrayList<Integer>> newGraph(int NumberOfVertices) {
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < NumberOfVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    /**
     * Demonstrates {@link #isCyclePresent} on two sample graphs: one that is a
     * pure tree (acyclic), and the same tree with one extra edge added back in
     * (which creates a cycle).
     *
     * @param args not used
     */
    public static void main(String[] args) {
        /**    Acyclic graph: a tree on 6 vertices (0-5)
         *              0
         *            /   \
         *           1     2
         *          / \
         *         3   4
         *        /
         *       5
         */
        ArrayList<ArrayList<Integer>> acyclicGraph = newGraph(6);
        addEdge(acyclicGraph, 0, 1);
        addEdge(acyclicGraph, 0, 2);
        addEdge(acyclicGraph, 1, 3);
        addEdge(acyclicGraph, 1, 4);
        addEdge(acyclicGraph, 3, 5);

        System.out.println("Acyclic graph has cycle? -> " + isCyclePresent(6, acyclicGraph));

        // Cyclic graph: same tree, plus one extra edge (4-5) closing a loop
        // 0-1-3-5-4-1 forms a cycle
        ArrayList<ArrayList<Integer>> cyclicGraph = newGraph(6);
        addEdge(cyclicGraph, 0, 1);
        addEdge(cyclicGraph, 0, 2);
        addEdge(cyclicGraph, 1, 3);
        addEdge(cyclicGraph, 1, 4);
        addEdge(cyclicGraph, 3, 5);
        addEdge(cyclicGraph, 4, 5); // extra edge creates the cycle

        System.out.println("Cyclic graph has cycle? -> " + isCyclePresent(6, cyclicGraph));
    }
}