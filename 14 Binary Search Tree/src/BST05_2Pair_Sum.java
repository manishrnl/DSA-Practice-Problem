import java.util.HashSet;
import java.util.Set;

public class BST05_2Pair_Sum {
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

    /**
     * <h1>Approach 1</h1><p>In util functions, we are calling for left and right node, and checking for data (target-node.data) in set
     * Here we use technique like {@code A + B = answer , so we can say A = answer - B } we are using this logic. if not present in stack , we push node.data into the stack and checks for next iteration</p>
     * <h1>Approach 2</h1><p>We can store In-Order traversal data into array or list etc and apply same above approach to get result</p>
     *
     * @param root   Passing whole Node
     * @param target Target which needs to be finds as a sum of 2 number only
     * @return True or false for further processing
     */
    public static boolean isPairPresent(Node root, int target) {
        Set<Integer> set = new HashSet<>();
        return util(root, target, set);
    }

    private static boolean util(Node root, int target, Set<Integer> set) {
        if (root == null)
            return false;
        if (util(root.left, target, set))
            return true;
        if (set.contains(target - root.data))
            return true;
        set.add(root.data);
        return util(root.right, target, set);
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
        int key = 120;
        log.info("Starting binary tree construction...");

        Node root = createTree(arrayTree);


        System.out.println();

        if (root == null) {
            log.error("Tree construction failed — tree is empty!");
            return;
        }
        log.debug("Tree constructed successfully!");
        log.success("Checking pair is Present : " + (isPairPresent(root, key) ? "Yes" : "No"));
    }
}