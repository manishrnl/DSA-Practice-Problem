import java.util.Scanner;

/**
 * <h2>Binary Tree Fundamentals & Architecture</h2>
 * <p>
 * A Binary Tree is a hierarchical non-linear data structure where each node is permitted to have at most two child nodes,
 * traditionally distinguished as the {@code left} child and the {@code right} child.
 *
 * <p><b>Structural Properties:</b></p>
 * <ul>
 *   <li>The maximum number of nodes at any given level {@code i } is {@code 2^i} (given 0-based indexing for levels).</li>
 *   <li>The maximum absolute capacity of nodes for a tree of height {@code h} is{@code 2^h -1}.</li>
 *   <li>When represented sequentially inside a 1-indexed array:
 *     <ul>
 *       <li>Left child pointer of node at index{@code i} evaluates to: {@code 2 * i}</li>
 *       <li>Right child pointer of node at index {@code i} evaluates to: {@code 2 * i + 1}</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class B01_Binary_Tree_Intro {

    static Scanner sc = null;

    public static class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Recursively constructs a Binary Tree based on interactive console inputs from the user.
     * Use a value of {@code -1} to denote a null or empty leaf terminal space.
     *
     * <p><b>Execution Paradigm:</b></p>
     * The node building block functions dynamically using a Pre-order approach (Root, Left, Right).
     *
     * @return the newly initialized root node instance, or {@code null} if termination sequence was met.
     */
    public static Node createTree() {

        int data = sc.nextInt();

        // Base case: If user inputs -1, no node should be constructed here
        if (data == -1) {
            return null;
        }

        // Allocate memory space for the active parent node structure
        Node root = new Node(data);

        // Recursively build out the left branch hierarchy under the parent node
        System.out.println("Enter left child value for node [" + data + "]");
        root.left = createTree();

        // Recursively build out the right branch hierarchy under the parent node
        System.out.println("Enter right child value for node [" + data + "]");
        root.right = createTree();

        return root;
    }

    /**
     * Traverses and prints out all active elements within the tree structure recursively.
     * Prints using Pre-Order Traversal sequence: (Root {@code ->} Left {@code ->} Right).
     *
     * @param root the starting base node of the active tree configuration to evaluate.
     */
    public static void printTree(Node root) {
        if (root == null) {
            return;
        }

        // Process current root data layout
        System.out.print(root.data + " ");

        // Cascade down into left sub-trees recursively
        printTree(root.left);

        // Cascade down into right sub-trees recursively
        printTree(root.right);
    }

    /**
     * System validation loop to create and inspect the tree's runtime data mapping.
     *
     * @param args command line argument parameters (unused).
     */
    public static void main(String[] args) {
        System.out.print("Enter data (-1 for no node): ");
        sc = new Scanner(System.in);

        System.out.println("--- Initialize Binary Tree Construction ---");
        Node root = createTree();

        System.out.println("\n--- Resulting Pre-Order Tree Traversal Output ---");
        if (root == null) {
            System.out.println("The constructed tree configuration is empty.");
        } else {
            printTree(root);
            System.out.println();
        }
    }
}