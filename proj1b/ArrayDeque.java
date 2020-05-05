public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static int INIT_CAPACITY = 8;
    private static double MIN_USAGE_RATIO = 0.25;

    /** Creates an empty array deque.
     *  The starting size of your array should be 8.
     */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }


    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];

        int curr = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArray[i] = items[curr];
            curr = plusOne(curr);
        }

        items = newArray;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque.
     *  Must take constant time.
     */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        int counter = 0;
        int curr = plusOne(nextFirst);
        while (counter < size){
            System.out.print(items[curr] + " ");
            counter += 1;
            curr = plusOne(curr);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int first = plusOne(nextFirst);
        T firstItem = items[first];
        items[first] = null;
        nextFirst = first;
        size -= 1;

        if (items.length > 8 && size < items.length * 0.25) {
            resize(items.length / 2);
        }

        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int last = minusOne(nextLast);
        T lastItem = items[last];
        items[last] = null;
        nextLast = last;
        size -= 1;

        if (items.length > 8 && size < items.length * 0.25) {
            resize(items.length / 2);
        }

        return lastItem;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque and must take constant time.
     */
    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }

        index = (plusOne(nextFirst) + index) % items.length;
        return items[index];
    }

    /**
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
        aa1.addLast(44);
        System.out.println(aa1.get(0));
        aa1.printDeque();
        System.out.println(aa1.size());
    }
     */
}
