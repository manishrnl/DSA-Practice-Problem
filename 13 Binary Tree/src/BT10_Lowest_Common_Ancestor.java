public class BT10_Lowest_Common_Ancestor {

    public static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    // Global index for Preorder array construction
    public static int position = 0;

    public static Node createTree(int[] arrayTree) {
        // Safety check MUST happen before accessing arrayTree[position]
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
     * Finds the Lowest Common Ancestor (LCA) of two target values in a Binary Tree.
     * <p>
     * The LCA is defined as the lowest node in the tree that has both {@code a}
     * and {@code b} as descendants (where a node can be a descendant of itself).
     * </p>
     *
     * <p><strong>Time Complexity:</strong> O(N) - In the worst case, we visit every node once.</p>
     * <p><strong>Space Complexity:</strong> O(H) - Call stack depth equals the height of the tree.</p>
     *
     * @param root The root of the tree/subtree.
     * @param a    The data value of the first target node.
     * @param b    The data value of the second target node.
     * @return The {@code Node} that is the LCA of {@code a} and {@code b}, or {@code null} if neither is found.
     */
    public static Node lowestCommonAncestor(Node root, int a, int b) {
        // Base Case 1: Reached end of a branch
        if (root == null) return null;

        // Base Case 2: Found one of the target nodes
        if (root.data == a || root.data == b) return root;

        // Search for target nodes in left and right subtrees
        Node left = lowestCommonAncestor(root.left, a, b);
        Node right = lowestCommonAncestor(root.right, a, b);

        // If both subtrees returned non-null, 'a' and 'b' are on opposite sides of 'root'.
        // Hence, 'root' is the Lowest Common Ancestor!
        if (left != null && right != null) return root;

        // Otherwise, return whichever subtree found one of the target nodes
        return (left != null) ? left : right;
    }

    public static void main(String[] args) {
        int[] arrayTree = new int[]{10, 20, 40, -1, -1, 60, -1, -1, 30, 90, -1, -1, 100, 5, -1, -1, -1};
        position = 0; // Reset index before building

        Node root = createTree(arrayTree);
        if (root == null) {
            System.out.println("Tree is Empty");
            return;
        }

        System.out.print("\nIn-Order Tree traversal is : ");
        printTree(root);

        int node1 = 30;
        int node2 = 9750;
        Node lca = lowestCommonAncestor(root, node1, node2);

        if (lca != null) {
            System.out.print("\nLowest Common Ancestor of (" + node1 + ", " + node2 + "): ");
            System.out.println(lca.data);
        } else {
            System.out.println("\nNo LCA found (one or both nodes missing).");
        }
    }
}