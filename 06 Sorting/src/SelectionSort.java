package src;
import java.util.Arrays;

import static java.lang.System.out;

public class SelectionSort {

    /**
     * Selection Sort is an in-place comparison-based algorithm that conceptually
     * divides the array into two parts:
     * 1. A sorted sublist (built from left to right).
     * 2. An unsorted sublist (the remainder of the array).
     * * Logic:
     * In each iteration, the algorithm scans the unsorted sublist to find the
     * minimum element and swaps it with the first element of that unsorted section.
     * This "selects" the correct element for the current position and expands the
     * sorted boundary by one.
     * * Time Complexity:
     * - Best, Average, and Worst Case: O(N²)
     * The algorithm uses nested loops. Even if the array is already sorted, it
     * must perform (N-1) + (N-2) + ... + 1 comparisons to confirm the minimum
     * element in each pass.
     * * Space Complexity:
     * - O(1): Sorting is performed in-place; no additional data structures are
     * required relative to the input size.
     */
    public static int[] selectionSort(int[] array) {
        int n = array.length;

        /**
         * The outer loop moves the boundary between the sorted and unsorted parts.
         * Everything to the left of 'i' is already sorted.
         *
         */
        for (int i = 0; i < n - 1; i++) {

            /**
             * STEP 1: Assume the first element of the unsorted part is the smallest.
             * We store its INDEX, not its value, so we can swap it later.
             */
            int minIndex = i;

            /**
             * STEP 2: The inner loop scans the remaining unsorted elements
             * (from i + 1 to the end) to find the actual minimum value.
             */
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    // We found a new "champion" smallest value
                    minIndex = j;
                }
            }

            /**
             * STEP 3: Swap the smallest element found with the element at index 'i'.
             * This effectively "selects" the minimum and places it in its final spot.
             */
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;

            /**
             * LOGIC CHECK:
             * After this swap, the element at index 'i' is officially sorted.
             * The loop then moves to 'i + 1' to find the next smallest.
             */
        }
        return array;
    }

    public static void main(String[] args) {
        int[] data = {29, 10, 14, 37, 13, 12, 3, 34, 456, 5, 4, 6, 56, 7, 6, 8, 78, 456, 79, 456, 89, 890, 456
                , 909, 0, 9, 0, 9, 0, 8, 9, 788, 7, 456, 67, 8, 6, 7, 5, 7, 56, 6, 546, 56, 5, 6, 56, 5, 6, 5, 6, 5, 6, 56, 456, 56, 5, 6, 56, 5
                , 6, 5, 6, 5, 6, 5, 6, 5, 66, 7, 67, 8, 78, 6, 3, 4, 2, 3, 13, 23, 45656767, 867, 89, 78890, 879, 89};

        out.println("\nOriginal: " + Arrays.toString(data));
        long start = System.nanoTime();
        selectionSort(data);
        long end = System.nanoTime();
        out.println("Sorted:   " + Arrays.toString(data) + " \n->Time taken to Execute is " + (end - start) + " ns");
    }
}