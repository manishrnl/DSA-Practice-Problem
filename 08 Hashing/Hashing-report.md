# HashMap and Hashing Detailed Report

## 1. What Is Hashing?

Hashing is a technique that converts input data into a fixed-size integer value called a hash code. A hash table uses that hash code to decide where the data should be stored.

In Java, `HashMap` uses hashing to store key-value pairs efficiently.

```java
HashMap<String, Integer> marks = new HashMap<>();
marks.put("Ravi", 95);
marks.put("Aman", 88);
marks.put("Neha", 91);
```

The keys are `"Ravi"`, `"Aman"`, and `"Neha"`. The values are `95`, `88`, and `91`. Instead of searching every key one by one, `HashMap` calculates a bucket index using the key's hash code.

The goal is simple:

- Insert data quickly.
- Search data quickly.
- Delete data quickly.
- Avoid scanning the whole collection.

For most real use cases, `HashMap` gives average `O(1)` time for `put`, `get`, `containsKey`, and `remove`.

## 2. What Is a HashMap?

`HashMap<K, V>` is a Java collection that stores data as key-value pairs.

```java
HashMap<Key, Value>
```

Example:

```java
HashMap<String, Integer> age = new HashMap<>();
age.put("Rahul", 21);
age.put("Priya", 22);

System.out.println(age.get("Rahul")); // 21
```

Here:

- `"Rahul"` is the key.
- `21` is the value.
- The key must be unique.
- The value can be duplicated.

If the same key is inserted again, the old value is replaced.

```java
age.put("Rahul", 21);
age.put("Rahul", 25);

System.out.println(age.get("Rahul")); // 25
```

## 3. Why HashMap Is Useful

Suppose we store names and marks in an array:

```java
String[] names = {"Aman", "Ravi", "Neha", "Priya"};
int[] marks = {88, 95, 91, 84};
```

To find Ravi's marks, we may need to search each name one by one.

That takes `O(n)` time.

With `HashMap`:

```java
HashMap<String, Integer> marks = new HashMap<>();
marks.put("Aman", 88);
marks.put("Ravi", 95);
marks.put("Neha", 91);
marks.put("Priya", 84);

System.out.println(marks.get("Ravi")); // 95
```

The lookup is average `O(1)` because HashMap calculates where `"Ravi"` should be stored.

## 4. Internal Structure of HashMap

Internally, Java's `HashMap` uses an array of buckets.

Each bucket can store:

- Nothing.
- One node.
- A linked list of nodes.
- A tree of nodes when too many collisions happen.

Simplified internal structure:

```text
HashMap
  |
  +-- table[]  (array of buckets)
        |
        +-- bucket 0 -> Node
        +-- bucket 1 -> null
        +-- bucket 2 -> Node -> Node -> Node
        +-- bucket 3 -> null
        +-- bucket 4 -> TreeNode tree
```

A simplified node looks like this:

```java
static class Node<K, V> {
    final int hash;
    final K key;
    V value;
    Node<K, V> next;
}
```

Each node stores:

- `hash`: hash value of the key.
- `key`: the actual key.
- `value`: the value connected with the key.
- `next`: reference to the next node if multiple keys land in the same bucket.

## 5. How HashMap Stores Data

When we run:

```java
map.put("Aman", 88);
```

HashMap performs these steps:

1. Calls `"Aman".hashCode()`.
2. Spreads the hash bits to reduce poor distribution.
3. Converts the hash into a valid bucket index.
4. Checks the bucket.
5. Stores the key-value pair in that bucket.
6. If another key already exists in that bucket, handles collision.
7. If the map becomes too full, resizes the bucket array.

Flow:

```text
key
 |
 v
key.hashCode()
 |
 v
spread hash bits
 |
 v
index = hash & (capacity - 1)
 |
 v
store Node at table[index]
```

## 6. How Java Hashes Data

Every Java object has a `hashCode()` method because it comes from the `Object` class.

Example:

```java
String name = "Aman";
int hash = name.hashCode();
System.out.println(hash);
```

For strings, Java calculates hash codes using the characters.

Conceptually, string hashing is similar to:

```java
hash = 31 * previousHash + currentCharacter;
```

For example, `"ABC"` is calculated like this:

```text
hash = 0
hash = 31 * 0 + 'A'
hash = 31 * hash + 'B'
hash = 31 * hash + 'C'
```

Java uses `31` because it gives a good distribution for strings and can be optimized efficiently by the JVM.

Important point:

- `hashCode()` does not return the array index directly.
- It returns an integer.
- HashMap converts that integer into a bucket index.

## 7. HashMap's Hash Spreading Function

