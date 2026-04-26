package src;
public class Trailing_Zeroes {

    /*     Q: Find no. of trailing 0's in n! .
           Hint :
            0 Occurs due to 2 * 5 = 10 , so count total no of 5 . that will give trailing zeroes.
            REMEMBER : at 25 we get TWO 5's similarly at 125 , 625 ,  and so on , therefore we
            are checking loop from i=5 to <=n at interval of i*5

    */
    public int trailingZeroes(int n) {
        int result = 0;
        System.out.print("Trailing Zeroes in " + n + " ! is : ");
        for (int i = 5; i <= n; i *= 5) {
            result += n / i;
        }

        return result;
    }

    public static void main(String[] args) {

        Trailing_Zeroes test = new Trailing_Zeroes();
        long startTime = System.nanoTime();
        int result = test.trailingZeroes(87);
        long endTime = System.nanoTime();
        System.out.print(result + " -> Time Taken  to compute is  : " + (endTime - startTime) + " ns");

    }
}