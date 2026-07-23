/**
 * <h1>Self-Balancing AVL Tree</h1>
 * <p>
 * The {@code BST07_Self_Balancing_AVL_Tree} class implements a self-balancing Binary Search Tree (BST).
 * Named after its inventors Adelson-Velsky and Landis, an AVL tree guarantees an {@code O(log N)} time complexity
 * for search, insertion, and deletion operations by maintaining a balanced height across all subtrees.
 * </p>
 *
 * <h2>1. Core Concepts</h2>
 * <ul>
 *   <li><b>Height:</b> The length of the longest path from a node to a leaf node (1-based for leaves).</li>
 *   <li><b>Balance Factor:</b> Defined for every node as {@code Balance Factor = height(left) - height(right)}.</li>
 *   <li><b>Balance Property:</b> A node is balanced if its Balance Factor is in {@code [-1, 0, 1]}.
 *       If {@code Balance Factor > 1}, the node is left-heavy. If {@code Balance Factor < -1}, it is right-heavy.</li>
 * </ul>
 *
 * <h2>2. Detailed Explanation of the 4 Rotation Cases</h2>
 * <p>When an insertion causes a node's Balance Factor to become {@code 2} or {@code -2}, one of four rotations is triggered:</p>
 *
 * <h3>Case 1: Left-Left (LL) Case</h3>
 * <ul>
 *   <li><b>Condition:</b> {@code balance > 1} and inserted key is smaller than left child's key ({@code key < node.left.key}).</li>
 *   <li><b>Description:</b> Node inserted into the left subtree of the left child.</li>
 *   <li><b>Fix:</b> Perform a single <b>Right Rotation</b> on the unbalanced node.</li>
 * </ul>
 * <pre>
 *        z (Unbalanced)               y
 *       /                            / \
 *      y            RightRotate     x   z
 *     /            ------------>
 *    x
 * </pre>
 *
 * <h3>Case 2: Right-Right (RR) Case</h3>
 * <ul>
 *   <li><b>Condition:</b> {@code balance < -1} and inserted key is larger than right child's key ({@code key > node.right.key}).</li>
 *   <li><b>Description:</b> Node inserted into the right subtree of the right child.</li>
 *   <li><b>Fix:</b> Perform a single <b>Left Rotation</b> on the unbalanced node.</li>
 * </ul>
 * <pre>
 *    z (Unbalanced)                   y
 *     \                              / \
 *      y            LeftRotate      z   x
 *       \          ------------>
 *        x
 * </pre>
 *
 * <h3>Case 3: Left-Right (LR) Case</h3>
 * <ul>
 *   <li><b>Condition:</b> {@code balance > 1} and inserted key is larger than left child's key ({@code key > node.left.key}).</li>
 *   <li><b>Description:</b> Node inserted into the right subtree of the left child.</li>
 *   <li><b>Fix:</b>
 *     1. Perform a <b>Left Rotation</b> on {@code node.left} (converts to LL case).<br>
 *     2. Perform a <b>Right Rotation</b> on the unbalanced {@code node}.
 *   </li>
 * </ul>
 * <pre>
 *      c (Unbalanced)            c (Unbalanced)               b
 *     /                         /                            / \
 *    a           LeftRotate    b            RightRotate     a   c
 *     \         ------------> /            ------------>
 *      b                     a
 * </pre>
 *
 * <h3>Case 4: Right-Left (RL) Case</h3>
 * <ul>
 *   <li><b>Condition:</b> {@code balance < -1} and inserted key is smaller than right child's key ({@code key < node.right.key}).</li>
 *   <li><b>Description:</b> Node inserted into the left subtree of the right child.</li>
 *   <li><b>Fix:</b>
 *     1. Perform a <b>Right Rotation</b> on {@code node.right} (converts to RR case).<br>
 *     2. Perform a <b>Left Rotation</b> on the unbalanced {@code node}.
 *   </li>
 * </ul>
 * <pre>
 *    a (Unbalanced)            a (Unbalanced)                 b
 *     \                         \                            / \
 *      c         RightRotate     b             LeftRotate   a   c
 *     /         ------------>     \           ------------>
 *    b                             c
 * </pre>
 */
public class BST07_Self_Balancing_AVL_Tree {

    /**
     * Represents a single node in the AVL tree containing a key, child references,
     * and the node's height.
     */
    static class Node {
        int key;
        int height;
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
            left = right = null;
        }
    }

    /**
     * Helper method to safely return the height of a node.
     *
     * @param N The node to check.
     * @return Height of node {@code N}, or {@code 0} if {@code N} is {@code null}.
     */
    static int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    /**
     * Rotates subtree to the right to fix Left-Left (LL) imbalance.
     *
     * @param y Root of the unbalanced subtree.
     * @return New root after right rotation.
     */
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        // Return new root
        return x;
    }

    /**
     * Rotates subtree to the left to fix Right-Right (RR) imbalance.
     *
     * @param x Root of the unbalanced subtree.
     * @return New root after left rotation.
     */
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        // Return new root
        return y;
    }

    /**
     * Computes the balance factor of a node.
     *
     * @param N Target node.
     * @return {@code height(N.left) - height(N.right)}, or {@code 0} if node is null.
     */
    static int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    /**
     * Inserts a key into the AVL subtree rooted at {@code node} and balances it.
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity:</b> {@code O(log N)}</li>
     *   <li><b>Space Complexity:</b> {@code O(log N)} stack space.</li>
     * </ul>
     *
     * @param node Root of current subtree.
     * @param key  Key to insert.
     * @return Updated root of subtree.
     */
    static Node insert(Node node, int key) {

        // Perform normal BST insertion
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Equal keys are not allowed in BST
            return node;

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases:

        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    /**
     * Prints pre-order traversal of the tree.
     *
     * @param root Root node of the tree.
     */
    static void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    /**
     * Main execution entry point.
     *
     * <h2>Constructed Tree Visualization:</h2>
     * <pre>
     *                   30
     *                 /    \
     *               20      40
     *              /  \       \
     *            10   25      50
     * </pre>
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Node root = null;

        root = insert(root, 10);
        root = insert(root, 20);
        root = insert(root, 30);
        root = insert(root, 40);
        root = insert(root, 50);
        root = insert(root, 25);

        preOrder(root);
    }
}