Java HashMap does not use the raw `hashCode()` directly. It applies an extra mixing step.

Simplified version:

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

Meaning:

- If the key is `null`, hash is `0`.
- Otherwise, Java gets the key's `hashCode()`.
- Then it mixes the higher bits into the lower bits using XOR.

Why this is needed:

HashMap's bucket index depends mostly on lower bits when capacity is a power of two. If many keys have different higher bits but similar lower bits, they may collide. The spreading function reduces that problem.

## 8. How Bucket Index Is Calculated

HashMap capacity is always a power of two:

```text
16, 32, 64, 128, 256, ...
```

The bucket index is calculated like this:

```java
index = hash & (capacity - 1);
```

For default capacity `16`:

```java
index = hash & 15;
```

Because `15` in binary is:

```text
0000 1111
```

This means the final index will always be between `0` and `15`.

Example:

```text
capacity = 16
capacity - 1 = 15

hash        = 10110110
15          = 00001111
----------------------
index       = 00000110 = 6
```

So the key is stored in bucket `6`.

This is faster than using modulo:

```java
index = hash % capacity;
```

Bitwise `&` is very fast, but it works correctly here because capacity is always a power of two.

## 9. Example of Storing Data in Buckets

Suppose capacity is `16`.

```java
map.put("Aman", 88);
map.put("Ravi", 95);
map.put("Neha", 91);
```

Possible internal placement:

```text
table[0]  -> null
table[1]  -> null
table[2]  -> ("Neha", 91)
table[3]  -> null
table[4]  -> ("Aman", 88)
table[5]  -> null
table[6]  -> ("Ravi", 95)
table[7]  -> null
...
table[15] -> null
```

When we call:

```java
map.get("Ravi");
```

HashMap:

1. Computes `"Ravi"` hash.
2. Converts it to an index.
3. Goes directly to that bucket.
4. Compares key.
5. Returns the value.

It does not start searching from bucket `0`.

## 10. What Is a Collision?

A collision happens when two different keys generate the same bucket index.

Example:

```text
hash("Aman") -> index 4
hash("Sita") -> index 4
```

Both keys want to go into bucket `4`.

HashMap must store both because the keys are different.

It stores them in the same bucket using chaining.

```text
table[4] -> ("Aman", 88) -> ("Sita", 90)
```

This linked structure is called a collision chain.

## 11. How HashMap Handles Collisions

When inserting a key into a bucket that already has nodes, HashMap checks each node.

It compares:

1. Hash value.
2. Key equality using `equals()`.

Simplified logic:

```java
if (existingNode.hash == newHash &&
    (existingNode.key == newKey || existingNode.key.equals(newKey))) {
    existingNode.value = newValue;
} else {
    addNewNodeToBucket();
}
```

So:

- Same key means update value.
- Different key but same bucket means collision.
- Collision is handled by adding another node.

## 12. Linked List Collision Example

```java
class BadKey {
    private final String value;

    BadKey(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return 1; // bad: every object has same hash
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BadKey other)) return false;
        return value.equals(other.value);
    }
}
```

Usage:

```java
HashMap<BadKey, Integer> map = new HashMap<>();

map.put(new BadKey("A"), 10);
map.put(new BadKey("B"), 20);
map.put(new BadKey("C"), 30);
```

All keys return the same hash code, so they go to the same bucket.

```text
table[index] -> A -> B -> C
```

Lookup becomes slower because HashMap may need to scan the chain.

## 13. Treeification in Java 8 and Later

If too many nodes collect in the same bucket, Java HashMap can convert the linked list into a red-black tree.

Important thresholds:

| Threshold | Meaning |
|---|---|
| `TREEIFY_THRESHOLD = 8` | Convert bucket list to tree when chain becomes too long |
| `UNTREEIFY_THRESHOLD = 6` | Convert tree back to list if it becomes small again |
| `MIN_TREEIFY_CAPACITY = 64` | Treeification happens only when table capacity is at least 64 |

Why not treeify immediately?

If the table is still small, resizing may distribute the keys into different buckets. So HashMap usually resizes first. Treeification is used when the table is already large enough and collisions are still happening.

Performance:

```text
Linked list bucket search: O(n)
Tree bucket search:        O(log n)
```

This protects HashMap from becoming too slow when many keys collide.

## 14. What Happens During `put()`

Simplified `put()` process:

```text
put(key, value)
 |
 v
calculate hash
 |
 v
if table is empty, initialize table
 |
 v
calculate bucket index
 |
 v
if bucket is empty:
    place new node
else:
    compare with existing nodes
    if same key:
        replace value
    else:
        add new node or tree node
 |
 v
increase size if new key was added
 |
 v
if size > threshold:
    resize table
```

