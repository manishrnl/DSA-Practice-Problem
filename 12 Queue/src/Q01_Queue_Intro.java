/**
 * <h1>Queue Implementation using a Singly Linked List Structure</h1>
 *
 * <h3>📋 Exhaustive Architectural Mechanics</h3>
 * <pre >
 * 1. REAL-WORLD ANALOGY:
 *    Operates precisely like a physical queue standing at a railway ticket window counter.
 *    -> New individuals step into line from the BACK edge (rear boundary).
 *    -> The individual at the absolute FRONT edge gets served first and exits (front boundary).
 *    -> Follows a strict FIFO strategy: First-In, First-Out lifecycle management.
 *
 * 2. STRUCTURAL BOUNDARIES:
 *    -> front pointer tracks the Head node element: Used exclusively for deQueue operations.
 *    -> rear pointer tracks the Tail node element: Used exclusively for enQueue operations.
 * </pre>
 *
 * <h3>⚙️ State Processing Lifecycle Matrix</h3>
 * <pre>
 *   Initial State: Queue is Empty [ front = null, rear = null ]
 *
 *   Operation   │ Node State Changes               │ Front Pointer Value │ Rear Pointer Value
 *   ════════════╪══════════════════════════════════╪═════════════════════╪════════════════════
 *   enQueue(10) │ First Node Added                 │ [10]                │ [10]
 *   enQueue(20) │ Tail Appended: 10 -> 20          │ [10]                │ [20]
 *   enQueue(30) │ Tail Appended: 10 -> 20 -> 30    │ [10]                │ [30]
 *   deQueue()   │ Returns 10. Front moves to 20    │ [20]                │ [30]
 *   deQueue()   │ Returns 20. Front moves to 30    │ [30]                │ [30]
 *   ════════════╧══════════════════════════════════╧═════════════════════╧════════════════════
 * </pre>
 */
public class Q01_Queue_Intro {

    /**
     * Internal data container block node mapping pointers dynamically.
     */
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    /**
     * Inserts an element into the rear boundary location of the queue structure.
     *
     * @param data The primitive integer payload value to append to the queue.
     */
    public void offer(int data) {
        Node temp = new Node(data);

        // Context Scenario A: The queue structure is completely empty
        if (front == null) {
            front = rear = temp;
            return; // Explicit return avoids running the else-logic loop bug
        }

        // Context Scenario B: General case tail pointer adjustment
        rear.next = temp;
        rear = temp;
    }

    /**
     * Removes and extracts the oldest element located at the front boundary of the queue.
     *
     * @return The primitive integer payload data value stored inside the dropped node element.
     * @throws RuntimeException if an execution drop is attempted against an empty collection.
     */
    public int poll() {
        if (front == null) {
            throw new RuntimeException("Cant delete Empty Queue");
        }

        int result = front.data;
        front = front.next;

        // Clean up boundary pointer state if the queue becomes completely empty
        if (front == null) {
            rear = null;
        }

        return result;
    }

    public static void main(String[] args) {
        Q01_Queue_Intro q = new Q01_Queue_Intro();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        q.offer(4);
        q.offer(5);
        System.out.println("De Queue value: " + q.poll());
        System.out.println("De Queue value: " + q.poll());
        System.out.println("De Queue value: " + q.poll());

    }
}