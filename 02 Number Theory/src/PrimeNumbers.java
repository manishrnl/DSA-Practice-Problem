package src;
import java.util.Arrays;

public class PrimeNumbers {
    public boolean[] sieveOfEratoSthenes(int n) {

        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;//Discarding index 0,1 as we are interested in no.
        // from 2 to n . we keep position and index same for not getting confused ,
        // therefore initialised isPrime =new boolean[n+1]

        for (int i = 2; i * i <= n; i++) { // Iterate till squareRoot(n) times only as after
            // that all factors is checked
            for (int j = 2 * i; j <= n; j += i) { // Marking multiples of j  to false so we
                // get true for only prime numbers once loops gets over
                isPrime[j] = false;
            }
        }
        return isPrime;
    }

    public static void main(String[] args) {
        PrimeNumbers p = new PrimeNumbers();
        long startTime = System.nanoTime();
        boolean[] result = p.sieveOfEratoSthenes(100);
        long endTime = System.nanoTime();
        System.out.print("Prime Numbers are : ");

        for (int i = 0; i < result.length; i++)
            if (result[i]) System.out.print(i + " ");
        System.out.print(" -> Time taken to execute function is : " + (endTime - startTime) +
                " ns");
    }
}
