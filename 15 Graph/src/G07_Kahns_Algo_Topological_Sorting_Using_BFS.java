import java.util.*;

/**
 * Performs topological sorting on a directed acyclic graph (DAG) using Kahn's
 * algorithm — a BFS-based alternative to the DFS + stack approach.
 * <p>
 * Kahn's algorithm works by repeatedly removing vertices that have no
 * remaining incoming edges (in-degree 0): such a vertex has no unresolved
 * dependency, so it's safe to place next in the order. Removing it then
 * lowers the in-degree of everything it pointed to, which may free up new
 * vertices to process. This continues, level by level, using a queue —
 * exactly the same "process the frontier, then move to the next layer"
 * structure as ordinary BFS.
 * </p>
 * <p>
 * This class first checks the DAG precondition with DFS-based cycle detection
 * (as in {@code G06_Topological_Sorting}), then performs the sort itself with
 * Kahn's algorithm rather than the DFS post-order technique.
 * </p>
 * <p>
 * <b>Vertex numbering:</b> vertices are 1-indexed (labelled {@code 1} to
 * {@code numberOfVertex - 1}); index {@code 0} of every array in this class is
 * simply left unused.
 * </p>
 */
public class G07_Kahns_Algo_Topological_Sorting_Using_BFS {

    /**
     * Produces a topological ordering of the given directed graph using Kahn's
     * algorithm, assuming it has already been confirmed acyclic (e.g. via
     * {@link #isCyclePresent}).
     * <p>
     * First computes the in-degree (number of incoming edges) of every vertex
     * by scanning every edge in the adjacency list once. Then seeds a queue
     * with every vertex that already has in-degree {@code 0} — these have no
     * unresolved dependencies and can safely go first — and delegates the
     * actual level-by-level processing to {@link #bfs}.
     * </p>
     *
     * @param numberOfVertex one more than the highest valid vertex label (i.e.
     *                       valid vertices are {@code 1} to
     *                       {@code numberOfVertex - 1}; index {@code 0} is
     *                       unused)
     * @param adjacencyList  the graph represented as a directed adjacency list
     * @return an array holding a valid topological order of the graph's
     * vertices
     */
    private static int[] kahnsSortTopological(int numberOfVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        int[] inDegrees = new int[numberOfVertex];

        for (ArrayList<Integer> list : adjacencyList) {
            for (Integer e : list) {
                inDegrees[e]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < numberOfVertex; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
            }
        }

        return bfs(queue, adjacencyList, inDegrees);
    }

    /**
     * Repeatedly removes a zero-in-degree vertex from {@code queue}, appends
     * it to the result order, and decrements the in-degree of each of its
     * outgoing neighbours. Any neighbour whose in-degree drops to {@code 0} as
     * a result has just had its last unresolved dependency satisfied, so it's
     * enqueued to be processed next.
     * <p>
     * If the graph truly is acyclic, every vertex eventually gets enqueued and
     * removed this way. (If it weren't acyclic, some vertices involved in a
     * cycle would never reach in-degree {@code 0} and would simply be missing
     * from the result — which is one reason {@link #isCyclePresent} should
     * always be checked first.)
     * </p>
     *
     * @param queue         initially holds every vertex with in-degree
     *                      {@code 0}; used as the BFS frontier and mutated as
     *                      newly-freed vertices are discovered
     * @param adjacencyList the graph represented as a directed adjacency list
     * @param inDegrees     the current in-degree of each vertex; decremented
     *                      in place as edges are "removed"
     * @return an array holding the vertices in topological order
     */
    private static int[] bfs(Queue<Integer> queue, ArrayList<ArrayList<Integer>> adjacencyList, int[] inDegrees) {
        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            order.add(curr);

            for (int neighbour : adjacencyList.get(curr)) {
                if (--inDegrees[neighbour] == 0) {
                    queue.add(neighbour);
                }
            }
        }

