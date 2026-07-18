import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Dynamic Stack Implementation</h1>
 * This class demonstrates a custom implementation of a Stack data structure using Java's
 * dynamic {@link ArrayList}. It strictly follows the <b>LIFO (Last-In-First-Out)</b> principle.
 * <p>
 * Since it uses a dynamic array list, it features automatic resizing and does not require
 * a predefined capacity, eliminating the risk of Stack Overflow errors during push operations.
 * </p>
 *
 * @author Your Name
 * @version 1.0
 * @see java.util.ArrayList
 */
public class S01_Stack_Intro {

    /**
     * A lightweight, dynamic Stack container.
     */
    public static class MyStack {
              private final List<Integer> array;

        /** Tracks the index of the top element of the stack. A value of -1 denotes an empty stack. */
        private int top;

        /**
         * Constructs a fresh, empty Stack with dynamic resizing capabilities.
         */
        public MyStack() {
            this.top = -1;
            this.array = new ArrayList<>();
        }

        /**
         * Pushes a new element onto the top of the stack.
         * <p>
         * <b>Time Complexity:</b> O(1) amortized.
         * </p>
         *
         * @param data The integer value to be pushed onto the stack.
         */
        public void push(int data) {
            top += 1;
            array.add(data); // Automatically appends the element at the end of the ArrayList
        }

        /**
         * Removes and returns the element at the top of this stack.
         * <p>
         * <b>Time Complexity:</b> O(1) constant time, as we are removing from the end of the list.
         * </p>
         *
         * @return The integer value removed from the top of the stack.
         * @throws Exception If this stack is empty (Stack Underflow).
         */
        public int pop() throws Exception {
            if (isEmpty()) {
                throw new Exception("Stack underflow! Cannot pop from an empty stack.");
            }

            // 1. Physically extract and remove the element at the 'top' index
            int poppedValue = array.remove(top);

            // 2. Decrement the top index pointer
            top--;

            return poppedValue;
        }

        /**
         * Retrieves, but does not remove, the element at the top of this stack.
         * <p>
         * <b>Time Complexity:</b> O(1) constant time.
         * </p>
         *
         * @return The integer value currently sitting at the top of the stack.
         * @throws Exception If this stack is empty.
         */
        public int peek() throws Exception {
            if (isEmpty()) {
                throw new Exception("Stack is empty! Cannot peek.");
            }
            return array.get(top);
        }

        /**
         * Verifies whether this stack contains any elements.
         *
         * @return {@code true} if the stack is empty; {@code false} otherwise.
         */
        public boolean isEmpty() {
            return top == -1;
        }

        /**
         * Traverses and prints the active contents of the stack from bottom to top.
         * Does not print remnants or unallocated memory blocks.
         */
        public void printStack() {
            if (isEmpty()) {
                System.out.println("[Empty Stack]");
                return;
            }
            System.out.print("Stack (bottom to top): ");
            // Loop strictly within the valid range of the active stack
            for (int i = 0; i <= top; i++) {
                System.out.print(array.get(i) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Entry point to run and test the dynamic Stack operations.
     */
    public static void main(String[] args) throws Exception {
        MyStack stack = new MyStack();

        // Push test elements
        stack.push(12);
        stack.push(13);
        stack.push(14);
        stack.push(15);
        stack.push(16);
        stack.push(17);
        stack.push(18);

        System.out.print("Before pop: ");
        stack.printStack();

        System.out.println("Peeking top element: " + stack.peek());
        System.out.println("Popping top element: " + stack.pop());

        System.out.print("After pop: ");
        stack.printStack();

        System.out.println("Is stack empty? " + stack.isEmpty());
    }
}