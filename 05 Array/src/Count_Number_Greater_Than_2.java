import java.util.HashMap;

public class Count_Number_Greater_Than_2 {
    //   Print number greater than  N/2 in an array  T.C = O(N)
    public static int getMaxOccurrence(int[] number) {

        HashMap<Integer, Integer> map = new HashMap<>(number.length);

        for (int value : number) map.put(value, map.getOrDefault(value, 0) + 1);

        // 2. Efficiency Trick: Iterate over EntrySet instead of KeySet.
        // This gives us the Key and Value at the same time . No need to call map.get() again.
        for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > (number.length >> 2) && entry.getKey() > (number.length / 2))
                return entry.getKey();
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {18, 18,  12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 18, 18, 45, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 1};
        System.out.println(array.length);
        long startTime = System.nanoTime();
        int result = getMaxOccurrence(array);
        long endTime = System.nanoTime();
        String response = result > 0 ? "Number Greater than  N / 2 is :  " + result : "No such number found which satisfy condition number >(N / 2 ) ";
        System.out.println(response + " -> Time taken by algorithm is  " + (endTime - startTime) + " ns");
    }
}