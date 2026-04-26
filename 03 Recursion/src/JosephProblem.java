
package src;
public class JosephProblem {
    /**
     * Problem: The Josephus Survivor
     * * Logic:
     * This uses a Recursive Relation. If we know the survivor position
     * for (n-1) people, we can find the position for n people by
     * shifting the index by 'k' and taking the modulo 'n'.
     * * @param n Total number of people
     * @param k The step rate (every k-th person is eliminated)
     * @return The 0-indexed position of the survivor
     */
    public static long josephProblem(int n, int k) {
        // Base Case: If there is only 1 person, they are the survivor (at index 0)
        if (n == 1) {
            return 0;
        }

        /* * Recursive Step:
         * 1. Solve the problem for n-1 people.
         * 2. Add k to shift the starting point.
         * 3. Use % n to wrap around the circle.
         */
        return (josephProblem(n - 1, k) + k) % n;
    }

    public static void main(String[] args) {
        int n = 5;
        int k = 3;

        long survivorPosition = josephProblem(n, k);

        System.out.println("The survivor in a circle of " + n +
                " people with a step of " + k +
                " is at index: " + survivorPosition);
    }
}