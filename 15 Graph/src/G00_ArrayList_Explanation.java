import java.util.ArrayList;

public class G00_ArrayList_Explanation {
    /**
     * <h2>1D — {@code ArrayList<Integer>}</h2>
     * <p>
     * A flat list of numbers, with no graph structure by itself. Used to
     * represent a <em>single</em> vertex's list of neighbour IDs — e.g. just
     * vertex {@code 0}'s neighbours, on their own, outside of any larger
     * per-vertex collection.
     * </p>
     * <pre>
     * adjacencyList
     *   ┌───┬───┐
     *   │ 1 │ 3 │
     *   └───┴───┘
     *     0   1     ← index within this list
     * </pre>
     * <p>
     * {@code adjacencyList.get(0)} → {@code 1} (an {@code int} directly).
     * </p>
     */
    public static ArrayList<Integer> oneDimensionalExample;

    /**
     * <h2>2D — {@code ArrayList<ArrayList<Integer>>}</h2>
     * <p>
     * A list indexed by <b>vertex</b>, where each slot holds that vertex's
     * own flat list of neighbours. This is the shape used in the unweighted,
     * undirected BFS/DFS files ({@code G02_BFS}, {@code G03_DFS},
     * {@code G04}/{@code G05_Detect_Cycle...}), where each edge is just "who
     * is connected to whom," with no extra data attached.
     * </p>
     * <pre>
     * adjacencyList
     * index 0 → [ 1, 3 ]        (vertex 0's neighbours: 1 and 3)
     * index 1 → [ 0, 2 ]        (vertex 1's neighbours: 0 and 2)
     * index 2 → [ 1, 3 ]        (vertex 2's neighbours: 1 and 3)
     * index 3 → [ 2, 0 ]        (vertex 3's neighbours: 2 and 0)
     * </pre>
     * <p>
     * {@code adjacencyList.get(0)} → the whole inner list {@code [1, 3]} (an
     * {@code ArrayList<Integer>}).<br>
     * {@code adjacencyList.get(0).get(1)} → {@code 3} (now drilled down to an
     * {@code int}).
     * </p>
     */
    public static ArrayList<ArrayList<Integer>> twoDimensionalExample;

    /**
     * <h2>3D — {@code ArrayList<ArrayList<ArrayList<Integer>>>}</h2>
     * <p>
     * Same outer structure as the 2D case (indexed by vertex), but now each
     * neighbour entry isn't a bare {@code int} — it's itself a small
     * 2-element list, {@code [neighbourVertex, weight]}, because a weighted
     * edge needs to carry an extra piece of data beyond just "who." This is
     * the shape used in {@code G08_Min_Spanning_Tree}.
     * </p>
     * <pre>
     * adjacencyList
     * index 0 → [ [1, 1], [3, 4] ]     vertex 0's edges: to 1 (wt 1), to 3 (wt 4)
     * index 1 → [ [0, 1], [2, 2] ]     vertex 1's edges: to 0 (wt 1), to 2 (wt 2)
     * index 2 → [ [1, 2], [3, 5] ]     vertex 2's edges: to 1 (wt 2), to 3 (wt 5)
     * index 3 → [ [2, 5], [0, 4] ]     vertex 3's edges: to 2 (wt 5), to 0 (wt 4)
     * </pre>
     * <p>
     * {@code adjacencyList.get(0)} → {@code [[1, 1], [3, 4]]} (an
     * {@code ArrayList<ArrayList<Integer>>} — vertex 0's list of edges).<br>
     * {@code adjacencyList.get(0).get(1)} → {@code [3, 4]} (one single edge —
     * an {@code ArrayList<Integer>}).<br>
     * {@code adjacencyList.get(0).get(1).get(0)} → {@code 3} (the neighbour
     * vertex — finally an {@code int}).<br>
     * {@code adjacencyList.get(0).get(1).get(1)} → {@code 4} (the weight of
     * that edge — also an {@code int}).
     * </p>
     * <p>
     * In short: 2D exists because <em>one vertex has many neighbours</em>. 3D
     * exists because <em>one neighbour relationship itself needs more than
     * one number attached to it</em> (who + how much it costs), so that
     * "edge" gets its own little list instead of being a bare {@code int}.
     * </p>
     */
    public static ArrayList<ArrayList<ArrayList<Integer>>> threeDimensionalExample;
}