Example:

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("apple", 10);
map.put("banana", 20);
map.put("apple", 30);

System.out.println(map.size());       // 2
System.out.println(map.get("apple")); // 30
```

The second `"apple"` does not create a new entry. It updates the existing value.

## 15. What Happens During `get()`

Simplified `get()` process:

```text
get(key)
 |
 v
calculate hash
 |
 v
calculate bucket index
 |
 v
go to table[index]
 |
 v
if bucket is empty:
    return null
 |
 v
if first node matches:
    return value
 |
 v
if bucket is tree:
    search in tree
else:
    search linked list
 |
 v
return value if found, otherwise null
```

Example:

```java
Integer value = map.get("banana");
```

HashMap does not know whether `"banana"` exists until it checks the correct bucket.

Important:

```java
map.get(key)
```

returns `null` in two cases:

1. Key does not exist.
2. Key exists but its value is actually `null`.

Use `containsKey()` to distinguish these cases.

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("A", null);

System.out.println(map.get("A"));          // null
System.out.println(map.containsKey("A"));  // true
System.out.println(map.containsKey("B"));  // false
```

## 16. What Happens During `remove()`

Simplified `remove()` process:

```text
remove(key)
 |
 v
calculate hash
 |
 v
calculate bucket index
 |
 v
search bucket
 |
 v
if key found:
    unlink node from list or tree
    decrease size
    return removed value
else:
    return null
```

Example:

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("A", 10);
map.put("B", 20);

map.remove("A");

System.out.println(map.containsKey("A")); // false
```

## 17. Load Factor and Threshold

Load factor controls how full the HashMap can become before resizing.

Default values:

```text
initial capacity = 16
load factor      = 0.75
threshold        = capacity * load factor
```

For default HashMap:

```text
threshold = 16 * 0.75 = 12
```

When the 13th entry is inserted, HashMap resizes.

Resize sequence:

```text
capacity 16  -> threshold 12
capacity 32  -> threshold 24
capacity 64  -> threshold 48
capacity 128 -> threshold 96
```

Load factor tradeoff:

| Load Factor | Effect |
|---|---|
| Lower load factor | More memory, fewer collisions, faster lookup |
| Higher load factor | Less memory, more collisions, potentially slower lookup |
| `0.75` | Good default balance |

## 18. How Resizing Works

Resizing means creating a bigger bucket array and moving entries into it.

Example:

```text
old capacity = 16
new capacity = 32
```

HashMap doubles the capacity.

During resize:

1. New array is created.
2. Each existing node is moved to the correct new bucket.
3. Threshold is updated.
4. Old table is replaced by new table.

Important optimization:

When capacity doubles, each entry either:

- Stays at the same index.
- Moves to `oldIndex + oldCapacity`.

Example:

```text
old capacity = 16
old index    = 5

after resize to 32, entry goes to:
5 or 21
```

This depends on one bit in the hash:

```java
(hash & oldCapacity) == 0
```

If true, entry stays at the same index. If false, it moves to `oldIndex + oldCapacity`.

This makes resizing faster than recalculating everything from scratch.

## 19. Why Capacity Is a Power of Two

HashMap keeps capacity as a power of two so index calculation can use:

```java
hash & (capacity - 1)
```

instead of:

```java
hash % capacity
```

Example with capacity `16`:

```text
capacity - 1 = 15
binary       = 00001111
```

The `&` operation gives a number from `0` to `15`.

This is fast and helps resizing because doubling capacity only changes one additional bit in the index calculation.

## 20. `hashCode()` and `equals()` Contract

HashMap correctness depends on `hashCode()` and `equals()`.

Rules:

1. If two objects are equal using `equals()`, they must have the same `hashCode()`.
2. If two objects have the same `hashCode()`, they do not have to be equal.
3. If an object is used as a key, do not mutate fields that affect `hashCode()` or `equals()` while it is inside the map.

Correct example:

```java
class Student {
    private final int id;
    private final String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student other)) return false;
        return id == other.id && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + name.hashCode();
        return result;
    }
}
```

Using it:

```java
HashMap<Student, Integer> map = new HashMap<>();

Student s1 = new Student(1, "Aman");
Student s2 = new Student(1, "Aman");

map.put(s1, 95);

System.out.println(map.get(s2)); // 95
```

Even though `s1` and `s2` are different objects, they are logically equal, so HashMap can find the value.

## 21. Common Mistake: Overriding `equals()` Without `hashCode()`

Wrong example:

```java
class Student {
    int id;

