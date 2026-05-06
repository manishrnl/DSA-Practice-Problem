package src;

/**
 * GOOGLE INTERVIEW QUESTION
 * Problem: Allocate Minimum Number of Pages
 * <p>
 * Objective:
 * Given an array of pages where array[i] represents the number of pages in the i-th book,
 * and 'k' students, allocate books such that:
 * 1. Each student gets at least one book.
 * 2. Each student reads a contiguous sequence of books.
 * 3. The maximum number of pages assigned to any single student is MINIMIZED.
 * <p>
 * Strategy: Binary Search on Answer
 * Range: [max(array), sum(array)]
 * <p>
 * T.C: O(N * log(Sum of Pages))
 * S.C: O(1)
 */
public class BookAllocation {

    public int minPages(int[] array, int totalStudents) {
        // Base case: If books are fewer than students, allocation isn't possible
        // under the "at least one book per student" rule.
        if (array.length < totalStudents) return -1;

        int min = maxOf(array); // A student must be able to hold at least the largest book
        int max = sumOf(array); // Max limit is one student reading everything
        int res = -1;

        while (min <= max) {
            int mid = min + (max - min) / 2; // Prevents potential overflow , same as int mid = ( low + high ) / 2

            if (isFeasible(array, totalStudents, mid)) {
                res = mid;          // This capacity works, let's try to find a smaller one
                max = mid - 1;
            } else {
                min = mid + 1;      // This capacity is too small, increase the limit
            }
        }
        return res;
    }

    /**
     * Helper to check if it's possible to distribute books such that
     * no student reads more than 'limit' pages.
     */
    private boolean isFeasible(int[] array, int k, int limit) {
        int students = 1, currentSum = 0;

        for (int pages : array) {
            if (currentSum + pages > limit) {
                students++;
                currentSum = pages;
            } else {
                currentSum += pages;
            }
        }
        return students <= k;
    }

    private int sumOf(int[] array) {
        int sum = 0;
        for (int i : array) sum += i;
        return sum;
    }

    private int maxOf(int[] array) {
        int max = 0;
        for (int i : array) if (i > max) max = i;
        return max;
    }

    public static void main(String[] args) {
        BookAllocation obj = new BookAllocation();

        int[] pages = {12, 34, 67, 90, 92, 94, 96, 98, 99, 100};
        int totalStudents = 6;

        int result = obj.minPages(pages, totalStudents);

        if (result != -1) {
            System.out.println("####################################################\n");
            System.out.println("The minimized maximum number of pages a student has to read is: " + result);
            System.out.println("\n####################################################\n");
        } else {
            System.out.println("Allocation impossible: More students than books available.");
        }
    }
}