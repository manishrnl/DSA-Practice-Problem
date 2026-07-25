import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**<h2>Problem Description: Minimum Spanning Tree (MST)</h2>
 * Given an undirected, weighted, connected graph with {@code V} vertices and {@code E} edges,
 * find a spanning tree (a subset of edges that connects all vertices without any cycles)
 * such that the total sum of the edge weights is minimized.
 *
 * <h2>Approach: Kruskal's Algorithm with Disjoint Set Union (DSU)</h2>
 * Kruskal's is a greedy algorithm that builds the MST step-by-step:
 * <ol>
 *   <li><b>Edge Extraction & Sorting:</b> Extract all edges from the graph's adjacency list representation
 *       and sort them in ascending order by weight.</li>
 *   <li><b>Greedy Selection:</b> Iterate through the sorted edges and pick the smallest weight edge.</li>
 *   <li><b>Cycle Prevention via DSU:</b> Use a <i>Disjoint Set Union (Union-Find)</i> data structure to check
 *       if adding the selected edge forms a cycle (i.e., both endpoints belong to the same connected component):
 *       <ul>
 *         <li><b>Path Compression:</b> Flattens the DSU tree during {@code find()} calls, optimizing lookup times.</li>
 *         <li><b>Union by Rank:</b> Attaches shorter component trees under taller ones during {@code union()} calls to keep trees balanced.</li>
 *       </ul>
 *   </li>
 *   <li><b>Termination:</b> Repeat until {@code V - 1} edges are included in the MST or all edges are processed.</li>
 * </ol>
 *
 * <h3>Complexity Analysis:</h3>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(E log E + E * α(V))}, which simplifies to {@code O(E log E)} or {@code O(E log V)}.
 *       Sorting the edges takes {@code O(E log E)} time, while DSU operations take near-constant time {@code O(α(V))} per operation, where {@code α} is the Inverse Ackermann function.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} to store edge lists, adjacency data, and DSU parent/rank tracking arrays.</li>
 * </ul>
 */
public class T02_Kruskal_Algorithm {

    public static class Edge implements Comparable<Edge> {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge that) {
            return Integer.compare(this.weight, that.weight);
        }
    }

    static int[] parent;
    static int[] rank;

    public T02_Kruskal_Algorithm(int numberOfVertex) {
        parent = new int[numberOfVertex];
        rank = new int[numberOfVertex];
        Arrays.fill(rank, 0);

        for (int i = 0; i < numberOfVertex; i++) {
            parent[i] = i;
        }
    }

    /**
     * Finds the representative (root) of the set containing {@code vertex} with and link each vertex directly to parent node so that traversal took O(1) time to traverse.
     */
    public static int find(int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent[vertex]); // Path compression
        }
        return parent[vertex];
    }

    /**
     * Merges two disjoint sets using Union by Rank.
     */
    public static void union(int vertex1, int vertex2) {
        int xRoot = find(vertex1);
        int yRoot = find(vertex2);

        if (xRoot == yRoot) return;

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[yRoot] < rank[xRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[xRoot] = yRoot;
            rank[yRoot]++;
        }
    }

    public static int kruskalMinSpanningTree(
            int numberOfVertex,
            ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList) {

        // Step 1: Extract unique edges
        boolean[][] isAdded = new boolean[numberOfVertex][numberOfVertex];
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < adjacencyList.size(); i++) {
            for (int j = 0; j < adjacencyList.get(i).size(); j++) {
                ArrayList<Integer> curr = adjacencyList.get(i).get(j);
                int dest = curr.get(0);
                int weight = curr.get(1);

                if (!isAdded[i][dest]) {
                    isAdded[i][dest] = true;
                    isAdded[dest][i] = true;
                    edges.add(new Edge(i, dest, weight));
                }
            }
        }

        // Step 2: Sort edges by weight
        Collections.sort(edges);

        // Step 3: Greedy selection
        int count = 1;
        int answer = 0;

        for (int i = 0; i < edges.size() && count < numberOfVertex; i++) {
            Edge edge = edges.get(i);
            int xRepresentative = find(edge.source);
            int yRepresentative = find(edge.destination);

            if (xRepresentative != yRepresentative) {
                union(xRepresentative, yRepresentative);
                count++;
                answer += edge.weight;
            }
        }

        return answer;
    }

    private static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adj, int u, int v, int w) {
        adj.get(u).add(new ArrayList<>(Arrays.asList(v, w)));
        adj.get(v).add(new ArrayList<>(Arrays.asList(u, w)));
    }
    /**
     * <pre>Graph Topology:
     *
     *       (10)
     *    0 -------- 1
     *    |  \       |
     * (6)|    \(5)  |(15)
     *    |      \   |
     *    2 ------- 3
     *       (4)
     *
     * Vertices V = 4 ,  Edges E = 5 ,  Expected MST Edges: (2-3: 4), (0-3: 5), (0-1: 10) -> Total Weight = 19   >/pre>
     */
    public static void main(String[] args) {


        int numberOfVertex = 4;

        // Initialize empty adjacency list structure
        ArrayList<ArrayList<ArrayList<Integer>>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertex; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Add edges: (u, v, weight)
        addEdge(adjacencyList, 0, 1, 10);
        addEdge(adjacencyList, 0, 2, 6);
        addEdge(adjacencyList, 0, 3, 5);
        addEdge(adjacencyList, 1, 3, 15);
        addEdge(adjacencyList, 2, 3, 4);

        // Initialize DSU structures for the given vertex count
        new T02_Kruskal_Algorithm(numberOfVertex);

        // Execute Kruskal's algorithm
        int mstWeight = kruskalMinSpanningTree(numberOfVertex, adjacencyList);


        System.out.println("Total Vertices : " + numberOfVertex);
        System.out.println("MST Total Weight: " + mstWeight);
        System.out.println("=========================================");
    }
}