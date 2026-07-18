# Heap Data Structures Detailed Notes

## 1. What Is a Heap?

A heap is a special tree-based data structure used when we need fast access to the smallest or largest element.

A binary heap must follow two rules:

1. It must be a complete binary tree.
2. It must follow the heap order property.

A complete binary tree means every level is completely filled except possibly the last level, and the last level is filled from left to right.

The heap order property depends on the heap type.

| Heap Type | Rule |
|---|---|
| Min Heap | Parent value is smaller than or equal to its children |
| Max Heap | Parent value is greater than or equal to its children |

Heap is commonly used to implement a priority queue.

## 2. Why Heap Is Useful

Suppose we repeatedly need the minimum or maximum value from a collection.

Using an unsorted array:

- Insert: `O(1)`
- Find min or max: `O(n)`
- Delete min or max: `O(n)`

Using a sorted array:

- Insert: `O(n)`
- Find min or max: `O(1)`
- Delete min or max: `O(1)` or `O(n)` depending on position

Using a heap:

- Insert: `O(log n)`
- Find min or max: `O(1)`
- Delete min or max: `O(log n)`

Heap gives a good balance when insertions and priority removals both happen frequently.

## 3. Conditions for a Binary Heap

A binary heap is valid only when both conditions are true.

### Condition 1: Complete Binary Tree

The tree must be filled level by level from left to right.

Valid complete binary tree:

```text
        10
      /    \
     20     30
    /  \   /
   40  50 60
```

Invalid complete binary tree:

```text
        10
      /    \
     20     30
       \      \
       50      70
```

The invalid tree has missing left positions while right positions are filled.

### Condition 2: Heap Order Property

For min heap:

```text
parent <= left child
parent <= right child
```

For max heap:

```text
parent >= left child
parent >= right child
```

Important points:

- Heap does not require the left child to be smaller than the right child.
- Heap does not store elements in fully sorted order.
- Heap only guarantees that the root has the highest priority.

## 4. Min Heap

In a min heap, the smallest element is always at the root.

Example:

```text
        5
      /   \
     10    8
    /  \  / \
   30  20 15 40
```

This is a valid min heap because every parent is smaller than or equal to its children.

Array representation:

```text
[5, 10, 8, 30, 20, 15, 40]
```

Use cases:

- Get minimum element quickly.
- Dijkstra's shortest path algorithm.
- Prim's minimum spanning tree algorithm.
- Merge k sorted lists.
- Find kth largest element using a min heap.
- Job scheduling by lowest priority value.

## 5. Max Heap

In a max heap, the largest element is always at the root.

Example:

```text
        50
      /    \
     30     40
    /  \   /  \
   10  20 35   25
```

This is a valid max heap because every parent is greater than or equal to its children.

Array representation:

```text
[50, 30, 40, 10, 20, 35, 25]
```

Use cases:

- Get maximum element quickly.
- Find kth smallest element using a max heap.
- Heap sort.
- Priority queue where larger value means higher priority.
- CPU or task scheduling by highest priority.

## 6. Heap Is Not a Binary Search Tree

Heap and binary search tree are different.

| Feature | Heap | Binary Search Tree |
|---|---|---|
| Main purpose | Fast min or max access | Fast search, insert, delete |
| Root | Minimum or maximum | Depends on insertion/order |
| Left vs right rule | No strict left-right ordering | Left smaller, right larger |
| Search any element | `O(n)` | Average `O(log n)` if balanced |
| Array representation | Very natural | Not usually array-based |

In a heap, only the parent-child relationship matters.

Example min heap:

```text
        5
      /   \
     20    10
```

This is valid even though the right child is smaller than the left child.

## 7. Array Representation of Heap

Because a heap is a complete binary tree, it can be stored efficiently in an array.

For 0-based indexing:

| Relation | Formula |
|---|---|
| Parent of index `i` | `(i - 1) / 2` |
| Left child of index `i` | `2 * i + 1` |
| Right child of index `i` | `2 * i + 2` |

Example:

```text
Array: [10, 20, 30, 40, 50, 60, 70]

Index:   0   1   2   3   4   5   6
Value:  10  20  30  40  50  60  70
```

Tree form:

```text
        10
      /    \
     20     30
    /  \   /  \
   40  50 60  70
```

For index `1`:

```text
value = 20
parent index = (1 - 1) / 2 = 0
left child index = 2 * 1 + 1 = 3
right child index = 2 * 1 + 2 = 4
```

