public class BT09_Diameter_Of_BT {
    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    // Global index for Preorder array construction
    static int position = 0;

    public static Node createTree(int[] arrayTree) {
        if (position >= arrayTree.length) return null;

        int data = arrayTree[position++];
        if (data == -1) return null;

        Node root = new Node(data);

        System.out.print("\nCreating Left child for node [" + data + "]");
        root.left = createTree(arrayTree);

        System.out.print("\nCreating Right child for node [" + data + "]");
        root.right = createTree(arrayTree);

        return root;
    }

    public static void printTree(Node root) {
        if (root == null) return;
        printTree(root.left);
        System.out.print(root.data + " ");
        printTree(root.right);
    }

    /**
     * Computes the height of a binary tree.
     * <p><strong>Time Complexity:</strong> O(N) - Visits every node in the subtree exactly once.</p>
     * <p><strong>Space Complexity:</strong> O(H) - Call stack depth equals the height of the tree.</p>
     *
     * @param root The root of the subtree.
     * @return height of the subtree.
     */
    public static int heightOfBT(Node root) {
        if (root == null) return 0;
        return Math.max(heightOfBT(root.left), heightOfBT(root.right)) + 1;
    }

    /**
     * NAIVE APPROACH: Finds the diameter of the binary tree.
     * <p>
     * The diameter is the longest path between any two nodes. It may or may not pass through the root.
     * This logic checks three things:
     * <ol>
     *     <li>Diameter strictly in the left subtree.</li>
     *     <li>Diameter strictly in the right subtree.</li>
     *     <li>Diameter passing through the current root (Left Height + Right Height + 1).</li>
     * </ol>
     * </p>
     * <p><strong>Time Complexity:</strong> O(N^2) in the worst case (skewed tree). For every node,
     * we traverse its entire left and right subtrees to calculate their heights.</p>
     * <p><strong>Space Complexity:</strong> O(H) - Call stack depth equals the height of the tree.</p>
     *
     * @param root The root of the tree.
     * @return The diameter of the tree.
     */
    public static int findDiameter(Node root) {
        if (root == null) return 0;

        int leftDiameter = findDiameter(root.left);
        int rightDiameter = findDiameter(root.right);
        int currentDiameter = heightOfBT(root.left) + heightOfBT(root.right) + 1;

        return Math.max(leftDiameter, Math.max(rightDiameter, currentDiameter));
    }

    // Global variable to track the maximum diameter found during the optimized run
    static int maxDiameter = 0;

    /**
     * <strong>OPTIMIZED APPROACH:</strong> Calculates both height and diameter in a single traversal.
     * <p>
     * Instead of recalculating height for every node, this method returns the height to its
     * parent while continuously updating a global {@code maxDiameter} variable behind the scenes.
     * </p>
     *
     * <p><strong>Time Complexity:</strong> O(N) - Every node is visited exactly once.</p>
     * <p><strong>Space Complexity:</strong> O(H) - Call stack depth equals the height of the tree.</p>
     *
     * @param root The current node.
     * @return The height of the current subtree.
     */
    public static int optimizedDiameterLogic(Node root) {
        if (root == null) return 0;

        // Fetch heights of left and right subtrees (post-order traversal)
        int leftHeight = optimizedDiameterLogic(root.left);
        int rightHeight = optimizedDiameterLogic(root.right);

        // Calculate the diameter that passes through this specific node
        int currentDiameter = leftHeight + rightHeight + 1;

        // Update the global maximum diameter if this one is larger
        maxDiameter = Math.max(maxDiameter, currentDiameter);

        // Return the height of this subtree up to the parent
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        int[] arrayTree = new int[]{10, 20, 40, -1, -1, 60, -1, -1, 30, 90, -1, -1, 100, 5, -1, -1, -1};
        System.out.print("Starting tree construction from array...");

        // Reset globals
        position = 0;
        maxDiameter = 0;

        Node root = createTree(arrayTree);

        if (root == null) {
            System.out.println("Tree is Empty");
            return;
        }

        System.out.print("\n\nIn-Order Tree traversal is : ");
        printTree(root);

        System.out.print("\n\n--- Results ---");
        System.out.print("\nNaive Diameter (O(N^2)) is     : " + findDiameter(root));

        // To get the optimized diameter, we trigger the logic to run, then print the global max
        optimizedDiameterLogic(root);
        System.out.print("\nOptimized Diameter (O(N)) is   : " + maxDiameter);
        System.out.println();
    }
}