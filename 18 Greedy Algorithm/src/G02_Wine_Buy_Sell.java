/**
 * <b>Wine Buy-Sell Problem</b>
 *
 * <p><b>PROBLEM:</b> Given an array where wine[i] represents transaction amount:
 * positive = units to sell, negative = units to buy. Moving wine between positions
 * costs distance × quantity. Find minimum total transport cost to balance all trades
 * (match all buys with sells).</p>
 *
 * <p><b>APPROACH: Two-Pointer Greedy</b></p>
 * <ol>
 *   <li>Find next position with wine to sell (positive value)</li>
 *   <li>Find next position with wine to buy (negative value)</li>
 *   <li>Match smaller quantity between them; cost = distance × quantity</li>
 *   <li>Reduce larger quantity, zeroing smaller; repeat until no buys/sells left</li>
 * </ol>
 * <p><b>Why greedy works:</b> Any unmatched buy-sell pair must eventually be paired.
 * Matching closest pairs first minimizes distance overhead before moving to distant pairs.</p>
 *
 * <p><b>COMPLEXITY: Time O(n) | Space O(1)</b></p>
 *
 * <p><b>EXAMPLE TRACE:</b></p>
 * <pre>
 * wine = [5, -4, 1, -3, 1]
 *         ↑   ↑ ↑  ↑  ↑
 *     Sell  Buy  Sell Buy Sell
 *
 * Visualization (distances & quantities):
 *   Position: 0   1   2   3   4
 *   Value:    5  -4   1  -3   1
 *   Status:   S   B   S   B   S
 *
 * Matching Logic:
 *   Seller at 0 (has 5) ← → Buyer at 1 (needs 4)
 *     → Match 4 units: cost = |0-1| × 4 = 4
 *     → wine[0] -= 4 (now has 1 left), wine[1] = 0
 *     → Continue...
 * </pre>
 *

 * @see #buySell(int[], int)
 */
public class G02_Wine_Buy_Sell {

    /**
     * Test driver. Input: wine = [5, -4, 1, -3, 1]
     * Expected: Minimum cost to settle all buy-sell transactions
     */
    public static void main(String[] args) {
        int[] wine = new int[]{5, -4, 1, -3, 1};
        int length = wine.length;
        System.out.print("\nMinimum operation needed to buy sell a wine is : " + buySell(wine, length));
    }

    /**
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     *
     * <p><b>STEP 1: Initialize Pointers</b></p>
     * <pre>
     * buy = 0 (search for next seller)
     * sell = 0 (search for next buyer)
     * answer = 0 (accumulate cost)
     * wine = [5, -4, 1, -3, 1]
     * </pre>
     *
     * <p><b>STEP 2: Find First Seller & Buyer</b></p>
     * <pre>
     * Skip positions where wine[buy] ≤ 0 (not sellers):
     *   buy=0: wine[0]=5 > 0 ✓ Stop, found seller
     *
     * Skip positions where wine[sell] ≥ 0 (not buyers):
     *   sell=0: wine[0]=5 ≥ 0 → continue
     *   sell=1: wine[1]=-4 < 0 ✓ Stop, found buyer
     *
     * Pointers: buy=0 (seller), sell=1 (buyer)
     * </pre>
     *
     * <p><b>STEP 3: Match Smallest Quantity (Greedy Choice)</b></p>
     * <pre>
     * Seller at buy=0:  wine[0]=5 units
     * Buyer at sell=1:  wine[1]=-4 units (needs 4)
     *
     * |5| > |-4|? → YES (seller has more)
     *   → Transport 4 units from 0→1
     *   → cost = |0-1| × 4 = 4
     *   → wine[0] += wine[1] = 5+(-4) = 1
     *   → wine[1] = 0
     * answer = 4
     *
     * State: wine = [1, 0, 1, -3, 1]
     * </pre>
     *
     * <p><b>STEP 4: Continue Matching</b></p>
     * <pre>
     * buy=0: wine[0]=1 > 0 ✓ (still has seller at pos 0)
     * sell=1: wine[1]=0 ≥ 0 → continue to sell=2,3...
     * sell=3: wine[3]=-3 < 0 ✓ Found buyer
     *
     * Seller: wine[0]=1 | Buyer: wine[3]=-3
     * |1| < |-3|? → YES (buyer needs more)
     *   → Transport 1 unit from 0→3
     *   → cost = |0-3| × 1 = 3
     *   → wine[3] += wine[0] = -3+1 = -2
     *   → wine[0] = 0
     * answer = 4 + 3 = 7
     *
     * State: wine = [0, 0, 1, -2, 1]
     * </pre>
     *
     * <p><b>STEP 5: Continue Until All Matched</b></p>
     * <pre>
     * buy=0: wine[0]=0 ≤ 0 → buy++
     * buy=1: wine[1]=0 ≤ 0 → buy++
     * buy=2: wine[2]=1 > 0 ✓ Found seller
     *
     * sell=3: wine[3]=-2 < 0 ✓ Found buyer
     *
     * Seller: wine[2]=1 | Buyer: wine[3]=-2
     * |1| < |-2|? → YES
     *   → cost = |2-3| × 1 = 1
     *   → wine[3] = -2+1 = -1
     *   → wine[2] = 0
     * answer = 7 + 1 = 8
     *
     * buy=4: wine[4]=1 > 0 ✓
     * sell=3: wine[3]=-1 < 0 ✓
     *
     * |1| = |-1|? → YES (exact match)
     *   → cost = |4-3| × 1 = 1
     *   → Both zeroed
     * answer = 8 + 1 = 9
     * </pre>
     *
     * <p><b>FINAL TABLE:</b></p>
     * <pre>
     * Iteration  │ Buyer Pos │ Seller Pos │ Qty  │ Distance │ Cost │ Total
     * ───────────┼───────────┼────────────┼──────┼──────────┼──────┼──────
     *     1      │     1     │     0      │  4   │    1     │  4   │  4
     *     2      │     3     │     0      │  1   │    3     │  3   │  7
     *     3      │     3     │     2      │  1   │    1     │  1   │  8
     *     4      │     3     │     4      │  1   │    1     │  1   │  9
     *
     * Total Minimum Cost: 9
     * </pre>
     *
     * @param wine array where positive = quantity to sell, negative = quantity to buy
     * @param length array length
     * @return minimum transport cost to settle all transactions
     */
    private static int buySell(int[] wine, int length) {
        int buy = 0, sell = 0, answer = 0;

        while (buy < length && sell < length) {
            // STEP 1: Skip sellers (find next position with wine to sell)
            while (wine[buy] <= 0) {
                buy++;
                if (buy == length) return answer;
            }

            // STEP 2: Skip buyers (find next position needing wine)
            while (wine[sell] >= 0) {
                sell++;
                if (sell == length) return answer;
            }

            // STEP 3: Match smaller quantity between buyer & seller
            if (Math.abs(wine[buy]) > Math.abs(wine[sell])) {
                // Seller has more: fulfill buyer completely, reduce seller
                answer += Math.abs(buy - sell) * Math.abs(wine[sell]);
                wine[buy] += wine[sell];  // Reduce seller quantity
                wine[sell] = 0;            // Buyer done
            } else {
                // Buyer needs more: fulfill seller completely, reduce buyer
                answer += Math.abs(buy - sell) * wine[buy];
                wine[sell] += wine[buy];   // Reduce buyer need
                wine[buy] = 0;             // Seller done
            }
        }

        return answer;
    }
}