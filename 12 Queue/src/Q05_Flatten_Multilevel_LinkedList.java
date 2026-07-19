import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <h2>Problem: Flattening a Multilevel Linked List</h2>
 * <p>
 * This class provides a solution for flattening a data structure where each node
 * contains standard {@code next} pointers as well as a {@code down} pointer to a
 * child linked list.
 *
 * <p><b>Flattening Strategy (Breadth-First / Level-Order):</b></p>
 * This implementation flattens the list level-by-level (FIFO order). The entire
 * first level is preserved, then the entire second level is appended to the end,
 * followed by the third level, and so on.
 *
 * <pre>
 * Visual Representation of Level-Order Flattening:
 *
 *   Level 1:  [1] -> [2] --------> [3] -> null
 *                     |
 *   Level 2:         [4] -> [5] -> null
 *                     |
 *   Level 3:         [6] -> null
 *
 *   Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null </pre>
 */
public class Q05_Flatten_Multilevel_LinkedList {

    public static class Node {
        public int data;
        public Node next;
        public Node down;

        public Node(int data) {
            this.data = data;
            this.next = null;
            this.down = null;
        }

        /**
         * Flattens a multilevel linked list into a standard, single-level linear list.
         *
         * <p><b>Complexity Analysis:</b></p>
         * <ul>
         *   <li><b>Time Complexity:</b> {@code O(n)}, where {@code n} is the total number of nodes in
         *   all levels combined. Every node is visited exactly twice (once to check for
         *   downward pointers, and once when advancing the tail).</li>
         *   <li><b>Space Complexity:</b> {@code O(k)}, where {@code k} is the maximum number of child
         *   list heads waiting across levels, scaling linearly with the max width of the multi-level structure.</li>
         * </ul>
         *
         * @param head the entry node (head) of the multilevel list.
         * @return the head node of the newly mutated, flat, linear linked list.
         */
        public Node flattenLinkedList(Node head) {
            // Edge Case: If the list is empty, return null immediately
            if (head == null) {
                return null;
            }

            // The tail pointer will continuously map out the current end of our flat list
            Node tail = head;

            // Queue to store references to child nodes discovered along the way
            Queue<Node> queue = new ArrayDeque<>();

            /**
             * PHASE 1: Process the primary main level.
             * Traverse to the end of Level 1, extracting any child nodes into the queue.
             */
            while (tail.next != null) {
                if (tail.down != null) {
                    queue.add(tail.down);
                    tail.down = null; // Sever the down pointer to clean up memory
                }
                tail = tail.next;
            }

            // Critical check: Ensure the absolute final node of the first level doesn't house a down pointer
            if (tail.down != null) {
                queue.add(tail.down);
                tail.down = null;
            }

            /**
             * PHASE 2: Process the queued child lists.
             * Pull a child head out, stitch it to the tail, and move the tail to the new end.
             */
            while (!queue.isEmpty()) {
                Node subHead = queue.poll();
                tail.next = subHead; // Connect the end of the flat list to the start of the sub-list

                // Drive the tail to the end of this newly integrated horizontal segment
                while (tail.next != null) {
                    if (tail.down != null) {
                        queue.add(tail.down);
                        tail.down = null;
                    }
                    tail = tail.next;
                }

                // Critical check: Check the final node of the current sub-segment
                if (tail.down != null) {
                    queue.add(tail.down);
                    tail.down = null;
                }
            }
            return head;
        }
    }

    /**
     * <pre>Constructing the following Multilevel Architecture:
     * 1 -> 2 -> 3
     *      |
     *      4 -> 5
     *      |
     *      6
     * </pre>
     */
    public static void main(String[] args) {

        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);

        // Attaching level 2 child below node '2'
        head.next.down = new Node(4);
        head.next.down.next = new Node(5);

        // Attaching level 3 child below node '4'
        head.next.down.down = new Node(6);

        // Flatten the structure
        Node result = head.flattenLinkedList(head);

        // Render output to console
        System.out.print("Flattened List Sequence: ");
        Node temp = result;
        while (temp != null) {
            System.out.print(temp.data + (temp.next != null ? " -> " : " -> null"));
            temp = temp.next;
        }
        System.out.println();
    }
}