So `20` has parent `10`, left child `40`, and right child `50`.

## 8. Basic Heap Operations

Common heap operations:

| Operation | Meaning | Time Complexity |
|---|---|---:|
| `peek()` | Return root element | `O(1)` |
| `insert(value)` | Add new value | `O(log n)` |
| `deleteRoot()` | Remove min or max value | `O(log n)` |
| `heapify(index)` | Fix heap from an index downward | `O(log n)` |
| `buildHeap(array)` | Convert array into heap | `O(n)` |
| Search element | Find any random value | `O(n)` |

The height of a binary heap is `O(log n)`, so upward and downward fixes take `O(log n)`.

## 9. Insert Operation

Insertion happens in two steps:

1. Add the new element at the end of the array.
2. Move it upward until the heap property becomes valid.

This upward movement is called:

- `sift up`
- `bubble up`
- `up heapify`

### Min Heap Insert Example

Current min heap:

```text
[10, 20, 30, 40, 50]
```

Insert `5`:

```text
[10, 20, 30, 40, 50, 5]
```

Tree:

```text
        10
      /    \
     20     30
    /  \   /
   40  50 5
```

`5` is smaller than its parent `30`, so swap.

```text
[10, 20, 5, 40, 50, 30]
```

Now `5` is smaller than its parent `10`, so swap again.

```text
[5, 20, 10, 40, 50, 30]
```

Final min heap:

```text
        5
      /   \
     20    10
    /  \  /
   40  50 30
```

## 10. Delete Root Operation

In a heap, we usually delete the root because it contains the highest-priority value.

For min heap:

- Delete the smallest value.

For max heap:

- Delete the largest value.

Deletion steps:

1. Store the root value.
2. Move the last element to the root.
3. Remove the last position.
4. Move the new root downward until the heap property is valid.

This downward movement is called:

- `sift down`
- `bubble down`
- `down heapify`

### Min Heap Delete Example

Current min heap:

```text
[5, 20, 10, 40, 50, 30]
```

Delete root `5`.

Move last element `30` to root:

```text
[30, 20, 10, 40, 50]
```

Compare `30` with its children `20` and `10`. The smaller child is `10`, so swap.

```text
[10, 20, 30, 40, 50]
```

Final min heap:

```text
        10
      /    \
     20     30
    /  \
   40  50
```

## 11. Heapify

Heapify means fixing a subtree so that it satisfies the heap property.

For min heap, heapify moves a large value downward until every parent is smaller than its children.

For max heap, heapify moves a small value downward until every parent is larger than its children.

Min heap heapify logic:

```text
heapify(index):
    smallest = index
    left = 2 * index + 1
    right = 2 * index + 2

    if left exists and heap[left] < heap[smallest]:
        smallest = left

    if right exists and heap[right] < heap[smallest]:
        smallest = right

    if smallest != index:
        swap heap[index] and heap[smallest]
        heapify(smallest)
```

Max heap heapify is similar, but it chooses the largest child instead of the smallest child.

## 12. Build Heap

Build heap means converting an unsorted array into a valid heap.

Example:

```text
Original array: [40, 10, 30, 50, 20, 60]
```

We can build a heap by calling heapify from the last non-leaf node down to the root.

Last non-leaf index:

```text
n / 2 - 1
```

For `n = 6`:

```text
last non-leaf index = 6 / 2 - 1 = 2
```

So heapify indexes:

```text
2, 1, 0
```

Build heap takes `O(n)` time, not `O(n log n)`.

Why:

- Many nodes are leaves and need no work.
- Most internal nodes are near the bottom and move only a small distance.
- Only a few nodes near the top can move all the way down.

## 13. Time Complexity

| Operation | Time Complexity | Reason |
|---|---:|---|
| Get min/max | `O(1)` | Root stores highest priority |
| Insert | `O(log n)` | New value may move up height of tree |
| Delete root | `O(log n)` | Root replacement may move down height of tree |
| Heapify | `O(log n)` | Value may move down height of tree |
| Build heap | `O(n)` | Bottom-up heap construction |
| Search arbitrary value | `O(n)` | Heap is not fully sorted |
| Heap sort | `O(n log n)` | Repeated extract operation |

## 14. Space Complexity

Array-based heap uses `O(n)` space for storing `n` elements.

Extra space:

| Implementation | Extra Space |
|---|---:|
| Iterative heap operations | `O(1)` |
| Recursive heapify | `O(log n)` recursion stack |
| Heap sort in-place | `O(1)` extra space |
| Java `H_04_PriorityQueueIntro` | `O(n)` internal array |

