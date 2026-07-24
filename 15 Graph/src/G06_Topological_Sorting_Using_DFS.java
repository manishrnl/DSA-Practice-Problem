import java.util.*;
/**
 * <h1>Conditions for topological sorting</h1>
 * <p>It must be a DIRECTED ACYCLIC (No Cycle present) GRAPH Meaning-</p>
 * <p>
 * This class checks that precondition: it detects whether a directed graph
 * contains a cycle, using DFS with a recursion-stack marker (the same
 * technique as cycle detection in a plain directed graph). If this returns
 * {@code true}, the graph is <b>not</b> a valid input for topological
 * sorting.
 * </p>
 * <p>
 * If the graph passes that check, {@link #topologicalSorting} performs the
 * actual sort, using the classic DFS post-order technique: a vertex is
 * only pushed onto the result stack after all of its descendants have
 * already been explored and pushed, so popping the stack yields an order
 * where every edge points from an earlier vertex to a later one.
 * </p>
 */
public class G06_Topological_Sorting_Using_DFS {
    /**
     * Produces a topological ordering of the given directed graph, assuming it
     * has already been confirmed acyclic (e.g. via {@link #isCyclePresent}).
     * Calling this on a graph that contains a cycle produces a meaningless
     * result rather than an error, since nothing here checks for back-edges —
     * that check is {@link #isCyclePresent}'s job, done beforehand.
     * <p>
     * Runs a DFS from every unvisited vertex (covering disconnected
     * components too), delegating the actual traversal and stack-building to
     * {@link #topologicalSortingHelper}. Once every vertex has been pushed,
     * popping them off the stack in order yields the topological order.
     * </p>
     *
     * @param numberOfVertex one more than the highest valid vertex label (i.e.
     *                       valid vertices are {@code 1} to
     *                       {@code numberOfVertex - 1}; index {@code 0} is
     *                       unused)
     * @param adjacencyList  the graph represented as a directed adjacency list
     * @return an array holding a valid topological order of the graph's
     * vertices
     */
    private static int[] topologicalSorting(int numberOfVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[numberOfVertex];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < numberOfVertex; i++) {
            if (!isVisited[i]) {
                topologicalSortingHelper(i, isVisited, stack, adjacencyList);
            }
        }

        int[] ans = new int[numberOfVertex];
        int i = 0;
        while (!stack.isEmpty()) ans[i++] = stack.pop();
        return ans;
    }

    /**
     * Recursive DFS helper that explores every descendant of
     * {@code currentVertex} before pushing {@code currentVertex} itself onto
     * {@code stack} — this post-order push is what makes the technique work:
     * a vertex can only end up below all of its dependencies on the stack, so
     * popping the stack later visits dependencies before dependents.
     *
     * @param currentVertex the vertex currently being visited
     * @param isVisited     tracks which vertices have been visited across the
     *                      whole search (shared across components)
     * @param stack         accumulates vertices in post-order as they finish
     *                      being explored; modified in place by each
     *                      recursive call
     * @param adjacencyList the graph represented as a directed adjacency list
     */
    private static void topologicalSortingHelper(int currentVertex, boolean[] isVisited, Stack<Integer> stack, ArrayList<ArrayList<Integer>> adjacencyList) {
        isVisited[currentVertex] = true;
        for (int i = 0; i < adjacencyList.get(currentVertex).size(); i++) {
            int neighbour = adjacencyList.get(currentVertex).get(i);

            if (!isVisited[neighbour]) {
                topologicalSortingHelper(neighbour, isVisited, stack, adjacencyList);

            }
        }
        stack.push(currentVertex);
    }

    /**
     * Adds a directed edge from {@code node1} to {@code node2}.
     */
    public static void addEdge(ArrayList<ArrayList<Integer>> adjacencyList, int node1, int node2) {
        adjacencyList.get(node1).add(node2);
    }

    public static List<Integer> answer = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        int numberOfVertex = 9; // valid vertex labels: 1 .. 8

        // Fill ALL numberOfVertex slots (0 .. numberOfVertex - 1), even though
        // index 0 goes unused, so every valid vertex label has a real slot to add() into.
        for (int i = 0; i < numberOfVertex; i++)
            adjacencyList.add(new ArrayList<>());

        addEdge(adjacencyList, 1, 2);
        addEdge(adjacencyList, 2, 3);
        addEdge(adjacencyList, 3, 4);
        addEdge(adjacencyList, 4, 5);
        addEdge(adjacencyList, 5, 6); // closes a cycle: 1 -> 2 -> 3 -> 4 -> 5 -> 1

        if (!isCyclePresent(numberOfVertex, adjacencyList)) {
            System.out.print("\nNo cycle found hence Calling sortTopological(..) method to sort graph");
            System.out.println("\nPrinting Sorting order :" + Arrays.toString(topologicalSorting(numberOfVertex, adjacencyList)));

        } else {
            System.out.print("Cycle is present at node " + answer + ",  Can't call method sortTopological(..)");
        }
    }

    /**
     * Recursive DFS helper that explores from {@code currentVertex}, looking
     * for a directed cycle within its reachable component.
     * <p>
     * The {@code for} loop below walks the <em>positions</em> of
     * {@code currentVertex}'s neighbour list — always starting at position
     * {@code 0} — and reads the actual neighbour vertex label out of each
     * position. It is that neighbour label, not the loop position, that gets
     * checked against {@code isVisited} and {@code recursiveStack} and passed
     * into the recursive call.
     * </p>
     * <p>
     * If a neighbour hasn't been visited yet, recurse into it. If it has been
     * visited <b>and</b> is still marked on {@code recursiveStack} (meaning
     * it's an ancestor on the current DFS path), a back-edge exists — both
     * that neighbour and {@code currentVertex} are genuine members of the
     * resulting cycle, since {@code currentVertex} has an edge directly back
     * to an active ancestor.
     * </p>
     *
     * @param currentVertex  the vertex currently being visited
     * @param adjacencyList  the graph represented as a directed adjacency list
     * @param isVisited      tracks which vertices have been visited across the
     *                       whole search (shared across components)
     * @param recursiveStack tracks which vertices are ancestors on the
     *                       <em>current</em> DFS path
     * @return {@code true} if a cycle is found anywhere in this vertex's
     * reachable component; {@code false} otherwise
     */
    public static boolean dfs(int currentVertex, ArrayList<ArrayList<Integer>> adjacencyList, boolean[] isVisited, boolean[] recursiveStack) {
        recursiveStack[currentVertex] = true;
        isVisited[currentVertex] = true;

        for (int i = 0; i < adjacencyList.get(currentVertex).size(); i++) {
            int neighbour = adjacencyList.get(currentVertex).get(i);

            if (!isVisited[neighbour]) {
                if (dfs(neighbour, adjacencyList, isVisited, recursiveStack))
                    return true;
            } else if (recursiveStack[neighbour]) {
                answer.add(currentVertex); // currentVertex closes the edge back to an active ancestor — a genuine cycle member
                return true;
            }
        }

        recursiveStack[currentVertex] = false;
        return false;
    }

     public static boolean isCyclePresent(int numberOfVertex, ArrayList<ArrayList<Integer>> adjacencyList) {
        boolean[] isVisited = new boolean[numberOfVertex];
        boolean[] recursiveStack = new boolean[numberOfVertex];
        answer.clear();

        for (int i = 1; i < numberOfVertex; i++) {
            if (!isVisited[i]) {
                if (dfs(i, adjacencyList, isVisited, recursiveStack))
                    return true;
            }
        }
        return false;
    }
}