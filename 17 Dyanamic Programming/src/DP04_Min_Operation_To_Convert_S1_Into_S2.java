import java.util.Arrays;

/**
 * <h2>Problem Statement: Minimum Operations to Convert S1 into S2 (Edit Distance / Levenshtein Distance)</h2>
 * <p>
 * Given two strings {@code sequence1} and {@code sequence2}, find the minimum number of operations
 * required to convert {@code sequence1} into {@code sequence2}.
 * </p>
 * <p>Allowed operations on a character:</p>
 * <ul>
 *   <li><b>Insert</b> a character</li>
 *   <li><b>Delete</b> a character</li>
 *   <li><b>Replace</b> a character</li>
 * </ul>
 *
 * <h2>Core State Transitions</h2>
 * <ul>
 *   <li>
 *     <b>Match Case ({@code s1[i-1] == s2[j-1]}):</b><br>
 *     No operation needed. Inherit score from diagonal.<br>
 *     {@code dp[i][j] = dp[i-1][j-1]}
 *   </li>
 *   <li>
 *     <b>Mismatch Case ({@code s1[i-1] != s2[j-1]}):</b><br>
 *     Take {@code 1 + min(delete, insert, replace)}:<br>
 *     1. <b>Delete:</b> {@code dp[i-1][j]}<br>
 *     2. <b>Insert:</b> {@code dp[i][j-1]}<br>
 *     3. <b>Replace:</b> {@code dp[i-1][j-1]}
 *   </li>
 * </ul>
 *
 * <h2>Recursion & State Diagram</h2>
 * <pre>{@code
 * Base Case Initialization:
 * dp[i][0] = i  (Deleting i characters to make empty string)
 * dp[0][j] = j  (Inserting j characters from empty string)
 *
 * Matrix Fill Example for "ABUSER" -> "ABDSCR":
 *
 *        ""   A   B   D   S   C   R   <-- sequence2
 *  ""  [  0,  1,  2,  3,  4,  5,  6 ]
 *  A   [  1,  0,  1,  2,  3,  4,  5 ]  <- 'A'=='A' (diagonal)
 *  B   [  2,  1,  0,  1,  2,  3,  4 ]  <- 'B'=='B' (diagonal)
 *  U   [  3,  2,  1,  1,  2,  3,  4 ]  <- Mismatch: 1 + min(top, left, diagonal)
 *  S   [  4,  3,  2,  2,  1,  2,  3 ]  <- 'S'=='S' (diagonal)
 *  E   [  5,  4,  3,  3,  2,  2,  3 ]  <- Mismatch
 *  R   [  6,  5,  4,  4,  3,  3,  2 ]  <- 'R'=='R' (diagonal)
 *  ^
 * sequence1                              ANSWER: dp[6][6] = 2
 * }</pre>
 */
public class DP04_Min_Operation_To_Convert_S1_Into_S2 {

    /**
     * Computes Edit Distance using Top-Down Memoization.
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length1 * length2)} for DP table + call stack.</p>
     *
     * @param length1   Remaining length of {@code sequence1}.
     * @param length2   Remaining length of {@code sequence2}.
     * @param sequence1 Source string.
     * @param sequence2 Target string.
     * @param memoDP    2D cache array for storing computed states.
     * @return Minimum operations for given subproblem.
     */
    private static int convert_S1_to_S2(int length1, int length2, String sequence1, String sequence2, int[][] memoDP) {
        // Base Cases
        if (length1 == 0) return length2; // Need to insert all remaining chars of sequence2
        if (length2 == 0) return length1; // Need to delete all remaining chars of sequence1

        if (memoDP[length1][length2] != -1) return memoDP[length1][length2];

        if (sequence1.charAt(length1 - 1) == sequence2.charAt(length2 - 1)) {
            return memoDP[length1][length2] = convert_S1_to_S2(length1 - 1, length2 - 1, sequence1, sequence2, memoDP);
        }

        int deleteOp = convert_S1_to_S2(length1 - 1, length2, sequence1, sequence2, memoDP);
        int insertOp = convert_S1_to_S2(length1, length2 - 1, sequence1, sequence2, memoDP);
        int replaceOp = convert_S1_to_S2(length1 - 1, length2 - 1, sequence1, sequence2, memoDP);

        return memoDP[length1][length2] = 1 + Math.min(deleteOp, Math.min(insertOp, replaceOp));
    }

