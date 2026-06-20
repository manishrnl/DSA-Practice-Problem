package src;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Find_Distinct_Elements {
//    Faster among all 3 function HashSet > HashMap > Stream
//    T.C O(n)
    public static int findDistinctElements_HashMap(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int value : array) {
            if (!map.containsKey(value))
                map.put(value, 1);
        }

        return map.size();
    }

    //    T.C O(n)
    public static int findDistinctElements_HashSet(int[] array) {
        HashSet<Integer> set = new HashSet<>();
        for (int value : array) {
            set.add(value);
        }
        return set.size();
    }

    //    T.C O(n)  but it takes much time .
    // Modern (Java 8+): Functional approach
    public static long findDistinctElements_Stream(int[] array) {
        return Arrays.stream(array).distinct().count();
    }

    static void main(String[] args) {
        int[] input = new int[]{1, 2, 3, 4, 7, 7, 7, 7, 77, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9};
        long start = System.nanoTime();
        int result = findDistinctElements_HashMap(input);
        long end = System.nanoTime();
        int result2 = findDistinctElements_HashSet(input);
        long end2 = System.nanoTime();
        long result3 = findDistinctElements_Stream(input);
        long end3 = System.nanoTime();
        System.out.println("Total Distinct elements via Hash Map : " + result);
        System.out.println("Time taken  to execute function : " + (end - start));

        System.out.println("Total Distinct elements via Hash Set : " + result2);
        System.out.println("Time taken  to execute function : " + (end2 - end));

        System.out.println("Total Distinct elements via Java 8 Stream : " + result3);
        System.out.println("Time taken  to execute function : " + (end3 - end2));

    }
}
