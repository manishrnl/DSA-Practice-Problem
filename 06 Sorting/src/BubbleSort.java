package src;
import java.util.Arrays;

import static java.lang.System.nanoTime;
import static java.lang.System.out;


public class BubbleSort {
    /**
     * BUBBLE SORT LOGIC
     * * 1. The Comparison: Start at the beginning of the array. Compare the current element (A)
     * with its immediate neighbor (B).
     * * 2. The Swap: If A > B, swap them. This ensures the larger value "bubbles" toward the right.
     * * 3. The Iteration: Move to the next pair (B & C) and repeat the comparison until the end
     * of the unsorted portion of the array is reached.
     * * 4. The Pass: After one full pass, the largest element of the unsorted section is
     * guaranteed to be in its final sorted position at the end.
     * * 5. Repeat: Repeat the process for the remaining unsorted elements. With each subsequent
     * pass, the range of comparison decreases by one.
     * * 6. Optimization (Optional): If a full pass occurs without any swaps, the array is
     * already sorted, and the algorithm can terminate early.
     * T.C Best O(N) , worst case O(N^2)
     * S.C O(1)
     */
    public static int[] bubbleSort(int[] array) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            boolean swapped = false;

            // Inner loop: length - i - 1 ensures we don't re-check the
            // elements that have already "bubbled" to the end.
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    /**
                     * ARCHITECTURAL NOTE ON FUNCTION CALLS:
                     * Theoretically, a function call adds overhead (stack pushing , context switching , adding new memory ,
                     * RAM etc. ). However, the modern JVM's JIT (Just-In-Time)  Compiler performs "Method Inlining"  injecting
                     * the swap logic  directly into the loop (into the called function) to  eliminate call overhead
                     * at runtime. Calling a  different function inside a function is just for humans
                     * readibility . It doesn't affect program overhead via context  switching etc.
                     */
                    swapNumbers(array, j, j + 1);
                    swapped = true;
                }
            }

            // EARLY EXIT OPTIMIZATION: If no elements were swapped during
            // a full pass, meaning the array is already sorted.
            if (!swapped) break;
        }
        return array;
    }

    /**
     * Swaps two elements in the array.
     * While XOR swapping is possible, the 'temp' variable method is
     * the industry standard due to its readability and high
     * optimization by modern hardware.
     */
    public static void swapNumbers(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {

        int size = 5000;
        int[] array = new int[size];

        // Fill the array with random numbers.
        for (int i = 0; i < size; i++) {
            int randomNumber = (int) (Math.random() * size);
            array[i] = randomNumber;
        }

        out.println("Starting Bubble Sort for " + size + " elements...");

        long startFunction = nanoTime();
        int[] result = bubbleSort(array);
        long endFunction = nanoTime();

        out.println("Result: " + Arrays.toString(result));
        out.println("-> Time taken: " + (endFunction - startFunction) + " ns");
        out.println("-> Time in seconds: " + (double) (endFunction - startFunction) / 1_000_000_000 + " s");
    }


}