import java.util.PriorityQueue;

/**
 * The H_06_Connect_N_Ropes_With_Min_Cost class provides an optimal solution
 * to connect N ropes with the minimum possible cost using a Greedy approach via Min-Heap.
 */
public class H_06_Connect_N_Ropes_With_Min_Cost {

    /**
     * Calculates the minimum cost required to connect all ropes together.
     * * <p><strong>Approach: Greedy via Min-Heap</strong><br>
     * 1. Insert all rope lengths into a Min-Heap.<br>
     * 2. While there is more than one rope remaining in the heap:<br>
     * a. Extract the two smallest elements (shortest ropes).<br>
     * b. Combine them (sum their lengths) to get the current merge cost.<br>
     * c. Add this merge cost to the running total cost.<br>
     * d. Insert the newly combined rope back into the Min-Heap.<br>
     * 3. Return the accumulated total cost.
     * </p>
     * * <strong>Time Complexity:</strong> O(N log N) where N is the number of ropes.<br>
     * <strong>Space Complexity:</strong> O(N) to store elements in the PriorityQueue.
     *
     * @param array An array containing the lengths of the ropes.
     * @return The minimum total cost to connect all ropes.
     * @throws IllegalArgumentException If the array is null or contains fewer than 2 ropes.
     *
     */
    public static int solution(int[] array) {
        // Defensive validation: We need at least 2 ropes to perform a connection
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Array must contain at least 2 ropes to connect.");
        }

        // Initialize a Min-Heap to automatically keep the shortest ropes at the top
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int totalCost = 0;

        // Step 1: Add all individual rope lengths into the Min-Heap
        for (int rope : array) {
            minHeap.add(rope);
        }

        // Step 2: Combine ropes until only one single integrated rope remains
        while (minHeap.size() > 1) {
            // Extract the two absolute shortest ropes currently available
            int firstShortest = minHeap.poll();
            int secondShortest = minHeap.poll();

            // The cost to connect these two ropes is their combined length
            int currentMergeCost = firstShortest + secondShortest;
            totalCost += currentMergeCost;

            // Step 3: Insert the newly formed rope back into the heap
            minHeap.add(currentMergeCost);
        }

        return totalCost;
    }

    /**
     * Main method to test the minimum rope connection cost algorithm.
     */
    public static void main(String[] args) {
        // Test case array representing rope lengths
        int[] array = {4, 3, 2, 6};

        System.out.print("Rope lengths: ");
        for (int rope : array) {
            System.out.print(rope + " ");
        }
        System.out.println();

        /*
         * Explanation for {4, 3, 2, 6}:
         * 1. Connect 2 and 3 (cost = 5). Ropes left: {4, 6, 5}
         * 2. Connect 4 and 5 (cost = 9). Ropes left: {6, 9}
         * 3. Connect 6 and 9 (cost = 15). Ropes left: {15}
         * Total Min Cost = 5 + 9 + 15 = 29
         */
        System.out.println("Minimum cost of connecting all ropes is: " + solution(array));
    }
}