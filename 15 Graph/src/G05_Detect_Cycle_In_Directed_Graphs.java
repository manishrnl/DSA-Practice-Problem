import java.util.ArrayList;
import java.util.List;

/**
 * Detects whether a directed graph contains a cycle, using Depth-First Search
 * (DFS) with a recursion-stack marker.
 * <p>
 * Unlike the undirected case, in a directed graph re-visiting an already
 * visited vertex does <b>not</b> automatically mean a cycle — it might just be
 * a vertex reachable from two different paths that have already finished
 * being explored (a "cross edge" or "forward edge"), which is perfectly
 * normal in a DAG. A true cycle only exists when DFS reaches a vertex that is
 * still <b>on the current recursion stack</b> — i.e. an ancestor of the
 * current vertex in this specific DFS path — which is what
 * {@code recursiveStack} tracks.
 * </p>
 * <p>
 * The graph may be disconnected, so the outer loop in {@link #isCycle} starts
 * a fresh DFS from every unvisited vertex to make sure every component gets
 * checked.
 * </p>
 */
public class G05_Detect_Cycle_In_Directed_Graphs {

    /**
     * Holds the vertex that closed the detected cycle (i.e. the ancestor vertex
     * that a back-edge pointed to) after a successful {@link #isCycle} call.
     * Cleared at the start of every {@link #isCycle} call. This vertex is
     * guaranteed to actually be part of the cycle, since it was still active on
     * the recursion stack when the back-edge to it was found.
     */
    public static List<Integer> answer = new ArrayList<>();

    /**
     * Determines whether the given directed graph contains at least one cycle,
     * checking every connected component.
     *
     * @param totalVertex   the total number of vertices in the graph
     * @param adjacencyList the graph represented as a directed adjacency list,
     *                      where {@code adjacencyList.get(v)} contains all
     *                      vertices that {@code v} has an outgoing edge to
     * @return {@code true} if any cycle exists anywhere in the graph;
     * {@code false} if the graph is a DAG (Directed Acyclic Graph). When
     * {@code true}, {@link #answer} holds the vertex that closed the cycle.
     */
    public static boolean isCycle(int totalVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[totalVertex];
        boolean[] recursiveStack = new boolean[totalVertex];
        answer.clear();

        for (int i = 0; i < totalVertex; i++) {
            if (!isVisited[i]) {
                if (dfs(i, adjacencyList, isVisited, recursiveStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recursive DFS helper that explores from {@code currentVertex}, looking
     * for a directed cycle within its reachable component.
     * <p>
     * For each outgoing neighbour: if it hasn't been visited yet, recurse into
     * it. If it has been visited <b>and</b> is still marked on
     * {@code recursiveStack} (meaning it's an ancestor on the current DFS
     * path), a back-edge exists and that neighbour is recorded in
     * {@link #answer} as a confirmed member of the cycle. If it has been
     * visited but is <b>not</b> on the recursion stack, it was already fully
     * explored via some other path — that's fine in a directed graph and is
     * not a cycle.
     * </p>
     * <p>
     * {@code recursiveStack[currentVertex]} is set back to {@code false}
     * before returning without a cycle, since {@code currentVertex} is no
     * longer an active ancestor once this call finishes — this is what lets
     * later, unrelated paths revisit it safely without a false positive.
     * </p>
     *
     * @param currentVertex  the vertex currently being visited
     * @param adjacencyList  the graph represented as a directed adjacency list
     * @param isVisited      tracks which vertices have been visited across the
     *                       whole search (shared across components)
     * @param recursiveStack tracks which vertices are ancestors on the
     *                       <em>current</em> DFS path; a vertex is only marked
     *                       here while it (and its descendants) are actively
     *                       being explored
     * @return {@code true} if a cycle is found anywhere in this vertex's
     * reachable component; {@code false} otherwise
     */
    public static boolean dfs(int currentVertex, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] isVisited, boolean[] recursiveStack) {
        isVisited[currentVertex] = true;
        recursiveStack[currentVertex] = true;

        for (int i = 0; i < adjacencyList.get(currentVertex).size(); i++) {
            int neighbor = adjacencyList.get(currentVertex).get(i);

            if (!isVisited[neighbor]) {
                if (dfs(neighbor, adjacencyList, isVisited, recursiveStack))
                    return true;
            } else if (recursiveStack[neighbor]) {
                answer.add(neighbor); // neighbor is a confirmed cycle member — still an active ancestor
                return true;
            }
        }
        recursiveStack[currentVertex] = false;
        return false;
    }

    /**
     * Adds a directed edge from {@code node1} to {@code node2}.
     *
     * @param adjacencyList the adjacency list representing the graph;
     *                      {@code node1} must already have an initialized
     *                      (possibly empty) list at its index
     * @param node1         the source vertex
     * @param node2         the destination vertex
     */
    public static void populateGraph(ArrayList<ArrayList<Integer>> adjacencyList, int node1, int node2) {
        adjacencyList.get(node1).add(node2);
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        int totalVertices = 7;
        for (int i = 0; i < totalVertices; i++)
            adjacencyList.add(new ArrayList<>());

        populateGraph(adjacencyList, 0, 1);
        populateGraph(adjacencyList, 0, 2);
        populateGraph(adjacencyList, 1, 2);
        populateGraph(adjacencyList, 1, 3);
        populateGraph(adjacencyList, 4, 5);
        populateGraph(adjacencyList, 5, 6);
        populateGraph(adjacencyList, 6, 4);

        System.out.println(isCycle(totalVertices, adjacencyList)
                ? "Yes, we detect a cycle in your graph at node : " + answer
                : "No");

        // Second example: cycle vertex is NOT the DFS start vertex.
        // 0 -> 1 -> 2 -> 3 -> 1 : the cycle is 1-2-3, vertex 0 merely feeds into it.
        // The old buggy version would have reported [0]; this version correctly reports [1].
        ArrayList<ArrayList<Integer>> secondGraph = new ArrayList<>();
        int secondTotalVertices = 4;
        for (int i = 0; i < secondTotalVertices; i++)
            secondGraph.add(new ArrayList<>());

        populateGraph(secondGraph, 0, 1);
        populateGraph(secondGraph, 1, 2);
        populateGraph(secondGraph, 2, 3);
        populateGraph(secondGraph, 3, 1);

        System.out.println(isCycle(secondTotalVertices, secondGraph)
                ? "Yes, we detect a cycle in your graph at node : " + answer
                : "No");
    }
}