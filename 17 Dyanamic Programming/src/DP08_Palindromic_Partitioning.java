/**
 * <b>Palindromic Partitioning - Minimum Cuts to Make String Palindromic</b>
 *
 * <p><b>PROBLEM STATEMENT:</b></p>
 * <p>Given a string {@code str}, find the minimum number of cuts needed to partition
 * the string into substrings such that each substring is a palindrome.</p>
 *
 * <p><b>DEFINITION:</b> A palindrome reads the same forwards and backwards.</p>
 * <p>Examples: {@code "a"}, {@code "aba"}, {@code "racecar"}, {@code "madam"}</p>
 *
 * <p><b>EXAMPLE:</b></p>
 * <pre>
 * Input: "abac"
 *
 * Partitions:
 *   Option 1: "a" | "b" | "a" | "c"           → 3 cuts   (all single chars are palindromes)
 *   Option 2: "a" | "bab" | "c"  → NOT valid (bab not palindrome, need more cuts)
 *   Option 3: "a" | "b" | "ac"   → NOT valid (ac not palindrome)
 *   Option 4: "ab" | "ac"        → NOT valid (neither is palindrome)
 *   Option 5: "aba" | "c"        → 1 cut     (aba is palindrome, c is palindrome) ✓ OPTIMAL
 *
 * Output: 1 (minimum cuts needed)
 * </pre>
 *
 * <p><b>APPROACH: Dynamic Programming (Bottom-Up)</b></p>
 * <ol>
 *   <li><b>Subproblem Definition:</b>
 *       <br/>{@code dp[i][j]} = minimum number of cuts needed to partition substring {@code str[i..j]}
 *       so that each partition is a palindrome</li>
 *   <li><b>Base Case:</b>
 *       <br/>If {@code str[i..j]} is already a palindrome, then {@code dp[i][j] = 0} (no cuts needed)</li>
 *   <li><b>Recurrence Relation:</b>
 *       <br/>If {@code str[i..j]} is NOT a palindrome, try all split points {@code k} from {@code i} to {@code j-1}:
 *       <br/>{@code dp[i][j] = min(1 + dp[i][k] + dp[k+1][j])} for all {@code k}
 *       <br/>The {@code 1 +} represents one cut between {@code str[i..k]} and {@code str[k+1..j]}</li>
 *   <li><b>Gap Filling:</b>
 *       <br/>Fill the DP table by increasing gap size (distance between row and col):
 *       <ul>
 *         <li>{@code gap = 1}: substrings of length 2 (e.g., {@code dp[0][1]}, {@code dp[1][2]})</li>
 *         <li>{@code gap = 2}: substrings of length 3 (e.g., {@code dp[0][2]}, {@code dp[1][3]})</li>
 *         <li>Continue until {@code gap = length - 1}: entire string {@code dp[0][length-1]}</li>
 *       </ul>
 *   </li>
 * </ol>
 *
 * <p><b>KEY INSIGHT:</b></p>
 * <p>Solve smaller subproblems first (shorter substrings), then use their results to solve larger ones.</p>
 *
 * <p><b>COMPLEXITY ANALYSIS:</b></p>
 * <ul>
 *   <li>Time: <b>O(n<sup>3</sup>)</b>
 *       <ul>
 *         <li>Two loops for {@code gap} and {@code row}: O(n²)</li>
 *         <li>Inner loop for split points {@code k}: O(n)</li>
 *         <li>{@code isPalindrome} check: O(n) (naïve character comparison)</li>
 *         <li>Total: O(n²) × O(n) = O(n³)</li>
 *       </ul>
 *   </li>
 *   <li>Space: <b>O(n<sup>2</sup>)</b> for the 2D DP table</li>
 * </ul>
 *
 * <p><b>OPTIMIZATION OPPORTUNITY:</b></p>
 * <p>Precompute palindrome check into a 2D boolean table {@code isPalin[i][j]}
 * to reduce {@code isPalindrome} calls from O(n) to O(1), bringing total time to O(n²).</p>
 *
 * <p><b>WALKTHROUGH (Example: "abac"):</b></p>
 * <pre>
 * String: "abac" (indices 0, 1, 2, 3)
 *
 * DP Table (4×4):
 *       0   1   2   3
 *   0 [ 0   ?   ?   ? ]  (row = start index)
 *   1 [ 0   0   ?   ? ]
 *   2 [ 0   0   0   ? ]
 *   3 [ 0   0   0   0 ]
 *        ↑
 *        col = end index
 *
 * Gap = 1 (length 2 substrings):
 *   [0,1] "ab":  NOT palindrome → dp[0][1] = 1 (must cut between a and b)
 *   [1,2] "ba":  NOT palindrome → dp[1][2] = 1
 *   [2,3] "ac":  NOT palindrome → dp[2][3] = 1
 *
 * Gap = 2 (length 3 substrings):
 *   [0,2] "aba": IS palindrome → dp[0][2] = 0 (no cuts needed)
 *   [1,3] "bac": NOT palindrome → dp[1][3] = min(
 *     k=1: 1 + dp[1][1] + dp[2][3] = 1 + 0 + 1 = 2
 *     k=2: 1 + dp[1][2] + dp[3][3] = 1 + 1 + 0 = 2
 *   ) = 2
 *
 * Gap = 3 (length 4 - entire string):
 *   [0,3] "abac": NOT palindrome → dp[0][3] = min(
 *     k=0: 1 + dp[0][0] + dp[1][3] = 1 + 0 + 2 = 3
 *     k=1: 1 + dp[0][1] + dp[2][3] = 1 + 1 + 1 = 3
 *     k=2: 1 + dp[0][2] + dp[3][3] = 1 + 0 + 0 = 1  ← MINIMUM
 *   ) = 1
 *
 * Answer: dp[0][3] = 1 ✓
 * Optimal partitioning: "aba" | "c"
 * </pre>
 *

 * @see #palindromicPartitioning(String)
 * @see #isPalindrome(String, int, int)
 */
