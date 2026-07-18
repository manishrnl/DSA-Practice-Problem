import java.util.Arrays;

/**
 * Heapify is the core operation used to maintain or build a Max-Heap structure
 * from an unsorted array. It ensures that a parent node is always greater than
 * or equal to its children. It transforms a subtree rooted at a given index into a valid Max-Heap.
 *
 * @author Manish
 * @version 1.0
 */
public class H_00_HeapifyLogic {

    /**
     * Heapify Explanation: A Max-Heap is a complete binary tree where every parent node is greater than
     * or equal to its child nodes. Heapify assumes that the left and right subtrees
     * of the current node are already valid heaps, but the parent itself might be
     * smaller than its children, violating the heap property. This method "sinks"
     * or bubbles down the parent node to its correct position.
     * <p>
     * Logic:
     * <ol>
     *   <li>Calculate the indices of the left child (2 * i + 1) and right child (2 * i + 2).</li>
     *   <li>Compare the value at 'currentIndex' with its children to find the largest of the three.</li>
     *   <li>If a child is larger than the parent, swap the parent with that largest child.</li>
     *   <li>Crucially, because the swap might have disrupted the heap property in the sub-tree
     *   we just moved the parent into, recursively (or iteratively) call heapify on the
     *   swapped child index until the node finds its correct resting place.</li>
     * </ol>
     * <p>
     * Real-World Example (ER Triage Severity Scores):
     * Let's trace an array of incoming trauma scores where higher numbers mean higher emergency priority:
     * {@code [12, 85, 90, 45, 50, 60, 75]}
     * <br>
     * Executing: heapify(array, length=7, currentIndex=0)
     * <p>
     * The tree structure starting at index 0 looks like this:
     * <pre>
     *                     12
     *                    /  \
     *                  /     \
     *                85      90
     *              /  \      / \
     *           45    50   60   75
     * </pre>
     * <p>
     * Trace:
     * <ul>
     *   <li>currentIndex = 0 (value = 12)</li>
     *   <li>leftChild = 1 (value = 85) -&gt; 85 &gt; 12, so largestIndex becomes 1</li>
     *   <li>rightChild = 2 (value = 90) -&gt; 90 &gt; 85, so largestIndex becomes 2</li>
     *   <li>Since largestIndex (2) != currentIndex (0), we swap array[0] and array[2].</li>
     * </ul>
     * <p>
     * Array after Swap 1: {@code [90, 85, 12, 45, 50, 60, 75]}
     * <p>
     * <ul>
     *   <li>Now, we recursively call heapify(array, length=7, currentIndex=2) because the mild severity value '12' was moved to index 2.</li>
     *   <li>Under index 2, the sub-tree structure looks like this:</li>
     * </ul>
     * <pre>
     *                     90
     *                    /  \
     *                  /     \
     *                85      12
     *              /  \      / \
     *           45    50   60   75
     * </pre>
     * <ul>
     *   <li>leftChild = 2 * 2 + 1 = 5 (value = 60)</li>
     *   <li>rightChild = 2 * 2 + 2 = 6 (value = 75)</li>
     *   <li>Both 60 and 75 are larger than 12. The largest is 75 (index 6).</li>
     *   <li>Swap array[2] and array[6].</li>
     * </ul>
     * <p>
     * Array after Swap 2: {@code [90, 85, 75, 45, 50, 60, 12]}
     * <p>
     * <ul>
     *   <li>Recursively call heapify on index 6. Its children would be index 13 and 14, which are &gt;= length (7). The recursion stops here.</li>
     *   <li>Final output match for index 0 heapify: {@code [90, 85, 75, 45, 50, 60, 12]} (The highest emergency score 90 successfully bubbled to the top!)</li>
     * </ul>
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity (T.C.):</b> O(log N) - In the worst case, a node must
     *   sink from the root down to the bottom leaf level. The height of a complete
     *   binary tree is bounded by log₂N.</li>
     *   <li><b>Space Complexity (S.C.):</b> O(log N) - Due to the memory consumption
     *   of the system call stack frames during recursive execution.</li>
     * </ul>
     *
     * @param array        The actual array where heapify would happen.
     * @param length       The boundary limit of the heap (elements beyond this index are ignored).
     * @param currentIndex The index of the parent node to look at and potentially sink down.
     */
    public static void heapify(int[] array, int length, int currentIndex) {
        int largestIndex = currentIndex;
        int leftChild = 2 * currentIndex + 1;
        int rightChild = 2 * currentIndex + 2;

        if (leftChild < length && array[leftChild] > array[largestIndex]) {
            largestIndex = leftChild;
        }

        if (rightChild < length && array[rightChild] > array[largestIndex]) {
            largestIndex = rightChild;
        }

        if (largestIndex != currentIndex) {
            swap(array, currentIndex, largestIndex);
            heapify(array, length, largestIndex);     // T.C: O(log N)
        }
    }

