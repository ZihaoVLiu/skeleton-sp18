public class ArrayDeque<T> {
    private T[] array;
    private int nextFirst;
    private int nextLast;
    private int size;
    private static int INIT_CAPACITY = 8;

    /** Creates an empty array deque */
    public ArrayDeque() {
        array = (T[]) new Object [INIT_CAPACITY];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    /** Change the index */
    public int addIndex(int index){
        return (index + 1 + array.length) % array.length;
    }

    public int minusIndex(int index){
        return (index - 1 + array.length) % array.length;
    }

    /** resize the array */
    public void resize(int CAPACITY) {
        T[] newArray = (T[]) new Object [CAPACITY];

        int currIndex = addIndex(nextFirst);
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[currIndex];
            currIndex = addIndex(currIndex);
        }
        array = newArray;
        nextFirst = CAPACITY - 1;
        nextLast = size;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == array.length){
            resize(2 * INIT_CAPACITY);
        }
        array[nextFirst] = item;
        nextFirst = minusIndex(nextFirst);
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        if (size == array.length){
            resize(2 * INIT_CAPACITY);
        }
        array[nextLast] = item;
        nextLast = addIndex(nextLast);
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        int currIndex = addIndex(nextFirst);
        while (currIndex != nextLast){
            System.out.print(array[currIndex] + " ");
            currIndex = addIndex(currIndex);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = addIndex(nextFirst);
        size -= 1;
        T value = array[nextFirst];

        if (array.length > INIT_CAPACITY && size / array.length < 0.25) {
            resize(array.length / 2);
        }
        return value;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusIndex(nextLast);
        size -= 1;
        T value = array[nextLast];

        if (array.length > INIT_CAPACITY && size / array.length < 0.25) {
            resize(array.length / 2);
        }
        return value;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (index > size){
            return null;
        }
        index = (index + addIndex(nextFirst) + array.length) % array.length;
        return array[index];
    }
}
