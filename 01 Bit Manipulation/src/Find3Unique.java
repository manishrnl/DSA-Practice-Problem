package src;

import java.util.*;

/**
 * Find the only non-repeating element in an array where every other element repeats thrice.
 * <a href="https://www.geeksforgeeks.org/dsa/find-unique-element-element-occurs-k-times-except-one/"> Find 3 Unique in an Array</a>
 */
public class Find3Unique {

    public int find3UniqueHashMap(int[] array, int k) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();

        // 1. Fill the frequency map
        for (int value : array)
            freqMap.put(value, freqMap.getOrDefault(value, 0) + 1);

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() != k) return entry.getKey();
        }
        return -1;
    }

    public int find3UniqueXOR(int[] array, int k) {

        int INT_SIZE = 32;
        int[] bitCounts = new int[INT_SIZE];

        // 2. Count the occurrences of '1' bits at each of the 32 positions
        for (int i = 0; i < INT_SIZE; i++) {
            for (int num : array) {
                // If the i-th bit of 'num' is set (1), increment the counter for that position
                if ((num & (1 << i)) != 0) {
                    bitCounts[i]++;
                }
            }
        }

        // Now consider all bits whose count is
        // not multiple of k to form the required
        // number.
        int res = 0;
        for (int i = 0; i < INT_SIZE; i++)
            res += (bitCounts[i] % k) * (1 << i);

        // Before returning the res we need
        // to check the occurrence  of that
        // unique element and divide it
        res = res / (array.length % k);

        return res;
    }

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 2, 1, 3, 1, 2, 3, 8, 8};
        Find3Unique obj = new Find3Unique();


        long startHashMap = System.nanoTime();
        int resultHashMap = obj.find3UniqueHashMap(array, 3);
        long endHashMap = System.nanoTime();
        System.out.println("Result via Hash Map Approach is : " + (resultHashMap) + " -> Execution Time : " + (endHashMap - startHashMap) + " ns");


        long startXOR = System.nanoTime();
        int resultXOR = obj.find3UniqueXOR(array, 3);
        long endXOR = System.nanoTime();
        System.out.println("Result via XOR Approach is : " + resultXOR + " -> Execution Time : " + (endXOR - startXOR) + " ns");

    }
}
