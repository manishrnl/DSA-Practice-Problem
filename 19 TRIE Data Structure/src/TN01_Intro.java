/**
 * <b>Trie (Prefix Tree) — Introduction</b>
 *
 * <p><b>PROBLEM:</b> Efficiently store and search strings. Support prefix-based queries
 * and word lookups in minimal time.</p>
 *
 * <p><b>DATA STRUCTURE: Trie (Prefix Tree)</b></p>
 * <ul>
 *   <li>Each node stores 26 pointers (for a-z) to child nodes</li>
 *   <li>Path from root to a node represents a prefix/word</li>
 *   <li>Flag {@code isEndOfWord} marks whether a node completes a valid word</li>
 * </ul>
 *
 * <p><b>APPROACH:</b></p>
 * <ul>
 *   <li><b>Insert:</b> Walk character-by-character, creating nodes as needed; mark last node as word-end</li>
 *   <li><b>Search:</b> Walk character-by-character; if any edge missing, word doesn't exist;
 *       if path complete and node marked, word exists</li>
 * </ul>
 *
 * <p><b>COMPLEXITY:</b></p>
 * <ul>
 *   <li>Insert: O(m) where m = word length</li>
 *   <li>Search: O(m) where m = word length</li>
 *   <li>Space: O(ALPHABET_SIZE × N) where N = total nodes created</li>
 * </ul>
 *
 * <p><b>EXAMPLE STRUCTURE:</b></p>
 * <pre>
 * Insert: "cat", "car", "dog"
 *
 *           root
 *          /    \
 *         c      d
 *         |      |
 *         a      o
 *        / \     |
 *       t   r    g
 *       ✓   ✓    ✓
 *
 * ✓ = isEndOfWord = true
 *
 * Search "cat" → root→c→a→t(✓) = FOUND
 * Search "ca"  → root→c→a(✗) = NOT FOUND (not marked as word-end)
 * Search "dog" → root→d→o→g(✓) = FOUND
 * Search "do"  → root→d→o(✗) = NOT FOUND
 * </pre>
 *

 * @see TrieNode
 * @see #insert(TrieNode, String)
 * @see #search(TrieNode, String)
 */
public class TN01_Intro {
    public static int ALPHABET_SIZE = 26;

    /**
     * Test driver. Builds a Trie with words {cat, car, card, dog, do} and
     * runs searches covering: exact match, prefix-only, and non-existent word.
     *
     * Expected output:
     *   cat  -> true
     *   ca   -> false  (prefix, not a complete word)
     *   card -> true
     *   dog  -> true
     *   do   -> true
     *   dot  -> false  (not inserted)
     */
    public static void main(String[] args) {
        TrieNode root = new TrieNode();

        String[] words = {"cat", "car", "card", "dog", "do"};
        for (String word : words) {
            insert(root, word);
        }

        String[] searchKeys = {"cat", "ca", "card", "dog", "do", "dot"};
        for (String key : searchKeys) {
            System.out.println(key + " -> " + search(root, key));
        }
    }