## 15. Min Heap Java Implementation

```java
import java.util.ArrayList;

class H_01_MinHeap {
    private final ArrayList<Integer> heap = new ArrayList<>();

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }

    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    public int remove() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int root = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }

        return root;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(parent) <= heap.get(index)) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    private void siftDown(int index) {
        int size = heap.size();

        while (true) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < size && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
```

Usage:

```java
H_01_MinHeap heap = new H_01_MinHeap();

heap.

insert(30);
heap.

insert(10);
heap.

insert(20);
heap.

insert(5);

System.out.

println(heap.peek());   // 5
        System.out.

println(heap.remove()); // 5
        System.out.

println(heap.remove()); // 10
```

## 16. Max Heap Java Implementation

```java
import java.util.ArrayList;

class H_02_MaxHeap {
    private final ArrayList<Integer> heap = new ArrayList<>();

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }

    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    public int remove() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int root = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }

        return root;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(parent) >= heap.get(index)) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    private void siftDown(int index) {
        int size = heap.size();

        while (true) {
            int largest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && heap.get(left) > heap.get(largest)) {
                largest = left;
            }

            if (right < size && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest == index) {
                break;
            }

            swap(index, largest);
            index = largest;
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
```

Usage:

```java
H_02_MaxHeap heap = new H_02_MaxHeap();

heap.

insert(30);
heap.

insert(10);
heap.

insert(20);
heap.

insert(50);

System.out.

println(heap.peek());   // 50
        System.out.

println(heap.remove()); // 50
        System.out.

println(heap.remove()); // 30
```

## 17. Java H_04_PriorityQueueIntro

Java provides heap functionality using `H_04_PriorityQueueIntro`.

By default, `H_04_PriorityQueueIntro` is a min heap.

```java
import java.util.H_04_PriorityQueueIntro;

H_04_PriorityQueueIntro<Integer> minHeap = new H_04_PriorityQueueIntro<>();

minHeap.offer(30);
minHeap.offer(10);
minHeap.offer(20);

System.out.println(minHeap.peek()); // 10
System.out.println(minHeap.poll()); // 10
System.out.println(minHeap.poll()); // 20
```

Common methods:

| Method | Meaning |
|---|---|
| `offer(value)` | Insert value |
| `add(value)` | Insert value, may throw exception in capacity-restricted queues |
| `peek()` | Return root without removing |
| `poll()` | Remove and return root |
| `remove(value)` | Remove a specific value |
| `isEmpty()` | Check whether queue is empty |
| `size()` | Return number of elements |

## 18. Max Heap Using H_04_PriorityQueueIntro

Java `H_04_PriorityQueueIntro` can become a max heap by using a reverse comparator.

```java
import java.util.Collections;
import java.util.H_04_PriorityQueueIntro;

H_04_PriorityQueueIntro<Integer> maxHeap = new H_04_PriorityQueueIntro<>(Collections.reverseOrder());

maxHeap.offer(30);
maxHeap.offer(10);
maxHeap.offer(50);
maxHeap.offer(20);

System.out.println(maxHeap.peek()); // 50
System.out.println(maxHeap.poll()); // 50
System.out.println(maxHeap.poll()); // 30
```

Another way:

```java
H_04_PriorityQueueIntro<Integer> maxHeap = new H_04_PriorityQueueIntro<>(
        (a, b) -> Integer.compare(b, a)
);
```

Avoid this:

```java
H_04_PriorityQueueIntro<Integer> maxHeap = new H_04_PriorityQueueIntro<>((a, b) -> b - a);
```

Subtraction can overflow when values are very large.

## 19. H_04_PriorityQueueIntro with Custom Objects

Example: sort students by lowest marks first.

```java
import java.util.H_04_PriorityQueueIntro;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

H_04_PriorityQueueIntro<Student> pq = new H_04_PriorityQueueIntro<>(
    (a, b) -> Integer.compare(a.marks, b.marks)
);

pq.offer(new Student("Aman", 85));
pq.offer(new Student("Ravi", 70));
pq.offer(new Student("Neha", 95));

while (!pq.isEmpty()) {
    Student student = pq.poll();
    System.out.println(student.name + " " + student.marks);
}
```

Output:

```text
Ravi 70
Aman 85
Neha 95
```

For highest marks first:

```java
H_04_PriorityQueueIntro<Student> pq = new H_04_PriorityQueueIntro<>(
        (a, b) -> Integer.compare(b.marks, a.marks)
);
```

