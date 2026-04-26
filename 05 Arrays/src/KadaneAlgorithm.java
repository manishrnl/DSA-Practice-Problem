package src;
public class KadaneAlgorithm {

    /**
     * Finds the Maximum Subarray Sum using Kadane’s Algorithm.
     * *
     * * THE LOGIC:
     * This algorithm is an optimized "Greedy" approach that solves the problem in a single
     * pass (O(N)). It operates on a simple principle:
     * "A subarray should only continue as long as its sum remains positive."
     * * If the 'currentSum' becomes negative, it means the preceding elements are
     * a 'burden' that would reduce the sum of any future subarray. Therefore, we
     * 'reset' the currentSum to 0 and start a fresh subarray from the next element.
     * * @param array The input integer array containing positive and negative numbers.
     *
     * @return The maximum sum found within any contiguous subarray.
     * <p>
     * <p>
     * Approach 2
     * use 2 for loop and use same condition but T.C would then be O(n^2)
     */
    public static int max__Pair_Sum(int[] array) {
        // 'maxSum' acts as the global record holder.
        // 'currentSum' tracks the potential of the current contiguous sequence.
        int maxSum = Integer.MIN_VALUE, currentSum = 0;

        for (int i = 0; i < array.length; i++) {
            // Step 1: Accumulate the current element into our local window.
            currentSum += array[i];

            // Step 2: If the local window is the best we've seen so far, update the global record.
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }

            // Step 3: THE RESET. If the window becomes a net-negative, it cannot contribute
            // positively to future elements. We discard it and reset the search.
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        return maxSum;
    }


    public static int Approach2(int[] array) {
        int length = array.length;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < length; i++) {
            int currentSum = 0;
            for (int j = i; j < length; j++) {
                currentSum += array[j];
                if (currentSum > maxSum)
                    maxSum = currentSum;
                if (currentSum < 0)
                    currentSum = 0;

            }

        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] array = {-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4, -10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000,
                -10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4, -10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-10, -3000, -4, -500, 4, 5, 6, 7, 8, 9, 4,-900};
        long startTime = System.nanoTime();
        int result = max__Pair_Sum(array);
        long endTime = System.nanoTime();

        int result1 = Approach2(array);
        long endTime1 = System.nanoTime();
        System.out.println("Result is : " + result + " -> Time taken by algorithm is  " + (endTime - startTime) + " ns");
        System.out.println("Result is : " + result1 + " -> Time taken by algorithm is  " + (endTime1 - endTime) + " ns");
    }

}
