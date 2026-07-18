import java.util.Arrays;
import java.util.Stack;

/**
 * <h1>Nearest Larger Element on the Left</h1>
 * This class uses a <b>Monotonic Stack</b> variant to efficiently discover
 * the closest larger integer value positioned to the left of each array element.
 * <p>
 * If no larger element exists to the left, a default marker of -1 is emitted.
 * </p>
 * <p>
 * <b>Time Complexity:</b> O(N) where N represents the number of elements in the array.
 * <b>Space Complexity:</b> O(N) auxiliary space used by the internal tracking stack.
 * </p>
 */
public class S04_immediate_Larger_Element {

    /**
     * Finds and prints the nearest greater value located to the left of each position.
     * Keeps a strictly decreasing stack order to optimize chronological lookups.
     *
     * @param array The raw input integer array to analyze.
     */
    public static void immediate_Larger(int[] array) {
        // FIXED: Null check must always precede structural length checks to prevent crashes
        if (array == null || array.length == 0) {
            System.out.println("Array is empty");
            return;
        }

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < array.length; i++) {
            // 1. Evict elements smaller than or equal to current value.
            // They can never serve as a valid "larger neighbor" going forward.
            while (!stack.isEmpty() && stack.peek() <= array[i]) {
                stack.pop();
            }

            // 2. Safely read our top value without clearing it from memory
            // FIXED: Using peek() instead of pop() preserves history for the next indices
            if (stack.isEmpty()) {
                System.out.print("-1 ");
            } else {
                System.out.print(stack.peek() + " ");
            }

            // 3. Register the current value as a potential larger boundary for upcoming items
            stack.push(array[i]);
        }
        System.out.println();
    }

    /**
     * Testing sequence validation block.
     */
    public static void main(String[] args) {
        int[] array = new int[]{4, 10, 5, 8, 20, 15, 13, 12};

        System.out.println("Input Array: " + Arrays.toString(array));
        System.out.print("Nearest Larger on Left: ");

        // Expected Output: -1 -1 10 10 -1 20 15 13
        immediate_Larger(array);
    }
}