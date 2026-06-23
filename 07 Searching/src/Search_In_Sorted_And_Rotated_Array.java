import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Search_In_Sorted_And_Rotated_Array {
    /**
     * A rotated sorted array is an array that was originally sorted in ascending order
     * but has had its elements shifted (rotated) to the left or right by some
     * number of positions.
     * <p>
     * Example:
     * Original Sorted Array: [10, 20, 30, 40, 50, 60, 70]
     * Rotated by 3 positions: [40, 50, 60, 70, 10, 20, 30]
     * <p>
     * In this example, the last three elements were moved to the front,
     * or the first three were "pushed" to the back.
     * <p>
     * ---
     * <p>
     * ### Key Characteristics
     * There are three main things to notice about a rotated sorted array:
     * <p>
     * 1. The Pivot Point: There is exactly one point where the next element is
     * smaller than the current element (e.g., from 70 to 10). This is called
     * the pivot or the "inflection point."
     * 2. Two Sorted Sub-arrays: A rotated array actually consists of two smaller
     * sorted arrays joined together. In our example: [40, 50, 60, 70] and [10, 20, 30].
     * 3. Search Properties: Because the array is no longer fully sorted, a standard
     * Binary Search won't work out of the box. However, because each "half" is
     * still sorted, we can use a Modified Binary Search to find elements.
     * <p>
     * ---
     * <p>
     * ### Practical Examples
     * <p>
     * Rotation Count | Resulting Array   | Description
     * ---------------|-------------------|---------------------------------------
     * 0 (Original)   | [1, 2, 3, 4, 5]   | No rotation; standard sorted array.
     * 1 Rotation     | [5, 1, 2, 3, 4]   | The largest element moves to the front.
     * 2 Rotations    | [4, 5, 1, 2, 3]   | Two elements moved from back to front.
     * 4 Rotations    | [2, 3, 4, 5, 1]   | Almost a full cycle; smallest at the end.
     * <p>
     * ---
     * <p>
     * Complexity:
     * Time Complexity: O(log n) - Modified Binary Search
     * Space Complexity: O(1) - Constant space
     *
     * @param array The rotated sorted array to search through.
     * @param key   The integer value to locate.
     * @return The index of the key if found; otherwise -1.
     */

    public static int rotatedArray(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;  // same as int mid =  ( low + high ) / 2

            if (array[mid] == key) return mid;

            // Scenario 1: Left part [low...mid] is sorted
            if (array[low] <= array[mid]) {
                // Key lies within the sorted left half
                if (key >= array[low] && key < array[mid]) high = mid - 1;
                else low = mid + 1;
            }
            // Scenario 2: Right part [mid...high] is sorted
            else {
                // Key lies within the sorted right half
                if (key > array[mid] && key <= array[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) throws IOException {
        int[] array = {11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n--- Rotated Binary Search Utility ---");
            System.out.print("Enter number to search (or -1 to QUIT): ");

            String input = bufferedReader.readLine();
            if (input == null || input.isEmpty()) continue;

            int number;
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (number == -1) break;

            long start = System.nanoTime();
            int result = rotatedArray(array, number);
            long end = System.nanoTime();

            if (result != -1) {
                System.out.printf("SUCCESS: Found %d at index %d\n", number, result);
            } else {
                System.out.println("NOT FOUND: Number not in list.");
            }
            System.out.printf("Execution Time: %d ns\n", (end - start));
        }
    }
}

