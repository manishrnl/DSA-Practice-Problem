/**
 * <h1>Circular Queue Using a Fixed Array Buffer</h1>
 *
 * <h3>📋 Exhaustive Architectural Mechanics</h3>
 * <pre style="white-space: pre-wrap; word-wrap: break-word; width: 100%; max-width: none;">
 * 1. WRAP-AROUND FORMULA:
 *    Pointer advances are governed by modular arithmetic: Index = (Current + 1) % Capacity.
 *
 * 2. INITIALIZATION STATE:
 *    Both front and rear pointers start at -1 to represent a completely empty collection.
 *
 * 3. IDENTIFYING QUEUE CAPACITY BOUNDS:
 *    &minus;&gt; Is Empty: front == -1
 *    &minus;&gt; Is Full: (rear + 1) % capacity == front
 * </pre>
 *
 * <h3>⚙️ State Processing Lifecycle Matrix</h3>
 * <pre style="white-space: pre-wrap; word-wrap: break-word; width: 100%; max-width: none;">
 *   Target Execution Flow Simulation:
 *
 *   Operation      │ Front Idx │ Rear Idx │ Internal Array Element Layout Mapping
 *   ═══════════════╪═══════════╪══════════╪═════════════════════════════════════════════════════════
 *   Initial State  │    -1     │    -1    │ [ null, null, null, null, null, null, null, null, null, null ]
 *   enQueue(1..10) │     0     │     9    │ [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ] (Queue is Full)
 *   deQueue() x 3  │     3     │     9    │ [ null, null, null, 4, 5, 6, 7, 8, 9, 10 ]
 *   enQueue(1)     │     3     │     0    │ [ 1, null, null, 4, 5, 6, 7, 8, 9, 10 ]
 *   enQueue(2)     │     3     │     1    │ [ 1, 2, null, 4, 5, 6, 7, 8, 9, 10 ]
 *   deQueue()      │     4     │     1    │ [ 1, 2, null, null, 5, 6, 7, 8, 9, 10 ]
 *   ═══════════════╧═══════════╧══════════╧═════════════════════════════════════════════════════════
 *   Final printQueue() Output Result Stream:  5 6 7 8 9 10 1 2
 * </pre>
 */
public class Q03_Circular_Queue_Using_Array {

    public static class Queue {
        Integer[] data;
        int capacity;
        int front;
        int rear;

        public Queue(int capacity) {
            this.capacity = capacity;
            this.data = new Integer[capacity];
            this.rear = -1;
            this.front = -1;
        }

        /**
         * Appends an element to the trailing rear section of the circular track.
         */
        public void offer(int value) {
            if (isFull()) {
                throw new RuntimeException("Cant Add As Array is full");
            }

            // FIXED: If inserting the first element, align both pointers to the base index 0
            if (isEmpty()) {
                front = 0;
            }

            // FIXED: Properly pass the step increment value 1
            setRear(1);
            data[rear] = value;
        }

        /**
         * Removes and retrieves the element sitting at the leading edge front position.
         */
        public int poll() {
            if (isEmpty()) {
                throw new RuntimeException("Cant Delete Empty Queue");
            }

            // FIXED: Capture data record safely before altering structure contents
            int holdingValue = data[front];
            data[front] = null; // Clear allocation slot

            // FIXED: Reset queue tracking bounds if this was the last remaining element
            if (front == rear) {
                front = -1;
                rear = -1;
            } else {
                // FIXED: Safely advance front pointer forward by a step value of 1
                setFront(1);
            }

            return holdingValue;
        }

        // FIXED: The correct wrap-around check evaluates if the next rear step collides with front
        public boolean isFull() {
            return (rear + 1) % capacity == front;
        }

        public boolean isEmpty() {
            return front == -1;
        }

        public void setRear(int step) {
            rear = (rear + step) % capacity;
        }

        public void setFront(int step) {
            front = (front + step) % capacity;
        }

        /**
         * FIXED: Traverses the active queue collection layout continuously from front index to rear index.
         */
        public void printQueue() {
            if (isEmpty()) {
                System.out.println("Queue is empty.");
                return;
            }

            int current = front;
            while (true) {
                System.out.print(data[current] + " ");
                if (current == rear) {
                    break;
                }
                current = (current + 1) % capacity; // Follow circular path
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Queue queue = new Queue(10);

        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        queue.offer(10);

        queue.poll();
        queue.poll();
        queue.poll();

        queue.offer(11);
        queue.offer(12);
        queue.poll();
        queue.poll();
        queue.poll();
        queue.offer(13);
        queue.offer(14);

        queue.printQueue();
    }
}