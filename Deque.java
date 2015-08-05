import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first, last;
    private class Node {
    private Item item;
    private Node next, previous;
    }

    public Deque() {                           // construct an empty deque
        N = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {                 // is the deque empty?
        return size() == 0;
    }

    public int size() {                        // return the number of items on the deque
        return N;
    }

    public void addFirst(Item item) {          // add the item to the front
        thrownullitem(item);
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (oldfirst == null) {
            first.next = null;
            first.previous = null;
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        N++;
    }
    
    public void addLast(Item item) {           // add the item to the end
        thrownullitem(item);
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (oldlast == null) {
            last.next = null;
            last.previous = null;
            first = last;
        } else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        N++;
    }
    
    public Item removeFirst() {               // remove and return the item from the front
        throwempty();
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            first.next.previous = null;
            first = first.next;
        }
        N--;
        return item;
    }
    
    public Item removeLast() {                // remove and return the item from the end
        throwempty();
        Item item = last.item;
        if (last.previous == null) {
            first = null;
            last = null;
        } else {
            last.previous.next = null;
            last = last.previous;
        }
        N--;
        return item;        
    }
    
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    private void thrownullitem(Item item) {
        if (item == null) throw new NullPointerException("New element shouldn't be null");
    }
    
    private void throwempty() {
        if (N == 0) throw new NoSuchElementException("cannot remove element from an empty deque");
    }
    public static void main(String[] args) {   // unit testing
        /*Deque<String> dq = new Deque<String>();
        
        
        dq.addLast("hello");
        System.out.print(dq.removeLast() + " ");
        dq.addFirst("me!");
        System.out.print(dq.removeLast() + " ");
        dq.addLast("from");
        dq.addFirst("world");
        System.out.print(dq.removeLast() + " ");
        System.out.println(dq.removeLast() + " ");
        
        dq.addLast("hello");
        dq.addFirst("me!");
        dq.addLast("from");
        dq.addFirst("world");
        Iterator<String> i = dq.iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }*/
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addLast(0);
        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.isEmpty());
        deque.addLast(4);
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        //StdOut.println("should be empty, but actually is "+ deque.isEmpty());
        deque.addLast(7);
        StdOut.println(deque.isEmpty());
        deque.addFirst(9);
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
    }
}