    /**
     * <p><b>TrieNode Structure:</b></p>
     * <pre>
     * TrieNode
     * ├─ children[26]   → Pointers to 26 child nodes (a-z)
     * │                    [null if edge doesn't exist]
     * └─ isEndOfWord    → true if this node completes a valid word
     *
     * Example node state after inserting "cat":
     * Root node:
     *   children[2] → points to 'c' node (index 2 = 'c' - 'a')
     *   all others = null
     *
     * 'c' node:
     *   children[0] → points to 'a' node (index 0 = 'a' - 'a')
     *   all others = null
     *   isEndOfWord = false (no word ends at 'c')
     *
     * 'a' node:
     *   children[19] → points to 't' node (index 19 = 't' - 'a')
     *   all others = null
     *   isEndOfWord = false (no word ends at 'ca')
     *
     * 't' node:
     *   children[all] = null
     *   isEndOfWord = true (word "cat" ends here)
     * </pre>
     */
    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];
        boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    /**
     * <p><b>Insert a word into the Trie.</b></p>
     *
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     * <pre>
     * Input: root (empty Trie), key = "cat"
     *
     * STEP 1: Start at root
     *   currentNode = root
     *
     * STEP 2: Process each character
     *   i=0, ch='c' (index 2):
     *     children[2] == null? YES → create new TrieNode
     *     currentNode = children[2] (move to 'c' node)
     *     isEndOfWord = false
     *
     *   i=1, ch='a' (index 0):
     *     children[0] == null? YES → create new TrieNode
     *     currentNode = children[0] (move to 'a' node)
     *     isEndOfWord = false
     *
     *   i=2, ch='t' (index 19):
     *     children[19] == null? YES → create new TrieNode
     *     currentNode = children[19] (move to 't' node)
     *     isEndOfWord = false
     *
     * STEP 3: Mark end of word
     *   currentNode.isEndOfWord = true (at 't' node)
     *
     * Result:
     *      root → 'c' → 'a' → 't' (✓ isEndOfWord=true)
     *
     * Tree state:
     *           root
     *           |
     *           c
     *           |
     *           a
     *           |
     *           t(✓)
     * </pre>
     *
     * <p><b>Inserting "car" into same Trie:</b></p>
     * <pre>
     * i=0, ch='c': children[2] exists → skip creation, move to 'c' node
     * i=1, ch='a': children[0] exists → skip creation, move to 'a' node
     * i=2, ch='r' (index 17): children[17] == null? YES → create new node at 'r'
     *                          currentNode = children[17]
     *                          isEndOfWord = false
     * Mark end: currentNode.isEndOfWord = true (at 'r' node)
     *
     * Result:
     *           root
     *           |
     *           c
     *           |
     *           a
     *          / \
     *         t(✓) r(✓)
     *
     * Nodes reused: root → c → a (shared prefix "ca")
     * New node created: r
     * </pre>
     *
     * @param root root node of Trie
     * @param key word to insert
     */
    static void insert(TrieNode root, String key) {
        TrieNode currentNode = root;

        // STEP 1: Traverse/create path for each character
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);

            // If edge doesn't exist, create it
            if (currentNode.children[ch - 'a'] == null) {
                TrieNode newNode = new TrieNode();
                currentNode.children[ch - 'a'] = newNode;
            }

            // Move to child node
            currentNode = currentNode.children[ch - 'a'];
            currentNode.isEndOfWord = false;
        }

        // STEP 2: Mark last node as end of word
        currentNode.isEndOfWord = true;
    }

    /**
     * <p><b>Search for a word in the Trie.</b></p>
     *
     * <p><b>ALGORITHM WALKTHROUGH:</b></p>
     * <pre>
     * Trie contains: "cat", "car", "dog"
     *
     * SEARCH 1: key = "cat"
     *   currentNode = root
     *
     *   i=0, ch='c':
     *     children[2] == null? NO (exists)
     *     currentNode = children[2]
     *
     *   i=1, ch='a':
     *     children[0] == null? NO (exists)
     *     currentNode = children[0]
     *
     *   i=2, ch='t':
     *     children[19] == null? NO (exists)
     *     currentNode = children[19]
     *
     *   End of key. Check: currentNode.isEndOfWord = true?
     *   Result: TRUE ✓ (word exists)
     *
     * SEARCH 2: key = "ca"
     *   currentNode = root
     *   i=0, ch='c': move to 'c' node
     *   i=1, ch='a': move to 'a' node
     *
     *   End of key. Check: currentNode.isEndOfWord = true?
     *   Result: FALSE ✗ (prefix "ca" exists, but not a complete word)
     *
     * SEARCH 3: key = "card"
     *   currentNode = root
     *   i=0, ch='c': move to 'c' node
     *   i=1, ch='a': move to 'a' node
     *   i=2, ch='r': move to 'r' node
     *   i=3, ch='d':
     *     children[3] == null? YES (no 'd' child)
     *     return FALSE ✗ (word doesn't exist)
     *
     * SEARCH 4: key = "dog"
     *   currentNode = root
     *   i=0, ch='d': move to 'd' node
     *   i=1, ch='o': move to 'o' node
     *   i=2, ch='g': move to 'g' node
     *   End of key. Check: currentNode.isEndOfWord = true?
     *   Result: TRUE ✓ (word exists)
     * </pre>
     *
     * <p><b>Search Result Table:</b></p>
     * <pre>
     * │ Search Key │ Path Exists? │ isEndOfWord │ Result  │
     * ├────────────┼──────────────┼─────────────┼─────────┤
     * │ "cat"      │     YES      │    true     │ FOUND ✓ │
     * │ "ca"       │     YES      │    false    │ NOT ✗   │
     * │ "card"     │      NO      │      -      │ NOT ✗   │
     * │ "dog"      │     YES      │    true     │ FOUND ✓ │
     * │ "do"       │     YES      │    false    │ NOT ✗   │
     * </pre>
     *
     * @param root root node of Trie
     * @param key word to search
     * @return true if word exists in Trie, false otherwise
     */
    public static boolean search(TrieNode root, String key) {
        TrieNode currentNode = root;

        // STEP 1: Traverse path for each character
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);

            // If any edge missing, word doesn't exist
            if (currentNode.children[ch - 'a'] == null) {
                return false;
            }

            // Move to child node
            currentNode = currentNode.children[ch - 'a'];
        }

        // STEP 2: Check if this node marks end of a word
        return currentNode.isEndOfWord;
    }
}