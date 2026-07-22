import java.util.ArrayList;
import java.util.List;

public class BST03_Is_Valid_BST {

    private static int position = 0;

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

    public static List<Integer> list = new ArrayList<>();

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

    public static void populateListHelper(Node root) {
        if (root == null) return;
        populateListHelper(root.left);
        list.add(root.data);
        populateListHelper(root.right);
    }

    public static void populateList(Node root) {
        list.clear();  // clears the list & then adds data inside lists so that data should not be duplicated if called multiple times
        populateListHelper(root);
    }

    /**
     * Validates whether the populated {@link #list} forms a valid Binary Search Tree sequence.
     * <p>
     * A valid BST in-order traversal yields a strictly increasing sequence where each element is
     * greater than or equal to the preceding element.
     * </p>
     * <b>Time Complexity:</b> {@code O(N)} — Iterates through the list of {@code N} elements.<br>
     * <b>Space Complexity:</b> {@code O(1)} — Uses constant auxiliary memory.
     *
     * @return {@code true} if the sequence maintains valid BST order; {@code false} otherwise.
     */
    public static boolean isValidBST() {
        if (list.isEmpty()) return true;

        /**
         * Stores the previous element encountered during BST sequence validation. <b>Note:</b> Initialized as {@code null} to correctly handle negative values.
         */
        int prevValue = list.getFirst();
        for (int i = 1; i < list.size(); i++) {
            if (prevValue > list.get(i)) return false;
            else if (prevValue == list.get(i)) return false;
            prevValue = list.get(i);
        }
        return true;
    }

    /**
     * <h1>Approach</h1><p>To check if Valid BST, convert it to In-Order Tree. and add all data that we get from In-Order traversal into list.
     * In-Order has all data in sorted fashion. Now check all data to its corresponding element. If any previous value > next value,
     * it means it is not a BST, hence we can say it is not a BST
     * </p>
     * </p>
     *
     * <h2>Tree Visual Structure:</h2>
     * <pre>
     * Original Binary Tree
     *
     *                       70
     *                    /     \
     *                  50        90
     *                /  \       /  \
     *              40    60   80   100
     *                              /
     *                            95
     * </pre>
     */
    public static void main(String[] args) {

        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, -1};
        Colorful_Console_Logs log = new Colorful_Console_Logs();
        position = 0;

        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);


        populateList(root);

        System.out.println();

        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!");

        System.out.println("Lists data are : " + list);
        boolean isValidInOrder = isValidBST();
        log.success("Checking if BST is valid (In-Order Check): " + (isValidInOrder ? "Yes, it is a valid BST" : "It is NOT a valid BST"));
    }
}