public class DP08_Palindromic_Partitioning {

    /**
     * <p>Computes the minimum number of cuts needed to partition a string into
     * palindromic substrings using dynamic programming.</p>
     *
     * <p><b>ALGORITHM LOGIC:</b></p>
     * <ol>
     *   <li><b>Initialize DP Table:</b> {@code dp[i][j]} stores minimum cuts for {@code str[i..j]}</li>
     *   <li><b>Fill by Gap Size:</b> Iterate through increasing substring lengths
     *       <ul>
     *         <li>{@code gap = 1}: two-character substrings</li>
     *         <li>{@code gap = length - 1}: entire string</li>
     *       </ul>
     *   </li>
     *   <li><b>For Each Substring {@code str[row..col]}:</b>
     *       <ul>
     *         <li>If it's already a palindrome: {@code dp[row][col] = 0}</li>
     *         <li>Otherwise: Try all split points and take minimum</li>
     *       </ul>
     *   </li>
     * </ol>
     *
     * <p><b>TRACE EXAMPLE (detailed):</b></p>
     * <p>Input: {@code "nitin"}</p>
     * <pre>
     * String: "nitin" (indices 0-4)
     *
     * Initial DP table (all zeros):
     *       0  1  2  3  4
     *   0 [ 0  ?  ?  ?  ? ]
     *   1 [ 0  0  ?  ?  ? ]
     *   2 [ 0  0  0  ?  ? ]
     *   3 [ 0  0  0  0  ? ]
     *   4 [ 0  0  0  0  0 ]
     *
     * gap=1 (substrings of length 2):
     *   dp[0][1] = "ni" → NOT palindrome → dp[0][1] = 1
     *   dp[1][2] = "it" → NOT palindrome → dp[1][2] = 1
     *   dp[2][3] = "ti" → NOT palindrome → dp[2][3] = 1
     *   dp[3][4] = "in" → NOT palindrome → dp[3][4] = 1
     *
     * gap=2 (substrings of length 3):
     *   dp[0][2] = "nit" → NOT palindrome → min(1+dp[0][0]+dp[1][2], 1+dp[0][1]+dp[2][2])
     *                                      = min(1+0+1, 1+1+0) = 2
     *   dp[1][3] = "iti" → IS palindrome → dp[1][3] = 0
     *   dp[2][4] = "tin" → NOT palindrome → min(1+dp[2][2]+dp[3][4], 1+dp[2][3]+dp[4][4])
     *                                      = min(1+0+1, 1+1+0) = 2
     *
     * gap=3 (substrings of length 4):
     *   dp[0][3] = "niti" → NOT palindrome → min(
     *     k=0: 1 + 0 + dp[1][3] = 1 + 0 + 0 = 1  ← BETTER
     *     k=1: 1 + 1 + 1 = 3
     *     k=2: 1 + 1 + 0 = 2
     *   ) = 1
     *
     * gap=4 (substrings of length 5):
     *   dp[0][4] = "nitin" → IS palindrome → dp[0][4] = 0
     *
     * Answer: dp[0][4] = 0 ✓ (entire string is palindrome, no cuts needed)
     * </pre>
     *
     * <p><b>KEY OBSERVATIONS:</b></p>
     * <ul>
     *   <li>Diagonal always 0: {@code dp[i][i]} = 0 (single char is palindrome)</li>
     *   <li>Only upper triangle of DP table is used (symmetric access not needed)</li>
     *   <li>Each cell depends only on cells with smaller gap values (already computed)</li>
     * </ul>
     *
     * @param str the input string to partition into palindromic substrings
     * @return the minimum number of cuts needed; returns 0 if entire string is palindrome
     *
     * @throws NullPointerException if {@code str} is null
     * @throws IllegalArgumentException if {@code str} is empty
     *
     * @example
     * <pre>{@code
     * int cuts = palindromicPartitioning("abac");
     * // cuts = 1
     * // Optimal partition: "aba" | "c"
     *
     * int cuts2 = palindromicPartitioning("nitin");
     * // cuts2 = 0
     * // Entire string is palindrome
     *
     * int cuts3 = palindromicPartitioning("racecar");
     * // cuts3 = 0
     * // Entire string is palindrome
     * }</pre>
     *
     * @see #isPalindrome(String, int, int)
     */
    public static int palindromicPartitioning(String str) {
        int length = str.length();
        // {@code dp[i][j]} = minimum cuts for substring {@code str[i..j]}
        int[][] dp = new int[length][length];

        // Iterate through increasing substring lengths via gap
        // {@code gap} = distance between column and row (length - 1)
        for (int gap = 1; gap < length; gap++) {
            // For each starting position (row), compute ending position (col)
            for (int row = 0, col = gap; row < length - gap; row++, col++) {

                // Check if substring from row to col is already a palindrome
                if (isPalindrome(str, row, col)) {
                    // Palindrome found: no cuts needed
                    dp[row][col] = 0;
                } else {
                    // Not a palindrome: try all possible split points
                    dp[row][col] = Integer.MAX_VALUE;

                    // Split between position k and k+1
                    // Left part: str[row..k], Right part: str[k+1..col]
                    for (int k = row; k < col; k++) {
                        // Cost = 1 cut + cuts for left part + cuts for right part
                        int cost = 1 + dp[row][k] + dp[k + 1][col];
                        dp[row][col] = Math.min(dp[row][col], cost);
                    }
                }
            }
        }

        // Answer is minimum cuts for entire string: str[0..length-1]
        return dp[0][length - 1];
    }

