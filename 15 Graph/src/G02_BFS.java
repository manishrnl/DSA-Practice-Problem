import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements Breadth-First Search (BFS) on an unweighted, undirected graph to find
 * the shortest paths and reachability between vertices.
 * <p>
 * BFS explores the graph level by level starting from a source vertex, which
 * guarantees that the first time a vertex is reached, it has been reached via a
 * shortest path (in terms of number of edges) from the source. This class tracks
 * parent pointers (predecessors) and edge distances from a source node, making it
 * suitable for reconstructing the shortest paths in unweighted graphs.
 * </p>
 */
public class G02_BFS {

    /**
     * Performs Breadth-First Search from a source vertex to determine whether a
     * destination vertex is reachable, while simultaneously calculating shortest
     * edge distances and recording predecessor pointers for path reconstruction.
     * <p>
     * The search terminates early as soon as {@code destination} is discovered, rather
     * than exhausting the entire graph, since BFS guarantees the destination is
     * found via a shortest path the first time it is visited.
     * </p>
     *
     * @param adjacencyList    the graph represented as an adjacency list, where
     *                         {@code adjacencyList.get(v)} contains all vertices
     *                         directly connected to vertex {@code v}
     * @param source           the starting (source) vertex index
     * @param destination      the target (destination) vertex index
     * @param numberOfVertices the total number of vertices in the graph
     * @param predecessor      output array; after the call, {@code predecessor[v]} holds
     *                         the vertex visited immediately before {@code v} on the
     *                         shortest path from {@code source}, or {@code -1} if {@code v}
     *                         is unreached or is {@code source} itself
     * @param distance         output array; after the call, {@code distance[v]} holds the
     *                         minimum number of edges from {@code source} to {@code v}, or
     *                         {@code Integer.MAX_VALUE} if {@code v} is unreachable
     * @return {@code true} if {@code destination} is reachable from {@code source};
     * {@code false} otherwise
     */
    private static boolean bfs(ArrayList<ArrayList<Integer>> adjacencyList, int source, int destination, int numberOfVertices, int[] predecessor, int[] distance) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[numberOfVertices];

        // Initialize node properties
        for (int i = 0; i < numberOfVertices; i++) {
            isVisited[i] = false;
            distance[i] = Integer.MAX_VALUE;
            predecessor[i] = -1;
        }

        isVisited[source] = true;
        distance[source] = 0;
        queue.add(source);

        while (!queue.isEmpty()) {
            int currentVertex = queue.remove();

            for (int i = 0; i < adjacencyList.get(currentVertex).size(); i++) {
                int neighbour = adjacencyList.get(currentVertex).get(i);

                if (!isVisited[neighbour]) {
                    isVisited[neighbour] = true;
                    distance[neighbour] = distance[currentVertex] + 1;
                    predecessor[neighbour] = currentVertex;
                    queue.add(neighbour);

                    // Return early as soon as the destination is discovered
                    if (neighbour == destination) {
                        return true;
                    }
                }
            }
        }
        return false;
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
//        Adding Bi-Directional Edges
        adjacencyList.get(node1).add(node2);
        adjacencyList.get(node2).add(node1);
    }

    /**
     * <pre>Sample graph used in main() — 10 vertices (0-9) connected as a binary tree,
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
     *   0-1, 0-2, 1-3, 1-4, 2-5, 2-6, 3-7, 3-8, 4-9  </pre>
     */
    public static void main(String[] args) {
        int numberOfVertices = 10; // Number of vertices (0 to 9)
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();

        // Initialize each vertex's adjacency list as an empty ArrayList.Without this, adjacencyList.get(i) would
        // throw IndexOutOfBoundsException in addEdge(), since the outer ArrayList starts with zero elements.
        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        // Add sample edges (see tree diagram above)
        addEdge(adjacencyList, 0, 1);
        addEdge(adjacencyList, 0, 2);
        addEdge(adjacencyList, 1, 3);
        addEdge(adjacencyList, 1, 4);
        addEdge(adjacencyList, 2, 5);
        addEdge(adjacencyList, 2, 6);
        addEdge(adjacencyList, 3, 7);
        addEdge(adjacencyList, 3, 8);
        addEdge(adjacencyList, 4, 9);

        int[] predecessor = new int[numberOfVertices];
        int[] distance = new int[numberOfVertices];

        int source = 0;
        int destination = 9;

        boolean isReached = bfs(adjacencyList, source, destination, numberOfVertices, predecessor, distance);

        System.out.print("Is vertex " + destination + " reachable from " + source + " ? : " + isReached);
        if (isReached) {
            System.out.println("Shortest distance from vertex " + source + " to " + destination + " is -> " + distance[destination]);
        }
    }
}