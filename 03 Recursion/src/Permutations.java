package src;
public class Permutations {

    /**
     * Problem: Print All Permutations
     * Logic: We use Backtracking. We swap the current character with
     * every character appearing after it to create new arrangements.
     * After the recursive call, we swap back (backtrack) to restore
     * the original string for the next iteration.
     * * @param str The string to permute
     *
     * @param startIndex The starting index of the window
     * @param endIndex   The ending index of the window
     */
    public static void printAllPermutations(String str, int startIndex, int endIndex) {
        // Base Case: If the window has narrowed to a single character
        if (startIndex == endIndex) {
            System.out.print(str + " ");
            return;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            // Step 1: Swap character at 'left' with character at 'i'
            str = swap(str, startIndex, i);

            // Step 2: Recurse for the rest of the string
            printAllPermutations(str, startIndex + 1, endIndex);

            // Step 3: Backtrack (Swap back to original)
            str = swap(str, startIndex, i);
        }
    }

    // Helper method to swap characters in a String
    private static String swap(String str, int i, int j) {
        /**
         * Imagine you are swapping the first and second characters of "abc" (i=0, j=1):toCharArray():
         * Java allocates a new block of memory specifically for an array: ['a', 'b', 'c'].
         * The original String "abc" remains untouched in the "String Pool."
         * The temp Variable: A tiny piece of memory holds 'a' so it doesn't get lost when you overwrite index 0.
         * The Array Update: The array becomes ['b', 'a', 'c'].String.valueOf():
         * A brand-new String object is created in memory: "bac".
         *
         * @Param ith character  from left of String str
         * @Param jth character from left of String str
         * */
        char[] charArray = str.toCharArray();
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    public static void main(String[] args) {
        String s = "abcd";
        System.out.println("Permutations of " + s + ": \n");
        printAllPermutations(s, 0, s.length() - 1);

    }
}