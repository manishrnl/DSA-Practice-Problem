import java.util.Arrays;

/**
 * Solves the classic "Two Sum" problem: given an array of integers and a target
 * sum, determine whether any two elements in the array add up to the target.
 * <p>
 * This implementation sorts the array and then uses the two-pointer technique —
 * one pointer starting at the beginning ({@code start}) and one at the end
 * ({@code end}) of the sorted array, moving inward based on whether the current
 * pair sum is too small or too large.
 * </p>
 * <p>
 * Complexity comparison:
 * <ul>
 *   <li>Brute force (nested loops, check every pair): O(n^2) time, O(1) space.</li>
 *   <li>HashSet-based (single pass, check for {@code target - current} as you go):
 *       O(n) time, but O(n) extra space.</li>
 *   <li>Sort + two-pointer (this implementation): O(n log n) time for the sort
 *       plus O(n) for the scan, so O(n log n) overall, with O(1) extra space
 *       (ignoring the space used by the sort itself). This is the approach used
 *       here since it avoids the extra O(n) space that the HashSet approach needs.</li>
 * </ul>
 * </p>
 */
public class A00_2_Sum_Problem {
    // start, end is used to track index of elements which adds up to give desired target sum
    public static int start = 0;
    public static int end = 0;

    /**
     * Determines whether any two distinct elements in {@code array} sum to
     * {@code targetSum}, using the sort-then-two-pointer technique.
     * <p>
     * The array is sorted in place first. Then, with {@code start} at index 0 and
     * {@code end} at the last index, the method repeatedly compares
     * {@code array[start] + array[end]} against {@code targetSum}:
     * <ul>
     *   <li>If the sum is too large, {@code end} moves one step left (to try a
     *       smaller value).</li>
     *   <li>If the sum is too small, {@code start} moves one step right (to try a
     *       larger value).</li>
     *   <li>If the sum matches exactly, a pair has been found and the method
     *       returns {@code true} immediately, leaving {@code start} and
     *       {@code end} pointing at the matching pair's indices.</li>
     * </ul>
     * Each iteration narrows the gap between {@code start} and {@code end} by
     * exactly one, so the search is guaranteed to terminate, and the two pointers
     * never end up pointing at the same index when a comparison is made.
     * </p>
     *
     * @param array     the array to search; sorted in place as a side effect of
     *                  this call
     * @param targetSum the sum to look for
     * @return {@code true} if some pair of elements sums to {@code targetSum};
     * {@code false} otherwise. When {@code true}, {@link #start} and
     * {@link #end} hold the indices (in the now-sorted array) of the matching
     * pair.
     */
    public static boolean twoSum(int[] array, int targetSum) {
        int length = array.length - 1;
        start = 0;
        end = length;
        Arrays.sort(array);  //Java's inbuilt function which sorts array in ascending order in O(n log n ) time

        for (int i = 0; i < length; i++) {
            if (array[start] + array[end] > targetSum)
                end--;
            else if (array[start] + array[end] < targetSum)
                start++;
            else
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] array = new int[]{2, 4, 3, 1, 7, 9, 1, 3, -3, -5, -3};
        int targetSum = 7;
        System.out.print((twoSum(array, targetSum) ? "Sum found and is by adding " + array[start] + " and " + array[end] : "Sum not found"));
        System.out.println(Arrays.toString(array));
    }
}