    Student(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student other)) return false;
        return id == other.id;
    }
}
```

Problem:

- `equals()` says two students with the same id are equal.
- But `hashCode()` is not overridden.
- Different objects may get different hash codes.
- HashMap may search the wrong bucket.

Fix:

```java
@Override
public int hashCode() {
    return Integer.hashCode(id);
}
```

## 22. Common Mistake: Mutable Keys

Wrong example:

```java
class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee other)) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
```

Usage:

```java
HashMap<Employee, Integer> map = new HashMap<>();

Employee e = new Employee("Aman");
map.put(e, 100);

e.name = "Ravi";

System.out.println(map.get(e)); // may return null
```

Why?

The key was stored using the hash of `"Aman"`, but after mutation HashMap searches using the hash of `"Ravi"`.

Best practice:

- Use immutable keys.
- Make key fields `final`.
- Do not change key state after insertion.

## 23. Null Keys and Null Values

`HashMap` allows:

- One `null` key.
- Multiple `null` values.

Example:

```java
HashMap<String, Integer> map = new HashMap<>();

map.put(null, 10);
map.put("A", null);
map.put("B", null);

System.out.println(map.get(null)); // 10
```

The `null` key always gets hash `0`, so it is stored in bucket `0`.

## 24. HashMap Iteration Order

HashMap does not guarantee insertion order.

```java
HashMap<String, Integer> map = new HashMap<>();
map.put("one", 1);
map.put("two", 2);
map.put("three", 3);

for (String key : map.keySet()) {
    System.out.println(key);
}
```

Output may not be:

```text
one
two
three
```

If insertion order is needed, use `LinkedHashMap`.

If sorted order is needed, use `TreeMap`.

## 25. Time Complexity

| Operation | Average Case | Worst Case Before Tree | Worst Case With Tree Bin |
|---|---:|---:|---:|
| `put` | `O(1)` | `O(n)` | `O(log n)` |
| `get` | `O(1)` | `O(n)` | `O(log n)` |
| `remove` | `O(1)` | `O(n)` | `O(log n)` |
| `containsKey` | `O(1)` | `O(n)` | `O(log n)` |
| Iteration | `O(capacity + size)` | `O(capacity + size)` | `O(capacity + size)` |

Average case is `O(1)` because keys are expected to be spread evenly across buckets.

Worst case happens when many keys go into the same bucket.

## 26. Space Complexity

HashMap uses:

- Bucket array memory.
- Node object memory for each entry.
- Extra linked list references for collisions.
- Extra tree node fields if a bucket becomes treeified.

Space complexity is `O(n)`.

Because the bucket array may have empty buckets, actual memory usage is more than just the number of entries.

## 27. Good Hash Function Qualities

A good hash function should:

- Return the same hash for the same object state.
- Spread values evenly.
- Use important fields.
- Be fast to calculate.
- Follow the `equals()` contract.

Bad hash function:

```java
@Override
public int hashCode() {
    return 1;
}
```

This puts every key into the same bucket.

Better hash function:

```java
@Override
public int hashCode() {
    int result = Integer.hashCode(id);
    result = 31 * result + name.hashCode();
    return result;
}
```

Modern Java can also use:

```java
@Override
public int hashCode() {
    return Objects.hash(id, name);
}
```

`Objects.hash()` is readable, but manual hashing can be faster in performance-sensitive code because `Objects.hash()` creates an internal array.

## 28. Practical Example: Frequency Map

HashMap is commonly used for counting frequency.

```java
String text = "banana";
HashMap<Character, Integer> freq = new HashMap<>();

for (char ch : text.toCharArray()) {
    freq.put(ch, freq.getOrDefault(ch, 0) + 1);
}

System.out.println(freq);
```

Possible output:

```text
{a=3, b=1, n=2}
```

This is efficient because each character count update is average `O(1)`.

## 29. Practical Example: Two Sum

HashMap is useful when we need fast lookup.

```java
public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> seen = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int need = target - nums[i];

        if (seen.containsKey(need)) {
            return new int[] {seen.get(need), i};
        }

        seen.put(nums[i], i);
    }

    return new int[] {-1, -1};
}
```

Without HashMap, this is usually `O(n^2)`.

With HashMap, this becomes average `O(n)`.

## 30. Choosing Initial Capacity

If you know the expected number of entries, pre-size the HashMap.

Formula:

```text
required capacity = expected entries / load factor
```

Example:

```text
expected entries = 1000
load factor      = 0.75

