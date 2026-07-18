import java.util.Arrays;

/**
 * H_03_HeapSort implements the classic comparison-based sorting algorithm using a Max-Heap.
 * <p>
 * It runs by taking an array, organizing it into a tree structure where the largest
 * element is at the top (root), and repeatedly moving that largest element to the end of
 * the array while shrinking the working tree boundaries.
 */
public class H_03_HeapSort {
    /**
     * Standard implementation of Heapify using structural recursion.
     * Evaluates a parent node and sinks it down until max-heap properties are met.
     *
     * @param array        The underlying array containing heap elements.
     * @param length       The active working size boundary of the heap (exclusive).
     * @param currentIndex The index of the parent node to evaluate and sink down.
     */
    public static void heapify(int[] array, int length, int currentIndex) {
        int largestIndex = currentIndex;
        int leftChild = 2 * currentIndex + 1;
        int rightChild = 2 * currentIndex + 2;
        if (leftChild < length && array[leftChild] > array[largestIndex])            largestIndex = leftChild;
        if (rightChild < length && array[rightChild] > array[largestIndex])            largestIndex = rightChild;

        if (largestIndex != currentIndex) {
            swap(array, currentIndex, largestIndex);
            heapify(array, length, largestIndex); // T.C: O(log N)
        }
    }

    /**
     * Sorts an array in ascending order using the Heap Sort algorithm.
     *
     * <h3>Logic:</h3>
     * <ol>
     *   <li><b>Phase 1 (Build Max-Heap):</b> Transform the raw array into a max-heap by
     *       running heapify from the bottom-up. T.C: {@code O(N)}.</li>
     *   <li><b>Phase 2 (Sort Down):</b> Repeatedly swap the root (index 0, the absolute maximum)
     *       with the last unsorted element at index {@code i}. Reduce the active heap size,
     *       and call {@code heapify(array, length=i, currentIndex=0)} to restore order at the top.</li>
     * </ol>
     *
     * <h3>Real-World Sort Trace:</h3>
     * Given an already formed Max-Heap of trauma priority scores: {@code [90, 85, 75, 45, 50, 60, 12]}
     * <p>
     * <b>Iteration 1 (i = 6):</b>
     * <ul>
     *   <li>Swap root {@code array[0]} (90) with last element {@code array[6]} (12).</li>
     *   <li>Array becomes: {@code [12, 85, 75, 45, 50, 60, | 90]} (90 is locked in place).</li>
     *   <li>Call {@code heapify(array, length=6, currentIndex=0)} to fix the tree up to index 5.</li>
     *   <li>Tree fixes to put next highest at top: {@code [85, 50, 75, 45, 12, 60, | 90]}</li>
     * </ul>
     * <b>Iteration 2 (i = 5):</b>
     * <ul>
     *   <li>Swap root (85) with last active element {@code array[5]} (60).</li>
     *   <li>Array becomes: {@code [60, 50, 75, 45, 12, | 85, 90]} (85 is locked).</li>
     *   <li>Call {@code heapify(array, length=5, currentIndex=0)}.</li>
     *   <li>Tree stabilizes to: {@code [75, 50, 60, 45, 12, | 85, 90]}</li>
     * </ul>
     * This sequence continues until the entire array is sorted seamlessly from lowest to highest score.
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity (T.C.):</b> O(N log N) - Both worst-case and average-case scenarios.
     *       Phase 1 takes O(N), and Phase 2 runs a loop N times calling an O(log N) heapify method.</li>
     *   <li><b>Space Complexity (S.C.):</b> O(log N) - Due to the recursive call stack height of the
     *       heapify function. (Can be reduced to O(1) if using an iterative heapify loop).</li>
     * </ul>
     *
     * @param array  The target array to be sorted in place.
     * @param length The total size of the target array.
     */
    public static void heapSort(int[] array, int length) {
        // PHASE 1: Transform ANY random array into a Max-Heap first
        for (int i = length / 2 - 1; i >= 0; i--)             heapify(array, length, i);

        // PHASE 2: Extract elements from the heap one by one and sort them down
        for (int i = length - 1; i > 0; i--) {
            swap(array, 0, i);         // Move current maximum node to the sorted end zone
            heapify(array, i, 0);      // FIXED: Restabilize the shortened heap of length i
        }
    }

    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] emergencyScores = {90, 85, 10, 9, 8, 7, 6, 5, 15, 11, 4, 75, 45, 50, 60, 12};
        System.out.println("Before Sorting: " + Arrays.toString(emergencyScores));
        heapSort(emergencyScores, emergencyScores.length);
        System.out.println("After Heap Sort: " + Arrays.toString(emergencyScores));
    }
}