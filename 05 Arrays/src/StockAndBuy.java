package src;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

/**
 * Problem: Best Time to Buy and Sell Stock
 * Goal: Find the maximum profit possible by buying on one day and selling on a future day.
 */
public class StockAndBuy {

    /**
     * APPROACH 1: Brute Force
     * Logic: Check every possible combination of buy and sell days.
     * Pros: Simple to implement.
     * Cons: Very slow for large inputs because of nested loops.
     * * Time Complexity: O(N^2) - Two nested loops iterate through the array.
     * Space Complexity: O(1) - No extra memory used regardless of input size.
     */
    public static int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            // 'j' starts from 'i + 1' because you must buy before you can sell
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        return maxProfit;
    }

    /**
     * APPROACH 2: Auxiliary Space (Suffix Max)
     * Logic: Pre-calculate the maximum price that occurs in the future for any given day.
     * By storing the "best possible sell price" for each day in a separate array,
     * we can find the profit in a single second pass.
     * * Time Complexity: O(N) - Two separate linear passes.
     * Space Complexity: O(N) - Requires an additional array of size N.
     */
    public static int maxProfitWithSpace(int[] prices) {
        int n = prices.length;
        if (n < 2) return 0;

        // Create an array where maxFuturePrices[i] is the highest price from day 'i' to the end
        int[] maxFuturePrices = new int[n];
        maxFuturePrices[n - 1] = prices[n - 1];

        // Fill the array from right to left
        for (int i = n - 2; i >= 0; i--) {
            maxFuturePrices[i] = Math.max(prices[i], maxFuturePrices[i + 1]);
        }

        int maxProfit = 0;
        // The profit for day 'i' is the (max future price) - (price on day 'i')
        for (int i = 0; i < n; i++) {
            maxProfit = Math.max(maxProfit, maxFuturePrices[i] - prices[i]);
        }
        return maxProfit;
    }

    /**
     * APPROACH 3: One-Pass (Optimal)
     * Logic: Iterate through the prices while keeping track of the lowest price
     * seen so far. For every new price, calculate the potential profit if we
     * sold today using that minimum price.
     * * Time Complexity: O(N) - We only scan the array once.
     * Space Complexity: O(1) - We only store two integer variables.
     */
    public static int maxProfitOptimized(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int minPrice = Integer.MAX_VALUE; // Track the cheapest day to buy
        int maxProfit = 0;               // Track the best profit found

        for (int price : prices) {
            if (price < minPrice) {
                // Found a new historical low; update buy price
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                // If selling today yields a better profit than before, update it
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 2, 4, 6, 8, 9, 11, 9, 4, 126};

        // Execution and Timing
        long startTime = nanoTime();
        int result = maxProfitBruteForce(array);
        long endTime = nanoTime();

        long startTime2 = nanoTime();
        int result2 = maxProfitOptimized(array);
        long endTime2 = nanoTime();

        long startTime3 = nanoTime();
        int result3 = maxProfitWithSpace(array);
        long endTime3 = nanoTime();

        out.println("--- Results ---");
        out.println("Brute Force T.C : O(N^2) &  S.C : O(1) is : " + result + " (" + (endTime - startTime) + " ns)");
        out.println("Optimized   T.C : O(N) &  S.C : O(N) is   : " + result2 + "(" + (endTime2 - startTime2) + " ns)");
        out.println("Suffix Max  T.C : O(N) &  S.C : O(1) is   : " + result3 + " (" + (endTime3 - startTime3) + " ns)");
    }
}