## 20. Heap Sort

Heap sort uses a heap to sort an array.

For ascending order, we usually build a max heap.

Steps:

1. Build a max heap from the array.
2. Swap the root with the last element.
3. Reduce heap size by one.
4. Heapify the root.
5. Repeat until the array is sorted.

Example:

```text
Original:       [4, 10, 3, 5, 1]
Build max heap: [10, 5, 3, 4, 1]

Move 10 to end: [1, 5, 3, 4, 10]
Heapify:        [5, 4, 3, 1, 10]

Move 5 to end:  [1, 4, 3, 5, 10]
Heapify:        [4, 1, 3, 5, 10]

Move 4 to end:  [3, 1, 4, 5, 10]
Heapify:        [3, 1, 4, 5, 10]

Move 3 to end:  [1, 3, 4, 5, 10]
```

Final sorted array:

```text
[1, 3, 4, 5, 10]
```

Heap sort properties:

| Property | Value |
|---|---|
| Time complexity | `O(n log n)` |
| Extra space | `O(1)` |
| Stable sorting | No |
| In-place sorting | Yes |

## 21. Heap Sort Java Code

```java
import java.util.Arrays;

public class H_03_HeapSort {
    public static void heapSort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            heapify(arr, end, 0);
        }
    }

    private static void heapify(int[] arr, int size, int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != index) {
            swap(arr, index, largest);
            heapify(arr, size, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {4, 10, 3, 5, 1};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
```

Output:

```text
[1, 3, 4, 5, 10]
```

## 22. Kth Largest Element

To find the kth largest element, use a min heap of size `k`.

Idea:

- Keep only the largest `k` elements in the heap.
- The root of this min heap is the kth largest element.

```java
import java.util.H_04_PriorityQueueIntro;

public int findKthLargest(int[] nums, int k) {
    H_04_PriorityQueueIntro<Integer> minHeap = new H_04_PriorityQueueIntro<>();

    for (int num : nums) {
        minHeap.offer(num);

        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }

    return minHeap.peek();
}
```

For:

```text
nums = [3, 2, 1, 5, 6, 4]
k = 2
```

Answer:

```text
5
```

Complexity:

```text
Time: O(n log k)
Space: O(k)
```

## 23. Kth Smallest Element

To find the kth smallest element, use a max heap of size `k`.

Idea:

- Keep only the smallest `k` elements.
- The root of this max heap is the kth smallest element.

```java
import java.util.H_04_PriorityQueueIntro;

public int findKthSmallest(int[] nums, int k) {
    H_04_PriorityQueueIntro<Integer> maxHeap = new H_04_PriorityQueueIntro<>(
        (a, b) -> Integer.compare(b, a)
    );

    for (int num : nums) {
        maxHeap.offer(num);

        if (maxHeap.size() > k) {
            maxHeap.poll();
        }
    }

    return maxHeap.peek();
}
```

Complexity:

```text
Time: O(n log k)
Space: O(k)
```

## 24. Merge K Sorted Arrays

Heap is useful when we need to repeatedly pick the smallest current element from multiple sorted sources.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.H_04_PriorityQueueIntro;

class Node {
    int value;
    int arrayIndex;
    int elementIndex;

    Node(int value, int arrayIndex, int elementIndex) {
        this.value = value;
        this.arrayIndex = arrayIndex;
        this.elementIndex = elementIndex;
    }
}

public List<Integer> mergeKSortedArrays(int[][] arrays) {
    H_04_PriorityQueueIntro<Node> minHeap = new H_04_PriorityQueueIntro<>(
        (a, b) -> Integer.compare(a.value, b.value)
    );

    for (int i = 0; i < arrays.length; i++) {
        if (arrays[i].length > 0) {
            minHeap.offer(new Node(arrays[i][0], i, 0));
        }
    }

    List<Integer> result = new ArrayList<>();

    while (!minHeap.isEmpty()) {
        Node current = minHeap.poll();
        result.add(current.value);

        int nextIndex = current.elementIndex + 1;
        if (nextIndex < arrays[current.arrayIndex].length) {
            int nextValue = arrays[current.arrayIndex][nextIndex];
            minHeap.offer(new Node(nextValue, current.arrayIndex, nextIndex));
        }
    }

    return result;
}
```

If there are `k` arrays and total `n` elements:

```text
Time: O(n log k)
Space: O(k)
```

## 25. Running Median

The median of a running stream can be found using two heaps.

Use:

- Max heap for the smaller half.
- Min heap for the larger half.

Rules:

1. Max heap stores numbers less than or equal to the median.
2. Min heap stores numbers greater than or equal to the median.
3. Heap sizes should differ by at most one.

```java
import java.util.Collections;
import java.util.H_04_PriorityQueueIntro;

