package src;

public class InclusionExclusion {

    /**
     * @param index          Which prime we are currently looking at
     * @param currentDivisor The product of the primes we've chosen so far
     * @param limit          The number we are checking up to (e.g., 100)
     * @param primes         Our list of divisors (e.g., {2, 3, 5})
     */

    public long countTotalDivisorsEfficient(int index, long currentDivisor, long limit,
                                            int[] primes) {
        // Base Case: If we've looked at all primes, stop.
        if (index == primes.length) return 0;

        // 1. Calculate for the current prime
        long combinedDivisor = currentDivisor * primes[index];

        // This is how many numbers are divisible by our "chosen" group
        long countWithThisPrime = limit / combinedDivisor;

        // 2. The Magic Logic:
        // We add the count of the current group,
        // then subtract the count of the NEXT group to fix the overlaps.
        return countWithThisPrime
                + countTotalDivisorsEfficient(index + 1, currentDivisor, limit, primes) // Option A: Skip this prime
                - countTotalDivisorsEfficient(index + 1, combinedDivisor, limit, primes); // Option B: Include this prime
    }


    public static void count_Total_Divisors_LongMethod(int n) {
        int count = 1;

        // Count the number of integers divisible by 2
        for (int i = 2; i < n; i += 2) count++;

        // Count the number of integers divisible by 3
        for (int i = 3; i < n; i += 3) count++;

        // Count the number of integers divisible by 5
        for (int i = 5; i < n; i += 5) count++;

        // Count the number of integers divisible by both 2 and 3
        for (int i = 6; i < n; i += 6) count--;

        // Count the number of integers divisible by both 2 and 5
        for (int i = 10; i < n; i += 10) count--;

        // Count the number of integers divisible by both 3 and 5
        for (int i = 15; i < n; i += 15) count--;

        // Count the number of integers divisible by 2, 3, and 5
        for (int i = 30; i < n; i += 30) count++;

        // Print the final count
        System.out.println("Long Method Result: " + count);
    }


    public static void main(String[] args) {
        InclusionExclusion obj = new InclusionExclusion();
        int[] primes = {2, 3, 5};
        long limit = 1584565654L;

        // --- Timing Efficient Method ---
        long startEff = System.nanoTime();
        long resultEff = obj.countTotalDivisorsEfficient(0, 1, limit, primes);
        long endEff = System.nanoTime();
        System.out.println("Efficient Method Result: " + resultEff);
        System.out.println("Efficient Method Time: " + (endEff - startEff) + " ns");

        System.out.println("------------------------------------");

        // --- Timing Long Method ---
        long startLong = System.nanoTime();
        count_Total_Divisors_LongMethod((int) limit);
        long endLong = System.nanoTime();
        System.out.println("Long Method Time: " + (endLong - startLong) + " ns");

        // Final comparison
        double ratio = (double) (endLong - startLong) / (endEff - startEff);
        System.out.printf("\nThe Efficient method was %.2f times faster! \n", ratio);
    }
}