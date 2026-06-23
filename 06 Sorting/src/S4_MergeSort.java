import java.util.Arrays;

import static java.lang.System.out;

public class S4_MergeSort {

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
        if (array.length <= 1) return array;    //  BASE CASE: If array has 1 element →
        // already sorted → return
        int mid = array.length / 2;        // 🔹 STEP 1, DIVIDE : Split array into two halves
        int[] leftArray = Arrays.copyOfRange(array, 0, mid), rightArray = Arrays.copyOfRange(array, mid, array.length);

        /* 🔥 RECURSION FLOW (VERY IMPORTANT)
         * Execution does NOT go like normal top-to-bottom. It goes like:
         *   1. Go LEFT completely (until base case)
         *   2. Then go RIGHT completely
         *   3. THEN merge
         * Think:  "I will NOT merge until both sides are fully sorted"
         */

        // 🔹 STEP 2: SORT LEFT HALF (go deep first)
        out.println("Splitting Left Part ..........." + Arrays.toString(leftArray));
        mergeSort(leftArray);
        out.println("All Left Array is being split up : " + Arrays.toString(leftArray));

        // 🔹 STEP 2: SORT RIGHT HALF
        out.println("Splitting Right Part ..........." + Arrays.toString(rightArray));
        mergeSort(rightArray);
        out.println("All Right Array is being split up : " + Arrays.toString(rightArray));

        // 🔹 STEP 3: MERGE (happens while returning back)
        // At this point:
        // leftArray is sorted ✔
        // rightArray is sorted ✔

        out.println("Now merging the splitted Array");
        merge(array, leftArray, rightArray);
        out.println("\n\n");

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
            if (leftArray[leftIndex] <= rightArray[rightIndex])
                result[k++] = leftArray[leftIndex++]; // take from left
            else
                result[k++] = rightArray[rightIndex++]; // take from right
        }

        // 🔹 Copy remaining elements (if any)
        while (leftIndex < leftArray.length)        // If left still has elements
            result[k++] = leftArray[leftIndex++];
        while (rightIndex < rightArray.length)         // If right still has elements
            result[k++] = rightArray[rightIndex++];

        out.println("====================== Merge Result ======================== " + Arrays.toString(result));
    }

    public static void main(String[] args) {
        int[] data = {1, 29, 10, 14, 37, 13, 12, 3, 34, 456, 5, 4, 6, 56, 7, 6, 8, 78};
        out.println("\nOriginal: " + Arrays.toString(data));
        long start = System.nanoTime();
        mergeSort(data);
        long end = System.nanoTime();
        out.println("Sorted:   " + Arrays.toString(data));
        out.println("Time taken: " + (end - start) + " ns");
    }
}