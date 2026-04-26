package src;

public class Palindrome {

    public void palindrome(int n) {
        int result = 0;
        for (int i = n; i > 0; i /= 10) {
            int mod = i % 10;
            result = result * 10 + mod;
        }
        if (n == result)
            System.out.printf("Number %d is a palindrome", n);
        else System.out.printf("Number %d  is not a palindrome", n);
    }

    public static void main(String[] args) {
        Palindrome test = new Palindrome();
        long startTime = System.nanoTime();
        test.palindrome(404);
        long endTime = System.nanoTime();
        System.out.print(" -> Time taken for execute function is : " + (endTime - startTime) +
                " ns");
    }
}
