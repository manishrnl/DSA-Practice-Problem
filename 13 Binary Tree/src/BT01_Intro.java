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
public class BT01_Intro {
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
     * <h3>Tracing Below Code (Step-by-Step)</h3>
     * <p>
     * To understand how this executes, we have to look at the <strong>Call Stack</strong>.  When a method calls itself, the computer
     * "pauses" the current method, saves its place, and starts a fresh version of the method.
     * <p>
     * Let's imagine the user types this exact sequence of numbers: {@code 5, 2, -1, -1, 8, -1, -1}. Here is exactly how the computer
     * executes the function with those inputs to build a tree with 5 at the top, 2 on the left, and 8 on the right:
     * </p>
     * <ol>
     *     <li>
     *         <strong>Call 1 creates the Root (Reads 5):</strong> The method starts. It reads 5. Since {@code 5 != -1}, it creates
     *         {@code Node root = new Node(5)}. Then, it hits the line {@code root.left = createTree()}.<br><em>Status:</em>
     *         Call 1 pauses and waits for the left side to finish building.
     *     </li>
     *     <li>
     *         <strong>Call 2 goes Left (Reads 2):</strong> A fresh {@code createTree()} starts.
     *         It reads 2. It creates {@code Node root = new Node(2)}. It hits
     *         {@code root.left = createTree()}.
     *         <br><em>Status:</em> Call 2 now pauses and waits. Call 1 is still waiting.
     *     </li>
     *     <li>
     *         <strong>Call 3 hits the Base Case (Reads -1):</strong> <em>(Node 2's left child)</em>
     *         A fresh {@code createTree()} starts. It reads -1. Because {@code data == -1}, it
     *         triggers the base case: {@code return null;}.
     *         <br><em>Status:</em> Call 3 finishes and disappears. It hands {@code null} back to Call 2.
     *     </li>
     *     <li>
     *         <strong>Call 2 resumes and goes Right (Reads -1):</strong> <em>(Node 2's right child)</em>
     *         Call 2 wakes up. It sets {@code Node(2).left = null}. Now it moves to the next line:
     *         {@code root.right = createTree()}. A fresh call (Call 4) reads the next input (-1),
     *         which returns {@code null}.
     *         <br><em>Status:</em> Call 2 sets {@code Node(2).right = null}.
     *     </li>
     *     <li>
     *         <strong>Call 2 finishes (Returns Node 2 to the Root):</strong> Call 2 has finished both
     *         its left and right lines of code. It hits {@code return root;}. It hands the fully
     *         completed {@code Node(2)} back up to Call 1.
     *         <br><em>Status:</em> Call 1 wakes up. It finally executes {@code Node(5).left = Node(2)}.
     *     </li>
     *     <li>
     *         <strong>Call 1 goes Right (Reads 8):</strong> Call 1 now moves to its next line:
     *         {@code root.right = createTree()}. The process repeats: A new call reads 8, makes
     *         {@code Node(8)}, asks for its left (-1, returns {@code null}), asks for its right
     *         (-1, returns {@code null}), and returns {@code Node(8)}.
     *     </li>
     *     <li>
     *         <strong>Call 1 finishes:</strong> Call 1 sets {@code Node(5).right = Node(8)}.
     *         It hits {@code return root;}. The entire tree is built and returned. Execution is over.
     *     </li>
     * </ol>
     */
    public static Node createTree() {
        int data = sc.nextInt();
        if (data == -1) return null;    // Base case: If user inputs -1, no node should be constructed here
        Node root = new Node(data); // Allocate memory space for the active parent node structure

        System.out.print("Enter left child value for node [" + data + "] : ");
        root.left = createTree();    // Recursively build out the left branch hierarchy under the parent node

        System.out.print("Enter right child value for node [" + data + "] : ");
        root.right = createTree();   // Recursively build out the right branch hierarchy under the parent node

        return root;
    }

    /**
     * Traverses and prints out all active elements within the tree structure recursively.
     * Prints using Pre-Order Traversal sequence: (Root {@code ->} Left {@code ->} Right).
     */
    public static void printTree(Node root) {
        if (root == null) return;
        System.out.print(root.data + " ");  // Process current root data layout
        printTree(root.left);        // Cascade down into left sub-trees recursively
        printTree(root.right);        // Cascade down into right sub-trees recursively
    }

    public static void main(String[] args) {
        System.out.print("Enter Root (-1 for no node): ");
        sc = new Scanner(System.in);
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