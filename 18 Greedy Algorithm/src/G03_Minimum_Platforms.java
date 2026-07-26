import java.util.Arrays;

/**
 * <b>Minimum Platforms Problem</b>
 *
 * <p><b>PROBLEM:</b> Given arrival[] and departure[] times of trains at a station,
 * find the minimum number of platforms needed so no train waits (each train occupies
 * a platform from arrival to departure).</p>
 *
 * <p><b>APPROACH: Two-Pointer Event Sweep</b></p>
 * <ol>
 *   <li>Sort both arrival and departure times independently</li>
 *   <li>Use two pointers: one tracking next arrival, one tracking next departure</li>
 *   <li>If next train arrives before previous train leaves: need another platform (count++)</li>
 *   <li>If next train arrives after previous train leaves: reuse platform (count--)</li>
 *   <li>Track maximum platforms needed at any moment</li>
 * </ol>
 * <p><b>Why it works:</b> Sorting decouples arrivals from departures, turning the problem
 * into a simple interval overlap counter. The two-pointer technique efficiently tracks
 * when platforms are occupied vs. freed.</p>
 *
 * <p><b>COMPLEXITY: Time O(n log n) | Space O(1)</b></p>
 *
 * <p><b>EXAMPLE VISUALIZATION:</b></p>
 * <pre>
 * Arrival:   [900, 940, 950, 1100, 1500, 1800]
 * Departure: [910, 1200, 1120, 1130, 1900, 2000]
 *
 * Timeline (sorted):
 *  900A ──────── 910D
 *  940A ──────────────── 1200D
 *  950A ──────── 1120D
 *         1100A ──────── 1130D
 *                      1500A ────── 1900D
 *                                    1800A ────── 2000D
 *
 * Peak overlap: platforms needed at time 1100-1120 = 4 trains
 * (Trains from 900A, 940A, 950A, 1100A all present)
 * </pre>
 *

 * @see #maxPlatform(int[], int[], int)
 */
public class G03_Minimum_Platforms {

    /**
     * Test driver. Trains with given arrival/departure times.
     * Expected: Find maximum overlapping train occupancies.
     */
    public static void main(String[] args) {
        int[] arrivalTime = new int[]{900, 940, 950, 1100, 1500, 1800};
        int[] departureTime = new int[]{910, 1200, 1120, 1130, 1900, 2000};
        int length = arrivalTime.length;
        System.out.print("Minimum Number of Platform needed to accommodate all trains is : " + maxPlatform(arrivalTime, departureTime, length));
    }