    /**
     * Computes Edit Distance using Tabular Dynamic Programming (Bottom-Up).
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length1 * length2)}</p>
     *
     * @param length1   Length of {@code sequence1}.
     * @param length2   Length of {@code sequence2}.
     * @param sequence1 Source string.
     * @param sequence2 Target string.
     * @return Minimum number of edit operations required.
     */
    private static int convert_S1_to_S2_Optimised(int length1, int length2, String sequence1, String sequence2) {
        int[][] dp = new int[length1 + 1][length2 + 1];

        // Base case setup
        for (int i = 0; i <= length1; i++) dp[i][0] = i; // Deletions needed
        for (int j = 0; j <= length2; j++) dp[0][j] = j; // Insertions needed

        for (int i = 1; i <= length1; i++) {
            for (int j = 1; j <= length2; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation required, Fill data from diagonal element
                } else {
//                    Select minimum from just 1 leftRow's element, just 1 UpperColumn's element, just 1 Diagonal element
                    int deleteOp = dp[i - 1][j];
                    int insertOp = dp[i][j - 1];
                    int replaceOp = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(deleteOp, Math.min(insertOp, replaceOp));
                }
            }
        }
        return dp[length1][length2];
    }

    /**
     * Computes Edit Distance using Space-Optimized DP (1D Rolling Arrays).
     *
     * <p>Time Complexity: {@code O(length1 * length2)}<br>
     * Space Complexity: {@code O(length2)} auxiliary space.</p>
     *
     * @param length1   Length of {@code sequence1}.
     * @param length2   Length of {@code sequence2}.
     * @param sequence1 Source string.
     * @param sequence2 Target string.
     * @return Minimum operations.
     */
    private static int convert_S1_to_S2_Space_Optimised(int length1, int length2, String sequence1, String sequence2) {
        int[] previousRow = new int[length2 + 1];
        int[] currentRow = new int[length2 + 1];

        // Base case initialization for previous row (row 0)
        for (int j = 0; j <= length2; j++) {
            previousRow[j] = j;
        }

        for (int i = 1; i <= length1; i++) {
            currentRow[0] = i; // Base case for column 0
            for (int j = 1; j <= length2; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    currentRow[j] = previousRow[j - 1];
                } else {
                    currentRow[j] = 1 + Math.min(previousRow[j], Math.min(currentRow[j - 1], previousRow[j - 1]));
                }
            }
            previousRow = currentRow.clone();
        }
        return previousRow[length2];
    }

    public static void main(String[] args) {
        String sequence1 = "ABUSER";
        String sequence2 = "ABDSCR";
        int length1 = sequence1.length();
        int length2 = sequence2.length();

        System.out.println("Min Operations (Normal Approach T.C = O (2^n)  )     : " + convert_S1_to_S2_Optimised(length1, length2, sequence1, sequence2));

        // Setup Memoization matrix initialized with -1
        int[][] memoDP = new int[length1 + 1][length2 + 1];
        for (int[] row : memoDP) {
            Arrays.fill(row, -1);
        }
        System.out.println("Min Operations (Optimised Approach T.C = O (n*m) )   : " + convert_S1_to_S2(length1, length2, sequence1, sequence2, memoDP));
        System.out.println("Min Operations (Space-Optimized T.C = O (n*m)  )     : " + convert_S1_to_S2_Space_Optimised(length1, length2, sequence1, sequence2));

    }
}