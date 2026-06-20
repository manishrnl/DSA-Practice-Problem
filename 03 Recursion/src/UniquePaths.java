package src;
public class UniquePaths {

    /**
     * PROBLEM: Grid Traversal / Unique Paths
     * * Logic:
     * This is a recursive implementation of the "Pascal's Identity".
     * Every cell in the grid (except the first row and first column)
     * can be reached from exactly two directions:
     * 1. From the Top (rows - 1)
     * 2. From the Left (columns - 1)
     * * Base Case:
     * If you are in the first row (rows == 1) or first column (columns == 1),
     * there is only 1 straight path to reach any cell in that line.
     * * Complexity:
     * - Time: O(2^(n+m)) - This is slow for large grids due to redundant calculations.
     * - Space: O(n+m) - Due to the recursion stack.
     */
    public static long countUniquePaths(int rows, int columns) {
        // If we are at the edge (first row or first column), only 1 way exists (straight line)
        if (rows == 1 || columns == 1) {
            return 1;
        }

        // Total ways = ways from the top cell + ways from the left cell
        return countUniquePaths(rows - 1, columns) + countUniquePaths(rows, columns - 1);
    }

    // Optimized above code to O(n*m) version
    public static long countPathsDP(int rows, int columns) {
        long[][] dp = new long[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0 || j == 0) dp[i][j] = 1; // Base case
                else dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // Sum of top and left
            }
        }
        return dp[rows - 1][columns - 1];
    }

    public static void main(String[] args) {
        int rows = 12, columns = 15;
        long startTime = System.nanoTime();
        long result = countUniquePaths(rows, columns);
        long endTime = System.nanoTime();
        System.out.println("Results via Recursion Problem: " + result + " in " + (endTime - startTime) + " ns");


        long startTime1 = System.nanoTime();
        long result2 = countPathsDP(rows, columns);
        long endTime1 = System.nanoTime();
        System.out.println("Results via DP Approach : " + result2 + " in " +(endTime1 - startTime1) + " ns");
    }
}