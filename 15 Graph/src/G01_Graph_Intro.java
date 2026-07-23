import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Demonstrates basic graph representation techniques in Java.
 * <p>
 * This class prompts the user to enter graph vertices and edges, then represents
 * the graph structure using both an <b>Adjacency Matrix</b> and an <b>Adjacency List</b>.
 * </p>
 */
public class G01_Graph_Intro {

    /**
     * Scanner instance for reading user input from the console.
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Populates an adjacency matrix for an undirected graph.
     * <p>
     * Sets entry at {@code [source][destination]} and {@code [destination][source]} to 1.
     * </p>
     *
     * @param array       the 2D array representing the adjacency matrix
     * @param source      the starting vertex of the edge
     * @param destination the ending vertex of the edge
     */
    private static void populateMatrix(int[][] array, int source, int destination) {
        array[source][destination] = 1;
        array[destination][source] = 1;
    }

    /**
     * Populates an adjacency list for an undirected graph.
     * <p>
     * Adds {@code destination} to the list of {@code source} and vice-versa.
     * </p>
     *
     * @param adjacencyList the nested ArrayList representing the graph connections
     * @param source        the starting vertex of the edge
     * @param destination   the ending vertex of the edge
     */
    private static void populateAdjacencyList(ArrayList<ArrayList<Integer>> adjacencyList, int source, int destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    /**
     * Main entry point for the program. Reads graph parameters, initializes
     * matrix and list structures, and prints their contents.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.print("Enter no.of vertices for a Graph: ");
        int vertex = sc.nextInt();

        // Matrix uses 1-based indexing (size vertex + 1)
        int[][] array = new int[vertex + 1][vertex + 1];

        // Initialize the Adjacency List for 1-based indexing
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i <= vertex; i++) {
            adjacencyList.add(new ArrayList<Integer>());
        }

        int count = 0;
        System.out.print("Enter source Node: ");
        int source = sc.nextInt();
        System.out.print("Enter Destination Node: ");
        int destination = sc.nextInt();

        int power = (int) Math.pow(2, (array.length - 1));

        while (source != -1 && destination != -1 && count < power) {
            populateMatrix(array, source, destination);
            populateAdjacencyList(adjacencyList, source, destination);

            System.out.print("Enter source Node: ");
            source = sc.nextInt();
            System.out.print("Enter Destination Node: ");
            destination = sc.nextInt();
            count++;
        }

        System.out.println("\nPrinting Adjacency Matrix:");
        System.out.println(Arrays.deepToString(array));

        System.out.println("\nPrinting Adjacency List:");
        System.out.println(adjacencyList);
    }
}