import java.util.*;

/**
 * A utility class demonstrating how to capture the Top and Bottom Views of a Binary Tree.
 * <p>
 * <strong>Horizontal Distance (HD):</strong> Imagine dropping vertical lines down through the tree.
 * The root is at HD 0. Every time you go left, HD decreases by 1. Every time you go right, HD increases by 1.
 * </p>
 * <ul>
 *     <li><strong>Top View:</strong> The first node you see at each HD when looking from above.</li>
 *     <li><strong>Bottom View:</strong> The last node you see at each HD when looking from below.</li>
 * </ul>
 */
public class B07_Top_Bottom_View_Of_BT {

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
     * A wrapper class to associate a Node with its Horizontal Distance.
     * Required because a standard Queue can only hold one object per slot.
     */
    public static class Pair {
        Node node;
        int horizontalDistance;

        public Pair(int horizontalDistance, Node node) {
            this.horizontalDistance = horizontalDistance;
            this.node = node;
        }
    }

    // Global index value to insert data into tree automatically from a Preorder Array
    static int position = 0;

    /**
     * Recursively builds a binary tree from a Preorder array.
     *
     * @param arrayTree An array of integers representing the tree in Preorder (Root, Left, Right).
     *                  {@code -1} represents a null child.
     * @return The fully constructed {@code Node} root, or {@code null} if the tree is empty.
     */
    public static Node createTree(int[] arrayTree) {
        if (position >= arrayTree.length) return null;

        int data = arrayTree[position++];
        System.out.print(data);

        if (data == -1) return null;
        Node root = new Node(data);

        System.out.print("\nData for Left Node [" + data + "] -> ");
        root.left = createTree(arrayTree);

        System.out.print("\nData for Right Node [" + data + "] -> ");
        root.right = createTree(arrayTree);

        return root;
    }

    /**
     * Prints the tree using standard Preorder traversal (Root, Left, Right).
     *
     * @param root The root of the tree.
     */
    public static void printTree(Node root) {
        if (root == null) return;
        printTree(root.left);
        System.out.print(root.data + " ");
        printTree(root.right);
    }

    /**
     * Prints the Top View of the binary tree.
     * <p>
     * <strong>Logic:</strong> We use Level-Order Traversal (BFS) via a Queue.
     * As we travel level by level, if we reach a Horizontal Distance we haven't seen yet,
     * it must be the topmost node for that vertical line, so we lock it into the Map.
     * </p>
     * <p><strong>Time Complexity:</strong> O(N log N) - We visit N nodes, and TreeMap insertion is O(log N).</p>
     * <p><strong>Space Complexity:</strong> O(N) - Queue and Map can both hold up to N elements.</p>
     *
     * @param root The root of the tree.
     */
    public static void topView(Node root) {
        if (root == null) return;

        Queue<Pair> queue = new ArrayDeque<>();
        // TreeMap automatically sorts the keys (Horizontal Distances) from negative (left) to positive (right)
        Map<Integer, Integer> map = new TreeMap<>();

        queue.add(new Pair(0, root));

        while (!queue.isEmpty()) {
            Pair curr = queue.poll();

            // TOP VIEW RULE: Only add the node if this is the FIRST time we are seeing this HD
            if (!map.containsKey(curr.horizontalDistance)) {
                map.put(curr.horizontalDistance, curr.node.data);
            }

            if (curr.node.left != null) {
                queue.add(new Pair(curr.horizontalDistance - 1, curr.node.left));
            }
            if (curr.node.right != null) {
                queue.add(new Pair(curr.horizontalDistance + 1, curr.node.right));
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
    }

    /**
     * Prints the Bottom View of the binary tree.
     * <p>
     * <strong>Logic:</strong> We use Level-Order Traversal (BFS).
     * Because we traverse top-to-bottom, we continuously overwrite the Map for a given
     * Horizontal Distance. The last node processed at any HD will naturally be the bottom-most.
     * </p>
     * <p><strong>Time Complexity:</strong> O(N log N) - We visit N nodes, and TreeMap insertion is O(log N).</p>
     * <p><strong>Space Complexity:</strong> O(N) - Queue and Map can both hold up to N elements.</p>
     *
     * @param root The root of the tree.
     */
    public static void bottomView(Node root) {
        if (root == null) return;

        Queue<Pair> queue = new ArrayDeque<>();
        Map<Integer, Integer> map = new TreeMap<>();

        queue.add(new Pair(0, root));

        while (!queue.isEmpty()) {
            Pair curr = queue.poll();

            // BOTTOM VIEW RULE: Always overwrite. The lowest level node will have the final say.
            map.put(curr.horizontalDistance, curr.node.data);

            if (curr.node.left != null) {
                queue.add(new Pair(curr.horizontalDistance - 1, curr.node.left));
            }
            if (curr.node.right != null) {
                queue.add(new Pair(curr.horizontalDistance + 1, curr.node.right));
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
    }

    public static void main(String[] args) {
        int[] arrayTree = new int[]{10, 20, 40, -1, -1, 60, -1, -1, 30, 90, -1, -1, 100, -1, -1};
        System.out.print("Enter data (-1) for no child nodes : ");

        // Reset position before building
        position = 0;
        Node root = createTree(arrayTree);

        if (root == null) {
            System.out.println("Tree is Empty");
            return;
        }
//      Expected output [40 ,20, 60, 10, 90, 30, 100]
        System.out.print("\nIn- Order Tree traversal is : ");
        printTree(root);

        System.out.print("\nTop View : ");
        topView(root);

        System.out.print("\nBottom View : ");
        bottomView(root);

        System.out.println();
    }
}