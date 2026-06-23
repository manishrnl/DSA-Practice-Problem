import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class HashSet_Intro {

    public static void main(String[] args) throws IOException {

        HashSet<Integer> set = new HashSet<>();
        set.add(5);
        set.add(15);
        set.add(15);
        set.add(35);
        set.add(45);
        set.add(55);
        set.add(65);
        set.add(75);

        System.out.println("Hash Set are :" + set);
        if (set.contains(10))
            System.out.println("HashSet contains value 10");
        else
            System.out.println("Value 10 is not present inside HashSet");
        set.remove(10);
        System.out.println("Is HashSet empty : " + set.isEmpty());
        System.out.println("Size of HashSet is : " + set.size());
        set.clear();
        System.out.println("Now HashSet have data after performing clear operation: " + set);

    }
}