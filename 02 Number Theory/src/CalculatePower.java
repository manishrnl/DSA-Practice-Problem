package src;

import java.math.BigInteger;

public class CalculatePower {
    public long calculatePower(long base, long power) {
        long result = 1;

        result = (long) Math.pow(base, power);
        System.out.printf("Power of %d ^ %d via Math.pow() is : ", base, power);
        return result;
    }

    /**
     * Optimized Power function using Binary Exponentiation.
     * Time Complexity: O(log power)
     * Space Complexity: O(1)
     */
    public long calculatePowerBitsOptimized(long base, long power) {
        System.out.printf("Power of %d ^ %d via Bits Calculation is : ", base, power);
        // Edge case: Any number to the power of 0 is 1
        if (power == 0) return 1;
        // Edge case: 0 to any positive power is 0
        if (base == 0) return 0;

        long result = 1;

        // Using a long for the base internally to prevent
        // immediate overflow during the squaring step
        long currentProduct = base;

        while (power > 0) {
            // If the least significant bit is 1, multiply result by currentProduct
            // (power & 1) is a faster way to check if power is odd
            if ((power & 1) != 0) {
                result *= currentProduct;
            }

            // Square the base for the next bit position
            currentProduct *= currentProduct;

            // Logical right shift to move to the next bit
            power = power >> 1;
        }

        return result;
    }


    public long calculateFastPower(long base, long power, long n) {

        /* PROPERTY USED:
            (a + b) % n = (a % n + b % n) % n
            (a - b) % n = (a % n - b % n) % n
            (a * b) % n = (a % n * b % n) % n
            (a / b) % n = (a % n / b % n) % n

         */
        System.out.printf("Power of %d ^ %d with 10 ^ %d { %d } via Bits Calculation is : ",
                base, power, String.valueOf(n).length(), n);
        // Edge case: if n is 1, the result is always 0
        if (n == 1) return 0;
        long result = 1;

        // Initial mod to handle cases where base >= n
        base %= n;

        while (power > 0) {
            // If the current bit of 'power' is 1 (power is odd)
            // Multiply the result with the current base
            if ((power & 1) != 0) {
                result = (result * base) % n;
            }

            // Square the base for the next bit position
            // Example: 3^1 -> 3^2 -> 3^4 -> 3^8
            base = (base * base) % n;

            // Right shift 'power' to process the next bit
            // Equivalent to power = power / 2
            power = power >> 1;
        }
        return result;
    }

    public BigInteger calculateTruePower(String base, long power) {
        BigInteger b = new BigInteger(base);
        BigInteger result = b.pow((int) power);
        System.out.printf("Power of  %s ^ %d Via True Power is : ", base, power);
        return result;
    }

    public static void main(String[] args) {
        long base = 12333L, power = 266L;
        long n = 1000000000L;
        CalculatePower calculatePower = new CalculatePower();
        long startTime = System.currentTimeMillis();
        long resultMath = calculatePower.calculatePower(base, power);
        long endTime = System.currentTimeMillis();
        System.out.println(resultMath + " -> Time taken to execute is : " + (endTime - startTime) + " ns");

        long startBits = System.nanoTime();
        long resultBits = calculatePower.calculatePowerBitsOptimized(base, power);
        long endBits = System.nanoTime();
        System.out.println(resultBits + " -> Time taken to execute is : " + (endBits - startBits) + " ns");


        long startFastPower = System.nanoTime();
        long resultFastPower = calculatePower.calculateFastPower(base, power, n);
        long endFastPower = System.nanoTime();
        System.out.println(resultFastPower + " -> Time taken to execute is : " + (endFastPower - startFastPower) + " ns");


        long startTruePower = System.nanoTime();
        BigInteger resultTruePower = calculatePower.calculateTruePower(String.valueOf(base),
                power);
        long endTruePower = System.nanoTime();
        System.out.println(resultTruePower + " -> Time taken to execute is : " + (endTruePower - startTruePower) + " ns");
    }
}
