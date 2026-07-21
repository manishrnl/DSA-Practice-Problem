<div align="center">

# 🧠 DSA Practice Problem

### A structured, topic-wise journey through Data Structures & Algorithms in Java

[![Language](https://img.shields.io/badge/Language-Java-orange?style=flat-square&logo=openjdk)](https://www.java.com/)
[![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-black?style=flat-square&logo=intellijidea)](https://www.jetbrains.com/idea/)
[![Topics](https://img.shields.io/badge/Topics-15-blue?style=flat-square)](#-topics-covered)
[![Problems](https://img.shields.io/badge/Problems%20Solved-80%2B-brightgreen?style=flat-square)](#-topics-covered)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-ff69b4?style=flat-square)](#-contributing)

*A hands-on collection of core DSA implementations, notes, and visual explanations — built while preparing for backend engineering interviews.*

</div>

---

## 📖 About

**DSA-Practice-Problem** is a personal, ever-growing repository of Data Structures & Algorithms solutions written in **pure Java**. Instead of scattering random solutions across notebooks and gists, this repo organizes everything into **numbered topic modules** — each one a self-contained IntelliJ IDEA module — so the learning progression from fundamentals to advanced structures is easy to follow.

Alongside the code, several topics include detailed **Markdown notes**, complete with tables, complexity breakdowns, and even **Mermaid diagrams** that visually trace how an algorithm executes step by step.

This repo is meant to be:
- 📚 A **reference** — a quick place to revisit an algorithm before an interview
- 🧪 A **playground** — small, runnable `main`-driven Java files for experimenting
- 🗺️ A **roadmap** — a topic-by-topic checklist of DSA fundamentals

---

## 📑 Table of Contents

- [About](#-about)
- [Repository Structure](#-repository-structure)
- [Topics Covered](#-topics-covered)
- [Featured Deep Dives](#-featured-deep-dives)
- [Getting Started](#-getting-started)
- [Additional Study Material](#-additional-study-material)
- [Roadmap](#-roadmap)
- [Contributing](#-contributing)
- [Author](#-author)
- [License](#-license)

---

## 🗂 Repository Structure

The project is organized as an **IntelliJ IDEA multi-module workspace**. Every topic lives in its own numbered folder (so the sidebar sorts in learning order) and contains its own `.iml` module file and `src/` directory:

```
DSA-Practice-Problem/
├── 00 Colorful Console Logs/     # Utility: styled console output for debugging
├── 01 Bits Manipulation/         # XOR tricks, finding unique elements
├── 02 Number Theory/             # Primes, GCD, Catalan numbers, factorial + README
├── 03 Recursion/                 # Classic recursive problems
├── 04 Back Tracking/             # N-Queens, Sudoku Solver + README
├── 05 Array/                     # Kadane's, rainwater trapping, stock buy/sell
├── 06 Sorting/                   # Bubble → Quick Sort + visual Mermaid README
├── 07 Searching/                 # Binary/linear search, rotated array search
├── 08 Hashing/                   # HashSet-based problems + detailed report
├── 09 Heap Data Structures/      # Min/Max heap, heap sort, priority queue + README
├── 10 Linked List/                # Reversal, palindrome check, cycle detection
├── 11 Stack/                     # Monotonic stack, histogram, infix/postfix
├── 12 Queue/                     # Circular queue, sliding window maximum
├── 13 Binary Tree/               # Traversals, views, diameter, LCA, burn tree
├── 14 Binary Search Tree/        # BST fundamentals
├── DSA Notes.pdf / .docx         # Consolidated personal notes
├── System Design.docx            # System design notes
├── Explaning Gitignore.md        # A breakdown of .gitignore syntax
└── README.md
```

> Each topic folder is a **standalone Java module** — open the repo in IntelliJ and every folder shows up as its own module, so you can run any single file without wiring up a build tool.

---

## 🧩 Topics Covered

| # | Topic | Highlights |
|---|-------|------------|
| 00 | **Colorful Console Logs** | Styled/colored console output utility for cleaner debugging |
| 01 | **Bit Manipulation** | Finding 1, 2, and 3 unique elements using XOR |
| 02 | **Number Theory** | Prime numbers, GCD, factorial, Catalan numbers, trailing zeroes, inclusion-exclusion, pigeonhole principle |
| 03 | **Recursion** | Josephus problem, permutations, power set, string palindrome, unique paths |
| 04 | **Backtracking** | N-Queens, Sudoku Solver |
| 05 | **Array** | Kadane's algorithm, rainwater trapping, best time to buy/sell stock |
| 06 | **Sorting** | Bubble, Insertion, Selection, Merge, Quick Sort — with visual step-by-step diagrams |
| 07 | **Searching** | Linear & binary search, book allocation problem, search in a rotated/infinite array |
| 08 | **Hashing** | HashSet fundamentals, distinct elements per window, subarray with given sum, union/intersection of arrays |
| 09 | **Heap Data Structures** | Heapify logic, Min/Max heap, heap sort, priority queues, Kth largest/smallest, connect N ropes, median of a stream |
| 10 | **Linked List** | Reversal, palindrome check, cycle detection |
| 11 | **Stack** | Array & linked-list backed stacks, next smaller/larger element, max area in histogram, largest rectangle in matrix, infix ↔ postfix conversion |
| 12 | **Queue** | Array & circular queue, queue using two stacks, flattening a multilevel linked list, sliding window maximum |
| 13 | **Binary Tree** | Traversals (in/pre/post/level order), left-right & top-bottom views, diameter, LCA, tree-to-doubly-linked-list, burning tree problem |
| 14 | **Binary Search Tree** | Core BST introduction |

*(80+ solved problems across 15 topics — and counting.)*

---

## 🔎 Featured Deep Dives

A few topics go beyond raw code and include dedicated notes:

- **[`06 Sorting/README.md`](./06%20Sorting/README.md)** — A visual guide to comparison-based sorts, using Mermaid diagrams to trace each swap step by step.
- **[`09 Heap Data Structures/README.md`](./09%20Heap%20Data%20Structures/README.md)** — A from-scratch explanation of heaps: what they are, why they're useful, and how Min/Max heap ordering works.
- **[`08 Hashing/Hashing-report.md`](./08%20Hashing/Hashing-report.md)** — A written report covering hashing-based problem patterns.
- **[`04 Back Tracking/README.md`](./04%20Back%20Tracking/README.md)** — Notes on the backtracking paradigm through N-Queens and Sudoku.
- **[`02 Number Theory/README.md`](./02%20Number%20Theory/README.md)** — Number theory concepts used across competitive programming.

---

## 🚀 Getting Started

### Prerequisites
- **JDK 8+** installed and configured
- **IntelliJ IDEA** (recommended — the repo is pre-structured as an IntelliJ workspace) or any Java-capable IDE/editor

### Clone the repository

```bash
git clone https://github.com/manishrnl/DSA-Practice-Problem.git
cd DSA-Practice-Problem
```

### Run a solution
Every problem lives in its own `.java` file with a `main` method, so you can run files independently:

**Using IntelliJ IDEA**
1. Open the cloned folder as a project — IntelliJ will automatically detect the existing modules.
2. Navigate to any topic's `src/` folder.
3. Right-click a file → **Run**.

**Using the command line**
```bash
cd "13 Binary Tree/src"
javac B05_Level_Order_Traversal.java
java B05_Level_Order_Traversal
```

---

## 📚 Additional Study Material

Beyond the code, this repo also carries broader prep material:

| File | Description |
|------|-------------|
| `DSA Notes.pdf` / `DSA Notes.docx` | Consolidated personal notes covering DSA concepts in depth |
| `System Design.docx` | Notes on system design fundamentals |
| `Explaning Gitignore.md` | A practical breakdown of `.gitignore` syntax (`*`, `**`, `!`, `[]`, `?`) for Java projects |

---

## 🗺 Roadmap

Planned/likely next additions as the DSA journey continues:

- [ ] Graph algorithms (BFS, DFS, Dijkstra, Union-Find)
- [ ] Dynamic Programming (1D, 2D, knapsack variants)
- [ ] Trie / advanced string algorithms
- [ ] More Binary Search Tree operations (insertion, deletion, balancing)
- [ ] Sliding window & two-pointer problem sets

---

## 🤝 Contributing

This is primarily a personal learning log, but suggestions, corrections, and alternative approaches are welcome:

1. **Fork** the repository
2. Create a feature branch: `git checkout -b add-topic-name`
3. Commit your changes: `git commit -m "Add: <what you added>"`
4. Push to your fork: `git push origin add-topic-name`
5. Open a **Pull Request** describing the change

If you spot a bug or a more optimal approach to an existing solution, feel free to open an issue too.

---

## 👤 Author

**Manish** ([@manishrnl](https://github.com/manishrnl))

If this repo helped you prepare for interviews or learn a concept, consider giving it a ⭐!

---

## 📄 License

No license file is currently included in this repository, which means default copyright applies and reuse isn't explicitly permitted. If you'd like this project to be freely reusable, consider adding an [MIT License](https://choosealicense.com/licenses/mit/) or similar.

</div>
