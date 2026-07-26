import java.util.Arrays;

/**
 * <h2>Problem Statement: Rod Cutting Problem</h2>
 * <p>
 * Given a rod of length {@code N} and an array of prices that contains prices of all
 * pieces of size smaller than or equal to {@code N}. Determine the maximum value
 * obtainable by cutting up the rod and selling the pieces.
 * </p>
 * <p>
 * <b>Note:</b> You can cut the rod into as many pieces as you want (Unbounded Knapsack variant).
 * </p>
 *
 * <h2>Core State Transition</h2>
 * <p>For a rod of length {@code i}, try making a cut of length {@code j} (where {@code 1 <= j <= i}):</p>
 * <pre>{@code
 * dp[i] = Math.max(dp[i], price[j - 1] + dp[i - j])
 * }</pre>
 *
 * <h2>Example Breakdown (Length = 8, Prices = [1, 5, 6, 9, 11, 12, 14, 16])</h2>
 * <pre>{@code
 * Optimal Cuts for Length 8:
 * - Two pieces of length 2 (price 5 + 5 = 10) -> sub-optimal
 * - Piece of length 2 (price 5) + Piece of length 6 (price 12) = 17
 * - Four pieces of length 2 (price 5 * 4) = 20
 *
 * Maximum obtainable profit = 22 (Cut into length 2 + length 2 + length 2 + length 2 = 5+5+5+5 or 2+6=17 vs 2*4=20 etc.)
 * Specifically: Length 2 (5) * 4 = 20 vs Length 2 (5) + Length 2 (5) + Length 2 (5) + Length 2 (5) = 20
 * Actually for length 8: Best is 2+2+2+2 = 20 OR 1+7 = 1+14=15 OR 2+6 = 5+12=17 OR 3+5 = 6+11=17 OR 4+4 = 9+9=18.
 * Maximum Profit = 20 (obtained by 4 cuts of size 2).
 * }</pre>
 */
public class DP05_Rod_Cutting_Problem {

    /**
     * Computes maximum profit using Bottom-Up Tabular Dynamic Programming.
     *
     * <p>Time Complexity: {@code O(N^2)} where {@code N = length}<br>
     * Space Complexity: {@code O(N)} for 1D DP table.</p>
     *
     * @param length Total length of the rod.
     * @param price  Array containing prices of pieces of length {@code 1} to {@code length}.
     * @return Maximum profit obtainable.
     */
    public static int cutRodTabular(int length, int[] price) {
        int[] dp = new int[length + 1];
        dp[0] = 0; // Base case: rod of length 0 yields 0 profit

        // i represents the current total rod length being solved
        for (int i = 1; i <= length; i++) {
            int maxProfitForLengthI = Integer.MIN_VALUE;

            // j represents the length of the first cut piece
            for (int j = 1; j <= i; j++) {
                int profitFromCut = price[j - 1] + dp[i - j];
                maxProfitForLengthI = Math.max(maxProfitForLengthI, profitFromCut);
            }

            dp[i] = maxProfitForLengthI;
        }

        return dp[length];
    }

    /**
     * Computes maximum profit using Top-Down Memoization.
     *
     * <p>Time Complexity: {@code O(N^2)} where {@code N = length}<br>
     * Space Complexity: {@code O(N)} for DP array + recursion stack.</p>
     *
     * @param length Remaining length of the rod to evaluate.
     * @param price  Array containing prices of pieces.
     * @param memoDP Cache array storing computed answers for sub-lengths.
     * @return Maximum profit obtainable for given length.
     */
    public static int cutRodMemoized(int length, int[] price, int[] memoDP) {
        // Base Case: Rod of length 0 has 0 profit
        if (length == 0) return 0;

        // Return cached value if already calculated
        if (memoDP[length] != -1) return memoDP[length];

        int maxProfit = Integer.MIN_VALUE;

        // Try every possible first cut size from 1 to length
        for (int cutLength = 1; cutLength <= length; cutLength++) {
            int currentProfit = price[cutLength - 1] + cutRodMemoized(length - cutLength, price, memoDP);
            maxProfit = Math.max(maxProfit, currentProfit);
        }

        return memoDP[length] = maxProfit;
    }

    public static void main(String[] args) {
        int length = 8;
        int[] price = {1, 5, 6, 9, 11, 12, 14, 16};

        System.out.println("Max Profit (Bottom-Up Tabular 1D) : " + cutRodTabular(length, price));

        // Setup Memoization table initialized with -1
        int[] memoDP = new int[length + 1];
        Arrays.fill(memoDP, -1);
        System.out.println("Max Profit (Top-Down Memoized)   : " + cutRodMemoized(length, price, memoDP));
    }
}