required capacity = 1000 / 0.75 = 1333.33
```

HashMap will round capacity to a power of two, so use at least `2048`.

Example:

```java
HashMap<String, Integer> map = new HashMap<>(2048);
```

This avoids repeated resizing while inserting many entries.

## 31. HashMap vs HashSet

`HashSet` internally uses a `HashMap`.

Conceptually:

```java
HashSet<E>
```

works like:

```java
HashMap<E, Object>
```

The set element is stored as the key. A dummy object is stored as the value.

Example:

```java
HashSet<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");

System.out.println(set.size()); // 2
```

The duplicate `"A"` is ignored because the key already exists in the backing map.

## 32. HashMap vs LinkedHashMap vs TreeMap

| Feature | HashMap | LinkedHashMap | TreeMap |
|---|---|---|---|
| Ordering | No guaranteed order | Insertion/access order | Sorted key order |
| Average lookup | `O(1)` | `O(1)` | `O(log n)` |
| Null key | Allows one | Allows one | Usually not allowed with natural ordering |
| Internal structure | Hash table | Hash table + linked list | Red-black tree |
| Best use | Fast lookup | Fast lookup with order | Sorted data |

Use:

- `HashMap` for fastest general-purpose lookup.
- `LinkedHashMap` when insertion order matters.
- `TreeMap` when sorted keys are required.

## 33. HashMap and Thread Safety

`HashMap` is not thread-safe.

If multiple threads modify a HashMap at the same time without synchronization, behavior can become incorrect.

For thread-safe maps:

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
```

or:

```java
Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
```

Prefer `ConcurrentHashMap` for high-concurrency use cases.

Important difference:

- `HashMap` allows null keys and null values.
- `ConcurrentHashMap` does not allow null keys or null values.

## 34. Complete Step-by-Step Example

Code:

```java
HashMap<String, Integer> map = new HashMap<>();

map.put("A", 10);
map.put("B", 20);
map.put("C", 30);

System.out.println(map.get("B"));
```

Internal idea:

```text
put("A", 10)
  hash("A")
  index = hash & 15
  store node in table[index]

put("B", 20)
  hash("B")
  index = hash & 15
  store node in table[index]

put("C", 30)
  hash("C")
  index = hash & 15
  store node in table[index]

get("B")
  hash("B")
  index = hash & 15
  go directly to table[index]
  compare key
  return 20
```

## 35. Interview-Style Explanation

HashMap stores key-value pairs using hashing. When a key is inserted, HashMap calls the key's `hashCode()` method, applies a spreading function, and calculates a bucket index using `hash & (capacity - 1)`. The entry is stored in an internal bucket array.

If multiple keys map to the same bucket, HashMap handles the collision using a linked list. In Java 8 and later, if the collision chain becomes too long and the table is large enough, the bucket is converted into a red-black tree. This improves worst-case lookup from `O(n)` to `O(log n)`.

HashMap resizes when `size > capacity * loadFactor`. The default capacity is `16`, and the default load factor is `0.75`, so the first resize happens after inserting the 13th entry. During resize, capacity doubles and entries are redistributed.

Average time complexity for `put`, `get`, and `remove` is `O(1)`, assuming a good hash function and proper distribution of keys.

## 36. Best Practices

- Use immutable objects as keys.
- Always override `hashCode()` when overriding `equals()`.
- Keep `hashCode()` fast and well distributed.
- Use `containsKey()` when a stored value may be `null`.
- Pre-size HashMap when inserting many known entries.
- Use `LinkedHashMap` if order matters.
- Use `ConcurrentHashMap` for concurrent access.
- Avoid using mutable fields in `hashCode()` and `equals()`.
- Do not depend on HashMap iteration order.

## 37. Quick Summary

| Concept | Meaning |
|---|---|
| Hashing | Converts data into an integer hash |
| HashMap | Stores key-value pairs using a hash table |
| Bucket | Position in internal array |
| Collision | Two keys map to same bucket |
| Chaining | Linked list used to store collided entries |
| Treeification | Converts long bucket chain into red-black tree |
| Load factor | Controls when resizing happens |
| Resizing | Doubling bucket array and redistributing entries |
| `hashCode()` | Produces hash value for object |
| `equals()` | Checks logical equality of keys |
| Average complexity | `O(1)` |
| Worst complexity | `O(n)`, improved to `O(log n)` with tree bins |

## 38. Final Takeaway

HashMap is fast because it avoids linear search. It uses a key's hash code to jump directly to a bucket. Collisions are handled by linked lists and, in modern Java, by red-black trees when needed. Resizing keeps the number of entries per bucket controlled. Correct `hashCode()` and `equals()` implementations are essential for HashMap to work properly.

For DSA problems, HashMap is one of the most useful tools because it turns many search-heavy problems from `O(n^2)` into average `O(n)`.
