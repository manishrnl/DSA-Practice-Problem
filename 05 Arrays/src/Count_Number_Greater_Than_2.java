package src;
import java.util.HashMap;
public class Count_Number_Greater_Than_2 {
    //   Print number > N/2 in an array  T.C = O(N)
    public static int getMaxOccurance(int[] number) {

        // 1. Initial Capacity: If you know the array size,
        // provide an initial capacity to prevent the Map from resizing.
        HashMap<Integer, Integer> map = new HashMap<>(number.length);

        for (int value : number)
            map.put(value, map.getOrDefault(value, 0) + 1);

        // 2. Efficiency Trick: Iterate over EntrySet instead of KeySet.
        // This gives us the Key and Value at the same time . No need to call map.get() again.
        for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > (number.length >> 2)) {

                return entry.getKey();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 1, 11, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 1, 1, 45, 5, 5, 5, 5, 5, 5};
        long startTime = System.nanoTime();
        int result = getMaxOccurance(array);
        long endTime = System.nanoTime();
        String response = result > 0 ? "Number Greater than  N / 2 is :  " + result : "No such number found which satisfy condition number >(N / 2 ) ";
        System.out.println(response + " -> Time taken by algorithm is  " + (endTime - startTime) + " ns");

    }
}