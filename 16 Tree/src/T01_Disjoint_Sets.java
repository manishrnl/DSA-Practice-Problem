import java.util.Arrays;

/**
 * <b>Problem:</b> Given a collection of elements, efficiently answer two
 * kinds of queries: "are these two elements in the same group?" and "merge
 * these two groups into one." This comes up any time you need to track
 * connected components that grow over time — Kruskal's MST, cycle detection,
 * network connectivity, etc.
 * <p>
 * <b>Data structure — Disjoint Set Union (DSU / Union-Find):</b> maintain a
 * forest of trees, one tree per group. Every element points to its parent;
 * the root of a tree (the element that points to itself) is that group's
 * unique representative. Two elements belong to the same group iff they share
 * the same root.
 * </p>
 * <p>
 * Two key optimisations keep both operations nearly O(1) amortised:
 * </p>
 * <ul>
 *   <li><b>Union by rank</b> — always attach the shorter tree under the
 *       taller one, so the tree never grows taller than it has to.
 *       {@code rank} is an upper bound on tree height, not exact height —
 *       path compression can shrink actual heights without updating rank.</li>
 *   <li><b>Path compression</b> — on every {@link #find} call, each node
 *       along the path to the root is re-wired to point <em>directly</em> at
 *       the root (parent → root, skipping all intermediate nodes), so future
 *       {@code find} calls on those same nodes cost O(1).</li>
 * </ul>
 * <p>
 * Together these give an amortised time of O(&alpha;(n)) per operation, where
 * &alpha; is the inverse Ackermann function — effectively a constant
 * (&le; 4) for any n that could ever arise in practice.
 * </p>
 *
 * <h2>Visual walkthrough — n = 5</h2>
 *
 * <b>Initial state:</b> every element is its own root.
 * <pre>
 *   parent: [ 0, 1, 2, 3, 4 ]
 *   rank:   [ 0, 0, 0, 0, 0 ]
 *
 *   0   1   2   3   4
 * </pre>
 *
 * <b>After union(0, 1):</b> ranks equal → 1 attaches under 0; rank[0]++.
 * <pre>
 *   parent: [ 0, 0, 2, 3, 4 ]
 *   rank:   [ 1, 0, 0, 0, 0 ]
 *
 *       0       2   3   4
 *       |
 *       1
 * </pre>
 *
 * <b>After union(2, 3):</b> ranks equal → 3 attaches under 2; rank[2]++.
 * <pre>
 *   parent: [ 0, 0, 2, 2, 4 ]
 *   rank:   [ 1, 0, 1, 0, 0 ]
 *
 *       0       2       4
 *       |       |
 *       1       3
 * </pre>
 *
 * <b>After union(0, 2):</b> rank[0]==rank[2]==1 → 2 attaches under 0; rank[0]++.
 * <pre>
 *   parent: [ 0, 0, 0, 2, 4 ]
 *   rank:   [ 2, 0, 1, 0, 0 ]
 *
 *         0           4
 *        / \
 *       1   2
 *           |
 *           3
 * </pre>
 *
 * <b>find(3) with path compression:</b>
 * <pre>
 *   Walk up: 3 → parent[3]=2 → parent[2]=0 → parent[0]=0 (root found)
 *   Compress: parent[3] = 0,  parent[2] = 0  (both now point directly at root)
 *
 *   After compression:
 *         0           4
 *       / | \
 *      1  2  3        (fully flat — all direct children of root 0)
 * </pre>
 */
public class T01_Disjoint_Sets {

    int[] rank;
    int[] parent;
    int n;

    public T01_Disjoint_Sets(int n) {
        this.n = n;
        rank = new int[n];
        parent = new int[n];
        Arrays.fill(rank, 0);

        for (int i = 0; i < n; i++) {
            parent[i] = i; // every element is its own root initially
        }
    }

    /**
     * Returns the root (representative) of {@code x}'s group, compressing the
     * path from {@code x} to the root as a side effect.
     * <p>
     * <b>How it works:</b> if {@code x} is not its own parent, recurse on
     * {@code parent[x]} to find the true root, then immediately overwrite
     * {@code parent[x]} with that root before returning. This single
     * assignment is path compression — every node visited on the way up now
     * points directly at the root, so the chain from child to root collapses
     * to length 1 after the first call.
     * </p>
     * <pre>
     *   Before: x → a → b → root
     *   After:  x → root,  a → root,  b → root
     * </pre>
     *
     * @param x the element whose group representative is needed
     * @return root of {@code x}'s group
     */
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // compress: point x directly at root on the way back
        }
        return parent[x]; // x is the root (base case), or now points directly at it (compressed)
    }

    /**
     * Merges the groups of {@code x} and {@code y} using union by rank.
     * <p>
     * Finds both roots first. If they're the same root, the elements are
     * already in the same group — nothing to do. Otherwise, the tree with
     * the lower rank attaches under the tree with the higher rank, keeping
     * the combined forest as flat as possible:
     * </p>
     * <ul>
     *   <li>{@code rank[xRoot] < rank[yRoot]} → xRoot attaches under yRoot;
     *       yRoot's rank unchanged (it didn't grow).</li>
     *   <li>{@code rank[xRoot] > rank[yRoot]} → yRoot attaches under xRoot;
     *       xRoot's rank unchanged.</li>
     *   <li>{@code rank[xRoot] == rank[yRoot]} → yRoot attaches under xRoot
     *       (arbitrary); {@code rank[xRoot]++} because the merged tree is
     *       now exactly one level taller than either input was.</li>
     * </ul>
     *
     * @param x first element
     * @param y second element
     */
    void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) return; // same group — nothing to merge

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;     // xRoot is shorter — hang under yRoot
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;     // yRoot is shorter — hang under xRoot
        } else {
            parent[yRoot] = xRoot;     // equal height — attach yRoot under xRoot (arbitrary)
            rank[xRoot]++;             // merged tree is one level taller
        }
    }

    /**
     * Demonstrates {@link #find} and {@link #union} across a sequence of
     * merges, printing group representatives and same-group checks to show
     * the forest evolving step by step.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        T01_Disjoint_Sets ds = new T01_Disjoint_Sets(5);

        System.out.println("--- Initial state: 5 singleton groups ---");
        for (int i = 0; i < 5; i++) {
            System.out.println("  find(" + i + ") = " + ds.find(i));
        }

        ds.union(0, 1);
        System.out.println("\n--- After union(0, 1) ---");
        System.out.println("  find(0)=" + ds.find(0) + "  find(1)=" + ds.find(1) + "  (same group? " + (ds.find(0) == ds.find(1)) + ")");

        ds.union(2, 3);
        System.out.println("\n--- After union(2, 3) ---");
        System.out.println("  find(2)=" + ds.find(2) + "  find(3)=" + ds.find(3) + "  (same group? " + (ds.find(2) == ds.find(3)) + ")");

        ds.union(0, 2);
        System.out.println("\n--- After union(0, 2): groups {0,1} and {2,3} merged ---");
        System.out.println("  find(1)=" + ds.find(1) + "  find(3)=" + ds.find(3) + "  (same group? " + (ds.find(1) == ds.find(3)) + ")");
        System.out.println("  find(4)=" + ds.find(4) + "  (4 still separate? " + (ds.find(4) != ds.find(0)) + ")");
    }
}