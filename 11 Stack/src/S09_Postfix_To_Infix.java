import java.util.Stack;

/**
 * <h1>Postfix to Infix Expression Converter</h1>
 *
 * <h3>📋 Exhaustive Step-by-Step Execution Rules</h3>
 * <pre>
 * STEP 1: Initialize a Stack of Strings to store building sub-expressions.
 *
 * STEP 2: Scan the input Postfix expression string from LEFT to RIGHT, character by character.
 *
 * STEP 3: Evaluate the current character token:
 *
 *    3.1 IF the character is an OPERAND (letters 'a'-'z' or numbers):
 *        &minus;&gt; Convert the character into a String.
 *        &minus;&gt; Push it directly onto the Stack framework.
 *
 *    3.2 IF the character is an OPERATOR ('+', '-', '*', or '/'):
 *        &minus;&gt; Pop the top element from the stack. Assign it to Operand 2 (Right-hand value).
 *        &minus;&gt; Pop the next element from the stack. Assign it to Operand 1 (Left-hand value).
 *        &minus;&gt; Build a grouped string combination: "(" + Operand 1 + Operator + Operand 2 + ")"
 *        &minus;&gt; Push this newly constructed string group back onto the Stack.
 *
 * STEP 4: Once the end of the input string is reached, the final remaining element on the stack is the completed Infix expression.
 * </pre>
 *
 * <h3>⚙️ State Processing Lifecycle Matrix</h3>
 * <pre style="white-space: pre-wrap; word-wrap: break-word; width: 100%; max-width: none;">
 *   Input Target String: "abde+*+"
 *
 *   Token │ Action                                            │ Stack State (Top on Right)
 *   ══════╪═══════════════════════════════════════════════════╪═════════════════════════════════════════════════
 *     a   │ Push Operand String                               │ [ "a" ]
 *     b   │ Push Operand String                               │ [ "a", "b" ]
 *     d   │ Push Operand String                               │ [ "a", "b", "d" ]
 *     e   │ Push Operand String                               │ [ "a", "b", "d", "e" ]
 *     +   │ Pop e(op2), d(op1) -> Push (d+e)                  │ [ "a", "b", "(d+e)" ]
 *     *   │ Pop (d+e)(op2), b(op1) -> Push (b*(d+e))          │ [ "a", "(b*(d+e))" ]
 *     +   │ Pop (b*(d+e))(op2), a(op1) -> Push                │ [ "(a+(b*(d+e)))" ]
 *   ══════╧═══════════════════════════════════════════════════╧═════════════════════════════════════════════════
 *   End of expression reached -> Return the single remaining element from the stack frame.
 * </pre>
 */
public class S09_Postfix_To_Infix {

    /**
     * Converts a standard reverse-polish postfix expression back into a readable infix string format.
     *
     * @param postfix The raw, valid postfix mathematical expression string.
     * @return A fully parenthized infix structural string expression.
     */
    public static String postfixToInfix(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);

            // If the scanned token is an operator, pop and combine
            if (isOperator(ch)) {
                // Defensive check to avoid empty stack crashes on malformed expressions
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Malformed postfix expression string input.");
                }

                String operand2 = stack.pop(); // Right-hand token
                String operand1 = stack.pop(); // Left-hand token

                // Formulate structural group boundaries
                String combinedExpression = "(" + operand1 + ch + operand2 + ")";
                stack.push(combinedExpression);
            }
            // If the scanned token is an operand, push it onto the stack as a string
            else {
                stack.push(String.valueOf(ch));
            }
        }

        return stack.pop();
    }

    /**
     * Structural evaluator helper checking operator statuses.
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Core validation runtime application container block.
     */
    public static void main(String[] args) {
        String postfixInput = "abde+*+";

        System.out.println("Postfix Expression: " + postfixInput);
        String infixOutput = postfixToInfix(postfixInput);

        // Expected Correct Reconstructed Infix Output: (a+(b*(d+e)))
        System.out.println("Infix Expression:   " + infixOutput);
    }
}