    /**
     * Complete, performance-optimized execution that automatically constructs a valid
     * Max-Heap from an entire raw array.
     *
     * <h3>Logic:</h3>
     * Rather than processing only a single node, this method builds a complete heap top-to-bottom.
     * It starts checking at the last non-leaf node {@code (length / 2 - 1)} and iterates backwards
     * down to index 0. For each node, it uses a high-speed, non-recursive {@code while(true)} state loop
     * to safely push elements downward without risking stack-overflows.
     *
     * <h3>Complexity:</h3>
     * <ul>
     *   <li><b>Time Complexity (T.C.):</b> O(N) - <b>Optimized.</b> While each step can theoretically
     *   take O(log N), the mathematical sum over the whole tree bounds total operations to linear time
     *   because the majority of elements reside near leaf levels and barely slide down.</li>
     *   <li><b>Space Complexity (S.C.):</b> O(1) - <b>Optimized.</b> Modifies data fully in place
     *   without allocation or stack frame creation.</li>
     * </ul>
     *
     * @param array  The raw, unsorted array containing elements to form into a max-heap structure.
     * @param length The total working size boundary limit of the targeted array data.
     */
    public static void heapifyLoopOptimised(int[] array, int length) {
        // Outer loop: safely cycles from the lowest parent down to the root node (0)
        for (int i = length / 2 - 1; i >= 0; i--) {  // T.C: Loops N/2 times
            int currentIndex = i;                    // Track target node to sink down

            // Inner state-machine: replaces traditional recursive overhead
            while (true) {                           // T.C: Runs up to O(log N) times per node
                int largestIndex = currentIndex;
                int leftChild = 2 * currentIndex + 1;
                int rightChild = 2 * currentIndex + 2;

                if (leftChild < length && array[leftChild] > array[largestIndex]) {
                    largestIndex = leftChild;
                }

                if (rightChild < length && array[rightChild] > array[largestIndex]) {
                    largestIndex = rightChild;
                }

                // If balance is matched or we hit a leaf tier, stop sinking this element
                if (largestIndex == currentIndex) {
                    break;
                }

                swap(array, currentIndex, largestIndex);
                currentIndex = largestIndex;         // Advance tracking down to child level
            }
        }
    }


    public static void swap(int[] array, int current, int largest) {
        int temp = array[current];
        array[current] = array[largest];
        array[largest] = temp;
    }

    public static void main(String[] args) {
        // Triage priority scores dataset
        int[] triageA = {12, 85, 90, 45, 50, 60, 75};
        int[] totalEmergencyHeap = {12, 85, 90, 45, 50, 60, 75};

        System.out.println("Unsorted Emergency Scores: " + Arrays.toString(triageA));

        // Isolated Single-Node Pass
        Long startTime = System.nanoTime();
        heapify(triageA, triageA.length, 0);
        Long endTime = System.nanoTime();
        System.out.println("Recursive Fix (Index 0 Only): " + Arrays.toString(triageA) + " & took " + (endTime - startTime) + " ns");

        System.out.println("\n--- Full Tree Optimization Mode ---");

        // Building the COMPLETE heap using your self-contained optimized function
        Long endTime2 = System.nanoTime();
        heapifyLoopOptimised(totalEmergencyHeap, totalEmergencyHeap.length);
        System.out.println("Fully Prioritized Queue (O(N)): " + Arrays.toString(totalEmergencyHeap) + " & took " + (endTime2 - endTime) + " ns");
    }
}