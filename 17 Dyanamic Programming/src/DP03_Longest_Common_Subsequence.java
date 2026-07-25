import java.util.Arrays;

/**
 * <h2>Problem Statement: Longest Common Subsequence (LCS)</h2>
 * <p>
 * Given two strings {@code sequence1} and {@code sequence2}, find the length of their longest
 * common subsequence. A <i>subsequence</i> is a sequence that appears in the same relative order
 * in both strings, but not necessarily contiguous.
 * </p>
 * <p>
 * <b>Example:</b><br>
 * {@code sequence1 = "ABUSER"}<br>
 * {@code sequence2 = "ABDSCR"}<br>
 * The Longest Common Subsequence is <b>"ABSR"</b> with length <b>4</b>.
 * </p>
 *
 * <h2>Core Algorithmic Logic</h2>
 * <p>
 * When comparing character {@code sequence1[i - 1]} against {@code sequence2[j - 1]}:
 * </p>
 * <ul>
 *   <li>
 *     <b>Match Case (Characters are equal):</b><br>
 *     The character contributes {@code 1} to the LCS length. Reduce both sequence lengths by 1.<br>
 *     {@code LCS(i, j) = 1 + LCS(i - 1, j - 1)}
 *   </li>
 *   <li>
 *     <b>Mismatch Case (Characters are different):</b><br>
 *     Branch into two decisions and take the maximum:<br>
 *     1. Drop last character of sequence1 &rarr; {@code LCS(i - 1, j)}<br>
 *     2. Drop last character of sequence2 &rarr; {@code LCS(i, j - 1)}<br>
 *     {@code LCS(i, j) = Math.max(LCS(i - 1, j), LCS(i, j - 1))}
 *   </li>
 * </ul>
 *
 * <h2>Recursion Decision Tree Diagram</h2>
 * <pre>{@code
 * Example: sequence1 = "ABU" (len=3), sequence2 = "ABD" (len=3)
 *
 *                          LCS("ABU", "ABD") [i=3, j=3]
 *                                 │
 *                  'U' != 'D'  (Mismatch Case)
 *                        /                 \
 *                       /                   \
 *       LCS("AB", "ABD") [i=2, j=3]     LCS("ABU", "AB") [i=3, j=2]
 *              │                               │
 *         'B' != 'D'                       'U' != 'B'
 *          /       \                       /        \
 *     (i=1,j=3)  (i=2,j=2)             (i=2,j=2)  (i=3,j=1)
 *                    │                     │
 *                'B' == 'B'            [CACHED IN DP!]
 *                 (Match)
 *                    │
 *             1 + LCS("A", "A") [i=1, j=1]
 *                    │
 *                'A' == 'A'
 *                 (Match)
 *                    │
 *             1 + LCS("", "") [i=0, j=0] = 0
 * }</pre>
 *
 * <h2>2D DP Array State Transition Diagram</h2>
 * <pre>{@code
 *  sequence1 = "ABUSER" (rows), sequence2 = "ABDSCR" (cols)
 *
 *        ""   A   B   D   S   C   R    <-- sequence2
 *  ""  [  0,  0,  0,  0,  0,  0,  0 ]
 *  A   [  0,  1,  1,  1,  1,  1,  1 ]  <- 'A'=='A': diagonal + 1
 *  B   [  0,  1,  2,  2,  2,  2,  2 ]  <- 'B'=='B': diagonal + 1
 *  U   [  0,  1,  2,  2,  2,  2,  2 ]  <- Mismatch: max(top, left)
 *  S   [  0,  1,  2,  2,  3,  3,  3 ]  <- 'S'=='S': diagonal + 1
 *  E   [  0,  1,  2,  2,  3,  3,  3 ]  <- Mismatch: max(top, left)
 *  R   [  0,  1,  2,  2,  3,  3,  4 ]  <- 'R'=='R': diagonal + 1
 *  ^
 * sequence1                              RESULT: dp[6][6] = 4
 * }</pre>
 *
 * <h2>Space-Optimized Array Diagram (1D Rolling Arrays)</h2>
 * <pre>{@code
 * Row 'i' only depends on Row 'i-1'. We replace the 2D grid with two 1D arrays:
 *
 * Initial:
 * previousRow = [0, 0, 0, 0, 0, 0, 0]   (Row i - 1)
 * currentRow  = [0, 0, 0, 0, 0, 0, 0]   (Row i)
 *
 * Iteration 1 ('A'): currentRow = [0, 1, 1, 1, 1, 1, 1] -> saved to previousRow
 * Iteration 2 ('B'): currentRow = [0, 1, 2, 2, 2, 2, 2] -> saved to previousRow
 * ...
 * Iteration 6 ('R'): currentRow = [0, 1, 2, 2, 3, 3, 4] -> final answer: 4
 * }</pre>
 */
