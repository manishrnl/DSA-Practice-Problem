/**
 * A class demonstrating how to create, reverse, and traverse a Doubly Linked List.
 */
public class L02_Reverse_LinkedList {
    /**
     * Represents a Node in a Doubly Linked List.
     * Contains data, a pointer to the previous node, and a pointer to the next node.
     */
    public static class Node {
        int data;
        Node prev;
        Node next;

        public Node(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    /**
     * Reverses a Doubly Linked List in-place.
     *
     * <p>This method swaps the forward and backward pointers of every node
     * in the list to reverse its direction, then updates the head.</p>
     *
     * <h3>Complexity Analysis:</h3>
     * <ul>
     *   <li><b>Time Complexity:</b> O(N), where N is the number of nodes in the list.
     *       We must traverse and update every node exactly once.</li>
     *   <li><b>Space Complexity:</b> O(1) Auxiliary Space. The reversal is done in-place
     *       by rewriting pointers, using no extra memory.</li>
     *   </ul>
     *
     * @param head The head node of the Doubly Linked List to be reversed.
     *             Can be {@code null} (handles empty lists gracefully).
     * @return The new head node of the reversed Doubly Linked List.
     */
    public static Node reverseLinkedList(Node head) {
        Node curr = head;
        Node prev = null;

        // Traverse the list and swap next and prev pointers for every node
        while (curr != null) {
            // Swap pointers
            Node temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        // After the loop, temp points to the old second node (now second-to-last node).
        // The new head is temp's previous node in the modified list structure.
        return prev;
    }

    public static Node reverseLinkedList_Recursion(Node head) {
        // 1. BASE CASE: If empty or we reach the last node
        if (head == null || head.next == null) return head;

        // 2. RECURSE: Dive deep, get the new head node
        Node newHead = reverseLinkedList_Recursion(head.next);

        // 3. SHAKE HANDS: Turn the arrow backwards
        Node nextNode = head.next;
        nextNode.next = head;

        // 4. CLEAN UP: Break original forward link
        head.next = null;

        // 5. Pass the new head all the way back up
        return newHead;
    }

    /**
     * Traverses and prints the linked list from head to tail.
     * * @param head The head node of the linked list.
     */
    public static void traverse(Node head) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
//        print null for last linked elements as last node must store null value
        System.out.println("null");
    }

    public static void main(String[] args) {
        System.out.println("--- Creating Linked List (1 to 9) ---");
        Node head = new Node(1);
        Node curr = head;
        for (int i = 2; i < 10; i++) {
            Node newNode = new Node(i);
            curr.next = newNode;
            newNode.prev = curr;
            curr = newNode;
        }

        System.out.print("Original List: ");
        traverse(head);

        System.out.println("\n--- Reversing List ---");
        head = reverseLinkedList(head);

        System.out.print("Reversed List: Normal Approach               : ");
        traverse(head);

        head = reverseLinkedList_Recursion(head);
        System.out.print("Reverse of Reversed List: Recursion Approach : ");
        traverse(head);
    }
}