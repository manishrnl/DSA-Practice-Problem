import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A utility class demonstrating how to capture the Left and Right Views of a Binary Tree.
 * <p>
 * The <strong>Left View</strong> contains all the nodes you would see if you looked at the tree from the left side
 * (the first node encountered at each depth).
 * The <strong>Right View</strong> contains all the nodes you would see from the right side.
 * </p>
 *
 * <h3>General Complexity Definitions:</h3>
 * <ul>
 *     <li><strong>N:</strong> The total number of nodes in the Binary Tree.</li>
 *     <li><strong>H:</strong> The maximum height (depth) of the Binary Tree.</li>
 * </ul>
 */
public class B06_Left_Right_View_Of_BT {

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

    static Scanner sc = new Scanner(System.in);

    public static Node createTree() {
        int data = sc.nextInt();
        if (data == -1) return null;

        Node root = new Node(data);
        System.out.print("Enter Left data for node [" + data + "]: ");
        root.left = createTree();
        System.out.print("Enter Right data for node [" + data + "]: ");
        root.right = createTree();

        return root;
    }

    /**
     * Helper method to recursively find the Left View of the tree.
     * <p>
     * <strong>Logic:</strong> We traverse using Preorder (Root, Left, Right).
     * If the size of our result list equals the current level, it means this is the very first
     * node we are visiting at this depth. Because we traverse Left before Right, the first
     * node seen at any level is guaranteed to be the leftmost.
     * </p>
     * <h3>Why we use {@code list.size() == level} instead of {@code list.get(level) == null}:</h3>
     * <p>
     * Unlike standard arrays that pre-fill empty slots with {@code null}, an {@code ArrayList}
     * starts completely empty (size = 0).
     * When visiting the root at Level 0, trying to evaluate {@code list.get(0)} on an empty
     * list immediately crashes the program with an <strong>{@code IndexOutOfBoundsException}</strong>,
     * because index 0 does not exist yet.
     * </p>
     * <strong>Rule:</strong> You cannot use {@code .get()} to check if a slot is empty in an
     * {@code ArrayList} unless you have already used {@code .add()} to place an element at
     * that specific index.
     * </p>
     *
     * @param root  The current node being processed.
     * @param list  The list storing the first nodes seen at each depth.
     * @param level The current depth in the tree (0 for root).
     */
    private static void printLeftViewUtil(Node root, List<Node> list, int level) {
        if (root == null) return;

        // If list size equals the level, we are visiting this depth for the first time
        if (list.size() == level) list.add(root);

        // Traverse left first, then right
        printLeftViewUtil(root.left, list, level + 1);
        printLeftViewUtil(root.right, list, level + 1);
    }

    /**
     * Prints the Left View of the binary tree.
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - We visit every node exactly once.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - O(H) for the call stack, plus O(H) for the ArrayList storing the view.</p>
     *
     * @param root The root of the tree.
     */
    public static void printLeftView(Node root) {
        // Initializing lists inside the method avoids leftover data on multiple calls.
        List<Node> leftViewList = new ArrayList<>();
        printLeftViewUtil(root, leftViewList, 0);

        for (Node curr : leftViewList) {
            System.out.print(curr.data + " ");
        }
    }

    /**
     * Helper method to recursively find the Right View of the tree.
     * <p>
     * <strong>Logic:</strong> This is perfectly mirrored from the Left View logic.
     * We traverse Right before Left (Root, Right, Left). Therefore, the first node seen
     * at any level is guaranteed to be the rightmost.
     * </p>
     *
     * @param root  The current node being processed.
     * @param list  The list storing the first nodes seen at each depth.
     * @param level The current depth in the tree (0 for root).
     */
    private static void printRightViewUtil(Node root, List<Node> list, int level) {
        if (root == null) return;

        // If list size equals the level, we are visiting this depth for the first time
        if (list.size() == level) list.add(root);

        // Traverse right first, then left
        printRightViewUtil(root.right, list, level + 1);
        printRightViewUtil(root.left, list, level + 1);
    }

    /**
     * Prints the Right View of the binary tree.
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - We visit every node exactly once.</p>
     * <p><strong>Space Complexity (SC):</strong> O(H) - O(H) for the call stack, plus O(H) for the ArrayList storing the view.</p>
     *
     * @param root The root of the tree.
     */
    public static void printRightView(Node root) {
        if (root == null) return;

        List<Node> rightList = new ArrayList<>();
        printRightViewUtil(root, rightList, 0);

        for (Node curr : rightList) {
            System.out.print(curr.data + " ");
        }
    }

    /**
     * Main method to execute the binary tree view demonstrations.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.print("Enter Root (-1 for no node): ");
        Node root = createTree();

        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        System.out.print("\nPrinting Left View Tree Traversal Output --- ");
        printLeftView(root);

        System.out.print("\nPrinting Right View Tree Traversal Output --- ");
        printRightView(root);
        System.out.println();
    }
}