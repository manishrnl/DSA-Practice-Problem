package src;

import java.util.Arrays;

import static java.lang.System.out;

public class MergeSort {

    /**
     * Merge Sort is a divide-and-conquer algorithm that recursively breaks
     * down an array into smaller subarrays until each subarray contains a single
     * element (which is inherently sorted).
     * * * Logic:
     * 1. Divide: Find the midpoint and split the array into two halves.
     * 2. Conquer: Recursively call sort on both halves.
     * 3. Combine (Merge): Merge the two sorted halves back into a single
     * sorted sequence using a temporary auxiliary array.
     * * * Time Complexity:
     * - Best, Average, and Worst Case: O(N log N)
     * The array is divided into halves (log N levels), and at each level,
     * we perform N operations to merge the elements.
     * * * Space Complexity:
     * - O(N): Not in-place. It requires temporary arrays to store the
     * elements during the merging process.
     */
    public static int[] mergeSort(int[] array) {
        if (array.length < 2) {
            return array; // Base case: array is already "sorted"
        }

        int mid = array.length / 2;
        int[] left = new int[mid];
        int[] right = new int[array.length - mid];

        // STEP 1: Split the main array into two temporary subarrays
        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, array.length - mid);

        // STEP 2: Recursively sort both halves
        mergeSort(left);
        mergeSort(right);

        // STEP 3: Merge the sorted halves back together
        merge(array, left, right);
        return array;
    }

    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Compare elements from left and right arrays and pick the smaller one
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        // Collect any remaining elements from the left side
        while (i < left.length) {
            result[k++] = left[i++];
        }

        // Collect any remaining elements from the right side
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

    public static void main(String[] args) {
        int[] data = {29, 10, 14, 37, 13, 12, 3, 34, 456, 5, 4, 6, 56, 7, 6, 8, 78};

        out.println("\nOriginal: " + Arrays.toString(data));
        long start = System.nanoTime();
        mergeSort(data);
        long end = System.nanoTime();
        out.println("Sorted:   " + Arrays.toString(data) +
                " \n->Time taken to Execute is " + (end - start) + " ns");
    }
}