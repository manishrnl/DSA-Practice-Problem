package src;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

public class Rainwater_Trapping_Problem {

    /**
     * ORIGINAL APPROACH: Pre-calculating Left and Right Max
     * Logic: Create two arrays to store the maximum height seen so far from the left
     * and the maximum height seen so far from the right.
     * The Goal: Given an array representing the height of bars, calculate how much water it can trap after raining.
     * The Logic: For any bar at index i, the water level is:
     * <p>
     * water[i] = min(maxLeft , maxRight) - height[i]
     * <p>
     * * Time Complexity: O(N) - Three linear passes.
     * Space Complexity: O(N) - Two extra arrays of size N.
     */
    public static int rainwater(int[] array) {
        int length = array.length;
        if (length == 0) return 0;

        int leftMax = array[0], rightMax = array[length - 1];
        int[] left = new int[length], right = new int[length];

        // Fill Left Max array
        left[0] = array[0];
        for (int i = 1; i < length; i++) {
            leftMax = Math.max(leftMax, array[i]);
            left[i] = leftMax;
        }

        // Fill Right Max array
        right[length - 1] = array[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            rightMax = Math.max(rightMax, array[i]);
            right[i] = rightMax;
        }

        int sum = 0;
        for (int i = 0; i < length; i++) {
            // Water trapped = min(highest left, highest right) - current height
            sum += Math.min(left[i], right[i]) - array[i];
        }

        return sum;
    }

    /**
     * OPTIMIZED APPROACH: Two Pointers
     * Logic: Instead of arrays, use two pointers (left and right). Move the pointer
     * pointing to the smaller height, as that's the "bottleneck" for water trapping.
     * * Time Complexity: O(N) - Single pass.
     * Space Complexity: O(1) - No extra arrays used.
     */
    public static int rainwaterOptimized(int[] array) {
        int n = array.length;
        if (n <= 2) return 0; // Need at least 3 bars to trap water

        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (array[left] < array[right]) {
                // If current bar is taller than leftMax, update it
                if (array[left] >= leftMax) {
                    leftMax = array[left];
                } else {
                    // Otherwise, we can trap water
                    totalWater += leftMax - array[left];
                }
                left++;
            } else {
                if (array[right] >= rightMax) {
                    rightMax = array[right];
                } else {
                    totalWater += rightMax - array[right];
                }
                right--;
            }
        }
        return totalWater;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 4, 0, 1, 3, 13, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2,
                4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1,
                2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3,
                1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3,
                3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1,
                3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0,
                1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0, 1, 3, 3, 1, 2, 4, 0}; // Note: Adjusted input for realistic trapping

        // Test Original
        long start1 = nanoTime();
        int res1 = rainwater(array);
        long end1 = nanoTime();

        // Test Optimized
        long start2 = nanoTime();
        int res2 = rainwaterOptimized(array);
        long end2 = nanoTime();

        out.println("Original  Result T.C O(N) , S.C (O(N) :  " + res1 + " | Time: " + (end1 - start1) + " ns");
        out.println("Optimized Result T.C O(N) , S.C (O(1) :  " + res2 + " | Time: " + (end2 - start2) + " ns");
    }
}