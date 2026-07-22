import java.util.*;

/**
 * A utility class demonstrating Level Order Traversal (Breadth-First Search) in a Binary Tree.
 * <p>
 * Unlike Depth-First Traversals (Inorder, Preorder, Postorder) which dive as deep as possible
 * using the Call Stack (Recursion), Level Order Traversal visits nodes layer by layer, from left
 * to right, using a <strong>Queue (FIFO - First In, First Out)</strong> data structure.
 * </p>
 *
 * <h3>General Complexity Definitions:</h3>
 * <ul>
 *     <li><strong>N:</strong> The total number of nodes in the Binary Tree.</li>
 *     <li><strong>H:</strong> The maximum height (depth) of the Binary Tree.</li>
 *     <li><strong>W:</strong> The maximum width of the Binary Tree (max nodes at any single level).</li>
 * </ul>
 */
public class BT05_Level_Order_Traversal {

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

    public static Node createTree() {
        int value = scanner.nextInt();
        if (value == -1) return null;

        Node head = new Node(value);

        System.out.print("Enter data for Left Node [" + value + "] : ");
        head.left = createTree();

        System.out.print("Enter data for Right Node [" + value + "] : ");
        head.right = createTree();

        return head;
    }

    public static int treeHeight(Node root) {
        if (root == null) return 0;
        return Math.max(treeHeight(root.right), treeHeight(root.left)) + 1;
    }

    /**
     * Prints the binary tree using Level Order Traversal on a single continuous line.
     * <p>
     * <strong>Logic:</strong> We add the root to a Queue. Then we loop: remove the front node,
     * print it, and add its left and right children to the back of the Queue. Because a Queue
     * is First-In-First-Out, nodes added earlier (higher levels) are always processed before
     * nodes added later (lower levels).
     * </p>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - Each node is added and removed from the queue exactly once.</p>
     * <p><strong>Space Complexity (SC):</strong> O(W) - At worst (a perfectly balanced tree), the queue will hold all the leaf nodes at once, which is roughly N/2. Therefore, SC is also expressed as O(N) in the worst case.</p>
     *
     * @param root The root node of the binary tree to traverse.
     */
    public static void level_Order_Traversal(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(curr.data + " ");

            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
    }

    /**
     * Prints the binary tree level-by-level, creating a new line for each level.
     *
     * <h3>Tracing the Null Marker Technique:</h3>
     * <p>
     * To know when one level ends and another begins, we insert a {@code null} into the queue
     * to act as a "bookmark".
     * </p>
     * <ol>
     *     <li>Insert the {@code root}, then immediately insert {@code null}.</li>
     *     <li>When we poll a normal node, we print it and add its children to the back.</li>
     *     <li>When we poll {@code null}, it means we have finished printing the current level.</li>
     *     <li>Because we processed an entire level, all of <em>their</em> children are now safely
     *         in the queue. We insert a new {@code null} at the back to mark the end of this new level,
     *         and print a new line {@code \n}.</li>
     *     <li>We stop when we poll a {@code null} and find the queue is empty (meaning no more children were added).</li>
     * </ol>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - We process N nodes and H null markers. O(N + H) simplifies to O(N).</p>
     * <p><strong>Space Complexity (SC):</strong> O(W) or O(N) - Same as standard level order traversal, plus one null marker.</p>
     *
     * @param root The root node of the binary tree to traverse.
     */
    public static void level_Order_Traversal_NewLine(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);                         // Added Null so that if we find null, we can print to next line
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr == null) {
                if (queue.isEmpty()) return;    // If the queue is empty after pulling null, we are completely done
                queue.add(null);                // Otherwise, mark the end of the next level we just finished enqueueing
                System.out.print("\n");
                continue;
            }
            System.out.print(curr.data + " ");
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
    }

    /**
     * Prints the binary tree level-by-level using a HashMap and Recursion.
     * <p>
     * <strong>Logic:</strong> Instead of using a Queue to process horizontally, we use
     * recursion to dive vertically. As we visit each node, we check what level we are currently on,
     * and add that node's data to a List inside the HashMap for that specific level.
     * After the map is fully populated, we loop through the map's keys (levels 0 to max) and print the lists.
     * </p>
     *
     * <p><strong>Time Complexity (TC):</strong> O(N) - We visit each node once during recursion, and iterate through them once during printing.</p>
     * <p><strong>Space Complexity (SC):</strong> O(N) - The HashMap stores every single node in the tree across its various lists.</p>
     *
     * @param root The root node of the binary tree.
     */
    public static void level_Order_Traversal_HashMap(Node root) {
        if (root == null) return;

        // Map structure: Level -> List of nodes at that level
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        // Step 1: Populate the map using a recursive helper function
        populateMap(root, 0, map);

        // Step 2: Iterate through the map from level 0 upwards and print
        int level = 0;
        while (map.containsKey(level)) {
            for (int data : map.get(level)) {
                System.out.print(data + " ");
            }
            System.out.println(); // New line for the next level
            level++;
        }
    }

    /**
     * Recursive helper method to populate the HashMap with nodes grouped by their level.
     */
    private static void populateMap(Node node, int level, HashMap<Integer, List<Integer>> map) {
        if (node == null) return;

        // If this is the first time we've reached this level, initialize an empty list for it
        map.putIfAbsent(level, new ArrayList<>());

        // Add the current node's data to this level's list
        map.get(level).add(node.data);

        // Recursively visit left and right children, increasing the depth (level) by 1
        populateMap(node.left, level + 1, map);
        populateMap(node.right, level + 1, map);
    }

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter node Element. -1 for null node: ");
        Node head = createTree();

        if (head == null) {
            System.out.println("Binary Tree is Empty");
        } else {
            int height = treeHeight(head);
            System.out.print("\nTree Height : " + height);

            System.out.print("\n\nLevel Order Traversal of Binary tree is : ");
            level_Order_Traversal(head);

            System.out.print("\n\nPrinting Each level in New Line:\n");
            level_Order_Traversal_NewLine(head);

            System.out.print("\nPrinting Each level in New Line Using Hash Map :\n");
            level_Order_Traversal_HashMap(head);
        }
    }
}