        int[] result = new int[order.size()];
        for (int i = 0; i < order.size(); i++) {
            result[i] = order.get(i);
        }
        return result;
    }

    public static void addEdge(ArrayList<ArrayList<Integer>> adjacencyList, int node1, int node2) {
        adjacencyList.get(node1).add(node2);
    }

    public static List<Integer> answer = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        int numberOfVertex = 9; // valid vertex labels: 1 .. 8

        // Fill ALL numberOfVertex slots (0 .. numberOfVertex - 1), even though
        // index 0 goes unused, so every valid vertex label has a real slot to add() into.
        for (int i = 0; i < numberOfVertex; i++)
            adjacencyList.add(new ArrayList<>());

        addEdge(adjacencyList, 1, 2);
        addEdge(adjacencyList, 2, 3);
        addEdge(adjacencyList, 3, 4);
        addEdge(adjacencyList, 4, 5);
        addEdge(adjacencyList, 5, 6); // acyclic chain — no cycle here

        if (!isCyclePresent(numberOfVertex, adjacencyList)) {
            System.out.print("\nNo cycle found hence Calling kahnsSortTopological(..) method to sort graph using BFS Technique");
            System.out.println("\nPrinting Sorting order :" + Arrays.toString(kahnsSortTopological(numberOfVertex, adjacencyList)));

        } else {
            System.out.print("Cycle is present at node " + answer + ",  Can't call method kahnsSortTopological(..)");
        }
    }

    /**
     * Determines whether the given directed graph contains at least one cycle,
     * checking every connected component reachable from vertices
     * {@code 1 .. numberOfVertex - 1}.
     *
     * @param numberOfVertex one more than the highest valid vertex label (i.e.
     *                       valid vertices are {@code 1} to
     *                       {@code numberOfVertex - 1}; index {@code 0} is
     *                       unused)
     * @param adjacencyList  the graph represented as a directed adjacency
     *                       list, where {@code adjacencyList.get(v)} contains
     *                       all vertices that {@code v} has an outgoing edge
     *                       to
     * @return {@code true} if any cycle exists anywhere in the graph;
     * {@code false} if the graph is a DAG. When {@code true}, {@link #answer}
     * holds the vertex that closed the cycle.
     */
    public static boolean isCyclePresent(int numberOfVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[numberOfVertex];
        boolean[] recursiveStack = new boolean[numberOfVertex];
        answer.clear();

        for (int i = 1; i < numberOfVertex; i++) {
            if (!isVisited[i]) {
                if (dfs(i, adjacencyList, isVisited, recursiveStack))
                    return true;
            }
        }
        return false;
    }

    /**
     * Recursive DFS helper that explores from {@code currentVertex}, looking
     * for a directed cycle within its reachable component. A back-edge to a
     * vertex still on {@code recursiveStack} (an active ancestor on the
     * current DFS path) confirms a cycle, and both that ancestor and
     * {@code currentVertex} are genuine members of it.
     *
     * @param currentVertex  the vertex currently being visited
     * @param adjacencyList  the graph represented as a directed adjacency list
     * @param isVisited      tracks which vertices have been visited across the
     *                       whole search (shared across components)
     * @param recursiveStack tracks which vertices are ancestors on the
     *                       <em>current</em> DFS path
     * @return {@code true} if a cycle is found anywhere in this vertex's
     * reachable component; {@code false} otherwise
     */
    public static boolean dfs(int currentVertex, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] isVisited, boolean[] recursiveStack) {
        recursiveStack[currentVertex] = true;
        isVisited[currentVertex] = true;

        for (int i = 0; i < adjacencyList.get(currentVertex).size(); i++) {
            int neighbour = adjacencyList.get(currentVertex).get(i);

            if (!isVisited[neighbour]) {
                if (dfs(neighbour, adjacencyList, isVisited, recursiveStack))
                    return true;
            } else if (recursiveStack[neighbour]) {
                answer.add(currentVertex); // currentVertex closes the edge back to an active ancestor — a genuine cycle member
                return true;
            }
        }

        recursiveStack[currentVertex] = false;
        return false;
    }
}