/**
 * <h1>Problem Statement: Concatenation of Consecutive Binary Numbers</h1>
 * <p>
 * Given an integer {@code n}, return the decimal value of the binary string formed by concatenating
 * the binary representations of 1 to {@code n} in order, modulo 10<sup>9</sup> + 7.
 * </p>
 *
 * <h2>Approach & Logic</h2>
 * <ul>
 *   <li><b>Bit Length Tracking:</b> As we iterate from 1 to {@code n}, each integer {@code i} requires a certain
 *       number of binary bits. Whenever {@code i} reaches a new power of 2, its binary representation length
 *       increases by 1 bit, requiring the space multiplier to double.</li>
 *   <li><b>Bitwise Power of 2 Check:</b> We use the bitwise expression {@code (i & (i - 1)) == 0} to detect
 *       when {@code i} hits a power of 2 in O(1) time complexity.</li>
 *   <li><b>Modular Arithmetic:</b> At each step, we shift the accumulated result to make room for {@code i}'s
 *       bits, add {@code i}, and immediately apply {@code % 1000000007} to prevent overflow.</li>
 * </ul>
 *
 * @author Solution
 */
public class Solution {

    /**
     * Concatenates the binary representations of numbers from 1 to {@code n}
     * and returns the decimal result modulo 10^9 + 7.
     *
     * @param n the upper bound integer to concatenate up to
     * @return the modulo 10^9 + 7 integer result
     */
    public static int concatenate(int n) {
        long MOD = 1000000007;
        long result = 0;
        long multiplier = 1;

        for (int i = 1; i <= n; i++) {
            /** When 'i' hits a new power of 2 (1, 2, 4, 8, 16...),
             *double the multiplier (which doubles the bit capacity needed)
             i     = 8  ->  1 0 0 0
             (i - 1) = 7  ->  0 1 1 1
             -------------------------
             i & (i-1)    ->  0 0 0 0  --> Equal to 0!

             i     = 6  ->  0 1 1 0
             (i - 1) = 5  ->  0 1 0 1
             -------------------------
             i & (i-1)    ->  0 1 0 0  --> 4 (Not equal to 0!)

             */
            if ((i & (i - 1)) == 0) {
                multiplier *= 2;
            }

            // Multiply result by the multiplier to "make room" for 'i', then add 'i'
            result = ((result * multiplier) + i) % MOD;
        }

        return (int) result;
    }

    public static void main(String[] args) {
        System.out.println("Solution.main : " + concatenate(1200));
    }
}