/**
 * <h1>Matrix Chain Multiplication - Optimal Parenthesization</h1>
 *
 * <p><b>PROBLEM:</b></p>
 * <p>Given a sequence of matrices {@code A1, A2, ..., An} with dimensions
 * {@code p0×p1, p1×p2, ..., p(n-1)×pn}.
 * Find the most efficient way to multiply these matrices together (minimum number of scalar multiplications).</p>
 *
 * <p><b>KEY INSIGHT:</b></p>
 * <p>Matrix multiplication is associative: {@code (A·B)·C = A·(B·C)}</p>
 * <p>Different parenthesizations result in different computational costs.</p>
 *
 * <p><b>EXAMPLE:</b></p>
 * <p>Three matrices: {@code A (10×30), B (30×5), C (5×60)}</p>
 * <pre>
 * Option 1: (A·B)·C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 multiplications
 * Option 2: A·(B·C) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 multiplications
 * Optimal: Option 1 with 4500 multiplications
 * </pre>
 *
 * <p><b>INPUT FORMAT:</b></p>
 * <p>{@code array[] = [p0, p1, p2, ..., pn]} where matrix {@code i} has dimensions {@code p[i-1] × p[i]}</p>
 * <p>Example: {@code [10, 30, 5, 60]} represents:</p>
 * <pre>
 *   - Matrix 0: 10×30
 *   - Matrix 1: 30×5
 *   - Matrix 2: 5×60
 * </pre>
 *
 * <p><b>APPROACH: Dynamic Programming (Bottom-Up / Tabulation)</b></p>
 * <ol>
 *   <li>{@code dp[i][j]} = minimum scalar multiplications needed to compute {@code matrices[i..j]}</li>
 *   <li>Try all possible split points {@code k} between {@code i} and {@code j}:
 *       <ul>
 *         <li>Left subchain: {@code matrices[i..k]} costs {@code dp[i][k]}</li>
 *         <li>Right subchain: {@code matrices[k+1..j]} costs {@code dp[k][j]}</li>
 *         <li>Multiply results: {@code array[i] × array[k+1] × array[j+1]} scalar multiplications</li>
 *         <li>Total: {@code dp[i][k] + dp[k][j] + array[i] × array[k+1] × array[j+1]}</li>
 *       </ul>
 *   </li>
 *   <li>Take minimum over all {@code k} values</li>
 * </ol>
 *
 * <p><b>STATE DEFINITION:</b></p>
 * <p>{@code dp[row][col]} = minimum cost to multiply matrices from index {@code row} to index {@code col} (inclusive)</p>
 *
 * <p><b>RECURRENCE RELATION:</b></p>
 * <pre>
 * dp[i][i] = 0  (single matrix, no multiplication needed)
 * dp[i][j] = min(dp[i][k] + dp[k+1][j] + array[i] × array[k+1] × array[j+1])
 *            for all k from i to j-1
 * </pre>
 *
 * <p><b>COMPLEXITY:</b></p>
 * <ul>
 *   <li>Time: <b>O(n<sup>3</sup>)</b> - three nested loops</li>
 *   <li>Space: <b>O(n<sup>2</sup>)</b> - 2D DP table</li>
 * </ul>
 * @see #matrixMultiplication(int[], int)
 */
public class DP07_Matrix_Chain_Multiplication {

