/**
 * Class to detect cycles in a Linked List using Floyd's Cycle-Finding Algorithm.
 * Time Complexity: O(n) - Linear traversal.
 * Space Complexity: O(1) - Constant memory allocation.
 */
public class L04_Detect_Cycle_LinkedList {

    public static class Node {
        int data;
        Node next;
        Node prev = null; // Left in for consistency with your Node definition

        public Node(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Safely traverses and prints the linked list.
     * Prevents infinite printing loops if a cycle is present.
     */
    public static void traverseWithSafety(Node head) {
        Node curr = head;
        int limit = 20;
        int count = 0;

        while (curr != null && count < limit) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
            count++;
        }
        if (curr != null) {
            System.out.println("... (Loop detected, stopping print)");
        } else {
            System.out.println("null");
        }
    }

    /**
     * Resolves the exact starting node of a cycle.
     *
     * MATH BEHIND THIS:
     * Distance from Head to CycleStart = Distance from MeetingPoint to CycleStart.
     * Therefore, moving two pointers at 1x speed from these positions guarantees they meet at the start node.
     *
     * @param head The original head of the linked list.
     * @param meetingPoint The node where slow and fast collided.
     */
    public static void findCyclePoints(Node head, Node meetingPoint) {
        if (meetingPoint == null) {
            System.out.println("No cycle to find points for.");
            return;
        }

        Node slow = head;          // Pointer 1 starts at the beginning
        Node fast = meetingPoint;  // Pointer 2 starts at the collision zone

        // Walk both pointers at exactly 1x speed
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // When they collide again, they are guaranteed to be on the cycle's starting node!
        System.out.println("Cycle found at Node: " + slow.data);
    }

    /**
     * Detects if a cycle exists in the linked list using the Tortoise & Hare method.
     *
     * @param head The head node of the linked list.
     */
    public static void detectCycle(Node head) {
        if (head == null || head.next == null) {
            System.out.println("No cycle found (List is too short).");
            return;
        }

        Node slow = head;
        Node fast = head;

        // Loop runs safely until the fast pointer reaches the end of a non-cyclic list
        while (fast != null && fast.next != null) {
            slow = slow.next;        // Moves 1 step
            fast = fast.next.next;   // Moves 2 steps

            // If they meet, a cycle exists
            if (slow == fast) {
                // Pass the head and our current meeting node to locate the cycle entrance
                findCyclePoints(head, slow);
                return;
            }
        }

        System.out.println("No cycle detected in this list.");
    }

    public static void main(String[] args) {
        // Step 1: Initialize the list head (Node 1)
        Node head = new Node(1);
        Node curr = head;

        // Step 2: Build a basic chain: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        int[] array = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node targetCycleNode = null;

        for (int i = 0; i < array.length; i++) {
            Node newNode = new Node(array[i]);
            curr.next = newNode;
            curr = curr.next;

            // Capture Node 4 as our loop destination
            if (array[i] == 4) {
                targetCycleNode = newNode;
            }
        }

        // Step 3: Loop Node 10's next pointer back to Node 4
        if (targetCycleNode != null) {
            curr.next = targetCycleNode;
        }

        System.out.print("Simulated list layout: ");
        traverseWithSafety(head);

        // Step 4: Run our cycle checker
        detectCycle(head);
    }
}