import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubArray_With_Given_Sum {
    /**
     * To find the subArray / part of Array whose sum == targetSum
     * Given sub array should be sequential
     * EX- for array {1,3,5,6,3,4,5} for targetSum = 14
     * subarray is {5,6,3} continuously
     */
    public static List<Integer> findSubArraysSum(int[] array, int targetSum) {
        List<Integer> list = new ArrayList<>();
        int start = 0, currentSum = 0;

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            currentSum += value;
            while (currentSum > targetSum && start < i) {
                currentSum -= array[start++];
            }
            if (currentSum == targetSum) {
                for (int j = start; j <= i; j++) {
                    list.add(array[j]);
                }
                return list;
            }
        }




        System.out.println("Lists data are : " + list);
        return list;
    }

    public static void findSubArraysSum_HashMap(int[] array, int targetSum) {
        int currentSum = 0, start = 0, end = -1;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];
            if ((currentSum - targetSum) == 0) {
                start = 0;
                end = i;
                break;
            }
            if (map.containsKey(currentSum - targetSum)) {
                start = map.get(currentSum - targetSum);
                end = i;
                break;
            }
            map.put(currentSum, i);
        }
        if (end == -1)
            System.out.println("Target sum not found via HashMap");
        else
            System.out.println("Target found from index via HashMap : " + start + " to " + end);


    }

    static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 45, 3, 5, 56, 57, 6, 78, 8, 79, 89, 8, 79, 7, 86, 7, 5, 6, 4, 5, 3, 4, 2, 456, 23, 4, 3, 5, 54, 6, 5, 7, 67, 86789,
                788090, 9, -9, 0, -89, 678, 54, 6, 34534, 45, 542354, 34, 534, 56, 45, 56, 7,};
        int targetSum = 635;
        long startTime = System.nanoTime();
        List<Integer> list = findSubArraysSum(array, targetSum);
        long endTime = System.nanoTime();
        findSubArraysSum_HashMap(array, targetSum);
        long endTime2 = System.nanoTime();
        System.out.println("To get Target Sum = " + targetSum + " , Arrays are : " + list.toString());
        System.out.println("Time taken to execute functions is : " + (endTime - startTime) + " ns");
        System.out.println("Time taken to execute HashMap function is : " + (endTime2 - endTime) + " ns");
    }
}
