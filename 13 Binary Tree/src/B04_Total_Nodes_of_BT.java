import java.util.Scanner;

/**
 * A utility class for Binary Tree traversal and aggregate calculations.
 * <p>
 * This class demonstrates how to recursively process a binary tree to find:
 * <ul>
 *     <li>The total number of nodes.</li>
 *     <li>The maximum value stored in the tree.</li>
 *     <li>The minimum value stored in the tree.</li>
 * </ul>
 * </p>
 *
 * <h3>General Complexity Definitions:</h3>
 * <ul>
 *     <li><strong>N:</strong> The total number of nodes in the Binary Tree.</li>
 *     <li><strong>H:</strong> The maximum height (depth) of the Binary Tree.</li>
 * </ul>
 */
public class B04_Total_Nodes_of_BT {

    /**
     * Represents a single node in the binary tree.
     */
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

    /**
     * Shared scanner instance for taking console input during recursive tree generation.
     */
    static Scanner scanner = new Scanner(System.in);

    /**
     * Recursively builds a binary tree by taking standard input from the user.
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Every node is created exactly once.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - The maximum number of recursive calls on the stack equals the height of the tree.</p>
     *
     * @return The fully constructed {@code Node}, or {@code null} if the user inputs -1.
     */
    public static Node createTree() {
        int input = scanner.nextInt();
        if (input == -1) return null;

        Node root = new Node(input);

        System.out.print("Enter Left Node for [" + input + "]: ");
        root.left = createTree();

        System.out.print("Enter Right Node for [" + input + "]: ");
        root.right = createTree();

        return root;
    }

    /**
     * Prints the binary tree using Preorder traversal (Root, Left, Right).
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Visits every node once.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - Auxiliary stack space used by recursion.</p>
     *
     * @param root The root node of the tree to print.
     */
    public static void printTree(Node root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        printTree(root.left);
        printTree(root.right);
    }

    /**
     * Calculates the total number of nodes present in the binary tree.
     * <p>
     * <strong>Logic:</strong> The total nodes equal the number of nodes in the left branch,
     * plus the number of nodes in the right branch, plus 1 (to count the current node).
     * </p>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Must visit every node to count it.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - Call stack depth is determined by tree height.</p>
     *
     * @param root The root node of the tree or subtree.
     * @return The total integer count of nodes.
     */
    public static int total_Nodes_Of_BT(Node root) {
        // Base case: A null node contributes 0 to the count
        if (root == null) return 0;

        return total_Nodes_Of_BT(root.left) + total_Nodes_Of_BT(root.right) + 1;
    }

    /**
     * Finds the maximum integer value stored anywhere in the binary tree.
     * <p>
     * <strong>Logic:</strong> Recursively finds the maximum value in the left subtree and the
     * right subtree, then compares those with the current node's value to find the absolute max.
     * </p>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Must check every single node to ensure it is not the maximum.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - Call stack space for recursion.</p>
     *
     * @param root The root node of the tree or subtree.
     * @return The maximum integer value found, or {@code Integer.MIN_VALUE} if the tree is empty.
     */
    public static int max_number_in_BT(Node root) {
        // Base case: Return the smallest possible integer so it doesn't falsely become the maximum
        if (root == null) return Integer.MIN_VALUE;

        return Math.max(max_number_in_BT(root.right), Math.max(max_number_in_BT(root.left), root.data));
    }

    /**
     * Finds the minimum integer value stored anywhere in the binary tree.
     * <p>
     * <strong>Logic:</strong> Recursively finds the minimum value in the left subtree and the
     * right subtree, then compares those with the current node's value to find the absolute min.
     * </p>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Must check every single node to ensure it is not the minimum.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - Call stack space for recursion.</p>
     *
     * @param root The root node of the tree or subtree.
     * @return The minimum integer value found, or {@code Integer.MAX_VALUE} if the tree is empty.
     */
    public static int min_number_in_BT(Node root) {
        // Base case: Return the largest possible integer so it doesn't falsely become the minimum
        if (root == null) return Integer.MAX_VALUE;

        return Math.min(min_number_in_BT(root.left), Math.min(min_number_in_BT(root.right), root.data));
    }

    /**
     * Driver code to test the binary tree operations.
     */
    public static void main(String[] args) {
        System.out.print("Enter data for Nodes . -1 for null or no data for child : ");
        Node root = createTree();

        if (root == null) {
            System.out.print("Binary Tree is Null. Add some value to view them");
            System.out.print("\nTotal nodes in a Binary Tree are : " + total_Nodes_Of_BT(root));

            // Note: For a null tree, max will print -2147483648 and min will print 2147483647
            // because of the mathematical base cases used in the recursive functions.
            System.out.print("\nMaximum number in a Binary Tree are : " + max_number_in_BT(root));
            System.out.print("\nMinimum number in a Binary Tree are : " + min_number_in_BT(root));
        } else {
            System.out.print("Tree Preorder Traversal: ");
            printTree(root);
            System.out.print("\nTotal nodes in a Binary Tree are : " + total_Nodes_Of_BT(root));
            System.out.print("\nMaximum number in a Binary Tree are : " + max_number_in_BT(root));
            System.out.print("\nMinimum number in a Binary Tree are : " + min_number_in_BT(root));
        }
    }
}