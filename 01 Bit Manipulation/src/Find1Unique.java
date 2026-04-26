package src;

import java.util.HashMap;
import java.util.Map;

/**
 * Q1. Find the only non-repeating element in an array where every other element repeats twice.
 * <a href="https://www.geeksforgeeks.org/dsa/find-element-appears-array-every-element-appears-twice/">Find Non-repeating elements</a>
 */
public class Find1Unique {
    //      General Approach T.C = O(n^2)  , , Space Complexity = O(1)
    public int find1UniqueGeneralApproach(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int value = array[i], count = 0;
            for (int j = 0; j < array.length; j++) {
                if (value == array[j]) count++;
            }
            if (count == 1) return value;
        }
        return -1;
    }

    //      Hash Map Approach T.C = O(n) , Space Complexity = O(n)
    public int find1UniqueHashMap(int[] array) {
        // 1. Create the "Tally Sheet" (HashMap)
        // The first Integer is the KEY (the number from the array)
        // The second Integer is the VALUE (how many times it appeared)
        HashMap<Integer, Integer> results = new HashMap<>();

        // 2. Loop through every number in your input array
        for (int value : array) {
        /* results.getOrDefault(value, 0):
           - "Check if 'value' is already in my tally sheet."
           - "If yes, give me the current count."
           - "If no, assume the current count is 0."

           results.put(..., count + 1):
           - "Take that count, add 1 to it, and save it back into the sheet."
        */
            results.put(value, results.getOrDefault(value, 0) + 1);
        }

        // 3. Look through the finished Tally Sheet
        // Map.Entry represents one "row" in your tally sheet (Key + Value)
        for (Map.Entry<Integer, Integer> entry : results.entrySet()) {

            // If the Value (the count) is exactly 1, we found our unique number!
            if (entry.getValue() == 1) {
                return entry.getKey(); // Return the Key (the original number)
            }
        }

        // If we checked the whole map and found nothing
        return -1;
    }

    public int find1UniqueXOR(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++)
            result ^= array[i];
        return result;
    }


    public static void main(String[] args) {
        int[] array = {1, 2, 1, 3, 4, 6, 6, 7, 2, 3, 4, 5, 7};
        Find1Unique obj = new Find1Unique();

        // 1. General Approach
        long startGeneral = System.nanoTime();
        int resultGeneral = obj.find1UniqueGeneralApproach(array);
        long endGeneral = System.nanoTime();
        System.out.println("\n Results Via General Approach are : " + resultGeneral +
                "  -> Execution Time: " + (endGeneral - startGeneral) + " ns");

        // 2. HashMap Approach
        long startHashMap = System.nanoTime();
        int resultHashMap = obj.find1UniqueHashMap(array);
        long endHashMap = System.nanoTime();
        System.out.println("\n Results via HashMap are : " + resultHashMap + "  -> Execution" +
                " Time: " + (endHashMap - startHashMap) + " ns");

        // 3. XOR Approach
        long startXOR = System.nanoTime();
        int resultXOR = obj.find1UniqueXOR(array);
        long endXOR = System.nanoTime();
        System.out.println("\n Results via XOR are : " + resultXOR + "  ->  Execution Time: " + (endXOR - startXOR) + " ns");
    }
}