    /**
     * <p>Computes the minimum number of scalar multiplications needed to multiply a chain of matrices
     * using dynamic programming with optimal substructure.</p>
     *
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     * <p><u>Example:</u> {@code array = [10, 30, 5, 60], n = 4}</p>
     * <p>This represents 3 matrices:</p>
     * <pre>
     * Matrix 0: 10×30
     * Matrix 1: 30×5
     * Matrix 2: 5×60
     * </pre>
     *
     * <p><u>DP TABLE:</u> (5×5, but only upper triangle used)</p>
     * <pre>
     *     0    1      2       3
     * 0 [ 0    ?      ?       ?  ]
     * 1 [ 0    0      ?       ?  ]
     * 2 [ 0    0      0       ?  ]
     * 3 [ 0    0      0       0  ]
     * 4 [ 0    0      0       0  ]
     * </pre>
     *
     * <p><b>Building the DP Table:</b></p>
     * <p><i>Step 1: Chain length</i> {@code len=2} (2 matrices multiplied)</p>
     * <pre>
     * dp[0][1] = cost to multiply M0·M1 (10×30×5) = 1500
     * dp[1][2] = cost to multiply M1·M2 (30×5×60) = 9000
     * </pre>
     *
     * <p><i>Step 2: Chain length</i> {@code len=3} (3 matrices multiplied)</p>
     * <pre>
     * dp[0][2] = min(
     *   k=0: dp[0][0] + dp[1][2] + array[0]×array[1]×array[2] = 0 + 9000 + 1500 = 10500
     *   k=1: dp[0][1] + dp[2][2] + array[0]×array[2]×array[3] = 1500 + 0 + 3000 = 4500 ← MIN
     * ) = 4500
     * </pre>
     *
     * <p><b>Key Points:</b></p>
     * <ul>
     *   <li>{@code dp[i][i] = 0} (single matrix requires no multiplication)</li>
     *   <li>Iterate through increasing chain lengths {@code len}</li>
     *   <li>For each chain, try all split points {@code k} and take minimum</li>
     *   <li>Answer always in {@code dp[0][n-1]}</li>
     * </ul>
     *
     * <p><b>Known Bugs Fixed:</b></p>
     * <ul>
     *   <li><code>❌ for (int len = 2; len &lt; n; len++)</code> → <code>✓ for (int len = 2; len &lt;= n-1; len++)</code>
     *       <br/>Reason: Original loop never considers full matrix chain</li>
     *   <li><code>❌ Math.min(dp[row][col], ...)</code> without init → <code>✓ dp[row][col] = Integer.MAX_VALUE first</code>
     *       <br/>Reason: Comparing with default 0 causes incorrect minimums</li>
     * </ul>
     *
     * @param array dimension array where {@code array[i]} is the row count of matrix {@code i}
     *              and {@code array[i+1]} is the column count of matrix {@code i}.
     *              For example, {@code [10, 30, 5, 60]} represents matrices with dimensions
     *              {@code 10×30, 30×5, 5×60}. Length must be {@code n}.
     * @param n     length of the {@code array} parameter (= number of matrices + 1)
     *
     * @return the minimum number of scalar multiplications required to compute the entire chain
     *
     * @throws IllegalArgumentException if {@code n < 2} or {@code array} has insufficient elements
     *
     * @example
     * <pre>{@code
     * // Example 1: Three matrices
     * int[] array = {10, 30, 5, 60};
     * int result = matrixMultiplication(array, 4);
     * // result = 4500
     * // Optimal: (M0·M1)·M2 = (10·30·5) + (10·5·60) = 1500 + 3000 = 4500
     *
     * // Example 2: Four matrices
     * int[] array2 = {40, 20, 30, 10, 30};
     * int result2 = matrixMultiplication(array2, 5);
     * // result2 = 26000
     * }</pre>
     *
     * @see java.lang.Math#min(int, int)
     */
    static int matrixMultiplication(int[] array, int n) {
        // dp[i][j] = minimum cost to multiply matrices from index i to j
        int[][] dp = new int[n][n];

        // len = number of matrices in current chain being considered (2, 3, 4, ... n-1)
        // len=2 means multiply 2 matrices (1 multiplication operation)
        // len=n-1 means multiply all n-1 matrices
        for (int len = 2; len <= n - 1; len++) {

            // For each chain of length 'len', try all starting positions
            // row = starting matrix index
            // col = ending matrix index = row + len
            for (int row = 0, col = len; row < n - len; row++, col++) {

                // BUG FIX: Initialize to Integer.MAX_VALUE before comparing with Math.min
                dp[row][col] = Integer.MAX_VALUE;

                // Try all possible split points between row and col
                // k divides the chain into two parts: [row..k] and [k+1..col]
                // Both must be computed first, then their results multiplied
                for (int k = row + 1; k < col; k++) {

                    // Cost = cost of left subchain + cost of right subchain + cost to multiply results
                    //
                    // Left subchain [row..k]:   dimensions array[row] × array[k+1]
                    // Right subchain [k+1..col]: dimensions array[k+1] × array[col+1]
                    // Multiplying results:      array[row] × array[k+1] × array[col+1] scalar mults

                    int cost = dp[row][k] + dp[k][col] + array[row] * array[k] * array[col];
                    dp[row][col] = Math.min(dp[row][col], cost);
                }
            }
        }

        // Return cost to multiply all matrices from index 0 to n-1
        return dp[0][n - 1];
    }

    /**
     * <p>Entry point and test driver for the matrix chain multiplication algorithm.</p>
     *
     * <p><b>Test Cases:</b></p>
     * <table border="1" cellpadding="5">
     *   <tr>
     *     <th>Case</th>
     *     <th>Matrices</th>
     *     <th>Optimal Cost</th>
     *     <th>Parenthesization</th>
     *   </tr>
     *   <tr>
     *     <td>1</td>
     *     <td>{@code A(10×30), B(30×5), C(5×60)}</td>
     *     <td>4500</td>
     *     <td>{@code (A·B)·C}</td>
     *   </tr>
     *   <tr>
     *     <td>2</td>
     *     <td>{@code A(40×20), B(20×30), C(30×10), D(10×30)}</td>
     *     <td>26000</td>
     *     <td>Optimal chain</td>
     *   </tr>
     *   <tr>
     *     <td>3</td>
     *     <td>{@code A(2×3), B(3×4)}</td>
     *     <td>24</td>
     *     <td>{@code A·B}</td>
     *   </tr>
     * </table>
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Test case 1: 3 matrices
        int[] array1 = {10, 30, 5, 60};
        int n1 = array1.length;
        System.out.println("Test Case 1:");
        System.out.println("Matrices: A(10x30), B(30x5), C(5x60)");
        System.out.println("Minimum scalar multiplications: " + matrixMultiplication(array1, n1));
        System.out.println("Expected: 4500\n");

        // Test case 2: 4 matrices
        int[] array2 = {40, 20, 30, 10, 30};
        int n2 = array2.length;
        System.out.println("Test Case 2:");
        System.out.println("Matrices: A(40x20), B(20x30), C(30x10), D(10x30)");
        System.out.println("Minimum scalar multiplications: " + matrixMultiplication(array2, n2));
        System.out.println("Expected: 26000\n");

        // Test case 3: 2 matrices
        int[] array3 = {2, 3, 4};
        int n3 = array3.length;
        System.out.println("Test Case 3:");
        System.out.println("Matrices: A(2x3), B(3x4)");
        System.out.println("Minimum scalar multiplications: " + matrixMultiplication(array3, n3));
        System.out.println("Expected: 24");
    }
}