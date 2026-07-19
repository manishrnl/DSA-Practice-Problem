public class Q02_Queue_Using_Array {

    public static class Queue {
        int[] data;
        int capacity;
        int rear;

        public Queue(int capacity) {
            this.capacity = capacity;
            this.data = new int[capacity];
            this.rear = -1;
        }

        public void offer(int value) {
            if (getRear() == capacity - 1) {
                System.out.println("Array is full. Cant Add data !");
                return;
            }
            data[setRear(+1)] = value;

        }

        public int poll() {
            int result = data[0];
            if (getRear() == -1) {
                System.out.println("Queue is empty. Cant Delete data !");
                return -1;
            }

            for (int i = 0; i < getRear(); i++)
                data[i] = data[i + 1];
            setRear(-1);
            return result;
        }

        public int getRear() {
            return this.rear;
        }

        public int setRear(int value) {
            this.rear += value;
            return rear;
        }

        public int getFront() {

            return this.data[0];
        }

        public void printQueue() {
            if (getRear() == -1) {
                System.out.print("Queue is empty. Cant Print data !");
                return;
            }
            System.out.print("Printing Queue : ");
            for (int i = 0; i <= rear; i++) {

                System.out.print(data[i] + " ");
            }

        }
    }

    public static void main(String[] args) {
        Queue q = new Queue(10);
        q.offer(11);
        q.offer(24);
        q.offer(38);
        q.offer(328);
        q.offer(318);
        q.offer(358);
        q.poll();
        q.printQueue();
    }

}
