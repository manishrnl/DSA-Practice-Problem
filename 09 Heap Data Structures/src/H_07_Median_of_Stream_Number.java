import java.util.Collections;
import java.util.PriorityQueue;

/**
 * The H_07_Median_of_Stream_Number class provides an optimal solution for
 * finding the median of a continuously flowing stream of numbers in O(log N) time.
 */
public class H_07_Median_of_Stream_Number {

    // maxHeap stores the smaller half of the numbers. The largest element of this lower half is at the top.
    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    // minHeap stores the larger half of the numbers. The smallest element of this upper half is at the top.
    private final PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    /**
     * Inserts a new number from the stream into the appropriate heap
     * and rebalances the heaps to maintain the size invariant.
     *
     * @param num The incoming integer from the stream.
     */
    public void insertNumber(int num) {
        // Step 1: Insert the number into the correct heap.
        // If the number is bigger than topmost of maxHeap, it means this number belongs to maxHeap
        if (maxHeap.isEmpty() || maxHeap.peek() >= num) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }

        // Step 2: Rebalance the heaps.
        // PRECONDITION: maxHeap is allowed to have at most ONE more element than minHeap
        // For simplicity . Alternatively we can have at most ONE more elements in minHeap
        // but then code logic will change fully.
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.add(maxHeap.poll());
        } else if (maxHeap.size() < minHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }

    /**
     * Computes and returns the median of all the numbers read so far from the stream.
     *
     * @return The median as a double value.
     * @throws IllegalStateException If called when no numbers have been inserted yet.
     */
    public double findMedian() {
        // Edge Case: Check if both heaps are empty to prevent NullPointerException
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            throw new IllegalStateException("No elements in the stream to find a median.");
        }

        // If both heaps have the same size, the total number of elements is even.
        // The median is the average of the tops of both heaps.
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        // If sizes are unequal, maxHeap has the extra element (odd total elements).
        // The median is simply the top of the maxHeap.
        else {
            return maxHeap.peek();
        }
    }

    /**
     * Main method to simulate a stream of incoming numbers and print the running median.
     */
    public static void main(String[] args) {
        H_07_Median_of_Stream_Number sol = new H_07_Median_of_Stream_Number();

        // Stream: [3] -> Median: 3.0
        sol.insertNumber(3);
        System.out.println("Current Median: " + sol.findMedian());

        // Stream: [3, 5] -> Median: (3 + 5) / 2 = 4.0
        sol.insertNumber(5);
        System.out.println("Current Median: " + sol.findMedian());

        // Stream: [3, 5, 55] -> Median: 5.0
        sol.insertNumber(55);
        System.out.println("Current Median: " + sol.findMedian());

        // Stream: [3, 5, 54, 55] -> Median: (5 + 54) / 2 = 29.5
        sol.insertNumber(54);
        System.out.println("Current Median: " + sol.findMedian());
    }
}