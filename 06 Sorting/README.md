# Sorting Algorithms Visual Guide

---

## A. Comparison-Based Sorts ($O(N^2)$)

These algorithms use **nested loops**:

* Outer loop → tracks sorted portion
* Inner loop → performs comparison and movement

---

## 1. Bubble Sort: "Neighbor-to-Neighbor"

**Idea:** Adjacent comparison. Largest element “bubbles” to the end.

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

    subgraph "Step 1: Compare 8 & 3 → Swap"
        G[3] --- H[8] --- I[6] --- J[2] --- K[7] --- L[1]
    end
    
    subgraph "Start: [8, 3, 6, 2, 7, 1]"
    A[8] --- B[3] --- C[6] --- D[2] --- E[7] --- F[1]
    end

```
```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;
    subgraph "Step 3: Compare 8 & 2 → Swap"
        S[3] --- T[6] --- U[2] --- V[8] --- W[7] --- X[1]
    end
    
    subgraph "Step 2: Compare 8 & 6 → Swap"
    M[3] --- N[6] --- O[8] --- P[2] --- Q[7] --- R[1]
    end



```
```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

    subgraph "Step 5: Compare 8 & 1 → Swap (Locked)"
        AE[3] --- AF[6] --- AG[2] --- AH[7] --- AI[1] --- AJ((8))
    end

subgraph "Step 4: Compare 8 & 7 → Swap"
Y[3] --- Z[6] --- AA[2] --- AB[7] --- AC[8] --- AD[1]
end


class A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ largeFont
style AJ fill:green,stroke:#333,stroke-width:2px
```

### Explanation

This shows **Pass 1 on `[8, 3, 6, 2, 7, 1]`**.

* `8` is compared with each neighbor
* It keeps swapping and moving right
* Finally, `8` reaches the last position and becomes fixed

Result after pass:

```
[3, 6, 2, 7, 1 | 8]
```

Each pass guarantees **one largest element is placed correctly**.

---

## 2. Selection Sort: "Search and Swap"

**Idea:** Find minimum → swap once → grow sorted section.

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;


    subgraph "Step 1: Search MIN in full array"
        G[8] -.- H[3] -.- I[6] -.- J[2] -.- K[7] -.- L((1))
    end
    
    subgraph "Start: [8, 3, 6, 2, 7, 1]"
        A[8] --- B[3] --- C[6] --- D[2] --- E[7] --- F[1]
    end
```
```mermaid
graph TD
    subgraph "Sorted Section grows"
        S((1)) --- T[3] --- U[6] --- V[2] --- W[7] --- X[8]
    end
    
    classDef largeFont font-size:25px, font-weight:bold;
    subgraph "Step 2: Swap MIN (1) with first element (8)"
    M((1)) --- N[3] --- O[6] --- P[2] --- Q[7] --- R[8]
end


class A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X largeFont
style M fill: green, stroke: #333, stroke-width: 4px
```

### Explanation

* Entire array is scanned → smallest element is `1`
* One swap happens → `1 ↔ 8`

Result:

```
[1 | 3, 6, 2, 7, 8]
```

* Sorted portion grows from left
* Only **one swap per pass**

---

## 3. Insertion Sort: "Shift and Slide"

**Idea:** Insert element into correct position in sorted section.

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

subgraph "Start: [4, 7, 3, 2, 6]"
A((4)) --- B((7)) --- C(3) --- D(2) --- E(6)
style A fill: green
style B fill: green
end

```
```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

    subgraph "Step 2: Shift 4"
        K[Hole] --- L(4) --- M(7) --- N(2) --- O(6)
    end
    
    subgraph "Step 1: Take 3 → shift 7"
    F((4)) --- G[Hole] --- H(7) --- I(2) --- J(6)
    style F fill: green
    end


```
```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

subgraph "Step 3: Insert 3"
P((3)) --- Q((4)) --- R((7)) --- S(2) --- T(6)
style P fill: green
style Q fill: green
style R fill: green
end

class A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T largeFont
```

### Explanation

Start:

```
[4, 7 | 3, 2, 6]
```

* `3` is inserted into sorted portion
* Larger elements (`7`, `4`) are shifted right

Result:

```
[3, 4, 7 | 2, 6]
```

👉 Key point: **shifting, not swapping**

---

## B. Divide and Conquer Sorts ($O(N \log N)$)

These algorithms use **recursion** to break problems into smaller parts.

---

## 4. Merge Sort: "Split and Zip"

**Idea:** Split → recursively → merge sorted arrays.

### Divide Phase

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

A[10, 3, 8, 2, 7, 5, 1, 6] --> B[10, 3, 8, 2]
A --> C[7, 5, 1, 6]

B --> D[10, 3]
B --> E[8, 2]

C --> F[7, 5]
C --> G[1, 6]

D --> H[10]
D --> I[3]

E --> J[8]
E --> K[2]

F --> L[7]
F --> M[5]

G --> N[1]
G --> O[6]

class A,B,C,D,E,F,G,H,I,J,K,L,M,N,O largeFont
```

### Merge Phase

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

H[10] -.-> P[3,10]
I[3] -.-> P

J[8] -.-> Q[2,8]
K[2] -.-> Q

P -.-> R[2,3,8,10]
Q -.-> R

