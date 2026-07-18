import java.util.Stack;

/**
 * <h1>Balanced Parentheses Checker</h1>
 * This class provides an optimal solution to validate if a string containing grouping
 * characters—parentheses {@code ()}, braces {@code {}}, and brackets {@code []}—is balanced.
 * <p>
 * A string is considered balanced if:
 * <ol>
 *   <li>Open brackets are closed by the same type of brackets.</li>
 *   <li>Open brackets are closed in the correct, sequential LIFO order.</li>
 *   <li>Every closing bracket has a corresponding preceding opening bracket.</li>
 * </ol>
 * </p>
 * <h3>Key IDEA:</h3>
 * Add all opening brackets into the stack, and pop the uppermost element only if
 * brackets match up correctly. Otherwise, the parentheses do not match. Non-bracket characters
 * are safely bypassed during processing.
 * <p>
 * <b>Time Complexity:</b> O(N) where N is the length of the string, as we scan the string exactly once.
 * <b>Space Complexity:</b> O(N) auxiliary space in the worst case to hold bracket symbols within the stack framework.
 * </p>
 */
public class S05_Parenthesis_Checker {

    /**
     * Evaluates a string to check if all opening and closing bracket expressions form matched pairs.
     * Non-bracket text structures are safely skipped during evaluation.
     *
     * @param str The raw input string containing characters and brackets to be parsed.
     * @return {@code true} if all bracket scopes match up correctly; {@code false} otherwise.
     */
    public static boolean parenthesisChecker(String str) {
        // Validation check to handle empty edge-cases cleanly
        if (str == null || str.isEmpty()) {
            System.out.println("Please input a string to initiate Checking Parenthesis");
            return false;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            // Skip over arbitrary letters, numbers, spaces, etc.
            if (isOtherChar(ch)) {
                continue;
            }
            // Track open boundaries
            else if (isOpeningBracket(ch)) {
                stack.push(ch);
            }
            // Evaluate closing boundaries
            else {
                // If a closing bracket turns up but no opening brackets are registered, it's invalid
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.peek();

                // If the closing bracket doesn't match the current top open bracket, fail fast
                if (!isMatchingBracket(top, ch)) {
                    return false;
                } else {
                    stack.pop(); // Pairs matched successfully; resolve and drop the open tracker
                }
            }
        }

        // If the stack is completely empty, all opened scopes were closed correctly
        return stack.isEmpty();
    }

    /**
     * Helper check to determine if a character is an opening delimiter symbol.
     *
     * @param ch The target character to evaluate.
     * @return true if the character is an open parenthesis, brace, or bracket.
     */
    public static boolean isOpeningBracket(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    /**
     * Identifies non-bracket noise characters that should be ignored by the validation loop.
     * Uses strict logical AND gates to prevent catching structural brackets.
     *
     * @param ch The target character to check.
     * @return true if the character is irrelevant to the parenthesis checking mechanics.
     */
    public static boolean isOtherChar(char ch) {
        return (ch != '(' && ch != ')' && ch != '{' && ch != '}' && ch != '[' && ch != ']');
    }

    /**
     * Helper evaluation to confirm if two distinct bracket tokens form a correct matching pair.
     *
     * @param a The opening token candidate sitting on top of the stack.
     * @param b The closing token candidate found currently in the loop iteration.
     * @return true if the characters fit together as a valid semantic pairing.
     */
    public static boolean isMatchingBracket(char a, char b) {
        return ((a == '(' && b == ')') ||
                (a == '{' && b == '}') ||
                (a == '[' && b == ']'));
    }

    /**
     * Execution container running verification checks over sample data inputs.
     */
    public static void main(String[] args) {
        // String contains text variables and spaces to thoroughly check filter logic robustness
        String string = "({[ ereyth ]})";

        boolean isValid = parenthesisChecker(string);
        System.out.println("Input Sequence: " + string);
        System.out.println(isValid ? "It is a Valid Parenthesis" : "Not a Valid Parenthesis");
    }
}