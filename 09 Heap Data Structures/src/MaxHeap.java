import java.util.Arrays;

public class MaxHeap {

    /**
     * In Max Heap:
     * Parent node must always be greater than child nodes.
     * For any node i , to find its parent,child , left node ,right node we have formula like
     * We assumed 0 based indexing so added 1 with each index.
     * For Nodes = i
     * Parent = floor ( ( i + 1) / 2)
     * Left Child = 2 * ( i + 1 )
     * Right Child = 2 * ( i + 2 )
     */

    public static void insertInto_MaxHeap(int[] array, int value) {
        int[] newArray = new int[array.length + 1];        // Step 1: Create new array with extra space
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;   // Step 2: Insert new value at last position
        int childIndex = newArray.length - 1;        // Child index = last index
        System.out.println("Original Arrays are : " + Arrays.toString(newArray));

        while (childIndex > 0) {  // Step 3: Heapify Up
            int parentIndex = (childIndex - 1) / 2; // Find parent index
            if (newArray[childIndex] > newArray[parentIndex]) {// If child > parent → swap
                int temp = newArray[childIndex];
                newArray[childIndex] = newArray[parentIndex];
                newArray[parentIndex] = temp;
                childIndex = parentIndex; // Move upward
            } else {
                // Heap property satisfied
                break;
            }
            System.out.println("After Swaps : " + Arrays.toString(newArray));
        }
        System.out.println("Heap after insertion:" + Arrays.toString(newArray));
    }

    /**
     * Key Idea to delete from Max Heap.
     * Step 1 : Find the element index which needs to be deleted
     * Step 2 : Remove the element to be deleted with last element
     * Step 3 : Replace it with last element
     * Step 4 : Heapify Down
     * Step 5 : Sometimes Heapify Up may also be needed
     *
     * @param array : Arrays data including the number to be deleted
     * @param value : Value to be deleted from Array
     */
    public static void deleteFrom_MaxHeap(int[] array, int value) {
        System.out.println("\n#############################################\nPerforming Delete operations for value : " + value);
        int n = array.length;
        int deleteIndex = -1;  // Step 1: Find index of value which needs to be deleted
        for (int i = 0; i < n; i++) {
            if (array[i] == value) {
                deleteIndex = i;
                break;
            }
        }
        if (deleteIndex == -1) { // Value not found
            System.out.println("Value not found in heap , retry with valid data ...");
            return;
        }
        array[deleteIndex] = array[n - 1];   // Step 2: Replace delete element with last element
        int[] newHeap = Arrays.copyOf(array, n - 1);  // Step 3: Create new array excluding last element
        System.out.println("Before Heapify:" + Arrays.toString(newHeap));
        int currentIndex = deleteIndex;

        while (true) {  // Step 4: Heapify Down
            int leftChild = (2 * currentIndex + 1), rightChild = (2 * currentIndex + 2), largest = currentIndex;
            if (leftChild < newHeap.length && newHeap[leftChild] > newHeap[largest])  // Compare left child
                largest = leftChild;
            if (rightChild < newHeap.length && newHeap[rightChild] > newHeap[largest])   // Compare right child
                largest = rightChild;
            if (largest == currentIndex)   // Heap property satisfied
                break;
            int temp = newHeap[currentIndex];
            newHeap[currentIndex] = newHeap[largest];
            newHeap[largest] = temp;
            currentIndex = largest; // Move downward
        }
        System.out.println("Heap after deletion:" + Arrays.toString(newHeap));
    }


    public static void main(String[] args) {
        int[] array = {90, 70, 60, 40, 50, 20, 10};
        int value = 80;
        insertInto_MaxHeap(array, value);
        deleteFrom_MaxHeap(new int[]{90, 80, 70, 60, 40, 50, 20, 10}, value);
    }
}
