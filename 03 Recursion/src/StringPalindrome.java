package src;
public class StringPalindrome {
    // This matches your requested signature
    public static boolean stringPalindrome(String str) {
        // Base case: null or empty strings are technically palindromes
        if (str == null || str.isEmpty()) return true;

        // Call the helper method with the initial start and end positions
        return stringPalindrome(str, 0, str.length() - 1);
    }

    // This is the recursive helper that uses your specific logic
    private static boolean stringPalindrome(String str, int startIndex, int lastIndex) {
        // Base case: If the pointers meet or cross, every character matched
        if (startIndex >= lastIndex) return true;

        char startChar = str.charAt(startIndex);
        char endChar = str.charAt(lastIndex);

        // If characters at current pointers don't match, it's not a palindrome
        if (startChar != endChar) return false;

        // Recursive call: move pointers inward
        return stringPalindrome(str, startIndex + 1, lastIndex - 1);
    }
    public static void main(String[] args) {
        String str = "Mams";
        boolean result = stringPalindrome(str.toLowerCase());
        String response = result ? "It is a Palindrome " : "It is not a Palindrome ";
        System.out.println(response);
    }
}
