import java.util.ArrayList;
import java.util.List;

// Your actual solution class
class PowerOfTwoMaxHeap {

    private final int childrenPower;  // the "x" from 2^x children
    private final int childrenCount;  // actual number of children
    private final List<Integer> heap; // underlying storage

    // Constructor
    public PowerOfTwoMaxHeap(int childrenPower) {
        if (childrenPower < 0) {
            throw new IllegalArgumentException("childrenPower must be >= 0");
        }
        this.childrenPower = childrenPower;
        this.childrenCount = 1 << childrenPower; // 2^x
        this.heap = new ArrayList<>();
    }

    // Public method: Insert value into heap
    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        while (index > 0) {
            int parent = parentIndex(index);

            if (heap.get(index) <= heap.get(parent)) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    // Public method: Remove and return max value
    public int popMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int maxValue = heap.get(0);
        int lastValue = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastValue);
            heapifyDown(0);
        }

        return maxValue;
    }

    // Private helper: swap two elements
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Private helper: heapify down after popMax
    private void heapifyDown(int index) {
        while (true) {
            int largest = index;
            int firstChild = firstChildIndex(index);

            for (int i = 0; i < childrenCount; i++) {
                int childIndex = firstChild + i;

                if (childIndex >= heap.size()) {
                    break;
                }

                if (heap.get(childIndex) > heap.get(largest)) {
                    largest = childIndex;
                }
            }

            if (largest == index) {
                break;
            }

            swap(index, largest);
            index = largest;
        }
    }

    // Private helper: calculate parent index
    private int parentIndex(int index) {
        return (index - 1) / childrenCount;
    }

    // Private helper: calculate first child index
    private int firstChildIndex(int index) {
        return index * childrenCount + 1;
    }
}

// Optional class for testing (not required by Walmart)
public class Main {
    public static void main(String[] args) {
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(2); // 2^2 = 4 children
        heap.insert(10);
        heap.insert(50);
        heap.insert(30);
        heap.insert(40);

        System.out.println(heap.popMax()); // 50
        System.out.println(heap.popMax()); // 40
    }
}
