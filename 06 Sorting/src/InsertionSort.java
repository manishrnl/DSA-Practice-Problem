package src;
import java.util.Arrays;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

public class InsertionSort {

    /**
     * LOGIC for Insertion Sort
     * Think of the array as being split into two parts: a sorted part on the left and an unsorted part on the right.
     * 1. Initially, the "sorted" part is just the first element array[0], because a single
     * number is always sorted by itself.
     * 2. We pick the first element from the unsorted part (the "Key").
     * 3. We look at the sorted part to its left and shift every number that is larger than
     * our "Key"  one position to the right.
     * 4. This creates an empty slot where the "Key" finally fits.
     * 5. The "Sorted Wall" moves one step to the right, and we repeat
     * T.C: Best O(N) , Average O(N^2), and Worst O(N^2)
     * S.C: O(1) - Sorting is performed in-place without auxiliary memory.
     */
    public static int[] insertionSort(int[] array) {
        int length = array.length;

        // Start from the second element (index 1)
        for (int i = 1; i < length; i++) {
            int key = array[i]; // 1. Pick the element to be inserted
            int j = i - 1;      // 2. Start comparing with the element immediately to the left

            // 3. The "Shifting" Phase
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]; // Move the larger element to the right
                j = j - 1;               // Move the pointer further left
            }

            // 4. The "Insertion" Phase
            array[j + 1] = key; // Drop the key into its correct hole
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = {1, 4, 3, 2, 6, 4, 77, 89, 54, 23, 78, 13};

        long startFunction = nanoTime();
        int[] result = insertionSort(array);
        long endFunction = nanoTime();

        out.println("Result for Insertion Sort : " + Arrays.toString(result));
        out.println("-> Time taken by algorithm is: " + (endFunction - startFunction) + " ns");
    }
}