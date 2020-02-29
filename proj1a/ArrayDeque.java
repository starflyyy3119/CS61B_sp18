/**
 * Array implementation of deque
 */
public class ArrayDeque<T> {
    private int size;
    /* pointer used to indicate the head of the deque, arrayList[front + 1] is the first item*/
    private int front;
    /* pointer used to indicate the last of the deque, arrayList[last - 1] is the last item */
    private int last;
    private T[] arrayList;

    public ArrayDeque() {
        size = 0;
        front = 0;
        last = 1;
        arrayList = (T[]) new Object[8];
    }

    public void addFirst(T item) {
        size = size + 1;
        if (size == arrayList.length) {
            resize(size * 2);
        }
        arrayList[front] = item;

        front = Math.floorMod(front - 1, arrayList.length);
    }

    public void addLast(T item) {
        size = size + 1;
        if (size == arrayList.length) {
            resize(size * 2);
        }
        arrayList[last] = item;

        last = (last + 1) % arrayList.length;
    }

    private void resize(int capacity) {
        T[] array = (T[]) new Object[capacity];
        arrayCopy(arrayList, array, front + 1);
        front = array.length - 1;
        last = size - 1;
        arrayList = array;
    }

    private void arrayCopy(T[] arraySource, T[] arrayDes, int index) {
        for (int i = 0; i < size; i++) {
            arrayDes[i] = arraySource[index % arraySource.length];
            index++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int f = (front + 1) % arrayList.length;
        for (int cnt = 0; cnt < size; cnt++) {
            StdOut.print(arrayList[f] + " ");
            f = (f + 1) % arrayList.length;
        }
    }

    public T removeFirst() {
        if (size == 0) return null;
        front = (front + 1) % arrayList.length;
        T item = arrayList[front];
        arrayList[front] = null;
        size--;

        if ((double) size / arrayList.length < 0.25) {
            resize(arrayList.length / 2);
            last = last + 1;
        }
        return item;
    }

    public T removeLast() {
        if (size == 0) return null;
        last = Math.floorMod(last - 1, arrayList.length);
        T item = arrayList[last];
        arrayList[last] = null;
        size--;

        if ((double) size / arrayList.length < 0.25) {
            resize(arrayList.length / 2);
            last = last + 1;
        }
        return item;
    }

    public T get(int index) {
        return arrayList[(front + index + 1) % arrayList.length];
    }
}
