package src;

import java.util.Arrays;

import static java.lang.System.out;

public class MergeSort {

    /**
     * ================= MERGE SORT (MENTAL MODEL) =================
     * Think in 2 phases:
     * 1) GO DOWN (Divide phase)
     * - Keep splitting array into halves
     * - Stop when size becomes 1 (already sorted)
     * 2) COME UP (Merge phase)
     * - Now start merging small sorted arrays
     * - Build bigger sorted arrays step by step
     * IMPORTANT RULE:
     * 👉 merge() is called ONLY when both left and right are already sorted
     * =============================================================
     * Time Complexity: O(N log N)
     * Space Complexity: O(N)
     */

    public static int[] mergeSort(int[] array) {

        //  BASE CASE
        // If array has 1 element → already sorted → return
        if (array.length <= 1) {
            return array;
        }

        // 🔹 STEP 1: DIVIDE
        // Split array into two halves
        int mid = array.length / 2;

        int[] leftArray = Arrays.copyOfRange(array, 0, mid);
        int[] rightArray = Arrays.copyOfRange(array, mid, array.length);

        /*
         * 🔥 RECURSION FLOW (VERY IMPORTANT)
         *
         * Execution does NOT go like normal top-to-bottom.
         *
         * It goes like:
         *
         *   1. Go LEFT completely (until base case)
         *   2. Then go RIGHT completely
         *   3. THEN merge
         *
         * Think:
         *   "I will NOT merge until both sides are fully sorted"
         */

        // 🔹 STEP 2: SORT LEFT HALF (go deep first)
        out.println("Left Split ..........." + Arrays.toString(leftArray) + "........" +
                " Right Split  ..........." + Arrays.toString(rightArray));
        mergeSort(leftArray);
        out.println("Left Array : " + Arrays.toString(leftArray));

        // 🔹 STEP 2: SORT RIGHT HALF
        mergeSort(rightArray);

        out.println("Right Array : " + Arrays.toString(rightArray));
        // 🔹 STEP 3: MERGE (happens while returning back)
        // At this point:
        // leftArray is sorted ✔
        // rightArray is sorted ✔
        merge(array, leftArray, rightArray);


        return array;
    }

    /**
     * ================= MERGE FUNCTION =================
     * Input:
     * left[]  → already sorted
     * right[] → already sorted
     * Output:
     * result[] → combined sorted array
     * Idea:
     * Compare the smallest elements from both arrays
     * Pick the smaller one and move forward
     */

    private static void merge(int[] result, int[] leftArray, int[] rightArray) {

        int leftIndex = 0; // pointer for left array
        int rightIndex = 0; // pointer for right array
        int k = 0; // pointer for result array

        // 🔹 Compare elements from both arrays
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {

            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                result[k++] = leftArray[leftIndex++]; // take from left
            } else {
                result[k++] = rightArray[rightIndex++]; // take from right
            }
        }

        // 🔹 Copy remaining elements (if any)

        // If left still has elements
        while (leftIndex < leftArray.length) {
            result[k++] = leftArray[leftIndex++];
        }

        // If right still has elements
        while (rightIndex < rightArray.length) {
            result[k++] = rightArray[rightIndex++];
        }
        out.println("******************** Merge Result ****************** " + Arrays.toString(result));
    }

    public static void main(String[] args) {

        int[] data = {
                1,1,1,1,1,1,1,1,1,1,1,29, 10, 14, 37, 13, 12, 3, 34,
                456, 5, 4, 6, 56, 7, 6, 8, 78
        };

        out.println("\nOriginal: " + Arrays.toString(data));
        long start = System.nanoTime();
        mergeSort(data);
        long end = System.nanoTime();
        out.println("Sorted:   " + Arrays.toString(data));
        out.println("Time taken: " + (end - start) + " ns");
    }
}