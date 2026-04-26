package src;

import java.util.*;

/**
 * Find the two non-repeating elements in an array where every other element repeats twice.
 * <a href="https://www.geeksforgeeks.org/dsa/find-two-non-repeating-elements-in-an-array-of-repeating-elements/">Find 2 Unique Elements in an array</a>
 */
public class Find2Unique {
    public int[] find2UniqueGeneralApproach(int[] array) {
        int length = array.length;
        List<Integer> lists = new ArrayList<>();

        for (int value : array) {
            int count = 0;
            for (int i : array) {
                if (value == i)
                    count++;

            }
            if (count == 1) {
                lists.add(value);
            }
        }
        int[] result = new int[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            result[i] = lists.get(i);
        }
        return result;

    }

    public int[] find2UniqueHashMap(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // 1. Fill the frequency map
        for (int value : array) {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        // 2. Use a temporary list to hold the unique keys
        List<Integer> uniqueList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                uniqueList.add(entry.getKey());
            }
        }

        // 3. INITIALIZE result here (now that we know the size)
        int[] result = new int[uniqueList.size()];

        // 4. Manual transfer (Fastest way)
        int index = 0;
        for (int num : uniqueList) {
            result[index++] = num; // Direct assignment
        }

        return result;
    }

    public int[] find2UniqueXOR(int[] array) {
        // Step 1: XOR all elements.
        // Pairs cancel out. resultXOR will be (Unique1 ^ Unique2)
        int combinedXOR = 0;
        for (int num : array) {
            combinedXOR ^= num;
        }

        // Step 2: Isolate the rightmost "set bit" (the rightmost 1).
        // This bit represents a position where Unique1 and Unique2 are DIFFERENT.
        // Formula: x & -x gives the lowest bit that is 1.
        int rightmostSetBit = combinedXOR & -combinedXOR;

        int[] result = new int[2];

        // Step 3: Divide numbers into two groups based on that specific bit.
        for (int num : array) {
            // Group A: Numbers that have 0 at this bit position
            if ((num & rightmostSetBit) == 0) {
                result[0] ^= num;
            }
            // Group B: Numbers that have a 1 at this bit position
            else {
                result[1] ^= num;
            }
        }
//        Sorting Array for consistency but sorting increases complexity to O(n^2) so not recommended
//        Arrays.sort(result);

        return result;
    }

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 2, 1, 4};
        Find2Unique obj = new Find2Unique();
        long startGeneral = System.nanoTime();
        int[] resultGeneral = obj.find2UniqueGeneralApproach(array);
        long endGeneral = System.nanoTime();
        System.out.println("Result via General Approach is : " + Arrays.toString(resultGeneral) + " -> Execution Time : " + (endGeneral - startGeneral) + " ns");


        long startHashMap = System.nanoTime();
        int[] resultHashMap = obj.find2UniqueHashMap(array);
        long endHashMap = System.nanoTime();
        System.out.println("Result via Hash Map Approach is : " + Arrays.toString(resultHashMap) + " -> Execution Time : " + (endHashMap - startHashMap) + " ns");


        long startXOR = System.nanoTime();
        int[] resultXOR = obj.find2UniqueXOR(array);
        long endXOR = System.nanoTime();
        System.out.println("Result via XOR Approach is : " + Arrays.toString(resultXOR) + " -> Execution Time : " + (endXOR - startXOR) + " ns");

    }
}
