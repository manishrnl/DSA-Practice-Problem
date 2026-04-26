# Catalan Number 

Catalan numbers are a sequence of natural numbers that occur in various counting problems, 
particularly those involving recursive structures. If i am into competitive programming 
or building complex data structures (like those in mine Airbnb or LinkedIn clones), I'll see 
these everywhere.

### What are Catalan Numbers?
The $n$-th Catalan number, denoted as $C_n$, counts things like:
* **Valid Parentheses:** The number of ways to arrange $n$ pairs of `()` so they are correctly nested.
* **Binary Search Trees:** The number of unique BSTs that can be formed with $n$ nodes.
* **Mountain Ranges:** Ways to draw $n$ upstrokes and $n$ downstrokes that never stay below the horizon.

The sequence starts: **1, 1, 2, 5, 14, 42, 132...**

---

### The Mathematical Formula
While there is a recursive formula, the most efficient way to calculate $C_n$ for a specific $n$ is using the **Binomial Coefficient**:

$$C_n = \frac{1}{n+1} \binom{2n}{n}$$



---

### Optimized Code (Linear Time)
This calculates $C_n$ in **$O(n)$ time** and **$O(1)$ space**.

We use the iterative property: $C_{n+1} = \frac{2(2n+1)}{n+2} C_n$.

```java
    
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
            int ng = 14 , nb=4444;
    
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

```

---

### Quick Logic Check
If you were asked, "How many ways can I arrange 3 pairs of parentheses?", you'd calculate $C_3$:
1.  $i=1: res = 1 \times (4(1)-2) / (1+1) = 2/2 = 1$
2.  $i=2: res = 1 \times (4(2)-2) / (2+1) = 6/3 = 2$
3.  $i=3: res = 2 \times (4(3)-2) / (3+1) = 20/4 = 5$
    **Answer: 5** (`((()))`, `(()())`, `(())()`, `()(())`, `()()()`)

# The Pigeonhole Principle (PHP) is a simple yet powerful concept in combinatorics. It states that:

> **If you have $n$ items (pigeonholes) and $m$ items (pigeons) to put into them, and $m > n$, then at least one pigeonhole must contain more than one pigeon.**

> **Imagine you have **3 pigeonholes** but **4 pigeons**. No matter how hard you try to spread  them out, one hole will end up with at least 2 pigeons.**



### Common Real-World Examples
* **Birthdays:** In a group of 367 people, at least two *must* share a birthday (since there are only 366 possible birthdays, including Feb 29).
* **Hair:** There are more people in New York City than there are hairs on a human head. Therefore, at least two people in NYC have the exact same number of hairs.
* **Arrays:** If you have an array of size $N$ containing numbers from $1$ to $N-1$, at least one number must be a duplicate.

---

### Coding Application: Finding a Duplicate
One of the most common ways we use PHP in coding is to find a duplicate in a constrained list.

**Problem:** You have an array of $n+1$ integers where each integer is between $1$ and $n$ inclusive. Find the duplicate.

#### The "Fast" Way (Bit Manipulation / Frequency Array)
While we could use nested loops ($O(n^2)$), the Pigeonhole Principle tells us a duplicate **must** exist, so we can use a frequency array (the "holes") to catch it in $O(n)$ time.

```java

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    
    public class PigeonholeExample {
        public static int findDuplicate(int[] number) {
            boolean[] holes = new boolean[number.length];
    
            for (int num : number) {
                // If the 'hole' is already occupied, we found our duplicate!
                if (holes[num]) {
                    return num;
                }
                // Otherwise, put the 'pigeon' in the hole
                holes[num] = true;
            }
            return -1; // Should not happen based on PHP
        }
    
        public static void totalDuplicate(int[] number) {
            List<Integer> totalDuplicates = new ArrayList<>();
            boolean[] holes = new boolean[number.length];
    
            for (int value : number) {
                if (holes[value])
                    totalDuplicates.add(value);
                holes[value] = true;
            }
            System.out.print("Duplicates value are : ");
            for (int value : totalDuplicates)
                System.out.print(value + " ");
        }
        public static void main(String[] args) {
            int[] pigeons = {1, 2, 3, 4, 2,3,4}; // 5 items, values 1-4
            System.out.println("Very 1st  duplicate pigeon is: " + findDuplicate(pigeons));
            totalDuplicate(pigeons);
    
        }
    }

```

### Performance Breakdown
* **Time Complexity:** $O(n)$. We only walk through the list once.
* **Space Complexity:** $O(n)$. We create a "hole" for every possible value.





# Inclusion-Exclusion is a counting technique used to find the size of the union of multiple sets. It helps to avoid the "double-counting" trap when sets overlap.

### The "Party Guest" Rule
Imagine you are hosting a party and checking your guest list:
1.  **Step 1:** You count everyone who likes **Pizza**.
2.  **Step 2:** You count everyone who likes **Burgers**.
3.  **Step 3:** You realize the people who like **BOTH** were counted twice! (Once in the Pizza group and once in the Burger group).
4.  **The Fix:** You subtract the "Both" group once to get the true number of unique guests.

**That is Inclusion-Exclusion.**

---

### The Easiest Implementation (Recursive)
Instead of using confusing bitmasks, we can use a simple recursive "Decision Tree." For every number (like 2, 3, or 5), we have two choices: **Use it** or **Skip it**.



```java

    public class InclusionExclusion {
    
        /**
         * @param index Which prime we are currently looking at
         * @param currentDivisor The product of the primes we've chosen so far
         * @param limit The number we are checking up to (e.g., 100)
         * @param primes Our list of divisors (e.g., {2, 3, 5})
         */
        public long count(int index, long currentDivisor, long limit, int[] primes) {
            // Base Case: If we've looked at all primes, stop.
            if (index == primes.length) return 0;
    
            // 1. Calculate for the current prime
            long combinedDivisor = currentDivisor * primes[index];
    
            // This is how many numbers are divisible by our "chosen" group
            long countWithThisPrime = limit / combinedDivisor;
    
            // 2. The Magic Logic:
            // We add the count of the current group,
            // then subtract the count of the NEXT group to fix the overlaps.
            return countWithThisPrime
                    + count(index + 1, currentDivisor, limit, primes) // Option A: Skip this prime
                    - count(index + 1, combinedDivisor, limit, primes); // Option B: Include this prime
        }
    
        public static void main(String[] args) {
            InclusionExclusion obj = new InclusionExclusion();
            int[] primes = {2, 3, 5};
            // We call it starting with index 0 and a starting divisor of 1
            System.out.println("Result: " + obj.count(0, 1, 100, primes));
        }
    }
    
```

---

### Why this is "Easier":
* **No Bitwise Operators:** You don't need to worry about `<<` or `>>`.
* **Human Logic:** The code literally says: "Give me the count for this prime, then go do the same for the rest, but don't forget to subtract the overlaps."
* **Less Code:** It’s just a few lines of logic.

### When to use this?
Use this when you have a small set of numbers (like 2, 3, 5, 7) and you want to find how many numbers in a huge range (like 1 to 1,000,000,000) are divisible by **any** of them.

