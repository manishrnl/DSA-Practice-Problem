import java.util.*;

/**
 * <b>Activity Selection Problem - Greedy Algorithm</b>
 *
 * <p><b>PROBLEM:</b> Given n activities with start[] and finish[] times,
 * select the maximum number of non-overlapping activities one person can perform.
 * Two activities i, j are compatible if start[j] &gt; finish[i].</p>
 *
 * <p><b>APPROACH: Greedy (Earliest Finish Time)</b></p>
 * <ol>
 *   <li>Sort activities by finish time (ascending)</li>
 *   <li>Always select the activity that finishes earliest</li>
 *   <li>Pick next activities only if start &gt; prevFinish</li>
 * </ol>
 * <p><b>Why it works:</b> Finishing earliest leaves maximum time for future activities.
 * This locally optimal choice guarantees globally optimal solution.</p>
 *
 * <p><b>COMPLEXITY: Time O(n log n) | Space O(n)</b></p>
 *
 * <p><b>TIMELINE VISUALIZATION:</b></p>
 * <pre>
 * Activities sorted by finish time:
 * │ Activity │ Start │ Finish │ Status        │
 * ├──────────┼───────┼────────┼───────────────┤
 * │    2     │   4   │   6    │ ✓ Select      │
 * │    1     │   1   │  13    │ ✗ Skip (1≤6)  │
 * │    5     │   7   │  13    │ ✓ Select      │
 * │    3     │  12   │  14    │ ✗ Skip (12≤13)│
 * │    4     │  15   │  19    │ ✓ Select      │
 *
 * Timeline:
 *  Activity 2: 4--6
 *  Activity 5:       7---------13
 *  Activity 4:                    15-----19
 *
 * Result: 3 non-overlapping activities [2, 4, 5]
 * </pre>
 *

 * @see #activityProblem(int[], int[])
 */
public class G01_Activity_Selection_Problem {

    /**
     * Test driver with example input and expected output.
     * Starting times: [1, 4, 12, 15, 7] | Finishing times: [13, 6, 14, 19, 13]
     * Expected: [2, 4, 5] — 3 maximum activities
     */
    public static void main(String[] args) {
        int[] startingTime = new int[]{1, 4, 12, 15, 7};
        int[] finishTime = new int[]{13, 6, 14, 19, 13};
        ArrayList<Integer> answer = activityProblem(startingTime, finishTime);
        System.out.print("\nMaximum Activity that can be selected without overlapping is : " + answer);
    }

    /**
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     *
     * <p><b>STEP 1: Build Activity Array (index, start, finish)</b></p>
     * <pre>
     * Input: start=[1,4,12,15,7], finish=[13,6,14,19,13]
     *
     * array[i][0]=index | array[i][1]=start | array[i][2]=finish
     * ───────────────────────────────────────────────────────
     *        1          |        1         |       13
     *        2          |        4         |        6
     *        3          |       12         |       14
     *        4          |       15         |       19
     *        5          |        7         |       13
     * </pre>
     *
     * <p><b>STEP 2: Sort by Finish Time</b></p>
     * <pre>
     * After Arrays.sort(array, comparingInt(o → o[2])):
     *
     * index | start | finish | ← Sorted by column [2]
     * ──────┼───────┼────────┤
     *   2   |   4   |   6    | ← Earliest
     *   1   |   1   |  13    |
     *   5   |   7   |  13    |
     *   3   |  12   |  14    |
     *   4   |  15   |  19    | ← Latest
     * </pre>
     *
     * <p><b>STEP 3: Greedy Selection Loop</b></p>
     * <pre>
     * prevFinish = 6 (from first activity)
     * answer = [2]
     *
     * i=1: Activity 1 (start=1):  1 > 6? NO  → Skip
     * i=2: Activity 5 (start=7):  7 > 6? YES → Select, prevFinish=13
     * i=3: Activity 3 (start=12): 12 > 13? NO  → Skip
     * i=4: Activity 4 (start=15): 15 > 13? YES → Select, prevFinish=19
     *
     * answer = [2, 5, 4]
     * </pre>
     *
     * <p><b>STEP 4: Sort Result by Original Index</b></p>
     * <pre>
     * Collections.sort([2, 5, 4]) → [2, 4, 5]
     * </pre>
     *
     * @param startingTime array of starting times
     * @param finishTime array of finishing times
     * @return 1-indexed activity numbers selected, sorted in ascending order
     */
    private static ArrayList<Integer> activityProblem(int[] startingTime, int[] finishTime) {
        int length = startingTime.length;
        ArrayList<Integer> answer = new ArrayList<>();

        // STEP 1: Create activity triples [index, start, finish]
        int[][] array = new int[length][3];
        for (int i = 0; i < length; i++) {
            array[i][0] = i + 1;           // 1-indexed activity number
            array[i][1] = startingTime[i];  // starting time
            array[i][2] = finishTime[i];    // finishing time
        }

        // STEP 2: Sort by finish time (greedy key)
        Arrays.sort(array, Comparator.comparingInt(o -> o[2]));

        // STEP 3: Greedy selection
        int prevFinish = array[0][2];
        answer.add(array[0][0]);

        // Loop through remaining activities
        for (int i = 1; i < length; i++) {
            int currStart = array[i][1];

            // Non-overlapping: current start > previous finish
            if (currStart > prevFinish) {
                answer.add(array[i][0]);
                prevFinish = array[i][2];
            }
        }

        // STEP 4: Sort selected activities by index
        Collections.sort(answer);
        return answer;
    }
}