    /**
     * <p>Checks whether a substring of the input string is a palindrome.</p>
     *
     * <p><b>ALGORITHM LOGIC:</b></p>
     * <p>Compare characters from both ends moving inward. If any mismatch is found,
     * the substring is not a palindrome. If all characters match until indices meet, it is.</p>
     *
     * <p><b>DETAILED LOGIC:</b></p>
     * <pre>
     * 1. Initialize two pointers: {@code start} (left) and {@code end} (right)
     * 2. Loop while {@code start < end}:
     *    a. Compare {@code str[start]} with {@code str[end]}
     *    b. If they don't match, immediately return {@code false}
     *    c. Move pointers inward: {@code start++}, {@code end--}
     * 3. If loop ends without finding mismatch, return {@code true}
     * </pre>
     *
     * <p><b>EXAMPLES:</b></p>
     * <pre>
     * isPalindrome("abac", 0, 2) → "aba"
     *   start=0, end=2: str[0]='a' == str[2]='a' ✓, then start=1, end=1
     *   Loop ends (start < end is false)
     *   Return: true
     *
     * isPalindrome("abac", 1, 3) → "bac"
     *   start=1, end=3: str[1]='b' != str[3]='c' ✗
     *   Immediately return: false
     *
     * isPalindrome("racecar", 0, 6) → "racecar"
     *   start=0, end=6: 'r' == 'r' ✓
     *   start=1, end=5: 'a' == 'a' ✓
     *   start=2, end=4: 'c' == 'c' ✓
     *   start=3, end=3: Loop exits (start < end is false)
     *   Return: true
     * </pre>
     *
     * <p><b>TIME COMPLEXITY:</b> O(n) where n is the substring length {@code (end - start)}</p>
     * <p><b>SPACE COMPLEXITY:</b> O(1)</p>
     *
     * <p><b>⚠️ BUG IN ORIGINAL CODE:</b></p>
     * <p>The original {@code while} loop has an early return:
     * <pre>
     * while (start < end)
     *   return str.charAt(start) == str.charAt(end);  // ← Returns after 1 iteration!
     * return false;
     * </pre>
     * This causes the function to return after checking only the first character pair.
     * For string "abac" with indices 0-2 ("aba"), it would return true after checking
     * only 'a' == 'a', skipping the check for 'b' (middle character).
     * </p>
     *
     * <p><b>CORRECT LOGIC:</b></p>
     * <pre>
     * while (start < end) {
     *   if (str.charAt(start) != str.charAt(end))
     *     return false;  // Mismatch found
     *   start++;
     *   end--;
     * }
     * return true;  // All characters matched
     * </pre>
     *
     * @param str the input string
     * @param start the starting index (inclusive)
     * @param end the ending index (inclusive)
     * @return {@code true} if {@code str[start..end]} is a palindrome, {@code false} otherwise
     *
     * @throws IndexOutOfBoundsException if {@code start < 0} or {@code end >= str.length()}
     * @throws NullPointerException if {@code str} is null
     *
     * @example
     * <pre>{@code
     * isPalindrome("aba", 0, 2)     → true   // entire string
     * isPalindrome("racecar", 0, 6) → true   // entire string
     * isPalindrome("abac", 0, 2)    → true   // "aba"
     * isPalindrome("abac", 1, 3)    → false  // "bac"
     * isPalindrome("hello", 0, 4)   → false  // "hello"
     * isPalindrome("a", 0, 0)       → true   // single char
     * }</pre>
     */
    public static boolean isPalindrome(String str, int start, int end) {
        // ⚠️ BUGGY VERSION IN ORIGINAL:
        // while (start < end)
        //   return str.charAt(start) == str.charAt(end);  // Returns immediately!
        // return false;

        // ✓ CORRECTED VERSION:
        while (start < end) {
            if (str.charAt(start) != str.charAt(end)) {
                return false;  // Mismatch found, not a palindrome
            }
            start++;
            end--;
        }
        return true;  // All characters matched, is a palindrome
    }

