package src;
public class N_Queen {
    static int N = 25;

    public static boolean solve(int[][] board, int row) {
        // BASE CASE: If we reached the end, we found a solution!
        if (row == N) return true;

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1; // Place Queen

                // Move to the next row
                if (solve(board, row + 1)) return true;

                // BACKTRACK: "Undo" the placement if it didn't work out
                board[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean isSafe(int[][] board, int row, int col) {
        /**
         * We only need to check 3 directions (Up, Up-Left, Up-Right)
         * because we haven't placed any queens below the current row yet.
         * Also bottom will always be empty , we are inserting from top .
         * so checking bottom doesn't make sense
         */
        for (int i = 1; i <= row; i++) {
            // 1. Check Straight Up till (row,0)
            if (board[row - i][col] == 1) return false;

            // 2. Check Up-Left Diagonal till (row-i,col-i)
            if (col - i >= 0 && board[row - i][col - i] == 1) return false;

            // 3. Check Up-Right Diagonal till (row-i,col+i)
            if (col + i < N && board[row - i][col + i] == 1) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int[][] board = new int[N][N];
        if (solve(board, 0)) {
            long endTime = System.nanoTime();
            System.out.printf(" Time taken to complete backTracking : %d ns\n\n", (endTime - startTime));
            printBoard(board);
        }
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell == 1 ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}
