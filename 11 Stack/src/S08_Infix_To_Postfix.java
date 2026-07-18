import java.util.Stack;

/**
 * <h1>Infix to Postfix Expression Converter</h1>
 *
 * <h3>📋 Exhaustive Step-by-Step Execution Rules</h3>
 * <pre ">
 * STEP 1: Scan the input expression string from LEFT to RIGHT, character by character.
 *
 * STEP 2: Evaluate the current character token:
 *
 *    2.1 IF the character is an OPENING BRACKET '(', '{', or '[':
 *        &minus;&gt; Push it directly onto the Stack framework to track a new nesting scope.
 *
 *    2.2 IF the character is a CLOSING BRACKET ')', '}', or ']':
 *        &minus;&gt; Enter a loop: Continually pop operators off the stack and print them until the matching open bracket is found.
 *        &minus;&gt; Pop the opening bracket out of the stack and discard both brackets.
 *
 *    2.3 IF the character is an OPERAND (letters 'a'-'z' or numbers):
 *        &minus;&gt; Print it immediately to the final output buffer. It never touches the stack.
 *
 *    2.4 IF the character is an OPERATOR '+', '-', '*', or '/':
 *        &minus;&gt; Enter a loop: Peek at the top element of the stack. Pop and print operators if the stack is not empty, the top item is not a bracket,
 *             and the top item's priority is GREATER THAN OR EQUAL to the current operator.
 *        &minus;&gt; Once the stack conditions clear, push your current operator onto the stack.
 *
 * STEP 3: Once the loop reaches the END of the string: Clear out the memory stack by popping and printing everything remaining.
 * </pre>
 *
 * <h3>⚙️ State Processing Lifecycle Matrix</h3>
 * <pre ">
 *   Input Target String: "a+b*(d+e)"
 *
 *   Token │ Action                     │ Stack (Top on Right) │ Output Buffer
 *   ══════╪════════════════════════════╪══════════════════════╪═══════════════
 *     a   │ Print Operand              │ [ ]                  │ a
 *     +   │ Push Operator (Empty Stack)│ [ + ]                │ a
 *     b   │ Print Operand              │ [ + ]                │ ab
 *     *   │ Push (Priority * &gt; +)      │ [ +, * ]             │ ab
 *     (   │ Push Opening Enclosure     │ [ +, *, ( ]          │ ab
 *     d   │ Print Operand              │ [ +, *, ( ]          │ abd
 *     +   │ Push (Inside Enclosure)    │ [ +, *, (, + ]       │ abd
 *     e   │ Print Operand              │ [ +, *, (, + ]       │ abde
 *     )   │ Pop till Matching '(' found│ [ +, * ]             │ abde+
 *   ══════╧════════════════════════════╧══════════════════════╧═══════════════
 *   End of expression reached &minus;&gt; Pop remaining items: Result = abde+*+
 * </pre>
 *
 * @param str The raw, well-formed infix mathematical string expression to convert.
 */

public class S08_Infix_To_Postfix {

    /**
     * Converts a standard infix equation expression string into sequential postfix order.
     *
     * @param str Raw input math equation string expression.
     */
    private static void infixToPostfix(String str) {
        if (str == null || str.isEmpty()) {
            System.out.println("Expression is empty.");
            return;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            // Step 1: If opening bracket found, push onto Stack
            if (isOpeningBracket(ch)) {
                stack.push(ch);
            }
            // Step 3: FIXED: Pop elements to output until matching opening bracket is found
            else if (isClosingBracket(ch)) {
                while (!stack.isEmpty() && !isOpeningBracket(stack.peek())) {
                    System.out.print(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // Drop the opening bracket from the stack framework
                }
            }
            // Step 4: Handle Operator conversions
            else if (isOperator(ch)) {
                // FIXED: Continually pop out items that have higher or equal priority
                while (!stack.isEmpty() && !isOpeningBracket(stack.peek()) && getPriority(stack.peek()) >= getPriority(ch)) {
                    System.out.print(stack.pop());
                }
                stack.push(ch);
            }
            // Step 2: If operand found, print it directly
            else {
                System.out.print(ch);
            }
        }

        // Step 5: FIXED: Safely flush out all remaining stack items using an empty status check loop
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    public static boolean isOpeningBracket(char ch) {
        return (ch == '(' || ch == '{' || ch == '[');
    }

    public static boolean isClosingBracket(char ch) {
        return (ch == ')' || ch == '}' || ch == ']');
    }

    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    public static int getPriority(char ch) {
        switch (ch) {
            case '+', '-':
                return 1;
            case '*', '/':
                return 2;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        String convert = "a+b*(d+e)";
        System.out.println("Infix Expression:    " + convert);
          System.out.print("Postfix Expression:  ");

        // Expected Correct Output: abde+*+
        infixToPostfix(convert);
    }
}