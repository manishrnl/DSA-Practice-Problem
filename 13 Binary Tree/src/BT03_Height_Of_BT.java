import java.util.Scanner;

/**
 * A utility class to construct a Binary Tree and calculate its dimensional properties.
 * <p>
 * This class demonstrates how to recursively find the <strong>Height</strong>
 * (maximum number of nodes in a path from root to leaf) and the <strong>Edges</strong>
 * (maximum number of connections in a path from root to leaf) of a binary tree.
 * </p>
 */
public class BT03_Height_Of_BT {

    /**
     * Represents a single node in the binary tree.
     */
    public static class Node {
        int data;
        Node right;
        Node left;

        public Node(int data) {
            this.data = data;
            this.right = null;
            this.left = null;
        }
    }

    /**
     * Recursively builds a binary tree by taking standard input from the user.
     *
     * @return The fully constructed {@code Node}, or {@code null} if the user inputs -1.
     */
    public static Node createTree() {
        int value = input.nextInt();
        if (value == -1) return null;

        Node root = new Node(value);

        System.out.print("Enter left data for Node [" + value + "] : ");
        root.left = createTree();

        System.out.print("Enter right data for Node [" + value + "] : ");
        root.right = createTree();

        return root;
    }

    /**
     * Prints the binary tree using Inorder traversal.
     *
     * @param head The root node of the tree to print.
     */
    public static void printTree(Node head) {
        if (head == null) return;
        printTree(head.left);
        System.out.print(head.data + " ");
        printTree(head.right);
    }

    /**
     * <h3>Tracing the Height Calculation (Bottom-Up Recursion)</h3>
     * <p>
     * Unlike tree creation, finding the height requires a <em>Post-order</em> thought process:
     * A parent node cannot know its height until both its left and right children report their heights first.
     * </p>
     * <p>
     * <strong>Example Trace (Node 5 has a left child Node 2, and no right child):</strong>
     * </p>
     * <ol>
     *     <li><strong>Call 1 (Node 5):</strong> Asks for {@code leftHeight}. Pauses.</li>
     *     <li><strong>Call 2 (Node 2):</strong> Asks for {@code leftHeight}. Pauses.</li>
     *     <li><strong>Call 3 (Node 2's Left):</strong> Is {@code null}. Returns {@code 0}.</li>
     *     <li><strong>Call 4 (Node 2's Right):</strong> Is {@code null}. Returns {@code 0}.</li>
     *     <li>
     *         <strong>Call 2 Resumes:</strong> Node 2 evaluates {@code Math.max(0, 0) + 1}.
     *         It returns {@code 1} back up to Node 5.
     *     </li>
     *     <li>
     *         <strong>Call 1 Resumes:</strong> Node 5 now knows its {@code leftHeight} is 1.
     *         It checks its right child, which is {@code null} (returns 0).
     *         Node 5 evaluates {@code Math.max(1, 0) + 1}, returning a final height of {@code 2}.
     *     </li>
     * </ol>
     *
     * @param head The root node of the tree or subtree.
     * @return The maximum number of nodes along the longest path from the root node down to the farthest leaf node.
     */
    public static int height_Of_BT(Node head) {
        // Base case: An empty tree has a height of 0
        if (head == null) return 0;

        int leftHeight = height_Of_BT(head.left);
        int rightHeight = height_Of_BT(head.right);

        // The height of the current node is the tallest of its two branches, plus itself (+1)
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Calculates the maximum number of edges from the root to the farthest leaf.
     * <p>
     * <strong>Mathematical definition:</strong>
     * The number of edges in a single path is always exactly one less than the number of nodes in that path.
     * Therefore, {@code max_edges = max_height - 1}.
     * </p>
     *
     * @param head The root node of the tree or subtree.
     * @return The maximum number of edges, or 0 if the tree is empty.
     */
    public static int largest_Edge_Of_BT(Node head) {
        if (head == null) return 0;

        // Using the established mathematical relationship rather than redundant recursion
        return height_Of_BT(head) - 1;

        /*
         * ALTERNATIVE: If you wanted to do this purely recursively, it would look like this:
         * if (head == null) return -1; // A null node subtracts the extra +1 added by the leaf
         * int leftEdge = edge_Of_BT(head.left);
         * int rightEdge = edge_Of_BT(head.right);
         * return Math.max(leftEdge, rightEdge) + 1;
         */
    }

    /**
     * Shared scanner instance for taking console input during recursive tree generation.
     */
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter node data. Enter -1 if child is NULL : ");
        Node root = createTree();

        if (root == null) {
            System.out.print("Cant print empty tree : ");
        } else {
            System.out.print("\n\nPrinting Tree : ");
            printTree(root);

            // Note: We only calculate properties if the tree isn't entirely empty
            System.out.print("\nMax Height of Binary Tree is : " + height_Of_BT(root));
            System.out.print("\nLargest Edge of Binary Tree is : " + largest_Edge_Of_BT(root));
        }
    }
}