class MedianFinder {
    private final H_04_PriorityQueueIntro<Integer> small = new H_04_PriorityQueueIntro<>(
        Collections.reverseOrder()
    );
    private final H_04_PriorityQueueIntro<Integer> large = new H_04_PriorityQueueIntro<>();

    public void addNum(int num) {
        if (small.isEmpty() || num <= small.peek()) {
            small.offer(num);
        } else {
            large.offer(num);
        }

        if (small.size() > large.size() + 1) {
            large.offer(small.poll());
        } else if (large.size() > small.size()) {
            small.offer(large.poll());
        }
    }

    public double findMedian() {
        if (small.size() == large.size()) {
            return (small.peek() + large.peek()) / 2.0;
        }

        return small.peek();
    }
}
```

Complexity:

```text
Add number: O(log n)
Find median: O(1)
Space: O(n)
```

## 26. Heap vs Priority Queue

Heap and priority queue are related but not exactly the same.

| Concept | Meaning |
|---|---|
| Priority Queue | Abstract data type |
| Heap | Concrete data structure used to implement priority queue |

A priority queue defines behavior:

- Insert item.
- Remove highest-priority item.
- Peek highest-priority item.

A heap is one efficient way to implement that behavior.

Java's `H_04_PriorityQueueIntro` is implemented using a binary heap.

## 27. Common Heap Problems

Common DSA problems where heap is useful:

| Problem | Common Heap Choice |
|---|---|
| Kth largest element | Min heap of size `k` |
| Kth smallest element | Max heap of size `k` |
| Merge k sorted lists | Min heap |
| Top k frequent elements | Min heap by frequency |
| Running median | One max heap and one min heap |
| Connect ropes with minimum cost | Min heap |
| Task scheduler | Max heap |
| Dijkstra algorithm | Min heap |
| Prim algorithm | Min heap |
| Sliding window median | Two heaps |

## 28. Connect Ropes with Minimum Cost

Problem:

Given rope lengths, connect all ropes into one rope. Cost of connecting two ropes is the sum of their lengths. Find the minimum total cost.

Greedy idea:

Always connect the two smallest ropes first.

```java
import java.util.H_04_PriorityQueueIntro;

public int minCostToConnectRopes(int[] ropes) {
    H_04_PriorityQueueIntro<Integer> minHeap = new H_04_PriorityQueueIntro<>();

    for (int rope : ropes) {
        minHeap.offer(rope);
    }

    int cost = 0;

    while (minHeap.size() > 1) {
        int first = minHeap.poll();
        int second = minHeap.poll();
        int sum = first + second;

        cost += sum;
        minHeap.offer(sum);
    }

    return cost;
}
```

Example:

```text
ropes = [4, 3, 2, 6]

Pick 2 and 3 -> cost 5
Heap becomes [4, 5, 6]

Pick 4 and 5 -> cost 9
Heap becomes [6, 9]

Pick 6 and 9 -> cost 15

Total cost = 5 + 9 + 15 = 29
```

## 29. Top K Frequent Elements

Use a min heap sorted by frequency.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.H_04_PriorityQueueIntro;

public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();

    for (int num : nums) {
        frequency.put(num, frequency.getOrDefault(num, 0) + 1);
    }

    H_04_PriorityQueueIntro<Integer> minHeap = new H_04_PriorityQueueIntro<>(
        (a, b) -> Integer.compare(frequency.get(a), frequency.get(b))
    );

    for (int num : frequency.keySet()) {
        minHeap.offer(num);

        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }

    return new ArrayList<>(minHeap);
}
```

Complexity:

```text
Time: O(n log k)
Space: O(n)
```

## 30. Important Mistakes

### Mistake 1: Thinking Heap Is Fully Sorted

Heap array:

```text
[5, 10, 8, 30, 20, 15]
```

This is a valid min heap, but the array is not sorted.

Only this is guaranteed:

```text
root is minimum
parent <= children
```

### Mistake 2: Searching in Heap Like BST

Heap does not follow BST ordering.

You cannot decide to move left or right while searching.

Searching arbitrary value takes `O(n)`.

