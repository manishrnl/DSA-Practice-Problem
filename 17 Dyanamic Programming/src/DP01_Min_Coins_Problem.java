import java.util.Arrays;

public class DP01_Min_Coins_Problem {

    public static void main(String[] args) {
        int targetSum = 18;
        int[] arrayOfCoins = new int[]{7, 5, 1};
        int[] dp = new int[targetSum + 1];

        // Initialize DP memoization table with -1 (indicating unvisited states)
        Arrays.fill(dp, -1);
        dp[0] = 0; // Base case: 0 coins needed to make sum 0

        System.out.printf("\nMinimum coins needed to get a target of %d from the coins %s via normal approach is : %d ", targetSum, Arrays.toString(arrayOfCoins), minCoinsWithoutDP(targetSum, arrayOfCoins));
        System.out.printf("\nMinimum coins needed to get a target of %d from the coins %s via Dynamic Programming is : %d ", targetSum, Arrays.toString(arrayOfCoins), minCoinsWithDP(targetSum, arrayOfCoins, dp));

        for (int i = 0; i < targetSum; i++) System.out.printf("\nTo get sum = %d  min no. of coins needed is %d ", i, dp[i]);

    }

    /**
     * Calculates the minimum number of coins required to form a target sum using plain recursion.
     * <p>
     * Note: This method has an exponential time complexity O(C^N) due to overlapping subproblems.
     *
     * @param targetSum    The target monetary value to reach.
     * @param arrayOfCoins Array containing available coin denominations.
     * @return Minimum number of coins needed, or Integer.MAX_VALUE if target cannot be formed.
     */
    private static int minCoinsWithoutDP(int targetSum, int[] arrayOfCoins) {
        if (targetSum == 0) return 0;   // Base Condition: 0 target needs 0 coins
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < arrayOfCoins.length; i++) {
            if (targetSum - arrayOfCoins[i] >= 0) {
                int subAnswer = minCoinsWithoutDP(targetSum - arrayOfCoins[i], arrayOfCoins);
                /**
                 * <h1>WHY ( subAnswer + 1 < answer ) ?</h1><pre>
                 * If that remaining amount can be formed, you need 1 extra coin (the coin you just picked in the loop) to make the full targetSum.
                 *  So subAnswer + 1 is the total coin count for this specific branch.  </pre>
                 */
                if (subAnswer != Integer.MAX_VALUE && subAnswer + 1 < answer)
                    answer = subAnswer + 1;
            }
        }
        return answer;
    }

    /**
     * Calculates the minimum number of coins required to form a target sum using Top-Down Dynamic
     * Programming (Memoization).
     * <p>
     * Time Complexity: O(targetSum * numberOfCoins)
     * Space Complexity: O(targetSum) for memoization array + recursion stack.
     *
     * @param targetSum    The target monetary value to reach.
     * @param arrayOfCoins Array containing available coin denominations.
     * @param dp           Memoization array to store previously calculated answers.
     * @return Minimum number of coins needed, or Integer.MAX_VALUE if target cannot be formed.
     */
    public static int minCoinsWithDP(int targetSum, int[] arrayOfCoins, int[] dp) {
        if (targetSum == 0) return 0;
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < arrayOfCoins.length; i++) {
            if (targetSum - arrayOfCoins[i] >= 0) {

                int subAnswer;
                // Check if the sub-problem has already been solved and stored in DP table
                if (dp[targetSum - arrayOfCoins[i]] != -1) {
                    subAnswer = dp[targetSum - arrayOfCoins[i]];
                } else {
                    subAnswer = minCoinsWithDP(targetSum - arrayOfCoins[i], arrayOfCoins, dp);
                }

                if (subAnswer != Integer.MAX_VALUE && subAnswer + 1 < answer)
                    answer = subAnswer + 1;
            }
        }

        // Save computed result to cache before returning
        dp[targetSum] = answer;
        return answer;
    }
}