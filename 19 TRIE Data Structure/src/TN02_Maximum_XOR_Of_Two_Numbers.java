/**
 * <b>Maximum XOR of Two Numbers in an Array — Trie (Bit Trie)</b>
 *
 * <p><b>PROBLEM:</b> Given an array of non-negative integers, find the maximum
 * possible value of {@code arr[i] XOR arr[j]} for any pair (i, j) in the array.</p>
 *
 * <p><b>NAIVE APPROACH:</b> Check every pair → O(n²). Too slow for large n.</p>
 *
 * <p><b>APPROACH: Bit Trie (Binary Trie)</b></p>
 * <ol>
 *   <li>Build a Trie where each node has only 2 children: bit 0 and bit 1</li>
 *   <li>Insert every number into the Trie as its 32-bit binary representation
 *       (most significant bit first)</li>
 *   <li>For each number, walk the Trie trying to go the <b>opposite</b> bit at
 *       every level (opposite bit maximizes XOR at that position)</li>
 *   <li>If opposite bit path doesn't exist, fall back to the same bit (best available)</li>
 *   <li>Track the maximum XOR found across all numbers</li>
 * </ol>
 * <p><b>Why it works:</b> XOR is maximized bit-by-bit from the most significant bit
 * down — a mismatched bit (0 XOR 1) contributes more to the result than a matched
 * bit at the same position. Greedily choosing the opposite bit at each level,
 * when available, is always at least as good as choosing the same bit.</p>
 *
 * <p><b>COMPLEXITY:</b></p>
 * <ul>
 *   <li>Time: O(n × 32) = O(n) — insert and query each number bit-by-bit (32 bits)</li>
 *   <li>Space: O(n × 32) — worst case, all numbers share no common prefix</li>
 * </ul>
 *
 * <p><b>EXAMPLE:</b></p>
 * <pre>
 * arr = [3, 10, 5, 25, 2, 8]
 *
 * Binary (using 5 bits for readability):
 *    3 = 00011
 *   10 = 01010
 *    5 = 00101
 *   25 = 11001
 *    2 = 00010
 *    8 = 01000
 */
public class TN02_Maximum_XOR_Of_Two_Numbers {

    public static final int BIT_LENGTH = 32;

    /**
     * <p><b>TrieNode Structure (Bit Trie):</b></p>
     * <pre>
     * TrieNode
     * └─ children[2]   → children[0] = path for bit '0'
     *                    children[1] = path for bit '1'
     *                    [null if that bit-path doesn't exist yet]
     *
     * Example: inserting 5 = 00000000000000000000000000000101 (32-bit)
     * Path from root: 0 → 0 → 0 → ... → 1 → 0 → 1
     *                 (29 leading zero-bits, then 1, 0, 1)
     * </pre>
     */
    static class TrieNode {
        TrieNode[] children = new TrieNode[2];
    }

    /**
     * <p><b>Insert a number into the Bit Trie, one bit at a time (MSB first).</b></p>
     *
     * <p><b>ALGORITHM WALKTHROUGH (using 5 bits for readability):</b></p>
     * <pre>
     * Insert num = 5 (binary: 00101)
     *
     * STEP 1: Start at root
     *   currentNode = root
     *
     * STEP 2: Process bits from most significant to least significant
     *   bit 4 (value 0): children[0] == null? YES → create node, move in
     *   bit 3 (value 0): children[0] == null? YES → create node, move in
     *   bit 2 (value 1): children[1] == null? YES → create node, move in
     *   bit 1 (value 0): children[0] == null? YES → create node, move in
     *   bit 0 (value 1): children[1] == null? YES → create node, move in
     *
     * Resulting path: root → 0 → 0 → 1 → 0 → 1
     *
     * Insert num = 3 (binary: 00011) next:
     *   bit 4 (0): children[0] exists → reuse, move in
     *   bit 3 (0): children[0] exists → reuse, move in
     *   bit 2 (0): children[0] == null? YES → create node, move in
     *   bit 1 (1): children[1] == null? YES → create node, move in
     *   bit 0 (1): children[1] == null? YES → create node, move in
     *
     * Shared prefix "00" reused; new branch created for remaining bits "011"
     * </pre>
     *
     * @param root Trie root
     * @param num  number to insert
     */
    static void insert(TrieNode root, int num) {
        TrieNode currentNode = root;

        // STEP 1: Walk bits from most significant (31) to least significant (0)
        for (int i = BIT_LENGTH - 1; i >= 0; i--) {
            int bit = (num >> i) & 1;

            // STEP 2: Create path for this bit if it doesn't exist
            if (currentNode.children[bit] == null) {
                currentNode.children[bit] = new TrieNode();
            }
            currentNode = currentNode.children[bit];
        }
    }

