/**
 * ArrayDequeInitial.java has passed the auto grader, but it is somewhat ugly
 * This implementation gets an inspiration from
 * @source https://www.cnblogs.com/xzxl/p/8643448.html
 */
public class ArrayDeque<T> {
    /* array is empty: front == last */

    /* number of items in the array: (last - front) mod array.length */
    /* a mod b: we try to find some q and r(must be positive), so that a = q * b + r*/
    /* for example: -6 mod 7, we have -6 = (-1) * 7 + 1, so -6 mod 7 = 1*/

    /* array is full: ((last + 1) mod array.length) == front */


    private int front; // array[front] is the first item of the array
    private int last;  // array[last - 1] is the last item of the array
    private T[] array;

    public ArrayDeque() {
        front = 0;
        last = 0;
        array = (T[]) new Object[8];
    }

    /**
     * A helper function that use to circulate the index
     * @param index that needs to be circulated
     * @return the circulated index
     */
    private int cast(int index) {
        return Math.floorMod(index, array.length);
    }

    private boolean isFull() {
        return (cast(last + 1) == front);
    }

    public boolean isEmpty() {
        return front == last;
    }

    public int size() {
        return cast(last - front);
    }

    /**
     * resize the array, and set front = 0, set last = size
     */
    private void resize(int capacity) {
        if (capacity < 8) return;
        int size = size();
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i + front];
        }
        array = newArray;
        front = 0;
        last = size;
    }

    public void addFirst(T item) {
        if (isFull()) {
            resize(size() * 2);
        }
        if (isEmpty()) {
            array[front] = item;
            last = cast(last + 1);
        }
        else { // !isEmpty()
            front = cast(front - 1);
            array[front] = item;
        }

    }

    public void addLast(T item) {
        if (isFull()) {
            resize(size() * 2);
        }

        array[last] = item;
        last = cast(last + 1);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = array[front];
        array[front] = null;
        front = cast(front + 1);

        if ((double) size() / array.length < 0.25) {
            resize(size() / 2);
        }

        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        last = cast(last - 1);
        T item = array[last];
        array[last] = null;

        if ((double) size() / array.length < 0.25) {
            resize(size() / 2);
        }

        return item;
    }

    public T get(int index) {
        return array[cast(index + front)];
    }
}
