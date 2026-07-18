/**
 * A comprehensive utility class to check if a Singly/Doubly Linked List is a Palindrome.
 * Time Complexity: O(n) - We traverse the list a constant number of times.
 * Space Complexity: O(1) - The list is modified in-place without using extra memory collections.
 */
public class L03_Palindrome_LinkedList {

    /**
     * Definition for a Linked List Node.
     * Contains hooks for a Doubly Linked List ('prev'),
     * though the algorithm handles verification using only 'next' pointers.
     */
    public static class Node {
        int data;
        Node next;
        Node prev = null;

        public Node(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Finds the middle point of a linked list using Floyd's Tortoise and Hare Algorithm.
     *
     * @param head The starting node of the list.
     * @return The middle node. For even-length lists, this returns the first node of the second half.
     */
    public static Node calculateMiddle(Node head) {
        Node fast = head;
        Node slow = head;

        // Fast pointer moves at 2x speed, Slow pointer moves at 1x speed.
        // The condition (fast != null && fast.next != null) prevents NullPointerExceptions.
        while (fast != null && fast.next != null) {
            slow = slow.next;        // Shift 1 step
            fast = fast.next.next;   // Shift 2 steps
        }
        // When fast reaches the end, slow is guaranteed to sit precisely at the midpoint.
        return slow;
    }

    /**
     * Reverses a linked list in-place using an iterative three-pointer technique.
     *
     * @param head The starting node of the segment to be reversed.
     * @return The new head of the reversed list segment.
     */
    public static Node reverseNode(Node head) {
        Node curr = head;
        Node prev = null;

        while (curr != null) {
            Node temp = curr.next; // 1. Stash the remaining forward track
            curr.next = prev;      // 2. Flip the pointer backward
            prev = curr;           // 3. Slide the 'prev' tracking anchor forward
            curr = temp;           // 4. Move our active cursor to the stashed node
        }
        return prev; // 'prev' now captures the new starting node of this inverted chain
    }

    /**
     * Checks if the linked list forms a palindrome by splitting it in half,
     * reversing the second segment, and running an element-by-element comparison.
     *
     * @param head The start of the linked list.
     */
    public static void isPalindrome(Node head) {
        // Step A: Extract the middle element anchor point
        Node middle = calculateMiddle(head);

        // Step B: Invert the tail section of the list starting at that midpoint
        Node reverse = reverseNode(middle);

        boolean isPalindrome = true;
        Node tempHead = head;
        Node tempReverse = reverse;

        // Step C: Scan elements concurrently from the outer bounds inward
        while (tempReverse != null && tempHead != null) {
            if (tempHead.data != tempReverse.data) {
                isPalindrome = false;
                break; // Short-circuit early: one mismatch invalidates the entire structure
            }
            tempHead = tempHead.next;       // Advance left pointer inwards
            tempReverse = tempReverse.next; // Advance right pointer inwards
        }

        // Print final verdict evaluation
        if (isPalindrome)
            System.out.println("It is a palindrome.");
        else
            System.out.println("It is not a palindrome.");
    }

    /**
     * Utility method to print out the layout visual tracking sequence of a list.
     *
     * @param head The node sequence starting point to display.
     */
    public static void traverse(Node head) {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next; // Step forward smoothly
        }
        System.out.println("null");
    }

    /**
     * Main execution pathway.
     * Generates a dynamic test dataset: 7 -> 8 -> 9 -> 10 -> 9 -> 8 -> 7 -> null
     */
    public static void main(String[] args) {
        Node head = new Node(7);
        Node curr = head;

        // Dynamic array sequence generation loop
        int[] array = new int[]{8, 9, 10, 9, 8, 7};
        for (int i = 0; i < array.length; i++) {
            Node newNode = new Node(array[i]);
            curr.next = newNode;
            curr = curr.next;
        }

        System.out.print("Original List Structure: ");
        traverse(head);

        // Run checking computation sequence
        isPalindrome(head);
    }
}