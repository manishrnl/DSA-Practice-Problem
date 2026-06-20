import java.util.Arrays;

public class MinHeap {

    /**
     * In Min Heap:
     * Parent node must always be smaller than child nodes.
     * <p>
     * Array formulas:
     * <p>
     * Parent      = (i - 1) / 2
     * Left Child  = 2*i + 1
     * Right Child = 2*i + 2
     */

    public static void insertInto_MinHeap(int[] array, int value) {

        int[] newArray = new int[array.length + 1]; // Create new array with one extra space
        System.arraycopy(array, 0, newArray, 0, array.length);    // Copy original heap
        newArray[array.length] = value;  // Insert new value at LAST position
        System.out.println("Before Heapify:" + Arrays.toString(newArray));
        int childIndex = newArray.length - 1;        // Child index = last index

        while (childIndex > 0) {
            int parentIndex = (childIndex - 1) / 2;   // Find parent
            if (newArray[childIndex] < newArray[parentIndex]) { // Min Heap condition
                int temp = newArray[childIndex];
                newArray[childIndex] = newArray[parentIndex];
                newArray[parentIndex] = temp;

                childIndex = parentIndex;  // Move upward
                System.out.println("After swaps :" + Arrays.toString(newArray));
            } else {
                break;
            }
        }

        System.out.println("Heap after insertion:" + Arrays.toString(newArray));
    }
    public static void deleteFrom_MinHeap(int[] array, int value) {

        System.out.println("\n###########################################");
        System.out.println("Performing Delete Operation");

        int length = array.length;

        int deletedIndex = -1;

        // Step 1: Find element
        for (int i = 0; i < length; i++) {

            if (array[i] == value) {
                deletedIndex = i;
                break;
            }
        }

        // Value not found
        if (deletedIndex == -1) {

            System.out.println("Value not found.");
            return;
        }

        // Step 2: Replace deleted node with last node
        array[deletedIndex] = array[length - 1];

        // Step 3: Remove last element
        int[] newArray = Arrays.copyOf(array, length - 1);

        System.out.println("Before Heapify:");
        System.out.println(Arrays.toString(newArray));

        int current = deletedIndex;

        // Step 4: Heapify Down
        while (true) {

            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;

            int smallest = current;

            // Compare left child
            if (leftChild < newArray.length &&
                    newArray[leftChild] < newArray[smallest]) {

                smallest = leftChild;
            }

            // Compare right child
            if (rightChild < newArray.length &&
                    newArray[rightChild] < newArray[smallest]) {

                smallest = rightChild;
            }

            // Heap property satisfied
            if (smallest == current) {
                break;
            }

            // Swap
            int temp = newArray[current];
            newArray[current] = newArray[smallest];
            newArray[smallest] = temp;

            // Move downward
            current = smallest;
        }

        System.out.println("After Heapify:");
        System.out.println(Arrays.toString(newArray));
    }

    public static void main(String[] args) {
        int[] array = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int value = 35;
        insertInto_MinHeap(array, value);
        deleteFrom_MinHeap(new int[]{10, 20, 30, 35, 40, 50, 60, 70, 80, 90, 100}, value);
    }
}