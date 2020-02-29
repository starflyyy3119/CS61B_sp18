/**
 * This interface defines methods for a deque data structure
 */
public interface Deque<T> {
    /**
     * Adds an item of type T to the front of the deque
     * @param item needs to be added
     */
    public void addFirst(T item);

    /**
     * Adds an item of type T to the last of the deque
     * @param item needs to be added
     */
    public void addLast(T item);

    /**
     * Check if the deque is empty
     * @return true if the deque is empty, otherwise false
     */
    public boolean isEmpty();

    /**
     * Get the number of items in the deque
     * @return the number of items
     */
    public int size();

    /**
     * Print the deque from front to last
     */
    public void printDeque();

    /**
     * Remove the first item in deque
     * @return the first item
     */
    public T removeFirst();

    /**
     * Remove the last item in deque
     * @return the last item
     */
    public T removeLast();

    /**
     * Get the index's item
     * @param index is the item's index
     * @return the index's item
     */
    public T get(int index);
}