    /**
     * <p><b>Find the number already in the Trie that gives maximum XOR with {@code num}.</b></p>
     *
     * <p><b>ALGORITHM WALKTHROUGH (using 5 bits for readability):</b></p>
     * <pre>
     * Trie contains: 5 (00101), 3 (00011)
     * Query num = 25 (11001)
     *
     * At each bit, prefer the OPPOSITE bit (maximizes XOR contribution at that position):
     *
     * bit 4: num's bit = 1 → want opposite = 0
     *   children[0] exists? YES → go there, xor bit = 1 (mismatched, good)
     *
     * bit 3: num's bit = 1 → want opposite = 0
     *   children[0] exists? YES → go there, xor bit = 1
     *
     * bit 2: num's bit = 0 → want opposite = 1
     *   children[1] exists? YES → go there, xor bit = 1
     *
     * bit 1: num's bit = 0 → want opposite = 1
     *   children[1] exists? NO (only 0 exists here from "5") → fall back to children[0]
     *   xor bit = 0 (matched, no gain here)
     *
     * bit 0: num's bit = 1 → want opposite = 0
     *   children[0] exists? NO (only 1 exists) → fall back to children[1]
     *   xor bit = 0
     *
     * Result bits: 1 1 1 0 0 = 11100 = 28
     * This matches 25 XOR 5 = 28 (the best available partner)
     * </pre>
     *
     * @param root Trie root containing previously inserted numbers
     * @param num  number to find the best XOR partner for
     * @return maximum XOR value achievable between {@code num} and any number in the Trie
     */
    static int findMaxXorPartner(TrieNode root, int num) {
        TrieNode currentNode = root;
        int maxXor = 0;

        // STEP 1: Walk bits from most significant to least significant
        for (int i = BIT_LENGTH - 1; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int oppositeBit = 1 - bit;

            // STEP 2: Prefer opposite bit (maximizes XOR); fall back if unavailable
            if (currentNode.children[oppositeBit] != null) {
                maxXor |= (1 << i);           // this bit position contributes to XOR
                currentNode = currentNode.children[oppositeBit];
            } else {
                currentNode = currentNode.children[bit];
            }
        }

        return maxXor;
    }

    /**
     * <p><b>Find the maximum XOR of any two numbers in the array.</b></p>
     *
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     * <pre>
     * arr = [3, 10, 5, 25, 2, 8]
     *
     * STEP 1: Seed Trie with the first number (nothing to compare against yet)
     *   insert(3)
     *
     * STEP 2: For each remaining number, query best partner already in Trie, then insert it
     *
     * │ num │ Trie contains before query  │ Best partner   │ XOR value │ Running max │
     * ├─────┼─────────────────────────────┼────────────────┼───────────┼─────────────┤
     * │ 10  │ {3}                         │  3             │    9      │     9       │
     * │  5  │ {3, 10}                     │ 10             │   15      │    15       │
     * │ 25  │ {3, 10, 5}                  │  5             │   28      │    28       │
     * │  2  │ {3, 10, 5, 25}              │ 25             │   27      │    28       │
     * │  8  │ {3, 10, 5, 25, 2}           │  3             │   11      │    28       │
     *
     * STEP 3: Return running maximum
     *   Maximum XOR = 28  (from pair 5 XOR 25)
     * </pre>
     *
     * @param arr array of non-negative integers
     * @return maximum XOR value obtainable from any pair in the array
     */
    static int findMaximumXOR(int[] arr) {
        TrieNode root = new TrieNode();
        int maxXor = 0;

        // STEP 1: Seed the Trie with the first number (nothing to query against yet)
        insert(root, arr[0]);

        // STEP 2: For every remaining number, query best partner, then insert it
        for (int i = 1; i < arr.length; i++) {
            maxXor = Math.max(maxXor, findMaxXorPartner(root, arr[i]));
            insert(root, arr[i]);
        }

        return maxXor;
    }

    /**
     * Test driver. Input: arr = [3, 10, 5, 25, 2, 8]
     * Expected: Maximum XOR = 28 (from pair 5 XOR 25)
     */
    public static void main(String[] args) {
        int[] arr = {3, 10, 5, 25, 2, 8};
        System.out.print("\nMaximum XOR of two numbers in the array is : " + findMaximumXOR(arr));
    }
}