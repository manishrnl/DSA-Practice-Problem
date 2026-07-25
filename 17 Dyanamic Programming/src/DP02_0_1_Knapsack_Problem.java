import java.util.Arrays;

/**
 * Solves the 0/1 Knapsack Problem using Dynamic Programming (Top-Down Memoization)
 * and traces the optimal path of selected items.
 *
 * <p>Given a set of items, each with a weight and a value, determine the maximum
 * value of items that can be included in a knapsack of a given capacity. Each item
 * can either be included or excluded (0/1 choice).</p>
 */
public class DP02_0_1_Knapsack_Problem {

    /**
     * Computes the maximum value reachable within a given weight capacity using recursion
     * and memoization.
     *
     * <p>Time Complexity: {@code O(totalItemsCount * maxKnapsackCapacity)} which is reduced from {@code 2^n} if calculated without memory or dp[][]<br>
     * Space Complexity: {@code O(totalItemsCount * maxKnapsackCapacity)} for the memoization table.</p>
     *
     * @param remCapacity The remaining capacity available in the knapsack.
     * @param itemWeight       Array containing weights of all items.
     * @param itemVal        Array containing monetary values of all items.
     * @param currItemIdx  The number of remaining items to consider (1-based index).
     * @param dp                2D cache array storing previously computed sub-problems.
     * @return The maximum total value achievable with the given constraints.
     */
    private static int maxWeight(int remCapacity, int[] itemWeight, int[] itemVal, int currItemIdx, int[][] dp) {
        if (currItemIdx == 0 || remCapacity == 0) return 0;   // Base Case: No items left to inspect OR no capacity remaining in knapsack
        if (dp[currItemIdx][remCapacity] != -1) return dp[currItemIdx][remCapacity];   // Return cached result if sub-problem is already calculated
        int currItemWt = itemWeight[currItemIdx - 1], currItemVal = itemVal[currItemIdx - 1];

        // Option 1: Current item fits into remaining capacity
        if (currItemWt <= remCapacity) {
             int includeChoice = currItemVal + maxWeight(remCapacity - currItemWt, itemWeight, itemVal, currItemIdx - 1, dp);
             int excludeChoice = maxWeight(remCapacity, itemWeight, itemVal, currItemIdx - 1, dp);
            dp[currItemIdx][remCapacity] = Math.max(includeChoice, excludeChoice);            // Store max of both choices into memoization table
        } else {
            // Option 2: Current item weight exceeds remaining capacity, must exclude
            dp[currItemIdx][remCapacity] = maxWeight(remCapacity, itemWeight, itemVal, currItemIdx - 1, dp);
        }

        return dp[currItemIdx][remCapacity];
    }

    public static void main(String[] args) {
        int maxKnapsackCapacity = 10;
        int[] itemWeights = new int[]{1, 3, 4, 6};
        int[] itemValues = new int[]{20, 30, 20, 50};
        int totalItemsCount = itemWeights.length;

        // Memoization table of size [totalItemsCount + 1][maxKnapsackCapacity + 1]
        int[][] dp = new int[totalItemsCount + 1][maxKnapsackCapacity + 1];

        // Initialize memoization table with -1 to indicate uncalculated states
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        int maxProfit = maxWeight(maxKnapsackCapacity, itemWeights, itemValues, totalItemsCount, dp);

        System.out.print("\nMaximum Value in Knapsack = " + maxProfit);

    }
}