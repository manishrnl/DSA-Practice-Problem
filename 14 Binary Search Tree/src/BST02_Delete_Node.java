/**
 * Demonstrates node deletion in a Binary Search Tree (BST) using the standard
 * In-Order Successor strategy for nodes with two children.
 *
 */
public class BST02_Delete_Node {

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

    /**
     * Deletes a node with the specified key from the BST while keeping BST structural invariants.
     *
     * <p><b>Handles 3 Deletion Cases:</b>
     * <ul>
     *   <li><b>Case 1 (Leaf Node):</b> Simply remove the node by returning {@code null}.</li>
     *   <li><b>Case 2 (Single Child):</b> Bypass the node by returning its non-null child.</li>
     *   <li><b>Case 3 (Two Children):</b> Find the In-Order Successor (smallest node in the right subtree),
     *       copy its value to the current node, and recursively delete the successor node.</li>
     * </ul>
     * </p>
     *
     * @param root         The root node of the current subtree.
     * @param nodeToDelete The target integer value to remove from the tree.
     * @return The updated root node reference of the subtree.
     *
     * <p><b>Time Complexity:</b><br>
     * - <b>Average Case:</b> O(log N) — For a balanced BST.<br>
     * - <b>Worst Case:</b> O(N) — For a degenerate/skewed tree.<br>
     * <b>Space Complexity:</b> O(H) — Auxiliary space consumed by recursion call frames.</p>
     */
    private static Node deleteNode(Node root, int nodeToDelete) {
        if (root == null) return null;

        // Search for the node in left or right subtrees
        if (nodeToDelete < root.data) {
            root.left = deleteNode(root.left, nodeToDelete);
        } else if (nodeToDelete > root.data) {
            root.right = deleteNode(root.right, nodeToDelete);
        } else if (nodeToDelete == root.data) {
            // Node found! Handle the 3 deletion cases:

            // Case 1 & 2: Node with zero or one child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                // Case 3: Node with two children
                // Step A: Find the In-Order Successor (smallest value in right subtree)
                root.data = minValue(root.right);

                // Step B: Delete the In-Order Successor node from the right subtree
                root.right = deleteNode(root.right, root.data);
            }
        }
        return root;
    }

    /**
     * Finds the minimum value stored in a given BST subtree by traversing
     * iteratively to the leftmost node.
     *
     * @param root The root node of the subtree to search.
     * @return The minimum integer value present in the subtree.
     *
     * <p><b>Time Complexity:</b> O(H) — Traversals extend down the left spine, bounded by height H.<br>
     * <b>Space Complexity:</b> O(1) — Iterative implementation uses constant auxiliary memory.</p>
     */
    public static int minValue(Node root) {
        int min = root.data;
        while (root.left != null) {
            min = root.left.data;
            root = root.left;
        }
        return min;
    }

    /**
     * <h2>Tree Visual Structure:</h2><pre>
     * Before Deletion Node 90                                                 After Deleting Node 90
     *
     *                       70                                                              70
     *                    /     \                                                         /     \
     *                  50        90                                                    50       95
     *                /  \       /  \                                                  /  \     /  \
     *              40    60   80   100                                              40    60  80   100
     *                              /
     *                            95   </pre>
     *
     * @param args Command-line arguments (unused).
     *
     *             <p><b>Time Complexity:</b> O(N) — Dominated by construction and traversal printing.<br>
     *             <b>Space Complexity:</b> O(N) — Space for node allocations and call stack.</p>
     */
    public static void main(String[] args) {

        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, -1};
        position = 0;
        int nodeToDelete = 90;

        Colorful_Console_Logs log = new Colorful_Console_Logs();
        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);
        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }

        log.debug("Tree constructed successfully!");

        log.success("In-Order Traversal (Before Deletion): ");
        printInOrder(root);

        root = deleteNode(root, nodeToDelete);

        log.success("In-Order Traversal (After Deleting " + nodeToDelete + "): ");
        printInOrder(root);
    }
}