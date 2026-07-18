import com.sun.source.tree.BreakTree;

import java.util.Stack;

/**
 * <h1>Linked List Stack Implementation</h1>
 * <h3>This class implements a Stack data structure using a Singly Linked List.
 * It follows the LIFO (Last-In-First-Out) protocol where insertions (push)
 * and deletions (pop) happen at the head of the list for optimal performance.
 *
 * <b>Time Complexity:</b> O(1) for both push and pop operations.
 * </h3>
 */
public class S02_Stack_Intro_LinkedList {

    /**
     * Represents a single container unit within the linked list stack.
     */
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Custom Stack memory manager handling node links.
     */
    public static class MyStack {
        int size;
        Node head; // Acts as the 'top' pointer of the stack

        public MyStack() {
            this.size = 0;
            this.head = null;
        }

        /**
         * Pushes a new item onto the top of the stack.
         * The new node becomes the new head of the internal linked list.
         *
         * @param data The raw integer value to store.
         */
        public void push(int data) {
            Node temp = new Node(data);
            temp.next = this.head; // Point new node to the old top
            this.head = temp;      // Shift top anchor to our new node
            setSize(+1);
        }

        /**
         * Removes and returns the top element node from the stack.
         *
         * @return The Node that was sitting at the top of the stack.
         * @throws RuntimeException If trying to pop from an empty stack.
         */
        public Node pop() {
            if (isEmpty()) {
                throw new RuntimeException("Stack is empty, Cant Delete Data");
            }

            Node temp = this.head; // Grab the current top node
            this.head = this.head.next; // Move top anchor down to the next element
            temp.next = null;      // Disconnect the popped node from the chain
            setSize(-1);

            return temp;
        }

        /**
         * Checks if the stack contains zero elements.
         *
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return getSize();
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size += size;
        }


        /**
         * Traverses the stack from top to bottom and prints its elements.
         */
        public void printStack() {
            Node curr = this.head;
            System.out.print("Stack (Top -> Bottom): ");
            while (curr != null) {
                System.out.print(curr.data + " -> ");
                curr = curr.next;
            }
            System.out.println("null");
        }
    }

    /**
     * Execution context to test Stack functionality.
     */
    public static void main(String[] args) {
        MyStack myStack = new MyStack();

        // Push data onto the myStack
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < array.length; i++) {
            myStack.push(array[i]);
        }

        System.out.print("Initial layout: ");
        myStack.printStack();

        // Perform pop operations
        System.out.println("Popped node data: " + myStack.pop().data);
        System.out.println("Popped node data: " + myStack.pop().data);

        System.out.print("Layout after modifications: ");
        myStack.printStack();
        System.out.println("Is Stack Empty ? " + (myStack.isEmpty() ? "Yes" : "No"));
        System.out.println("Size of Stack : " + myStack.size());


        Stack<Integer> systemStack = new Stack<>();
        systemStack.push(10);
        systemStack.push(11);
        systemStack.push(12);
        systemStack.push(13);
        systemStack.pop();
        System.out.println("Stack is : " + systemStack);
    }
}