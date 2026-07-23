import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solves the "3Sum" problem: given an array of integers and a target sum,
 * determine whether any three distinct elements in the array add up to the
 * target, and if so, record which three.
 * <p>
 * Approach: sort the array, then for each index {@code i} treat
 * {@code array[i]} as the first element of the triplet and search the remaining
 * sub-array ({@code i+1 .. end}) for a pair that sums to
 * {@code targetSum - array[i]} using the standard sorted two-pointer technique.
 * This runs in O(n^2) time overall (O(n log n) sort + O(n) outer loop times
 * O(n) inner two-pointer scan) with O(1) extra space, versus O(n^3) for a
 * naive triple-nested-loop brute force.
 * </p>
 */
public class A01_3_Pointer_Sum {

    static List<Integer> sumPointer = new ArrayList<>();

    /**
     * Determines whether any three distinct elements of {@code array} sum to
     * {@code targetSum}.
     * <p>
     * The array is sorted in place first. Then for each index {@code i} from
     * {@code 0} to {@code array.length - 3}, {@code array[i]} is fixed as the
     * first element of the candidate triplet, and {@link #twoSum} searches the
     * remainder of the array ({@code i+1} to the last index) for a pair summing
     * to {@code targetSum - array[i]}. The first triplet found is returned
     * immediately; the search does not continue to look for every possible
     * triplet.
     * </p>
     *
     * @param array     the array to search; sorted in place as a side effect of
     *                  this call
     * @param targetSum the sum to look for
     * @return {@code true} if some triplet of elements sums to {@code targetSum};
     * {@code false} otherwise. When {@code true}, {@link #sumPointer} holds the
     * three matching values.
     */
    public static boolean threeSum(int[] array, int targetSum) {
        Arrays.sort(array); // Java's inbuilt sort, ascending order, O(n log n) time
        sumPointer.clear();

        for (int i = 0; i < array.length - 2; i++) {
            if (twoSum(array, targetSum - array[i], i + 1, array.length - 1)) {
                sumPointer.add(array[i]);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches the sorted sub-array {@code array[start..end]} (inclusive) for a
     * pair of elements that sum exactly to {@code targetSum}, using two pointers
     * moving inward from opposite ends.
     * <p>
     * On each step, exactly one pointer moves: {@code start} advances when the
     * current pair sum is too small, and {@code end} retreats when it's too
     * large — never both in the same step — which guarantees every possible
     * pair in the range is considered exactly once and the pointers can never
     * cross past each other unexamined.
     * </p>
     *
     * @param array     the (already sorted) array to search within
     * @param targetSum the sum to look for within the given range
     * @param start     inclusive lower index of the search range
     * @param end       inclusive upper index of the search range
     * @return {@code true} if a matching pair was found in range, in which case
     * both values have been appended to {@link #sumPointer}; {@code false}
     * otherwise, with {@link #sumPointer} left unchanged
     */
    public static boolean twoSum(int[] array, int targetSum, int start, int end) {
        while (start < end) {
            int sum = array[start] + array[end];

            if (sum == targetSum) {
                sumPointer.add(array[start]);
                sumPointer.add(array[end]);
                return true;
            } else if (sum < targetSum) {
                start++;
            } else {
                end--;
            }
        }
        return false;
    }

    /**
     * Demonstrates {@link #threeSum} on a sample array and prints the matching
     * triplet if one is found, along with the sorted array.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        int[] array = new int[]{2, 4, 3, 11, 7, 9, 1, 13, -3, -5, -53};
        int targetSum = 9;
        System.out.print((threeSum(array, targetSum) ? "Sum of " + targetSum + " found and is by adding" + sumPointer : "Sum not found"));
        System.out.print("\nArrays Data are : " + Arrays.toString(array));
    }
}