### Mistake 3: Wrong Comparator

Avoid this for max heap:

```java
(a, b) -> b - a
```

Use this instead:

```java
(a, b) -> Integer.compare(b, a)
```

This avoids integer overflow.

### Mistake 4: Forgetting Empty Heap Cases

Always check heap emptiness before calling:

```java
peek()
poll()
remove()
```

Java `H_04_PriorityQueueIntro.peek()` and `H_04_PriorityQueueIntro.poll()` return `null` when the queue is empty.

## 31. Min Heap vs Max Heap

| Point | Min Heap | Max Heap |
|---|---|---|
| Root element | Smallest | Largest |
| Parent rule | Parent <= children | Parent >= children |
| Java default | Yes, `H_04_PriorityQueueIntro` | No, needs comparator |
| Used for kth largest | Yes, size `k` | Usually no |
| Used for kth smallest | Usually no | Yes, size `k` |
| Heap sort ascending | Not usually | Yes |
| Heap sort descending | Yes | Not usually |

## 32. Quick Dry Run: Min Heap Insert

Insert values:

```text
20, 15, 30, 5, 10
```

Step by step:

```text
Insert 20: [20]
Insert 15: [20, 15] -> [15, 20]
Insert 30: [15, 20, 30]
Insert 5:  [15, 20, 30, 5] -> [15, 5, 30, 20] -> [5, 15, 30, 20]
Insert 10: [5, 15, 30, 20, 10] -> [5, 10, 30, 20, 15]
```

Final min heap:

```text
[5, 10, 30, 20, 15]
```

## 33. Quick Dry Run: Max Heap Insert

Insert values:

```text
20, 15, 30, 5, 10
```

Step by step:

```text
Insert 20: [20]
Insert 15: [20, 15]
Insert 30: [20, 15, 30] -> [30, 15, 20]
Insert 5:  [30, 15, 20, 5]
Insert 10: [30, 15, 20, 5, 10]
```

Final max heap:

```text
[30, 15, 20, 5, 10]
```

## 34. Interview-Style Explanation

A heap is a complete binary tree that satisfies a heap order property. In a min heap, every parent is less than or equal to its children, so the minimum element is at the root. In a max heap, every parent is greater than or equal to its children, so the maximum element is at the root.

Because a heap is complete, it is usually stored in an array. For index `i`, the left child is `2 * i + 1`, the right child is `2 * i + 2`, and the parent is `(i - 1) / 2`.

Insertion adds the new element at the end and moves it upward using sift up. Deleting the root replaces it with the last element and moves that value downward using heapify or sift down. Both operations take `O(log n)` time because the height of the heap is logarithmic.

Heap is not fully sorted and is not a binary search tree. It only guarantees fast access to the minimum or maximum element. Java's `H_04_PriorityQueueIntro` is a built-in heap-based priority queue and works as a min heap by default.

## 35. Best Practices

- Use `H_04_PriorityQueueIntro` in Java unless the problem asks for manual heap implementation.
- Use a min heap when you repeatedly need the smallest element.
- Use a max heap when you repeatedly need the largest element.
- For kth largest, keep a min heap of size `k`.
- For kth smallest, keep a max heap of size `k`.
- Use `Integer.compare()` instead of subtraction in comparators.
- Remember that heap search is `O(n)`.
- Remember that heap is not fully sorted.
- Check empty heap conditions before `peek` or `poll`.
- Use bottom-up build heap for `O(n)` heap construction.

## 36. Quick Summary

| Concept | Meaning |
|---|---|
| Heap | Complete binary tree with heap order property |
| Min heap | Root is minimum |
| Max heap | Root is maximum |
| Complete binary tree | Filled level by level from left to right |
| Heapify | Fix heap property by moving value down |
| Sift up | Move inserted value upward |
| Sift down | Move root replacement downward |
| Priority queue | Abstract structure often implemented by heap |
| Java heap class | `H_04_PriorityQueueIntro` |
| Insert complexity | `O(log n)` |
| Delete root complexity | `O(log n)` |
| Peek complexity | `O(1)` |
| Build heap complexity | `O(n)` |
| Search complexity | `O(n)` |

## 37. Final Takeaway

Heap is the best data structure when we need quick access to the minimum or maximum element while still supporting efficient insertions and deletions.

Use min heap when the smallest element has highest priority.

Use max heap when the largest element has highest priority.

For Java DSA problems, `H_04_PriorityQueueIntro` is the most important built-in class for heap-based solutions.
