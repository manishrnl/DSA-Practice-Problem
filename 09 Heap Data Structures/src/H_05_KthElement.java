import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * The H_05_KthElement class provides efficient solutions to find both the
 * k-th largest and k-th smallest elements in an unsorted array using Heaps.
 */
public class H_05_KthElement {

    /**
     * Finds the k-th largest element in an unsorted integer array using a Min-Heap.
     * * Time Complexity: O(N log k)
     * Space Complexity: O(k)
     */
    public static int kthLargestElements(int[] array, int k) {
        if (array == null || k <= 0 || k > array.length) {
            throw new IllegalArgumentException("Invalid value of k or null array.");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < k; i++) {
            minHeap.add(array[i]);
        }

        for (int i = k; i < array.length; i++) {
            if (minHeap.peek() < array[i]) {
                minHeap.poll();
                minHeap.add(array[i]);
            }
        }

        return minHeap.peek();
    }

    /**
     * Finds the k-th smallest element in an unsorted integer array using a Max-Heap.
     * * <p><strong>Approach: Max-Heap</strong><br>
     * 1. Initialize a Max-Heap and insert the first 'k' elements.<br>
     * 2. For the remaining elements, if an element is smaller than the largest element
     * in our Max-Heap (the root), it belongs in the top 'k' smallest elements.<br>
     * 3. Replace the root with the smaller element.<br>
     * 4. The root of the Max-Heap will eventually hold the k-th smallest element.
     * </p>
     * * Time Complexity: O(N log k)
     * Space Complexity: O(k)
     */
    public static int kthSmallestElements(int[] array, int k) {
        if (array == null || k <= 0 || k > array.length) {
            throw new IllegalArgumentException("Invalid value of k or null array.");
        }

        // Initialize a Max-Heap using Collections.reverseOrder()
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // Step 1: Add the first 'k' elements into the Max-Heap
        for (int i = 0; i < k; i++) {
            maxHeap.add(array[i]);
        }

        // Step 2: Iterate through the remaining elements
        for (int i = k; i < array.length; i++) {
            // FIX: Changed 'while' to 'if' to prevent an infinite loop
            if (maxHeap.peek() > array[i]) {
                maxHeap.poll();        // Remove the largest element among the current smallest 'k'
                maxHeap.add(array[i]); // Insert the even smaller element
            }
        }

        // Step 3: The root of the Max-Heap is the k-th smallest element
        return maxHeap.peek();
    }

    /**
     * Main method to execute and test the functionality.
     */
    public static void main(String[] args) {
        int[] array = {11, 2, 31, 4, 5, 43, 293, 45, 67, 87, 45, 23, 22, 12, 40};
        int k = 5;

        System.out.print("Array elements : " + Arrays.toString(array) + "\n");

        System.out.println("The " + k + " -st/nd/rd/th largest element is: " + kthLargestElements(array, k));
        System.out.println("The " + k + " -st/nd/rd/th smallest element is: " + kthSmallestElements(array, k));
    }
}