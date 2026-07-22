public class BST04_Nearest_Floor_Ceil_Value {
    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static int position = 0;

    public static Node createTree(int[] array) {
        if (position >= array.length) {
            return null;
        }

        int data = array[position++];
        if (data == -1) {
            return null;
        }

        Node root = new Node(data);
        root.left = createTree(array);
        root.right = createTree(array);
        return root;
    }

    public static int nearestFloorValue(Node root, int key) {
        int answer = Integer.MAX_VALUE;
        while (root != null) {
            if (root.data == key) return root.data;
            else if (root.data > key) root = root.left;
            else {
                answer = root.data;
                root = root.right;
            }

        }

        return answer;
    }

    public static int nearestCeilValue(Node root, int key) {
        int answer = Integer.MIN_VALUE;
        while (root != null) {

            if (root.data == key)
                return root.data;
            else if (root.data > key) {
                answer = root.data;
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return answer;
    }

    /**
     * <h1>Approach</h1><p>To check if Valid BST, convert it to In-Order Tree. and add all data that we get from In-Order traversal into list.
     * In-Order has all data in sorted fashion. Now check all data to its corresponding element. If any previous value > next value,
     * it means it is not a BST, hence we can say it is not a BST
     * </p>
     * </p>
     *
     * <h2>Tree Visual Structure:</h2>
     * <pre>
     * Original Binary Tree
     *
     *                       70
     *                    /     \
     *                  50        90
     *                /  \       /  \
     *              40    60   80   100
     *                              /
     *                            95
     * </pre>
     */
    public static void main(String[] args) {

        int[] arrayTree = new int[]{70, 50, 40, -1, -1, 60, -1, -1, 90, 80, -1, -1, 100, 95, -1, -1, -1};
        Colorful_Console_Logs log = new Colorful_Console_Logs();
        position = 0;
        int key = 93;
        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);


        System.out.println();

        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!");

        log.success("Nearest Floor Value of : " + key + " is " + nearestFloorValue(root, key));
        log.success("Nearest Ceil Value of : " + key + " is " + nearestCeilValue(root, key));

    }
}