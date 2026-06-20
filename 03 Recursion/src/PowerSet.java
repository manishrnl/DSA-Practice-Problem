package src;
public class PowerSet {

    /**
     * Problem: Print Power Set
     * Logic: For every character in the string, we have two choices:
     * 1. Include the character in the current subset.
     * 2. Exclude the character from the current subset.
     * This creates a binary recursion tree.
     * * @param str The original string
     * @param index The current character we are considering
     * @param current The subset built so far
     */
    public static void printPowerSet(String str, int index, String current) {
        // Base Case: If we have considered all characters
        if (index == str.length()) {
            System.out.println("\"" + current + "\"");
            return;
        }

        // Choice 1: Include the current character
        printPowerSet(str, index + 1, current + str.charAt(index));

        // Choice 2: Exclude the current character
        printPowerSet(str, index + 1, current);
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println("Power Set of " + s + ":");
        printPowerSet(s, 0, "");
    }
}