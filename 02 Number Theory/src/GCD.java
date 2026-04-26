package src;

public class GCD {
    public long greatestCommonDivision(int num1, int num2) {

        if (num2 == 0) return num1;
        if (num1 == 0) return num2;
        return greatestCommonDivision(num1, num1 % num2);
    }

    public static void main(String[] args) {
        GCD gcd = new GCD();
        System.out.print("GCD of numbers are : ");
        long startTime = System.nanoTime();
        long result = gcd.greatestCommonDivision(0, 47);
        long endTime = System.nanoTime();
        System.out.println(result + " -> Execution time for function :" + (endTime - startTime) + " ns");
    }
}
