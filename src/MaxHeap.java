public class MaxHeap {
    int[] arr;
    int maxSize;
    int heapSize;

    MaxHeap(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        heapSize = 0;
    }

    void MaxHeapify(int i) {
        int l = lChild(i);
        int r = rChild(i);
        int largest = i;
        if (l < heapSize && arr[l] > arr[i])
            largest = l;
        if (r < heapSize && arr[r] > arr[largest])
            largest = r;
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            MaxHeapify(largest);
        }
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    int lChild(int i) {
        return (2 * i + 1);
    }

    int rChild(int i) {
        return (2 * i + 2);
    }

    void insertKey(int x) {
        heapSize++;
        int i = heapSize - 1;
        arr[i] = x;
        MaxHeapify(i);
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(100);
        maxHeap.insertKey(10);
        maxHeap.insertKey(5);
        maxHeap.insertKey(3);
        maxHeap.insertKey(2);
        maxHeap.insertKey(4);

        for (int x: maxHeap.arr){
            System.out.println(x);
        }

        System.out.println();

        maxHeap.insertKey(15);

        for (int x: maxHeap.arr){
            System.out.println(x);
        }
    }
}
