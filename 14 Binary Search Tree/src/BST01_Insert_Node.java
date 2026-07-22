public class BST01_Insert_Node {

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

    public static int position = 0;

    public static Node createTree(int[] arrayTree) {
        if (position >= arrayTree.length) return null;

        int data = arrayTree[position++];
        if (data == -1) return null;

        Node curr = new Node(data);
        curr.left = createTree(arrayTree);
        curr.right = createTree(arrayTree);

        return curr;
    }

    public static void printTree(Node root) {
        if (root == null) return;
        printTree(root.left);
        System.out.print(root.data + " ");
        printTree(root.right);
    }

    /**
     * Inserts a key into the Binary Search Tree while maintaining the BST structural invariant:
     * <code>Left Subtree Data &lt; Current Root Data &lt; Right Subtree Data</code>.
     *
     * @param root The root node of the BST or current subtree.
     * @param key  The integer value to insert into the tree.
     * @return The root node reference of the modified tree/subtree after insertion.
     *
     * <p><b>Time Complexity:</b><br>
     * - <b>Average Case:</b> O(log N) — For a balanced BST, search depth reduces by half each step.<br>
     * - <b>Worst Case:</b> O(N) — For a degenerate/skewed tree where height equals N.<br>
     * <b>Space Complexity:</b> O(H) — Call stack frame depth proportional to tree height H.</p>
     */
    public static Node insertNode(Node root, int key) {
        if (root == null) return new Node(key);

        if (root.data > key) {
            root.left = insertNode(root.left, key);
        } else if (root.data < key) {
            root.right = insertNode(root.right, key);
        }

        return root;
    }
    /**
     * <h2>Tree Visual Structure:</h2><pre>
     *
     * Before Inserting Node 55                                                 After Inserting Node 55
     *
     *                       70                                                              70
     *                    /     \                                                         /     \
     *                  50        90                                                    50       90
     *                /  \       /  \                                                  /  \     /  \
     *              40    60   80   100                                              40    60  80   100
     *                              /                                                     /         /
     *                            95                                                    55         95                   </pre>
     */
    public static void main(String[] args) {

        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, -1};
        position = 0;

        Colorful_Console_Logs log = new Colorful_Console_Logs();
        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);
        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }

        log.debug("Tree constructed successfully!");

        int nodeToInsert = 55;
        log.success("\nIn-Order Traversal (Before Insertion): ");
        printTree(root);

        Node temp = insertNode(root, nodeToInsert);
        log.success("\nCreated a Node for data : " + nodeToInsert);

        log.success("In-Order Traversal (After Insertion): ");
        printTree(temp);
    }
}