package src;

import java.math.BigInteger;

public class Factorial {
    public BigInteger findFactorialViaBigInteger(int n) {

        if (n <= 1) return BigInteger.ONE;

        return BigInteger.valueOf(n).multiply(findFactorialViaBigInteger(n - 1));

    }

    public long findFactorialGenerally(int n) {
        long factorial = 1;
        if (n <= 1) return 1;
        return (n * findFactorialGenerally(n - 1));


    }


    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        long startTime = System.nanoTime();
        BigInteger result = factorial.findFactorialViaBigInteger(12);
        long endTime = System.nanoTime();
        int size = result.toString().length();

        System.out.printf(result + "\n Size of result is %d Digits long and  -> Execution " +
                "time is %d ns ", size, (endTime - startTime));


        long startTime1 = System.nanoTime();
        long result1 = factorial.findFactorialGenerally(12);
        long endTime1 = System.nanoTime();
        long size1 = String.valueOf(result1).length();

        System.out.printf("\n Results are " + result1 + "\n Size of result is %d Digits long" +
                " and  -> Execution " +
                "time is %d ns ", size1, (endTime1 - startTime1));
    }
}