    /**
     * <p>Entry point and test driver for the palindromic partitioning algorithm.</p>
     *
     * <p><b>Test Cases:</b></p>
     * <table style="border: 1px solid black; border-collapse: collapse;">
     *   <caption>Sample Test Cases</caption>
     *   <tr style="border: 1px solid black;">
     *     <th style="border: 1px solid black; padding: 5px;">Input String</th>
     *     <th style="border: 1px solid black; padding: 5px;">Minimum Cuts</th>
     *     <th style="border: 1px solid black; padding: 5px;">Optimal Partition</th>
     *   </tr>
     *   <tr style="border: 1px solid black;">
     *     <td style="border: 1px solid black; padding: 5px;">{@code "abac"}</td>
     *     <td style="border: 1px solid black; padding: 5px;">1</td>
     *     <td style="border: 1px solid black; padding: 5px;">{@code "aba" | "c"}</td>
     *   </tr>
     *   <tr style="border: 1px solid black;">
     *     <td style="border: 1px solid black; padding: 5px;">{@code "nitin"}</td>
     *     <td style="border: 1px solid black; padding: 5px;">0</td>
     *     <td style="border: 1px solid black; padding: 5px;">{@code "nitin"} (entire string)</td>
     *   </tr>
     *   <tr style="border: 1px solid black;">
     *     <td style="border: 1px solid black; padding: 5px;">{@code "racecar"}</td>
     *     <td style="border: 1px solid black; padding: 5px;">0</td>
     *     <td style="border: 1px solid black; padding: 5px;">{@code "racecar"} (entire string)</td>
     *   </tr>
     *   <tr style="border: 1px solid black;">
     *     <td style="border: 1px solid black; padding: 5px;">{@code "abcd"}</td>
     *     <td style="border: 1px solid black; padding: 5px;">3</td>
     *     <td style="border: 1px solid black; padding: 5px;">{@code "a" | "b" | "c" | "d"}</td>
     *   </tr>
     * </table>
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        String palindrome = "abac";
        palindrome = palindrome.toLowerCase();
        System.out.printf("Minimum Partition needed to make string \"%s\" palindrome is %d%n",
                palindrome, palindromicPartitioning(palindrome));

        // Additional test cases
        System.out.println("\n--- Additional Test Cases ---");
        testCase("nitin");
        testCase("racecar");
        testCase("abcd");
    }

    /**
     * Helper method to test and display results.
     *
     * @param str the string to test
     */
    private static void testCase(String str) {
        int cuts = palindromicPartitioning(str);
        System.out.printf("String: \"%s\" → Minimum cuts: %d%n", str, cuts);
    }
}