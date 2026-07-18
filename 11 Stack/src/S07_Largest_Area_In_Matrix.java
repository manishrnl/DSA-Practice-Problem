import java.util.Stack;

/**
 * <h1>Largest Rectangular Area in Binary Matrix</h1>
 *
 * <h3>1. Row-by-Row Histogram Conversion Diagram</h3>
 * <pre>
 * Matrix Source Data:          Histogram Formed at Row Floor:
 * ┌───┬───┬───┬───┬───┐
 * │ 1 │ 1 │ 0 │ 1 │ 1 │
 * ├───┼───┼───┼───┼───┤        Row 0 Base:  [1, 1, 0, 1, 1]
 * │ 1 │ 1 │ 1 │ 1 │ 1 │  ===>  Row 1 Base:  [2, 2, 1, 2, 2]
 * ├───┼───┼───┼───┼───┤        Row 2 Base:  [0, 3, 2, 3, 3] &lt;-'0' breaks col chain
 * │ 0 │ 1 │ 1 │ 1 │ 1 │
 * └───┴───┴───┴───┴───┘
 * </pre>
 *
 * <p>
 * <b>Time Complexity:</b> O(R * C) where R is rows and C is columns.
 * <b>Space Complexity:</b> O(C) auxiliary space allocated for the column layout window trackers.
 * </p>
 */
public class S07_Largest_Area_In_Matrix {

    /**
     * Finds the maximum area of a rectangle filled completely with 1s inside a binary matrix.
     *
     * @param array2D The 2D grid filled with 0s and 1s or any number.
     * @return The largest structural rectangle area size value.
     */
    public static int maxAreaMatrix(int[][] array2D) {
        if (array2D == null || array2D.length == 0 || array2D[0].length == 0) {
            return 0;
        }

        int row2D = array2D.length;
        int col2D = array2D[0].length;
        int maxArea = 0;

        // Converting  2D into 1D by adding array2D[row][column] column wise into array1D
        int[] array1D = new int[col2D];

        for (int i = 0; i < row2D; i++) {
            for (int j = 0; j < col2D; j++) {
                // FIXED: Index by column index [j]. If 0 breaks the chain, reset tracking height to 0.
                if (array2D[i][j] == 0) array1D[j] = 0;
                else array1D[j] += array2D[i][j];
            }
            // FIXED: Boundary tracking lists must be evaluated inside the loop after the row heights update
            int[] leftList = find_Immediate_Left_Smaller(array1D);
            int[] rightList = find_Immediate_Right_Smaller(array1D);
            maxArea = Math.max(maxArea, getMaxArea(array1D, leftList, rightList));
        }

        return maxArea;
    }

    /**
     * Standard Maximum Area calculation helper.
     * Area = Height * (RightIndex - LeftIndex - 1)
     */
    public static int getMaxArea(int[] heights, int[] leftList, int[] rightList) {
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            // FIXED: Re-injected height parameter multiplier to transform width spans into valid area counts
            int width = rightList[i] - leftList[i] - 1;
            int currentArea = heights[i] * width;
            maxArea = Math.max(maxArea, currentArea);
        }
        return maxArea;
    }

    public static int[] find_Immediate_Left_Smaller(int[] array) {
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

    public static int[] find_Immediate_Right_Smaller(int[] array) {
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

    public static void main(String[] args) {
        int[][] array = new int[][]{
                {1, 1, 0, 8, 1},
                {1, 2, 9, 1, 1},
                {0, 1, 1, 4, 1},
                {2, 2, 2, 2, 2},
                {1, 0, 3, 1, 1},
                {1, 1, 1, 1, 1}};

        System.out.println("Max Area for above array is : " + maxAreaMatrix(array));
    }
}