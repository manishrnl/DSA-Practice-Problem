import java.util.Collections;
import java.util.PriorityQueue;

/**
 * <h2>Sliding Window Maximum Finder</h2>
 * <p>
 * This class provides an implementation to compute the maximum value within every
 * contiguous sub-array (window) of a fixed size {@code k} as it moves across an input array.
 *
 * <p><b>Algorithmic Strategy:</b></p>
 * This variant utilizes a Max-Heap (via {@link PriorityQueue} configured with a reverse order comparator)
 * to dynamically track the largest element of the window currently in scope.
 *
 * <pre>
 * Complexity Profile:
 * - Time Complexity: {@code O(n * k)} where {@code n} is the array length and {@code k}is the window size.
 *   This is due to the PriorityQueue.remove(Object) method operating in linear time {@code O(k)} internally.
 * - Space Complexity: {@code O(k)} to maintain the elements inside the max-heap window container. </pre>
 */
public class Q06_Max_Number_In_Sliding_Window_K {

    public static void maxNumber(int[] array, int windowSize) {
        if (array == null || array.length == 0 || windowSize <= 0 || windowSize > array.length)
            throw new IllegalArgumentException("Invalid input array dimensions or window configuration.");

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());  // Max-Heap Priority Queue

        for (int i = 0; i < windowSize; i++) maxHeap.add(array[i]);

        System.out.println("Initial Max Heap configuration: " + maxHeap);
        System.out.print("Maximum Number per Sliding Window of size " + windowSize + " is : ");

        int nextElementPointer = windowSize;

        // Step 2: Slide the frame incrementally over the remaining segments of the array
        for (int i = 0; i < array.length - windowSize + 1; i++) {
            System.out.print(maxHeap.peek() + " ");

            // If there's a new incoming element down the road, cache it into the heap structure
            if (nextElementPointer < array.length) {
                maxHeap.add(array[nextElementPointer]);
                nextElementPointer++;
            }
            maxHeap.remove(array[i]);   // Remove element from left one-by-one as its work is done
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {4, 1, 3, 5, 1, 2, 3, 2, 1, 1, 5};
        int windowSize = 5;

        maxNumber(array, windowSize);
    }
}