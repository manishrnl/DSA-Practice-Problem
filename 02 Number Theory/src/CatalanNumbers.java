package src;

import java.math.BigInteger;

public class CatalanNumbers {

    /**
     * Calculates the n-th Catalan Number.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public long findCatalanViaLong(int n) {
        long res = 1;

        // Using the formula: C(n) = C(n-1) * { (4n - 2) / (n + 1) }
        for (int i = 1; i <= n; i++) {
            // Multiply by (4i - 2)
            res = res * (4L * i - 2);
            // Divide by (i + 1)
            res = res / (i + 1);
        }

        return res;
    }

    public BigInteger findCatalanViaBigInteger(int n) {
        BigInteger result = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {

            result = result.multiply(BigInteger.valueOf(4L * i - 2));
            result = result.divide(BigInteger.valueOf(i + 1));
        }
        return result;

    }

    public static void main(String[] args) {
        CatalanNumbers cn = new CatalanNumbers();
        int ng = 14 , nb=4344;

        long start = System.nanoTime();
        long result = cn.findCatalanViaLong(ng);
        long end = System.nanoTime();

        System.out.println("The " + ng + "-th Catalan Number is: " + result);
        System.out.println("Length: " + String.valueOf(result).length() + " digits");
        System.out.println("Time: " + (end - start) + " ns");



        long start1 = System.nanoTime();
        BigInteger result1 = cn.findCatalanViaBigInteger(nb);
        long end1 = System.nanoTime();

        System.out.println("The " + nb + "-th Catalan Number is: " + result1);
        System.out.println("Length: " + result1.toString().length() + " digits");
        System.out.println("Time: " + (end1 - start1) + " ns");


    }
}