    /**
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     *
     * <p><b>STEP 1: Sort Both Arrays Independently</b></p>
     * <pre>
     * Input:
     *   arrivalTime   = [900, 940, 950, 1100, 1500, 1800]
     *   departureTime = [910, 1200, 1120, 1130, 1900, 2000]
     *
     * After sorting (order is preserved within each array):
     *   arrivalTime   = [900, 940, 950, 1100, 1500, 1800]  (already sorted)
     *   departureTime = [910, 1120, 1130, 1200, 1900, 2000]
     *
     * Decoupling: we lose "train i departs at departureTime[i]" mapping.
     * Instead, we ask: "at any moment, how many trains overlap?"
     * </pre>
     *
     * <p><b>STEP 2: Initialize Two Pointers</b></p>
     * <pre>
     * arrival = 0    (index into arrivalTime)
     * departure = 0  (index into departureTime)
     * count = 0      (current platforms in use)
     * max = 0        (maximum platforms ever needed)
     * </pre>
     *
     * <p><b>STEP 3: Two-Pointer Event Processing</b></p>
     * <pre>
     * Key insight: compare the NEXT arrival with NEXT departure.
     * If arrival ≤ departure: new train arrives before old train leaves → need more platforms.
     * If arrival > departure: old train leaves before new train arrives → free a platform.
     *
     * +------+--------------+--------------+---------------+-------+-----+-------------------------------+
     * | Iter | Arrival      | Departure    | Check         | Count | Max | Action                        |
     * +------+--------------+--------------+---------------+-------+-----+-------------------------------+
     * |  1   | arr[0] = 900 | dep[0] = 910 | 900 <= 910? Y |   1   |  1  | Train arrives; arrival++      |
     * |  2   | arr[1] = 940 | dep[0] = 910 | 940 <= 910? N |   2   |  2  | Train arrives; arrival++      |
     * |  3   | arr[1] = 940 | dep[1] = 1120| 940 <= 1120? Y|   2   |  2  | Train arrives; arrival++      |
     * |  4   | arr[2] = 950 | dep[1] = 1120| 950 <= 1120? Y|   3   |  3  | Train arrives; arrival++      |
     * |  5   | arr[3] = 1100| dep[1] = 1120| 1100<= 1120? Y|   4   |  4  | Train arrives; arrival++      |
     * |  6   | arr[4] = 1500| dep[1] = 1120| 1500<= 1120? N|   3   |  4  | Train departs; departure++    |
     * |  7   | arr[4] = 1500| dep[2] = 1130| 1500<= 1130? N|   2   |  4  | Train departs; departure++    |
     * |  8   | arr[4] = 1500| dep[3] = 1200| 1500<= 1200? N|   1   |  4  | Train departs; departure++    |
     * |  9   | arr[4] = 1500| dep[4] = 1900| 1500<= 1900? Y|   2   |  4  | Train arrives; arrival++      |
     * | 10   | arr[5] = 1800| dep[4] = 1900| 1800<= 1900? Y|   3   |  4  | Train arrives; arrival++      |
     * | 11   | arr[6] = OOB | dep[4] = 1900| arr = 6 (len) |   -   |  4  | Exit loop (arrival >= length) |
     * +------+--------------+--------------+---------------+-------+-----+-------------------------------+
     * Maximum platforms: 4
     * </pre>
     *
     * <p><b>STATE TRACE AT PEAK (Iteration 5):</b></p>
     * <pre>
     * When 4 platforms needed (arrival index 3, 1100 timestamp):
     *
     * Sorted arrivals:   900    940    950   1100 ← now
     *                     ↓      ↓      ↓      ↓
     * Active trains at 1100:
     *   Train 1: 900-910  (left at 910, not active)
     *   Train 2: 940-1200 (active: 940 ≤ 1100 ≤ 1200) ✓
     *   Train 3: 950-1120 (active: 950 ≤ 1100 ≤ 1120) ✓
     *   Train 4: 1100-1130 (just arrived at 1100)     ✓
     *
     * Wait, re-check departure order: [910, 1120, 1130, 1200, ...]
     * At iteration 5:
     *   - Trains 2, 3, 4 arrived: count = 3
     *   - No trains departed yet (first departure is 910, already happened at iteration 1)
     *   - Actually: departure pointer is at 1120, but train at 910 was "virtual"...
     *
     * The algorithm counts: if next arrival ≤ next departure, increment count.
     * At 1100 arrival, next departure is 1120, so 1100 ≤ 1120 → count++
     * This means: a train arriving at 1100 must occupy a platform before
     * the train departing at 1120 leaves. So we need an extra platform.
     *
     * Platforms in use when 1100 arrives (before it boards):
     * - 940-1200 (train still there)
     * - 950-1120 (train still there)
     * Total: 2 + the new train at 1100 = 3 platforms needed at this instant.
     * </pre>
     *
     * <p><b>FINAL ANSWER:</b></p>
     * <pre>
     * max = 4 platforms minimum required
     * </pre>
     *
     * @param arrivalTime   array of train arrival times
     * @param departureTime array of train departure times
     * @param length        number of trains
     * @return minimum platforms needed to accommodate all trains without waiting
     */
    private static int maxPlatform(int[] arrivalTime, int[] departureTime, int length) {
        int count = 0, max = 0;

        // STEP 1: Sort both arrays independently
        Arrays.sort(arrivalTime);
        Arrays.sort(departureTime);

        // STEP 2: Initialize two pointers
        int arrival = 0, departure = 0;

        // STEP 3: Two-pointer sweep
        while (arrival < length) {
            // If next train arrives before (or when) current train departs
            if (arrivalTime[arrival] <= departureTime[departure]) {
                count++;           // Need another platform
                max = Math.max(count, max);  // Track maximum
                arrival++;         // Move to next arrival
            } else {
                // Next train arrives after current train departs
                count--;           // Can reuse platform
                departure++;       // Move to next departure
            }
        }

        return max;
    }
}