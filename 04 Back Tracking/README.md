# This Sudoku solver is a perfect example of Recursive Backtracking . It essentially uses a "Brute Force" approach but with a brain—it drops a path the moment it violates a rule.

---

## Consider full code

```java

public class SudokuSolver {

    public static boolean solveSudoku(int[][] board) {
        int n = board.length;
        int row = -1;
        int col = -1;
        boolean emptyLeft = false;

        // 1. Find the first empty cell (0)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    emptyLeft = true;
                    break;
                }
            }
            if (emptyLeft) break;
        }

        // Base Case: No empty cells left, Sudoku is solved!
        if (!emptyLeft) return true;

        // 2. Try numbers 1 to 9
        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                // Move to next step
                if (solveSudoku(board)) return true;

                // BACKTRACK: Undo the choice
                board[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check Row and Column
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        // Check 3x3 Sub-grid
        // This math finds the "top-left" corner of the 3x3 box
        int rowStart = row - (row % 3);
        int colStart = col - (col % 3);

        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        if (solveSudoku(board)) printBoard(board);
    }

    private static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }
}

```

## Code Explanation

### 1. The Search Phase (Finding a Target)

Before the algorithm can try a number, it needs to know *where* to put it.Once it finds the
very first digits = `0` , it breaks , goes inside another loop to check at $safeSearch(..)$ ,
then if placing digits is successfully done, it again comes back here to find next `0` .

This continues untill all are filled and no 0's are left

```bash

  for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        if (board[i][j] == 0) { // Found an empty spot!
            row = i;
            col = j;
            emptyLeft = true;
            break;
        }
    }
    if (emptyLeft) break;
  }
```

* **What it does:** It scans the 9x9 grid from top-left to bottom-right looking for a `0`.
* **The Base Case:** If `emptyLeft` remains `false`, it means every cell is filled. The
  recursion stops and returns `true`.

---

### 2. The Trial & Error Phase (Recursion)

Once a cell is picked, the code enters a loop from 1 to 9.

```bash
  for (int num = 1; num <= 9; num++) {
      if (isSafe(board, row, col, num)) {
          board[row][col] = num; // Hypothesis: Let's assume this is 'num'
  
          if (solveSudoku(board)) return true; // Move to the next empty cell
  
          board[row][col] = 0; // BACKTRACK: My hypothesis was wrong, reset it.
      }
  }
```

* **The Decision Tree:** For every empty cell, the code "branches" out into 9 possible paths.
* **The Backtrack:** This is the most important part. If the code reaches a state where no
  numbers (1–9) are "safe" for a future cell, the current function call finishes and returns
  `false`. This triggers the previous call to reset its cell to `0` and try the *next* number.

---

### 3. The Validation Phase (`isSafe`)

This is the "Rules of the Game" section. It checks three distinct constraints:

#### A. Row and Column Check

```bash
  if(board[row][i]==num ||board[i][col]==num)return false;
```

It scans the entire horizontal row and vertical column. If `num` exists there, it's an
immediate "No."

#### B. The 3x3 Grid Check

This is usually the most confusing part for developers. How do we know which 3x3 box we are in?

```bash
  int rowStart = row - (row % 3);
  int colStart = col - (col % 3);
  for (int i = rowStart; i < rowStart + 3; i++) {
        for (int j = colStart; j < colStart + 3; j++) {
            if (board[i][j] == num) return false;
        }
  }
```

* **The Logic:** Using modulo `% 3` finds the offset from the start of the box.
    * If you are at **Row 5**: $5 \% 3 = 2$. Then $5 - 2 = 3$. Your box starts at **Row 3**.
    * If you are at **Row 2**: $2 \% 3 = 2$. Then $2 - 2 = 0$. Your box starts at **Row 0**.


```bash
  if (board[i][j] == num) return false;
 ```

### The "Validator" Logic (Explained Meaningfully)

The isSafe(..) function acts as a pre-condition validator. It treats the num as a candidate,
checking its compatibility against the existing constraints of the board (Row, Column, and 3x3
Box) before any state change occurs.

Because the candidate is not yet written into the matrix, the function effectively performs a
collision check in a read-only state. If the candidate clears all three 'gatekeepers' without a
conflict, the calling function is granted permission to 'commit' that value to the board. If a
conflict is found, the candidate is rejected immediately, saving the algorithm from exploring
an invalid path.


---

### Summary of Execution

1. **Find** a `0`.
2. **Try** `1`. Is it safe? Yes.
3. **Recurse** to the next `0`.
4. **Repeat**.
5. If you hit a dead end, **Backtrack** (step back one cell) and try `2`.
