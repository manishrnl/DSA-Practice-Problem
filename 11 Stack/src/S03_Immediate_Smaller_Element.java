import java.util.Arrays;
import java.util.Stack;

/**
 * <h1>Nearest Smaller Element on the Left</h1>
 * This class provides an optimal solution using a <b>Monotonic Stack</b> to locate
 * the nearest smaller element to the left of each array position.
 * <p>
 * If no smaller element exists to the left of a target number, it returns -1.
 * </p>
 * <p>
 * <b>Time Complexity:</b> O(N) where N is the number of elements. Each element is
 * pushed and popped from the stack at most once.
 * <b>Space Complexity:</b> O(N) in the worst case to hold stack frames.
 * </p>
 */
public class S03_Immediate_Smaller_Element {

    /**
     * Finds and prints the nearest smaller element on the left side of each position.
     * Utilizes a non-destructive peek strategy to keep valid histories intact.
     *
     * @param array The raw input integer array to evaluate.
     */
    public static void immediate_Smaller_Left(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < array.length; i++) {
            // 1. Pop out elements that are greater than or equal to current element.
            // They can never be the "nearest smaller" option for any element to the right.
            while (!stack.isEmpty() && stack.peek() >= array[i]) {
                stack.pop();
            }

            // 2. If stack is empty, no smaller element exists to the left.
            // FIXED: Using stack.peek() instead of stack.pop() ensures data remains for next iterations.
            if (stack.isEmpty()) {
                System.out.print(-1 + " ");
            } else {
                System.out.print(stack.peek() + " ");
            }

            // 3. Add the current element as a potential candidate for subsequent elements
            stack.push(array[i]);
        }
        System.out.println();
    }

    /**
     * Code execution entry point.
     */
    public static void main(String[] args) {
        int[] array = new int[]{4, 10, 5, 8, 20, 15, 13, 12};

        System.out.println("Input Array: " + Arrays.toString(array));
        System.out.print("Nearest Smaller on Left: ");

        // Expected Output: -1 4 4 5 8 8 8 8
        immediate_Smaller_Left(array);
    }
}