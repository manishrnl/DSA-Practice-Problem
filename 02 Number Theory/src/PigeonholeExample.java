package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PigeonholeExample {
    public static int findDuplicate(int[] number) {
        boolean[] holes = new boolean[number.length];

        for (int num : number) {
            // If the 'hole' is already occupied, we found our duplicate!
            if (holes[num]) {
                return num;
            }
            // Otherwise, put the 'pigeon' in the hole
            holes[num] = true;
        }
        return -1; // Should not happen based on PHP
    }

    public static void totalDuplicate(int[] number) {
        List<Integer> totalDuplicates = new ArrayList<>();
        boolean[] holes = new boolean[number.length];

        for (int value : number) {
            if (holes[value])
                totalDuplicates.add(value);
            holes[value] = true;
        }
        System.out.print("Duplicates value are : ");
        for (int value : totalDuplicates)
            System.out.print(value + " ");
    }
    public static void main(String[] args) {
        int[] pigeons = {1, 2, 3, 4, 2,3,4}; // 5 items, values 1-4
        System.out.println("Very 1st  duplicate pigeon is: " + findDuplicate(pigeons));
        totalDuplicate(pigeons);

    }
}