public class DP03_Longest_Common_Subsequence {

    /**
     * Entry point to execute Top-Down, Bottom-Up, and Space-Optimized variants of LCS.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        String sequence1 = "ABUSER";
        String sequence2 = "ABDSCR";
        int length1 = sequence1.length();
        int length2 = sequence2.length();

        // Memoization table initialized with -1
        int[][] dp = new int[length1 + 1][length2 + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        System.out.println("LCS Length (Normal Approach T.C = O (2^n)  )     : " + lcs(length1, length2, sequence1, sequence2, dp));
        System.out.println("LCS Length  (Optimised Approach T.C = O (n*m) )  : " + lcs_Optimised(length1, length2, sequence1, sequence2));
        System.out.println("LCS Length (Space-Optimized T.C = O (n*m)  )     : " + lcs_SpaceOptimised(length1, length2, sequence1, sequence2));
    }

    /**
     * Computes LCS using Top-Down Memoization (Recursion + Caching).
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length1 * length2)} for the memoization table + recursion stack space.</p>
     *
     * @param length1   Remaining length of {@code sequence1} to evaluate.
     * @param length2   Remaining length of {@code sequence2} to evaluate.
     * @param sequence1 First input string.
     * @param sequence2 Second input string.
     * @param dp        Memoization table storing results for state {@code (length1, length2)}.
     * @return Length of LCS for the evaluated sub-strings.
     */
    private static int lcs(int length1, int length2, String sequence1, String sequence2, int[][] dp) {
        // Base Case: If either string length reaches 0, LCS is 0
        if (length1 == 0 || length2 == 0) return 0;

        // Cache Check: Return precomputed answer if available
        if (dp[length1][length2] != -1) return dp[length1][length2];

        // Match Case: Characters match
        if (sequence1.charAt(length1 - 1) == sequence2.charAt(length2 - 1)) {
            return dp[length1][length2] = 1 + lcs(length1 - 1, length2 - 1, sequence1, sequence2, dp);
        }

        // Mismatch Case: Max choice of skipping from sequence1 or sequence2
        return dp[length1][length2] = Math.max(
                lcs(length1 - 1, length2, sequence1, sequence2, dp),
                lcs(length1, length2 - 1, sequence1, sequence2, dp)
        );
    }

    /**
     * Computes LCS using Bottom-Up Tabular Iteration (2D Matrix).
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length1 * length2)} for the 2D DP matrix.</p>
     *
     * @param length1   Length of {@code sequence1}.
     * @param length2   Length of {@code sequence2}.
     * @param sequence1 First input string.
     * @param sequence2 Second input string.
     * @return Length of LCS.
     */
    private static int lcs_Optimised(int length1, int length2, String sequence1, String sequence2) {
        int[][] dpTabular = new int[length1 + 1][length2 + 1];

        for (int i = 1; i <= length1; i++) {
            for (int j = 1; j <= length2; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    dpTabular[i][j] = 1 + dpTabular[i - 1][j - 1];
                } else {
                    dpTabular[i][j] = Math.max(dpTabular[i - 1][j], dpTabular[i][j - 1]);
                }
            }
        }
        return dpTabular[length1][length2];
    }

    /**
     * Computes LCS using Space-Optimized DP (1D Rolling Arrays).
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length2)} auxiliary memory (stores only 2 rows instead of entire matrix).</p>
     *
     * @param length1   Length of {@code sequence1}.
     * @param length2   Length of {@code sequence2}.
     * @param sequence1 First input string.
     * @param sequence2 Second input string.
     * @return Length of LCS.
     */
    private static int lcs_SpaceOptimised(int length1, int length2, String sequence1, String sequence2) {
        int[] previousRow = new int[length2 + 1];
        int[] currentRow = new int[length2 + 1];

        for (int i = 1; i <= length1; i++) {
            for (int j = 1; j <= length2; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    currentRow[j] = 1 + previousRow[j - 1];
                } else {
                    currentRow[j] = Math.max(previousRow[j], currentRow[j - 1]);
                }
            }
            // Copy current row data to previous row for next character iteration
            previousRow = currentRow.clone();
        }
        return previousRow[length2];
    }
}