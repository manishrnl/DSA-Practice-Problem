import java.util.Arrays;

/**
 * DP06_Maximum_Submatrix_Sum
 *
 * <p>Finds the maximum sum submatrix in a 2D integer array using column-compression
 * combined with Kadane's 1D maximum subarray algorithm.
 *
 * <p><b>Algorithm:</b> Iterate over all column pairs (cStart, cEnd). For each pair,
 * compress rows into a 1D array where each element is the sum of that row's values
 * from cStart to cEnd. Apply Kadane's algorithm to find the maximum subarray sum,
 * which corresponds to the optimal contiguous row range for the current columns.
 * Track the global maximum across all iterations.
 *
 * <p><b>Complexity:</b> Time O(cols² × rows), Space O(rows).
 *
 * @author
 * @version 1.0
 */
public class DP06_Maximum_Submatrix_Sum {

    /**
     * Computes the maximum sum of any submatrix in a 2D array.
     *
     * <p>A submatrix is defined by choosing a contiguous range of columns and a
     * contiguous range of rows. This method iterates over all possible column ranges
     * and uses Kadane's algorithm to find the best row range for each column pair.
     *
     * <p><b>Example:</b>
     * <pre>
     * Input matrix:
     *   1  2 -1 -4 -20
     *  -8 -3  4 -2   1
     *   3  8  9  1   3
     *  -4 -1  1  7  -6
     *  -2 -3  8  1  -1
     *
     * Output: 31
     * (Submatrix: rows 2-4, columns 1-3 = [8,9,1], [-1,1,7], [-3,8,1])
     * </pre>
     *
     * @param array     the input 2D matrix (rows × columns)
     * @param rowLength number of rows in {@code array}
     * @param colLength number of columns in {@code array}
     * @return the maximum sum over all possible submatrices; {@link Integer#MIN_VALUE}
     *         if matrix is empty
     *
     * @throws NullPointerException if {@code array} is null
     * @throws ArrayIndexOutOfBoundsException if rowLength or colLength don't match array dimensions
     */
    private static int maximumSubmatrixSum(int[][] array, int rowLength, int colLength) {
        int maxSum = Integer.MIN_VALUE;
        int[] sum = new int[rowLength];

        // Iterate over all starting columns
        for (int cStart = 0; cStart < colLength; cStart++) {
            Arrays.fill(sum, 0);

            // Iterate over all ending columns from cStart onwards
            for (int cEnd = cStart; cEnd < colLength; cEnd++) {
                // Compress all rows in range [cStart, cEnd] into 1D array
                for (int row = 0; row < rowLength; row++) {
                    sum[row] += array[row][cEnd];
                }
                // Find max subarray sum in compressed 1D array
                int curMaxSum = kadaneMaxSum(sum);
                maxSum = Math.max(maxSum, curMaxSum);
            }
        }
        return maxSum;
    }

    /**
     * Kadane's Algorithm — finds the maximum sum contiguous subarray.
     *
     * <p>Maintains a running sum ({@code currentSum}) and tracks the maximum seen
     * ({@code maxSum}). When the running sum becomes negative, it is reset to 0,
     * effectively starting a fresh subarray search. This greedy approach works because
     * a negative prefix sum can only decrease future sums, making it optimal to discard it.
     *
     * <p>Correctly handles all-negative arrays by initializing {@code maxSum} to
     * {@link Integer#MIN_VALUE}, ensuring the largest (least negative) element is returned.
     *
     * <p><b>Example:</b>
     * <pre>
     * Input:  [-2, 1, -3, 4, -1, 2, 1, -5, 4]
     * Output: 6 (subarray [4, -1, 2, 1])
     * </pre>
     *
     * @param array the 1D input array (must be non-null and non-empty)
     * @return the maximum sum of any contiguous subarray
     *
     * @throws NullPointerException if {@code array} is null or empty
     */
    private static int kadaneMaxSum(int[] array) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        int length = array.length;

        for (int i = 0; i < length; i++) {
            currentSum += array[i];

            // Update global max if current sum is better
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
            // Discard negative prefix (starting fresh may yield better result)
            if (currentSum < 0) {
                currentSum = 0;
            }
        }

        System.out.println("Answer for each column range is: " + maxSum);
        return maxSum;
    }


    /**
     * Main method — demonstrates {@link #maximumSubmatrixSum} on an 8×5 test matrix.
     *
     * <p>Test case verifies correct output: 34
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        int[][] array = new int[][]{
                {1, 2, -1, -4, -20},
                {-8, -3, 4, -2, 1},
                {3, 8, 9, 1, 3},
                {-4, -1, 1, 7, -6},
                {-2, -3, 8, 1, -1}};

        int rowLength = array.length;
        int colLength = array[0].length;
        System.out.println("Row length: " + rowLength + " Col length: " + colLength);

        System.out.println("Maximum Sub Matrix Sum is: " + maximumSubmatrixSum(array, rowLength, colLength));
    }
}