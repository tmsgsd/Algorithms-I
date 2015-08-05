import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;                             //size
    public RandomizedQueue() {                 // construct an empty randomized queue
        N = 0;
        a = (Item[]) new Object[1];
    }
    public boolean isEmpty() {                 // is the queue empty?
        return size() == 0;
    }
    public int size() {                       // return the number of items on the queue
        return N;
    }
    public void enqueue(Item item) {           // add the item
        thrownullitem(item);
        if (N == a.length) {
            resize(2*a.length);
        }
        a[N++] = item;
    }
    public Item dequeue() {                    // remove and return a random item
        throwempty();
        Item temp;
        int i = StdRandom.uniform(N);
        temp = a[i];
        a[i] = a[--N];                        //put the last element in the place that just be dequeued
        a[N] = null;                          //to avoiding loitering
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return temp;
    }
    public Item sample() {                     // return (but do not remove) a random item
        throwempty();
        return a[StdRandom.uniform(N)];
    }
    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new RandomQueueIterator();
    }
    
    private class RandomQueueIterator implements Iterator<Item> {
        private int current;
        private int[] peer = new int[N];
        public boolean hasNext() { 
            if (current == 0) {
                for (int i = 0; i < N; i++) {
                    peer[i] = i;
                }
                StdRandom.shuffle(peer);
            }
            return current < N;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[peer[current++]];
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }  
    }
    
    private void resize(int capacity) {     //resize the queue to a larger one
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private void thrownullitem(Item item) {
        if (item == null) throw new NullPointerException("New element shouldn't be null");
    }
    
    private void throwempty() {
        if (N == 0) throw new NoSuchElementException("cannot remove element from an empty randomqueue");
    }
    public static void main(String[] args) {   // unit testing
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");
        Iterator<String> i = rq.iterator();
        while (i.hasNext()) {
            StdOut.print(i.next() + " ");
        }
        StdOut.println();
        while (!rq.isEmpty()) {
            StdOut.print(rq.dequeue() + " ");
        }
    }
}

