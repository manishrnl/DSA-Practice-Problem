import java.util.*;

/**
 * <h1>BST Vertical Order Traversal</h1>
 * <p>
 * The {@code BST06_Vertical_Order_Traversal} class provides functionality to construct
 * a Binary Search Tree (BST) from a serialized array format and perform a Vertical Order Traversal.
 * </p>
 *
 * <h2>Algorithm Logic</h2>
 * Uses Breadth-First Search (BFS) with a {@link Queue} and a {@link TreeMap} to group node values
 * by their horizontal distance relative to the root (where root distance is {@code 0}).
 * Left children subtract {@code 1} from horizontal distance, while right children add {@code 1}.
 */
public class BST06_Vertical_Order_Traversal {

    public static class Pair {
        int horDistance;
        Node node;

        public Pair(int horDistance, Node node) {
            this.horDistance = horDistance;
            this.node = node;
        }
    }

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

    /**
     * Traverses the binary tree vertically from top to bottom and left to right horizontal levels.
     * <p>
     * Prints nodes grouped by their horizontal distance in ascending order.
     * </p>
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity:</b> {@code O(N log K)} — Where {@code N} is the total number of nodes and {@code K} is the number of unique horizontal distances.</li>
     *   <li><b>Auxiliary Space Complexity:</b> {@code O(N)} — For storing nodes in the BFS queue and map.</li>
     * </ul>
     *
     * @param root The root {@link Node} of the tree.
     */
    public static void verticalOrderTraversal(Node root) {
        if (root == null) {
            return;
        }

        Map<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(0, root));

        while (!queue.isEmpty()) {
            Pair curr = queue.poll();
            if (map.containsKey(curr.horDistance)) {
                map.get(curr.horDistance).add(curr.node.data);
            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(curr.node.data);
                map.put(curr.horDistance, temp);
            }
            /**
             * <p>After above if-else logic finishes, it executes both 2 if logic and adds right + left data into queue then goes inside while loop for checking</p>
             */
            if (curr.node.left != null) {
                queue.add(new Pair(curr.horDistance - 1, curr.node.left));
            }
            if (curr.node.right != null) {
                queue.add(new Pair(curr.horDistance + 1, curr.node.right));
            }
        }

        // Print node data sorted by horizontal distance
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * Main entry point for tree setup and vertical order traversal execution.
     *
     * <h2>Target Binary Tree Visual Structure:</h2>
     * <pre>
     *                       70
     *                    /     \
     *                  50        90
     *                /  \       /  \
     *              40    60   80   100
     *                              / \
     *                            95  105
     * </pre>
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Colorful_Console_Logs log = new Colorful_Console_Logs();
        position = 0;

        // Correct pre-order array for the ASCII tree structure above
        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, 105, -1, -1};

        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);

        System.out.println();

        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!\n");
        verticalOrderTraversal(root);
    }
}