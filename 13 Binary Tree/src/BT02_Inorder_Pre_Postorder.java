import java.util.Scanner;

/**
 * A complete implementation of a Binary Tree creation and its depth-first traversals.
 * <p>
 * This class demonstrates how to construct a binary tree recursively from standard input
 * and print its contents using the three standard DFS (Depth-First Search) algorithms:
 * <ul>
 *     <li><strong>Inorder</strong> (Left, Root, Right)</li>
 *     <li><strong>Preorder</strong> (Root, Left, Right)</li>
 *     <li><strong>Postorder</strong> (Left, Right, Root)</li>
 * </ul>
 * </p>
 */
public class BT02_Inorder_Pre_Postorder {

    /**
     * Represents a single node in the binary tree.
     * Contains the integer data and pointers to the left and right child nodes.
     */
    public static class Node {
        int data;
        Node left;
        Node right;

        /**
         * Constructs a new Node with the given data.
         * By default, the left and right children are initialized to {@code null}.
         *
         * @param data The integer value to be stored in this node.
         */
        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * <h3>Tracing the Recursive Construction</h3>
     * <p>
     * This method recursively builds a binary tree. When a method calls itself, the computer "pauses"
     * the current method, saves its place on the Call Stack, and starts a fresh version of the method.
     * </p>
     * <p>
     * Example execution if a user types: {@code 5, 2, -1, -1, 8, -1, -1}:
     * </p>
     * <ol>
     *     <li>
     *         <strong>Root Creation (Reads 5):</strong> Reads 5. Since {@code 5 != -1}, it creates
     *         {@code Node node = new Node(5)}. It then hits {@code node.left = insertIntoBinaryTree()}.
     *         <br><em>Status:</em> Pauses and waits for the left side to finish.
     *     </li>
     *     <li>
     *         <strong>Goes Left (Reads 2):</strong> A fresh call starts. Reads 2. Creates
     *         {@code Node node = new Node(2)}. Hits {@code node.left = insertIntoBinaryTree()}.
     *         <br><em>Status:</em> Pauses and waits.
     *     </li>
     *     <li>
     *         <strong>Base Case (Reads -1):</strong> <em>(Node 2's left child)</em> Reads -1.
     *         Triggers base case: {@code return null;}.
     *         <br><em>Status:</em> Finishes and hands {@code null} back to Node 2's left pointer.
     *     </li>
     *     <li>
     *         <strong>Goes Right (Reads -1):</strong> <em>(Node 2's right child)</em> Node 2 moves
     *         to its next line: {@code node.right = insertIntoBinaryTree()}. Reads -1, returns {@code null}.
     *         <br><em>Status:</em> Node 2 sets its right pointer to {@code null}.
     *     </li>
     *     <li>
     *         <strong>Returns up the stack:</strong> Node 2 is finished and returns itself to Node 5.
     *         Node 5 sets {@code node.left = Node(2)}. Node 5 then executes its right pointer logic,
     *         eventually reading 8, building Node 8, and attaching it to the right.
     *     </li>
     * </ol>
     *
     * @return The fully constructed {@code Node} (which acts as the root of this specific subtree),
     *         or {@code null} if the user inputs -1.
     */
    public static Node insertIntoBinaryTree() {
        int data = input.nextInt();

        // Base case: -1 indicates no node should exist at this position
        if (data == -1) return null;

        Node node = new Node(data);

        System.out.print("Enter left data for Node [" + data + "] : ");
        node.left = insertIntoBinaryTree();

        System.out.print("Enter right data for Node [" + data + "] : ");
        node.right = insertIntoBinaryTree();

        return node;
    }

    /**
     * Prints the binary tree in <strong>Inorder</strong> traversal sequence.
     * <p>
     * Traversal order: {@code Left Child -> Current Node -> Right Child}
     * </p>
     * <p>
     * In a Binary Search Tree (BST), this traversal naturally prints the elements in ascending sorted order.
     * </p>
     *
     * @param root The starting node of the tree or subtree to traverse.
     */
    public static void print_Inorder(Node root) {
        if (root == null) return;
        print_Inorder(root.left);
        System.out.print(root.data + " ");
        print_Inorder(root.right);
    }

    /**
     * Prints the binary tree in <strong>Postorder</strong> traversal sequence.
     * <p>
     * Traversal order: {@code Left Child -> Right Child -> Current Node}
     * </p>
     * <p>
     * This traversal is often used to safely delete a tree from memory, as it ensures
     * children are processed (or deleted) before their parent node.
     * </p>
     *
     * @param root The starting node of the tree or subtree to traverse.
     */
    public static void print_Postorder(Node root) {
        if (root == null) return;
        print_Postorder(root.left);
        print_Postorder(root.right);
        System.out.print(root.data + " ");
    }

    /**
     * Prints the binary tree in <strong>Preorder</strong> traversal sequence.
     * <p>
     * Traversal order: {@code Current Node -> Left Child -> Right Child}
     * </p>
     * <p>
     * This traversal is useful for creating a copy of the tree, as it processes the parent
     * node before branching out to construct the children.
     * </p>
     *
     * @param root The starting node of the tree or subtree to traverse.
     */
    public static void print_Preorder(Node root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        print_Preorder(root.left);
        print_Preorder(root.right);
    }

    static Scanner input=new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter data for binary tree: -1 if no data for children : ");
        Node root = insertIntoBinaryTree();

        if (root == null) {
            System.out.print("\nEmpty root, so cant print \n");
        } else {
            System.out.print("\nPrinting Inorder tree :");
            print_Inorder(root);

            System.out.print("\nPrinting Pre-order tree : ");
            print_Preorder(root);

            System.out.print("\nPrinting Post-order tree :");
            print_Postorder(root);
        }
    }
}