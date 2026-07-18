/**
 * A clean, robust implementation of a Singly Linked List.
 * Demonstrates creation, traversal, insertion (at head, tail, or any index), and deletion.
 */
public class L01_Linked_List_Info {
    /**
     * Inner class representing a single Node in the Singly Linked List.
     */
    public static class Node {
        public int data;
        public Node next;

        // Constructor to initialize a node with data
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Traverses the linked list starting from the head and prints each element.
     * <p>
     * <strong>Time Complexity:</strong> O(N) where N is the number of nodes.<br>
     * <strong>Space Complexity:</strong> O(1)
     * </p>
     *
     * @param head The start node of the linked list.
     */
    public static void traverse(Node head) {
        if (head == null) {
            System.out.println("The list is empty.");
            return;
        }

        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    /**
     * Inserts a new node with the specified data at the beginning (head) of the list.
     * <p>
     * <strong>Time Complexity:</strong> O(1)<br>
     * </p>
     *
     * @param head The current head of the list.
     * @param data The value to insert.
     * @return The new head of the linked list.
     */
    public static Node insertAtHead(Node head, int data) {
        Node newNode = new Node(data);
        newNode.next = head; // Point new node to the old head
        return newNode;      // New node is now the new head
    }

    /**
     * Inserts a new node with the specified data at the end (tail) of the list.
     * <p>
     * <strong>Time Complexity:</strong> O(N) to traverse to the end.
     * </p>
     *
     * @param head The current head of the list.
     * @param data The value to insert.
     * @return The head of the linked list (remains unchanged unless list was empty).
     */
    public static Node insertAtTail(Node head, int data) {
        Node newNode = new Node(data);

        // If the list is empty, the new node becomes the head
        if (head == null) {
            return newNode;
        }
//      It gets the last node address , once found last node , it gets  nodes address and
//      save it inside curr variable
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
//    We had successfully retrieved the last nodes address , now inserting data at last node
        curr.next = newNode;
        return head;
    }

    /**
     * Inserts a node at any specified 0-based index/position in the list.
     * <p>
     * <strong>Time Complexity:</strong> O(N) in the worst case (inserting near the end).
     * </p>
     *
     * @param head     The current head of the list.
     * @param data     The value to insert.
     * @param position The 0-based index where the new node should be placed.
     * @return The updated head of the list.
     */
    public static Node insertAnywhere(Node head, int data, int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be negative.");
        }
        Node toInsert = new Node(data);

        // Case 1: Inserting at position 0 (updates head)
        if (position == 0) {
            toInsert.next = head;
            return toInsert; // Return the new head node back to main
        }

        // Case 2: Traverse to the node right BEFORE the target insertion position
        Node prev = head;
        for (int i = 0; i < position - 1; i++) prev = prev.next;

        // Double check if we reached null unexpectedly
        if (prev == null) {
            System.out.println("Position " + position + " is out of bounds. Element not inserted.");
            return head;
        }

        // Case 3: Re-wire the pointers to insert the new node
        toInsert.next = prev.next;
        prev.next = toInsert;

        return head;
    }

    /**
     * Deletes the first occurrence of a node containing the specified value.
     * <p>
     * <strong>Time Complexity:</strong> O(N) in the worst case.
     * </p>
     *
     * @param head  The current head of the list.
     * @param value The value to be removed.
     * @return The head of the linked list after deletion.
     */
    public static Node deleteByValue(Node head, int value) {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete.");
            return null;
        }

        if (head.data == value) {
            return head.next; // Bypasses the head
        }

        Node curr = head;
        while (curr.next != null && curr.next.data != value) {
            curr = curr.next;
        }

        if (curr.next != null) {
//            we had remmoved the reference of the node , Java will delete the node which is
//            not referenced by any node automatically via Garbage Collectors
            curr.next = curr.next.next;
        } else {
            System.out.println("Value " + value + " not found in the list.");
        }

        return head;
    }

    /**
     * Main method to test and run the Linked List operations.
     */
    public static void main(String[] args) {
        Node head = null;

        System.out.println("--- Inserting Elements ---");
        head = insertAtTail(head, 12);
        head = insertAtTail(head, 43);
        head = insertAtTail(head, 45);
        head = insertAtTail(head, 34);
        head = insertAtTail(head, 3);

        System.out.print("Initial List: ");
        traverse(head); // Expected: 12 -> 43 -> 45 -> 34 -> 3 -> null

        // Insert at head
        System.out.println("\nInserting 100 at Head:");
        head = insertAtHead(head, 100);
        traverse(head); // Expected: 100 -> 12 -> 43 -> 45 -> 34 -> 3 -> null

        // Deleting values
        System.out.println("\nDeleting 45 (Middle element):");
        head = deleteByValue(head, 45);
        traverse(head); // Expected: 100 -> 12 -> 43 -> 34 -> 3 -> null

        System.out.println("\nDeleting 100 (Head element):");
        head = deleteByValue(head, 100);
        traverse(head); // Expected: 12 -> 43 -> 34 -> 3 -> null

        // Insert at position 3
        System.out.println("\nInserting 290 at Position 3:");
        head = insertAnywhere(head, 290, 3); // Make sure you assign the return value back to head!
        traverse(head); // Expected: 12 -> 43 -> 34 -> 290 -> 3 -> null
    }
}