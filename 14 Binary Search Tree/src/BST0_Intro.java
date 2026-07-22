/**
 * <p>A <strong>Binary Search Tree (BST)</strong> is a node-based binary tree data structure
 * that maintains a strict ordering property for every node {@code N}:
 * <ul>
 *   <li>All values in {@code N}'s left subtree are <strong>strictly smaller</strong> than {@code N.data}.</li>
 *   <li>All values in {@code N}'s right subtree are <strong>strictly larger</strong> than {@code N.data}.</li>
 *   <li>Both left and right subtrees must also be valid Binary Search Trees.</li>
 * </ul>
 * </p>
 *
 * <h3>Key Characteristics of BSTs:</h3>
 * <ul>
 *   <li><strong>Sorted Output:</strong> An <i>In-Order Traversal</i> (Left, Root, Right) always visits
 *       nodes in ascending sorted order.</li>
 *   <li><strong>Efficient Search:</strong> At each step, half of the remaining subtrees are eliminated,
 *       similar to Binary Search in an array.</li>
 *   <li><strong>Height Dependence:</strong> Operations run in {@code O(H)} time, where {@code H} is tree height.
 *       For balanced trees {@code H = O(log N)}, while skewed trees degrade to {@code H = O(N)}.</li>
 * </ul>
 *
 * <h3>Time &amp; Space Complexity Summary:</h3>
 * <table border="1" cellpadding="5" cellspacing="0">
 *   <caption>BST Operation Performance</caption>
 *   <tr><th>Operation</th><th>Average Case (Balanced)</th><th>Worst Case (Skewed)</th></tr>
 *   <tr><td>Search</td><td>{@code O(log N)}</td><td>{@code O(N)}</td></tr>
 *   <tr><td>Insert</td><td>{@code O(log N)}</td><td>{@code O(N)}</td></tr>
 *   <tr><td>Delete</td><td>{@code O(log N)}</td><td>{@code O(N)}</td></tr>
 *   <tr><td>Space Complexity</td><td colspan="2">{@code O(H)} call stack space</td></tr>
 * </table>
 *
 * @author Manish
 */
public class BST0_Intro {

    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    static int position = 0;

    public static Node createTree(int[] array) {
        if (position >= array.length) return null;

        int data = array[position++];
        if (data == -1) return null;

        Node root = new Node(data);
        root.left = createTree(array);
        root.right = createTree(array);
        return root;
    }

    public static void printInOrder(Node root) {
        if (root == null) return;

        printInOrder(root.left);             // 1. Visit Left Subtree (Smaller values)
        System.out.print(root.data + " ");   // 2. Visit Current Root Node
        printInOrder(root.right);            // 3. Visit Right Subtree (Larger values)
    }

    /**
     * Searches for a target value in a BST by exploiting the tree's ordering property. {@code REMEMBER} It is a BST where it is in sorted form
     * and all element on right is greater than all element on left. At every step:
     * <p>
     * <ul>
     *   <li>If {@code root.data == target}, the element is found.</li>
     *   <li>If {@code root.data > target}, search continues in the left subtree (pruning the right).</li>
     *   <li>If {@code root.data < target}, search continues in the right subtree (pruning the left).</li>
     * </ul>
     * </p>
     *
     * <p><strong>Time Complexity:</strong> {@code O(H)} where {@code H} is tree height
     * ({@code O(log N)} for balanced trees, {@code O(N)} for skewed trees).</p>
     * <p><strong>Space Complexity:</strong> {@code O(H)} call stack depth due to recursion.</p>
     *
     * @param root   The root node of the BST.
     * @param target The integer value to search for.
     * @return {@code true} if {@code target} exists in the BST, {@code false} otherwise.
     */
    public static boolean searchBST(Node root, int target) {
        // Base Cases: Tree is empty OR target node is found
        if (root == null) return false;
        if (root.data == target) return true;

        // Route search based on BST ordering rule
        if (root.data > target) {
            return searchBST(root.left, target);  // Target must reside in the left subtree
        } else {
            return searchBST(root.right, target); // Target must reside in the right subtree
        }
    }

    public static void main(String[] args) {
        Colorful_Console_Logs log = new Colorful_Console_Logs();

        // Preorder array representing a valid BST
        int[] array = {20, 10, 5, -1, -1, 15, -1, -1, 30, 25, -1, -1, 40, -1, -1};
        position = 0; // Reset index pointer

        Node root = createTree(array);

        // Verify tree creation before executing operations
        if (root == null) {
            log.error("Tree construction failed: Tree is empty!");
            return;
        }

        log.info("Printing BST elements (In-Order Traversal / Sorted):");
        printInOrder(root);
        System.out.println("\n");

        int target = 25;
        log.info("Searching for target value: " + target);

        boolean isFound = searchBST(root, target);

        if (isFound) {
            log.success("Element [" + target + "] found in the BST!");
        } else {
            log.error("Element [" + target + "] does NOT exist in the BST.");
        }
    }
}