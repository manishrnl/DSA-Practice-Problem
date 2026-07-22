public class BT08_BT_To_Doubly_LinkedList {

    public static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Global index for Preorder array construction
    public static int position = 0;

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
     * Iterates through the newly formed Doubly Linked List and prints it.
     *
     * @param head The head node of the Doubly Linked List.
     */
    public static void printQueue(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.right; // In our DLL, 'right' is the 'next' pointer
        }
    }

    // Global pointers to track state during the in-place conversion
    static Node prev = null, head = null;

    /**
     * Converts a Binary Tree into an in-place Doubly Linked List based on In-Order traversal.
     * <p>
     * <strong>Logic:</strong> We traverse the tree In-Order (Left, Root, Right).
     * - The {@code head} pointer locks onto the very first node processed (the leftmost node).
     * - The {@code prev} pointer keeps track of the previously processed node.
     * - We link {@code prev.right = curr} and {@code curr.left = prev}.
     * </p>
     *
     * @param root The current node being processed.
     * @return The head of the newly formed Doubly Linked List.
     */
    public static Node binaryToDoublyLinkedList(Node root) {
        if (root == null) return null;

        // 1. Convert the left subtree
        binaryToDoublyLinkedList(root.left);

        // 2. Process the current node
        if (prev == null) {
            // If prev is null, this is the leftmost node. It becomes the head of the DLL.
            head = root;
        } else {
            // Wire the current node and the previous node together
            root.left = prev;
            prev.right = root;
        }

        // Move the prev pointer forward to the current node
        prev = root;

        // 3. Convert the right subtree
        binaryToDoublyLinkedList(root.right);

        return head;
    }

    public static void main(String[] args) {
        int[] arrayTree = new int[]{10, 20, 40, -1, -1, 60, -1, -1, 30, 90, -1, -1, 100, -1, -1};
        System.out.print("Starting tree construction from array...");

        // Reset globals (Best practice if main is executed repeatedly in a test suite)
        position = 0;
        prev = null;
        head = null;

        Node root = createTree(arrayTree);

        if (root == null) {
            System.out.println("Tree is Empty");
            return;
        }
//      Expected output [40 ,20, 60, 10, 90, 30, 100]

        System.out.print("\n\nIn-Order Tree traversal is : ");
        printTree(root);

        // Convert and capture the new head
        Node listHead = binaryToDoublyLinkedList(root);

        System.out.print("\nBinary Tree to Doubly Linked List: ");
        printQueue(listHead);
        System.out.println();
    }
}