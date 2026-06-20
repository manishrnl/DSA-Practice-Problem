package src;

import com.sun.source.tree.BreakTree;

import java.util.HashSet;

public class Union_Arrays {
    public static HashSet<Integer> findUnion(int[] array1, int[] array2) {
        HashSet<Integer> set = new HashSet<>();
        for (int value1 : array1)
            set.add(value1);
        for (int value2 : array2)
            set.add(value2);
        return set;
    }

    static void main(String[] args) {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {11, 12, 13, 14, 51};
        System.out.println("Union are :" +  findUnion(array1, array2));
    }

}
