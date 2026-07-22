public class BT11_Burn_BT {
    public static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    /**
     * Helper wrapper class to hold and update the distance to the target node
     * across recursive call stack frames.
     */
    public static class Depth {
        int d;

        public Depth(int d) {
            this.d = d;
        }
    }

    public static int ans = 0;    // Global variable to store the maximum time needed to burn the entire tree
    public static int position = 0;    // Global index for Preorder array construction

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
     * Calculates the minimum time (in seconds) required to burn the entire Binary Tree
     * starting from a given target node.
     * <p>
     * Fire spreads to all adjacent connected nodes (left child, right child, and parent)
     * simultaneously at a rate of 1 edge per second.
     * </p>
     *
     * <p><strong>Time Complexity:</strong> O(N) - Every node in the binary tree is visited once.</p>
     * <p><strong>Space Complexity:</strong> O(H) - Call stack depth is proportional to tree height.</p>
     *
     * @param root   The root node of the binary tree.
     * @param target The value of the node where the fire starts.
     * @return The maximum time in seconds needed to burn every node in the tree.
     */
    public static int minTime(Node root, int target) {
        Depth depth = new Depth(-1);
        ans = 0; // Reset global answer variable
        burnTree(root, target, depth);
        return ans;
    }

    /**
     * Helper method using Post-Order traversal to compute subtree heights
     * while tracking distance from the target node.
     *
     * @param root   The current node being processed.
     * @param target The value of the node where the fire starts.
     * @param depth  Wrapper object tracking distance of current node from target (-1 if target not in subtree).
     * @return The height of the subtree rooted at {@code root}.
     */
    public static int burnTree(Node root, int target, Depth depth) {
        if (root == null) return 0;

        Depth ld = new Depth(-1);
        Depth rd = new Depth(-1);

        // 1. Calculate heights of left and right subtrees first (Post-Order)
        int lh = burnTree(root.left, target, ld);
        int rh = burnTree(root.right, target, rd);

        // 2. Case A: Current node is the target node
        if (root.data == target) {
            depth.d = 0; // Distance to itself is 0
            // Max time to burn target's own subtrees
            ans = Math.max(ans, Math.max(lh, rh));
            return Math.max(lh, rh) + 1;
        }

        // 3. Case B: Target was found in the left subtree
        if (ld.d != -1) {
            ans = Math.max(ans, ld.d + 1 + rh);
            depth.d = ld.d + 1;
        }
        // 4. Case C: Target was found in the right subtree
        else if (rd.d != -1) {
            ans = Math.max(ans, rd.d + 1 + lh);
            depth.d = rd.d + 1;
        }

        return Math.max(lh, rh) + 1;
    }

    public static void main(String[] args) {
        int[] arrayTree = new int[]{10, 20, 40, -1, -1, 60, -1, -1, 30, 90, -1, -1, 100, 5, -1, -1, -1};
        position = 0;
        Colorful_Console_Logs log = new Colorful_Console_Logs();
        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);
        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!");
        int nodeToBurn = 40;
        log.success("\nIn-Order Traversal: ");
        printTree(root);
        log.success("\n");
        log.warn("Considering 1 second burn time per connecting node.");
        int totalTime = minTime(root, nodeToBurn);
        log.success("Time taken to burn tree from node [" + nodeToBurn + "] is " + totalTime + " seconds.");
    }
}