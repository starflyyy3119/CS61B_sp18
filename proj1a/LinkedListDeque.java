/**
 * Double ended queues which are sequence containers with dynamic sizes
 * that can be expanded or contracted on both ends
 */
public class LinkedListDeque<T> {
    private static class Node<T> {
        public T item;
        public Node<T> next;
        public Node<T> prev;

        public Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }
        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        /* item in sentinel is never readable, the first item in the deque is sentinel.next */
        sentinel = new Node<>();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type T to the front of the deque
     * @param item that needs to be added
     */
    public void addFirst(T item) {
        /* Now first node in the deque */
        Node<T> first = sentinel.next;
        Node<T> p = new Node<>(item, first, sentinel);
        first.prev = p;
        sentinel.next = p;
        size = size + 1;
    }

    /**
     * Adds an item of type T to the back of the deque
     * @param item that needs to be added
     */
    public void addLast(T item) {
        /* Now last node in the deque */
        Node<T> last = sentinel.prev;
        Node<T> p = new Node<>(item, sentinel, last);
        last.next = p;
        sentinel.prev = p;
        size = size + 1;
    }

    /**
     * Check whether or not the deque is empty
     * @return true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The size of the items
     * @return the item number in the deque
     */
    public int size() {
        return size;
    }
    /**
     * Prints the items in the deque from first to last, separated by a space
     */
    public void printDeque() {
        Node<T> p = sentinel.next;
        while(p != sentinel) {
            StdOut.print(p.item + " ");
            p = p.next;
        }
        StdOut.println("\n");
    }

    /**
     * Removes the first item of the deque
     * @return the first item, if no such item, return null
     */
    public T removeFirst() {
        if (isEmpty()) return null;

        Node<T> first = sentinel.next;
        Node<T> newFirst = first.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;

        T item = first.item;
        first = null;
        size = size - 1;
        return item;
    }

    /**
     * Removes the last item of the deque
     * @return the last item, if no such item, return null
     */
    public T removeLast() {
        if (isEmpty()) return null;

        Node<T> last = sentinel.prev;
        Node<T> newLast = last.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;

        T item = last.item;
        last = null;
        size = size - 1;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front
     * @param index the specific index
     * @return the index's item in the deque
     */
    public T get(int index) {
        Node<T> p = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (p == sentinel) return null;
            p = p.next;
        }
        return p.item;
    }

    /**
     * Gets the item at the given index, but use recursion
     * @param index the specific index
     * @return the index's item in the deque
     */
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    private T getRecursive(int index, Node<T> p) {
        if (p == sentinel) return null;
        if (index == 0) return p.item;
        return getRecursive(index - 1, p.next);
    }
}
