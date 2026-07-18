import java.util.Collections;
import java.util.PriorityQueue;

/**
 * The H_04_PriorityQueueIntro class demonstrates the fundamentals of using
 * Java's PriorityQueue for both natural ordering (Min-Heap) and custom
 * reverse ordering (Max-Heap).
 */
public class H_04_PriorityQueueIntro {

    /**
     * Demonstrates an ascending order Priority Queue.
     * By default, Java's {@code PriorityQueue} implements a Min-Heap.
     * The element with the lowest value is assigned the highest priority
     * and is kept at the head (peek/poll position) of the queue.
     * </p>
     */
    public static void ascendingPriorityQueue() {
        // Create a default Min-Heap Priority Queue
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Add unsorted elements into the queue
        pq.add(5);
        pq.add(15);
        pq.add(10);
        pq.add(20);

        // Process and print elements until the queue is empty
        // Elements will be retrieved in sorted ascending order (5 10 15 20)
        while (!pq.isEmpty()) {
            System.out.print(pq.peek() + " "); // Inspect the element at the head
            pq.poll();                         // Remove the element from the head
        }
    }

    /**
     * Demonstrates a descending order Priority Queue.
     * <p>
     * By passing {@code Collections.reverseOrder()} into the constructor,
     * the queue behaves as a Max-Heap. The element with the highest value
     * is assigned the highest priority and is kept at the head.
     * </p>
     */
    public static void descendingPriorityQueue() {
        // Create a Max-Heap Priority Queue using a reverse-order Comparator
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        // Add unsorted elements into the queue
        pq.add(5);
        pq.add(15);
        pq.add(10);
        pq.add(20);

        // Process and print elements until the queue is empty
        // Elements will be retrieved in sorted descending order (20 15 10 5)
        while (!pq.isEmpty()) {
            System.out.print(pq.peek() + " "); // Inspect the element at the head
            pq.poll();                         // Remove the element from the head
        }
    }

    /**
     * Main method to execute and showcase the difference between
     * Min-Heap and Max-Heap behavior.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        System.out.print("Ascending order Priority Queue : ");
        ascendingPriorityQueue();

        System.out.print("\nDescending order Priority Queue : ");
        descendingPriorityQueue();
        System.out.println(); // Prints a final trailing newline
    }
}