L[7] -.-> S[5,7]
M[5] -.-> S

N[1] -.-> T[1,6]
O[6] -.-> T

S -.-> U[1,5,6,7]
T -.-> U

R -.-> V[1,2,3,5,6,7,8,10]
U -.-> V

class H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V largeFont
style V fill: green, stroke: #333, stroke-width: 4px
```

### Explanation

Start:

```
[10, 3, 8, 2, 7, 5, 1, 6]
```

* Split until single elements
* Merge step builds sorted arrays

Final result:

```
[1, 2, 3, 5, 6, 7, 8, 10]
```

👉 Sorting happens **during merging**

---
## Consider another example to understand merge sort.

✅ Example Array (21 elements)
```bash

[21, 4, 18, 7, 2, 15, 9, 1, 13, 6, 11, 3, 20, 8, 5, 14, 10, 19, 12, 16, 17]
```
🌳 1. DIVIDE PHASE (Top → Down)

👉 Merge sort keeps splitting until single elements
```bash
    mergeSort([21 items])
    │
    ├── Left (10 items)
    │   [21,4,18,7,2,15,9,1,13,6]
    │
    └── Right (11 items)
        [11,3,20,8,5,14,10,19,12,16,17]
        
    🔽 Keep going LEFT first (depth-first)
    
    [21,4,18,7,2,15,9,1,13,6]
    │
    ├── [21,4,18,7,2]
    │   ├── [21,4]
    │   │   ├── [21]
    │   │   └── [4]
    │   │   → merge → [4,21]
    │   │
    │   └── [18,7,2]
    │       ├── [18]
    │       └── [7,2]
    │           ├── [7]
    │           └── [2]
    │           → merge → [2,7]
    │       → merge → [2,7,18]
    │
    │   → merge → [2,4,7,18,21]
    │
    └── [15,9,1,13,6]
    ├── [15,9]
    │   ├── [15]
    │   └── [9]
    │   → merge → [9,15]
    │
    └── [1,13,6]
    ├── [1]
    └── [13,6]
    ├── [13]
    └── [6]
    → merge → [6,13]
    → merge → [1,6,13]
    → merge → [1,6,9,13,15]
    
    → FINAL LEFT MERGE:
    
    [2,4,7,18,21] + [1,6,9,13,15]
    → [1,2,4,6,7,9,13,15,18,21]
```
```bash

    🔽 NOW RIGHT SIDE (only AFTER left is done)
    [11,3,20,8,5,14,10,19,12,16,17]
    │
    ├── [11,3,20,8,5]
    │   ├── [11,3]
    │   │   → [3,11]
    │   │
    │   └── [20,8,5]
    │       ├── [20]
    │       └── [8,5]
    │           → [5,8]
    │       → merge → [5,8,20]
    │
    │   → merge → [3,5,8,11,20]
    │
    └── [14,10,19,12,16,17]
        ├── [14,10,19]
        │   → [10,14,19]
        │
        └── [12,16,17]
            → [12,16,17]
        → merge → [10,12,14,16,17,19]
    
    → FINAL RIGHT MERGE:
    
    [3,5,8,11,20] + [10,12,14,16,17,19]
    → [3,5,8,10,11,12,14,16,17,19,20]
    
    🔥 FINAL MERGE (ROOT)
    LEFT:
    [1,2,4,6,7,9,13,15,18,21]
    
    RIGHT:
    [3,5,8,10,11,12,14,16,17,19,20]
    
    👉 Merge step:
    
    → [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21]


```

## 5. Quick Sort: "Pivot Partition"

**Idea:** Choose pivot → partition → recurse.

```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

    subgraph "Step 2 : Partition using pivot = 5"
        H(Smaller than 5) --> I[3, 2, 1]
        J(Greater than 5) --> K[8, 6, 7]
    end
        subgraph "Start: [8, 3, 6, 2, 7, 1, 5]"
        A[8] --- B[3] --- C[6] --- D[2] --- E[7] --- F[1] --- G((5))
    
end

```
```mermaid
graph TD
classDef largeFont font-size:25px, font-weight:bold;

subgraph "Result"
L[3, 2, 1] --- M((5)) --- N[8, 6, 7]
end

class A,B,C,D,E,F,G,H,I,J,K,L,M,N largeFont
style M fill: green, stroke: #333, stroke-width: 4px
```

### Explanation

Start:

```
[8, 3, 6, 2, 7, 1, 5]
```

* Pivot = `5`
* Left → `[3, 2, 1]`
* Right → `[8, 6, 7]`

Result:

```
[3, 2, 1] | 5 | [8, 6, 7]
```

👉 Pivot is **already in final position**

---

## 🏁 Summary Comparison

| Algorithm      | Strategy        | Best Case  | Worst Case | Memory   |
|----------------|-----------------|------------|------------|----------|
| Bubble Sort    | Adjacent Swap   | O(N)       | O(N²)      | In-place |
| Selection Sort | Find Minimum    | O(N²)      | O(N²)      | In-place |
| Insertion Sort | Shift & Insert  | O(N)       | O(N²)      | In-place |
| Merge Sort     | Divide & Merge  | O(N log N) | O(N log N) | O(N)     |
| Quick Sort     | Pivot Partition | O(N log N) | O(N²)      | O(log N) |

---
