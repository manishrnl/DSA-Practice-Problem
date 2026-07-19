import java.util.Stack;

/**
 * A class that implements a Queue data structure using two standard Stacks.
 * This implementation follows the First-In, First-Out (FIFO) principle.
 * <p>
 * This specific variant features an $O(1)$ time complexity for the {@code offer} operation
 * and an {@code O(n)} time complexity for the {@code poll} operation.
 * </p>
 */
public class Q04_Queue_Using_2_Stack {

    /**
     * Inner class representing the Queue structure.
     */
    public static class Queue {
        /**
         * Primary stack used to store incoming elements.
         */
        Stack<Integer> s1;

        /**
         * Auxiliary stack used to reverse element order during retrieval operations.
         */
        Stack<Integer> s2;

        /**
         * Initializes a new, empty Queue.
         */
        public Queue() {
            this.s1 = new Stack<>();
            this.s2 = new Stack<>();
        }

        /**
         * Inserts an element at the end of the queue (Enqueue operation).
         * This operation runs in $O(1)$ constant time.
         *
         * @param data the integer element to be added to the queue.
         */
        public void offer(int data) {
            s1.push(data);
        }

        /**
         * Retrieves and removes the element at the front of this queue (Dequeue operation).
         * This operation runs in {@code O(n)} linear time as it shuffles elements between stacks.
         *
         * @return the element at the front of the queue.
         * @throws RuntimeException if the queue is empty and no data can be retrieved.
         */
        public int poll() {
            if (s1 == null) // Note: In practice, you'd want s1.isEmpty() here instead
                throw new RuntimeException("Stack is Empty, Cant delete data");

            // Transfer all elements from s1 to s2 to reverse the order
            while (!s1.isEmpty())
                s2.push(s1.pop());

            // The top of s2 is now the oldest element (front of the queue)
            int poppedData = s2.pop();

            // Transfer elements back to s1 to preserve original order for future offers
            while (!s2.isEmpty())
                s1.push(s2.pop());

            return poppedData;
        }

        public void printQueue() {
            // Reverse elements into s2 to access them in FIFO order
            while (!s1.isEmpty())
                s2.push(s1.pop());

            // Print and simultaneously restore elements back to s1
            while (!s2.isEmpty()) {
                System.out.print(s2.peek() + " ");
                s1.push(s2.pop());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Queue q = new Queue();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        q.offer(4);
        q.offer(5);
        q.offer(6);
        q.poll();
        q.poll();
        q.poll();
        q.poll();
        q.printQueue();

    }
}