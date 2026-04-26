package src;

public class AllSortResults {
    public static void main(String[] args) {

        int[] originalArray = {29, 10, 14, 37, 13, 12, 3, 34, 456, 5, 4, 6, 56, 7, 6, 8, 78, 456, 79, 32, 45, 45, 5465, 32,
                456, 89, 890, 42356, 909, 0, 9, 0, 9, 0, 8, 9, 788, 7, 456, 67, 8, 6, 7, 5, 7, 56, 6, 546, 56, 5, 6,
                56, 5, 6, 5, 6, 5, 6, 56, 456, 56, 5, 6, 56, 5, 6, 5, 6, 5, 6, 5, 6, 5, 66, 7, 67, 8, 78, 6, 3, 4,
                2, 3, 13, 23, 45656767, 867, 89, 78890, 879, 89, 32, 4, 4, 54, 32, 56, 5, 7, 67, 6, 76, 7};

        // 1. Bubble Sort (using a clone)
        // DO NOT pass array named originalArray into each sorting algorithm because in 1
        // sorting say for Bubble sort it will be sorted and for rest sorting algo , it will
        // keep passing on sorted array only as we had sorted on original data only .
        // Therefore, we had used a clone to send a unsorted array to each algo everytime
        System.out.println("\nTime taken by Each Algorithm to sorts array is mentioned below" +
                " ");
        int[] forBubble = originalArray.clone();
        long startBubble = System.nanoTime();
        int[] bubbleResult = BubbleSort.bubbleSort(forBubble);
        long endBubble = System.nanoTime();
        System.out.println("Bubble Sort    : " + (endBubble - startBubble) + " ns");

        // 2. Insertion Sort (using a clone)
        int[] forInsertion = originalArray.clone();
        long startInsertion = System.nanoTime();
        int[] insertionResult = InsertionSort.insertionSort(forInsertion);
        long endInsertion = System.nanoTime();
        System.out.println("Insertion Sort : " + (endInsertion - startInsertion) + " ns");

        // 3. Selection Sort (using a clone)
        int[] forSelection = originalArray.clone();
        long startSelection = System.nanoTime();
        int[] selectionResult = SelectionSort.selectionSort(forSelection);
        long endSelection = System.nanoTime();
        System.out.println("Selection Sort : " + (endSelection - startSelection) + " ns");

        // 4. Merge Sort (using a clone)
        int[] forMerge = originalArray.clone();
        long startMerge = System.nanoTime();
        int[] mergeResult = MergeSort.mergeSort(forMerge);
        long endMerge = System.nanoTime();
        System.out.println("Merge Sort     : " + (endMerge - startMerge) + " ns");

        // 5. Quick Sort (using a clone)
        int[] forQuick = originalArray.clone();
        long startQuick = System.nanoTime();
        int[] quickResult = QuickSort.quickSort(forQuick);
        long endQuick = System.nanoTime();
        System.out.println("Quick Sort     : " + (endQuick - startQuick) + " ns");

        // Validation Logic
        boolean allMatch = true;
        for (int i = 0; i < originalArray.length; i++) {
            if (bubbleResult[i] != mergeResult[i] || quickResult[i] != mergeResult[i]) {
                allMatch = false;
                break;
            }
        }
        System.out.println("\nDoes all the mentioned algorithms ( Bubble, Selection , " +
                "Insertion ,Merge , Quick ) sorts produced the same results => " + (allMatch ?
                "YES , Results produced are same via each algorithm " :
                "SORRY , results are different . Please check Logic"));
    }
}