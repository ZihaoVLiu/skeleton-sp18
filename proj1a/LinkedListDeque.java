public class LinkedListDeque<T> {
    private int size;
    public class Node {
        T item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /** create an empty deque */
    private Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addFirst(T item){
        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;
        size += 1;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addLast(T item){
        Node node = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        Node curr = sentinel.next;
        while (curr != sentinel) {
            System.out.print(curr.item + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst(){
        if (isEmpty()){
            return null;
        }
        T temp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return temp;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast(){
        if ((isEmpty())){
            return null;
        }
        T temp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return temp;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index){
        if (index > size) {
            return null;
        }
        int counter = 0;
        Node curr = sentinel.next;
        while (counter < index){
            curr = curr.next;
            counter += 1;
        }
        return curr.item;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index){
        return getRecursiveHelper(sentinel.next, index);
    }

    /** getRecursive helper function */
    public T getRecursiveHelper(Node node, int index) {
        if (index > size){
            return null;
        }
        else if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        lld1.addFirst("wocao");
        lld1.addFirst("dashabi");
        System.out.println(lld1.get(1));
        lld1.printDeque();
        System.out.println(lld1.size());
    }
}
