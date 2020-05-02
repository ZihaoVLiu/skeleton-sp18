public class ArrayDeque<T> {
    private T[] array;
    private int nextFirst;
    private int nextLast;
    private int size;
    private static final int INIT_CAPACITY = 8;

    /** Creates an empty array deque */
    public ArrayDeque() {
        array = (T[]) new Object [INIT_CAPACITY];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        array = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Change the index */
    private int addIndex(int index) {
        return (index + 1) % array.length;
    }

    private int minusIndex(int index) {
        return (index - 1 + array.length) % array.length;
    }

    /** resize the array */
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];

        int curr = addIndex(nextFirst);
        for (int i = 0; i < size; i++) {
            newArray[i] = array[curr];
            curr = addIndex(curr);
        }
        array = newArray;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == array.length){
            resize((2 * size));
        }
        array[nextFirst] = item;
        size += 1;
        nextFirst = minusIndex(nextFirst);
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == array.length) {
            resize((2 * size));
        }
        array[nextLast] = item;
        size += 1;
        nextLast = addIndex(nextLast);
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        int currIndex = addIndex(nextFirst);
        while (currIndex != nextLast){
            System.out.print(array[currIndex] + " ");
            currIndex = addIndex(currIndex);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = addIndex(nextFirst);
        size -= 1;
        T value = array[nextFirst];

        if (array.length > INIT_CAPACITY && size < array.length * 0.25) {
            resize(array.length / 2);
        }
        return value;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusIndex(nextLast);
        size -= 1;
        T value = array[nextLast];

        if (array.length > INIT_CAPACITY && size < array.length * 0.25) {
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
        index = (index + addIndex(nextFirst)) % array.length;
        return array[index];
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        ArrayDeque<Integer> aa1 = new ArrayDeque<Integer>();
        aa1.addFirst(100);
        aa1.addFirst(50);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(300);
        aa1.addLast(44);
        aa1.addFirst(100);
        aa1.addFirst(50);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(200);
        aa1.addLast(300);
        aa1.addLast(55);
        System.out.println(aa1.get(7));
        aa1.printDeque();
        System.out.println(aa1.size());
    }
}
