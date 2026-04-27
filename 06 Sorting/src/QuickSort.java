package src;

import java.util.Arrays;

import static java.lang.System.out;

public class QuickSort {

    /**
     * Quick Sort is a Divide and Conquer algorithm that picks an element
     * as a 'pivot' and partitions the given array around the picked pivot.
     * * * Logic:
     * 1. Pivot Selection: Pick an element (commonly the last element).
     * 2. Partitioning: Rearrange the array so that all elements smaller than
     * the pivot are on the left, and all elements larger are on the right.
     * 3. Recursion: Recursively apply the same logic to the left and right sub-arrays.
     * * * Time Complexity:
     * - Best & Average Case: O(N log N) - Occurs when the pivot divides the
     * array into two nearly equal halves.
     * - Worst Case: O(N²) - Occurs when the pivot is consistently the smallest
     * or largest element (e.g., sorting an already sorted array without random pivot).
     * * * Space Complexity:
     * - O(log N): Due to the recursive stack space. While it sorts in-place,
     * the recursion depth requires stack memory.
     */
    public static int[] quickSort(int[] array) {
        if (array == null || array.length == 0)
            return array;
        sort(array, 0, array.length - 1);
        return array;
    }

    private static void sort(int[] array, int low, int high) {
        if (low < high) {
            int pivot = partition(array, low, high);
            sort(array, low, pivot - 1);  // Left side
            sort(array, pivot + 1, high); // Right side
        }
    }

    private static int partition(int[] array, int low, int high) {

        int pivot = array[high];    // We choose the last element as the pivot
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // Final Swap: Place the pivot in its correct sorted position by swapping it with the element at i + 1.
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1; // Return the position of the pivot
    }

    public static void main(String[] args) {
        int[] data = {29, 10, 14, 37, 13, 12, 3, 34, 456, 5, 4, 6, 56, 7, 6, 8, 78, 456, 79, 456, 89, 890, 456
                , 909, 0, 9, 0, 9, 0, 8, 9, 788, 7, 456, 67, 8, 6, 7, 5, 7, 56, 6, 546, 56, 5, 6, 56, 5, 6, 5, 6, 5, 6, 56, 456, 56, 5, 6, 56, 5
                , 6, 5, 6, 5, 6, 5, 6, 5, 66, 7, 67, 8, 78, 6, 3, 4, 2, 3, 13, 23, 45656767, 867, 89, 78890, 879, 89};

        out.println("\nOriginal: " + Arrays.toString(data));
        long start = System.nanoTime();
        quickSort(data);
        long end = System.nanoTime();
        out.println("Sorted:   " + Arrays.toString(data) + " \n->Time taken to Execute is " + (end - start) + " ns");
    }
}