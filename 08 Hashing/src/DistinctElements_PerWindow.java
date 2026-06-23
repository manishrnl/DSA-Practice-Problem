import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class DistinctElements_PerWindow {
    /**
     * Count distinct Elements in every window of size 'k'
     * <p>
     * Example: -> array = {1,2,-2,1,3,1,1,3} and k = 4
     * distinct elements are :
     * for index {0-3} as k =4 , distinct elements are :3 {1,2,-2}
     * for index {1-4} as k =4 , distinct elements are :4 {2,-2,1,3}
     * for index {2-5} as k =4 , distinct elements are :3 {-2,1,3}
     * for index {3-6} as k =4 , distinct elements are :2 {1,3}
     * for index {4-7} as k =4 , distinct elements are :2 {3,1}
     * <p>
     * T.C =O(n * k )
     *
     * @param array
     * @param k
     * @return
     */

    public static List<Integer> distinctElements_PerWindow_TC(int[] array, int k) {

//         Time Complexity O(n)
//         for first k elements add all data modify HashMap only after first k elements had
//         been inserted . Why Clearing all data from map and again insert same data .We can
//         instead remove from extreme left by getting hashKey .
//         REMEMBER : Unlike array we can not get hashmaps index , because it does not store
//         data at index logic rather it hashes the value, and then it stores data at that
//         hashing place not at index .  Again to retrieve data we get hashKey via method map
//         .get(array[i]) which returns data stored at that place

        List<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < k; i++) {
            map.put(array[i], map.getOrDefault(array[i], 0) + 1);
        }
        list.add(map.size());

//          Now Push & remove  elements from index k+1

        for (int i = k; i < array.length; i++) {
            int in = array[i];  // to insert new elements inside HashMap
            int out = array[i - k];  // to remove elements inside HashMap from index array[i-k] i.e., from extreme left side
//               If only last data then we have to delete that data or else it will store 0 at that place which will be
//               counted as a different value and our result would be compromised
            if (map.get(out) == 1) map.remove(out);
            else map.put(out, map.getOrDefault(out, 0) - 1);
            map.put(in, map.getOrDefault(in, 0) + 1);
            list.add(map.size());
            System.out.println("For window of size " + k + " Distinct Elements are : " + map.keySet());
        }
        return list;


    }

    public static List<Integer> distinctElements_PerWindow(int[] array, int k) {
        /**  Time Complexity = O(n * k ) */
        List<Integer> list = new ArrayList<>();

        HashSet<Integer> set = new HashSet<>(k);
        for (int i = 0; i <= (array.length - k); i++) {

            for (int j = 0; j < k; j++) {
                set.add(array[i + j]);
            }
            list.add(set.size());
            set.clear();
        }
        return list;
    }


    public static void main(String[] args) throws IOException {
        int[] array = {1, 2, -2, 1, 3, 1, 1, 3};

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int k;

        while (true) {
            System.out.println("\nArray Content: " + Arrays.toString(array));
            System.out.print("Enter Key size (or -1 to QUIT): ");

            String input = bufferedReader.readLine();
            if (input == null || input.isEmpty()) continue;

            try {
                k = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a k value : ");
                continue;
            }

            if (k == -1) {
                System.out.println("Goodbye!");
                break;
            }

            long start = System.nanoTime();
            List<Integer> result = distinctElements_PerWindow(array, k);
            long end = System.nanoTime();
            List<Integer> result2 = distinctElements_PerWindow_TC(array, k);
            long end2 = System.nanoTime();

            System.out.print("Total distinct Elements per Window is :" + result);
            System.out.printf("  Time: %d ns\n", (end - start));
            System.out.print("Total distinct Elements per Window T.C   :" + result2);
            System.out.printf("  Time: %d ns\n", (end2 - end));

        }
    }
}