import java.util.Arrays;
import java.util.Stack;

/**
 * <h1>Largest Rectangle in Histogram</h1>
 * This class implements an optimal algorithm to find the largest rectangular area
 * printable within a histogram chart layout using a <b>Monotonic Stack</b>.
 * <p>
 * For every element, we find the first smaller element indices on its immediate left
 * and right sides to calculate the max width boundary for that specific block height.
 * </p>
 * <p>
 * <b>Time Complexity:</b> O(N) where N represents the number of histogram elements.
 * <b>Space Complexity:</b> O(N) auxiliary space used to retain track indices.
 * </p>
 * <h1>Largest Rectangle in Histogram (Monotonic Stack Approach)</h1>
 *
 * <h3>1. Conceptual Diagram</h3>
 * <pre>
 * Height
 *   6 |                 ██
 *   5 |         ██      ██
 *   4 | ██      ██      ██      ██
 *   3 | ██      ██      ██  ██  ██
 *   2 | ██  ██  ██      ██  ██  ██  ██  ===&gt; Max Rectangle Area Found here!
 *   1 | ██  ██  ██  ██  ██  ██  ██  ██       Height = 2, Width = (8 - 1 - 1) = 6
 *   0 +---------------------------------     Area = 2 * 6 = 12
 *      [4,  2,  5,  1,  6,  3,  2,  4,  2] &lt;--- Input Array Data
 *       0   1   2   3   4   5   6   7   8  &lt;--- Index Values
 * </pre>
 *
 * <h3>2. Boundary Window Mechanics</h3>
 * <pre>
 * For a given index i, we find the boundaries where it can expand horizontally:
 *
 *            Left Boundary Index (Exclusive)       Right Boundary Index (Exclusive)
 *                     [Index: 1]                              [Index: 8]
 *                        Value: 2                                Value: 2
 *                          │                                       │
 *                          ▼                                       ▼
 *      Index Tracking:     1     2     3     4     5     6     7     8
 *      Values:          [  2  │  5  ,  1  ,  6  ,  3  ,  2  ,  4  │  2  ]
 *                             └───────────────────────────────────┘
 *                                  Width expansion zone for index 5 (val=3)
 *                                  Width = Right - Left - 1
 *                                  Width = 6 - 3 - 1 = 2
 * </pre>
 * <p>
 *
 */


public class S06_MaxArea_In_Histogram {

    /**
     * Calculates the absolute maximum rectangular area bounded inside the histogram bars.
     *
     * <h3>Processing Steps Loop Diagram:</h3>
     * <pre>
     *  i  │ Height │ Left Boundary Idx │ Right Boundary Idx │ Width │  Area
     * ════╪════════╪═══════════════════╪════════════════════╪═══════╪═══════════
     * 0   │    4   │       -1          │           1        │   1   │  4 * 1 = 4
     * 1   │    2   │       -1          │           3        │   3   │  2 * 3 = 6
     * 2   │    5   │        1          │           3        │   1   │  5 * 1 = 5
     * 3   │    1   │       -1          │           9        │   9   │  1 * 9 = 9
     * 4   │    6   │        3          │           5        │   1   │  6 * 1 = 6
     * 5   │    3   │        3          │           6        │   2   │  3 * 2 = 6
     * 6   │    2   │        3          │           9        │   5   │  2 * 5 = 10
     * 7   │    4   │        6          │           8        │   1   │  4 * 1 = 4
     * 8   │    2   │        3          │           9        │   5   │  2 * 5 = 10
     * ════╧════════╧═══════════════════╧════════════════════╧═══════╧════════════
     * </pre>
     *
     * @param array Primitive array representing consecutive column heights.
     * @return The maximum area integer calculation.
     */
    public static int maxArea(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int[] leftSmallerIndex = find_Immediate_Left_Smaller(array);
        int[] rightSmallerIndex = find_Immediate_Right_Smaller(array);

        int maxArea = 0;

        for (int i = 0; i < array.length; i++) {
            int width = rightSmallerIndex[i] - leftSmallerIndex[i] - 1;
            int currentArea = array[i] * width;
            maxArea = Math.max(maxArea, currentArea);
        }

        return maxArea;
    }

    /**
     * Monotonic Increasing Stack Processing for Left Side Boundaries.
     * <pre>
     * Stack Mutation Tracking for Left Scan:
     * i = 0 (Val=4) -&gt; Stack Empty           -&gt; Bound = -1 | Push Index 0 [0]
     * i = 1 (Val=2) -&gt; Pop 0 (since 4 &gt;= 2)  -&gt; Bound = -1 | Push Index 1 [1]
     * i = 2 (Val=5) -&gt; Peek 1 (since 2 &lt; 5)  -&gt; Bound =  1 | Push Index 2 [1, 2]
     * </pre>
     */
    private static int[] find_Immediate_Left_Smaller(int[] array) {
        int[] leftBounds = new int[array.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < array.length; i++) {
            while (!stack.isEmpty() && array[stack.peek()] >= array[i]) {
                stack.pop();
            }
            leftBounds[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return leftBounds;
    }

    /**
     * Monotonic Increasing Stack Processing for Right Side Boundaries.
     * <pre>
     * Stack Mutation Tracking for Right Scan (Reverse Loop):
     * i = 8 (Val=2) -&gt; Stack Empty         -&gt; Bound =  9 | Push Index 8 [8]
     * i = 7 (Val=4) -&gt; Peek 8 (since 2 &lt; 4)  -&gt; Bound =  8 | Push Index 7 [8, 7]
     * i = 6 (Val=2) -&gt; Pop 7 (since 4 &gt;= 2) -&gt; Bound =  9 | Push Index 6 [8, 6]
     * </pre>
     */
    private static int[] find_Immediate_Right_Smaller(int[] array) {
        int[] rightBounds = new int[array.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = array.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && array[stack.peek()] >= array[i]) {
                stack.pop();
            }
            rightBounds[i] = stack.isEmpty() ? array.length : stack.peek();
            stack.push(i);
        }
        return rightBounds;
    }

    /**
     * Code runtime entry loop.
     */
    public static void main(String[] args) {
        int[] array = {4, 2, 5, 1, 6, 3, 2, 4, 2};

        System.out.println("Input Histogram Heights: " + Arrays.toString(array));
        int totalMaxArea = maxArea(array);

        System.out.println("Maximum area of given Histogram is : " + totalMaxArea);
    }
}