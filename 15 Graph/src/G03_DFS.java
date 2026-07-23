import java.util.ArrayList;

/**
 * Implements Depth-First Search (DFS) on an unweighted graph using recursion.
 * <p>
 * DFS explores as far as possible along each branch before backtracking. Starting
 * from a fixed vertex, it visits a vertex, then recursively visits an unvisited
 * neighbor, repeating until no unvisited neighbor remains, at which point it
 * backtracks to the previous vertex and tries the next unvisited neighbor there.
 * </p>
 * <p>
 * Note: this implementation always starts the traversal from vertex 0. If the
 * graph is disconnected, vertices unreachable from vertex 0 will not appear in
 * the result.
 * </p>
 */
public class G03_DFS {
    static int totalDisconnectedGraph = 0;

    /**
     * Performs a full Depth-First traversal of the graph starting from vertex 0
     * and returns the vertices in the order they were first visited.
     *
     * @param numberOfVertex the total number of vertices in the graph
     * @param adjacencyList  the graph represented as an adjacency list, where
     *                       {@code adjacencyList.get(v)} contains all vertices
     *                       directly connected to vertex {@code v}
     * @return a list of vertex indices in DFS visit order, starting from vertex 0
     */
    public static ArrayList<Integer> dfsOfGraph(int numberOfVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[numberOfVertex];
        ArrayList<Integer> ans = new ArrayList<>();

//      This will run for all vertex whether connected or not connected

        for (int i = 0; i < numberOfVertex; i++) {

            if (!isVisited[i]) {
                dfs(i, adjacencyList, isVisited, ans);
                System.out.println("Graph Disconnects at node : " + i);
                totalDisconnectedGraph++;
            }

        }
        return ans;
    }

    /**
     * Recursive helper that visits a single vertex, records it, and then recurses
     * into each of its unvisited neighbours in the order they appear in the
     * adjacency list.
     *
     * @param currentVertex the vertex currently being visited
     * @param adjacencyList the graph represented as an adjacency list
     * @param isVisited     tracks which vertices have already been visited, to
     *                      avoid revisiting a vertex and looping forever on cycles
     * @param ans           accumulator list that collects vertices in visit order;
     *                      modified in place by each recursive call
     */
    private static void dfs(int currentVertex, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] isVisited, ArrayList<Integer> ans) {
        isVisited[currentVertex] = true;
        ans.add(currentVertex);

        for (int neighbour : adjacencyList.get(currentVertex)) {

            if (!isVisited[neighbour]) {
                dfs(neighbour, adjacencyList, isVisited, ans);
            }

        }
    }

    /**
     * Adds an undirected edge between two vertices in the graph by inserting each
     * vertex into the other's adjacency list.
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

    /*
     * Sample graph used in main() - 10 vertices (0-9) connected as a binary tree,
     * rooted at vertex 0:
     *
     *                     0
     *                   /   \
     *                  1     2
     *                /   \   / \
     *               3     4 5   6
     *              / \     \
     *             7   8     9
     *
     * Edges (undirected):
     *   0-1, 0-2, 1-3, 1-4, 2-5, 2-6, 3-7, 3-8, 4-9
     *
     * Expected DFS order from vertex 0: 0, 1, 3, 7, 8, 4, 9, 2, 5, 6
     */
    public static void main(String[] args) {
        int numberOfVertices = 14;
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        addEdge(adjacencyList, 0, 1);
        addEdge(adjacencyList, 0, 2);
        addEdge(adjacencyList, 1, 3);
        addEdge(adjacencyList, 1, 4);
        addEdge(adjacencyList, 2, 5);
        addEdge(adjacencyList, 2, 6);
        addEdge(adjacencyList, 3, 7);
        addEdge(adjacencyList, 3, 8);
        addEdge(adjacencyList, 4, 9);

//        Adding disconnected Graphs for sample
        addEdge(adjacencyList, 10, 11);
        addEdge(adjacencyList, 12, 13);
//        Added total of 3 disconnected graphs at node 0,10,12


        ArrayList<Integer> dfsOrder = dfsOfGraph(numberOfVertices, adjacencyList);


        System.out.println("DFS traversal order starting from vertex 0 -> " + dfsOrder);
        System.out.println("Total Disconnected Graphs found is : " + totalDisconnectedGraph);
    }
}
