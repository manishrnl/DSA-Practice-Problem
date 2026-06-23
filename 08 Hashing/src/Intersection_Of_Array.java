import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Intersection_Of_Array {
    public static int findUnion(int[] array1, int[] array2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int value : array1)            map.put(value, map.getOrDefault(value, 0) + 1);
        for (int value : array2)            map.put(value, map.getOrDefault(value, 0) + 1);

        HashSet<Integer> set = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1)                 set.add(entry.getKey());
        }
        System.out.println("Intersection of array via HashMap is : " + set);
        return set.size();
    }

    public static int findUnion_HashSet(int[] array1, int[] array2) {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> intersection = new HashSet<>();
        for (int value : array1)            set.add(value);
        for (int value : array2) {
            if (set.contains(value))        intersection.add(value);
        }
        System.out.println("Intersection of array via HashSet is : " + intersection);
        return intersection.size();
    }

    static void main(String[] args) {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {11, 12, 13, 4, 5};
        int result = findUnion(array1, array2);
        int result2 = findUnion_HashSet(array1, array2);
    }
}
