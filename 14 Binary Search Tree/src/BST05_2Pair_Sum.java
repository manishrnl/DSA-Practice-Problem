import java.util.HashSet;
import java.util.Set;

/**
 * <h1>BST Two-Sum Pair Finder</h1>
 * <p>
 * The {@code BST05_2Pair_Sum} class provides functionality to construct a Binary Search Tree (BST)
 * from a serialized array format and determine whether there exist two distinct nodes in the tree
 * whose values sum up to a specific target value.
 * </p>
 *
 * <h2>Mathematical Logic</h2>
 * Given a target sum {@code T} and a node value {@code A}, the complement required is {@code B = T - A}.
 * The algorithm uses a {@link HashSet} to check if {@code B} has already been visited in {@code O(1)} average time.
 */
public class BST05_2Pair_Sum {

    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static int position = 0;
    public static Set<Integer> set = new HashSet<>();

    /**
     * Reconstructs a Binary Tree recursively from a pre-order traversal array representation.
     * <p>
     * An element value of {@code -1} inside the array indicates a {@code null} (leaf) node reference.
     * </p>
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity:</b> {@code O(N)} — Visited each element in array at most once.</li>
     *   <li><b>Space Complexity:</b> {@code O(H)} — Where {@code H} is the height of the tree (recursion stack).</li>
     * </ul>
     *
     * @param array Pre-order array representation of the binary tree with {@code -1} for null nodes.
     * @return The root {@link Node} of the constructed tree, or {@code null} if empty/leaf node.
     */
    public static Node createTree(int[] array) {
        if (position >= array.length) {
            return null;
        }

        int data = array[position++];
        if (data == -1) {
            return null;
        }

        Node root = new Node(data);
        root.left = createTree(array);
        root.right = createTree(array);
        return root;
    }

    /**
     * Determines whether there exists a pair of nodes in the binary tree whose values add up to a specified target.
     *
     * <h3>Algorithm Description (HashSet Traversal):</h3>
     * <p>
     * Performs an In-Order traversal over the tree. For each node, it calculates the required complement:
     * {@code complement = target - current node data}
     * If the complement exists in {@link #set}, a valid pair is found. Otherwise, the current node value
     * is inserted into {@link #set} for future lookups.
     * </p>
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity:</b> {@code O(N)} worst-case, since each node is visited once and HashSet operations take {@code O(1)} average time.</li>
     *   <li><b>Auxiliary Space Complexity:</b> {@code O(N)} for storing tree values in the hash set, plus {@code O(H)} call stack space.</li>
     * </ul>
     *
     * @param root   The root {@link Node} of the tree or subtree.
     * @param target The target integer sum to find.
     * @return {@code true} if a pair exists that sums to {@code target}; {@code false} otherwise.
     */
    private static boolean isPairPresent(Node root, int target) {
        if (root == null) return false;

        if (isPairPresent(root.left, target))        // Traverse left subtree
            return true;

        if (set.contains(target - root.data))        // Check if complement exists in set
            return true;

        set.add(root.data);        // Store current node's value
        return isPairPresent(root.right, target);        // Traverse right subtree
    }


    /**
     * Main entry point for binary tree setup and two-sum evaluation test.
     *
     * <h2>Target Binary Tree Visual Structure:</h2>
     * <pre>
     *                       70
     *                    /     \
     *                  50        90
     *                /  \       /  \
     *              40    60   80   100
     *                              /
     *                            95
     * </pre>
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Colorful_Console_Logs log = new Colorful_Console_Logs();
        position = 0;
        int target = 120;

        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, -1};

        // Reset state before building
        set.clear();

        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);

        System.out.println();

        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!");
        log.success("Checking if 2 pair adds up to " + target + " is Present or not : "
                + (isPairPresent(root, target) ? "Yes , sum is present" : "No, sum isn't in the nodes"));
    }
}