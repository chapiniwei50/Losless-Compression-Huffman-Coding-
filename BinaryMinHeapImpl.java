
import java.util.*;


/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    /**
     * {@inheritDoc}
     */
    private ArrayList<Entry<Key, V>> heap;
    private HashMap<V, Integer> map;


    public BinaryMinHeapImpl() {
        heap = new ArrayList<Entry<Key, V>>();
        map = new HashMap<V, Integer>();
    }

    private void minHeapify(int index) {
        int l, r, smallest;
        l = index * 2 + 1;
        r = index * 2 + 2;

        //compareTo check
        if (l < heap.size() && heap.get(l).key.compareTo(heap.get(index).key) < 0) {
            smallest = l;
        } else {
            smallest = index;
        }
        if (r < heap.size() && heap.get(r).key.compareTo(heap.get(smallest).key) < 0) {
            smallest = r;
        }
        if (smallest != index) {
            V s, i;
            s = heap.get(smallest).value;
            i = heap.get(index).value;

            map.replace(i, smallest);
            map.replace(s, index);
            Collections.swap(heap, smallest, index);
            minHeapify(smallest);
        }


    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return (heap.isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return map.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (containsValue(value)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Entry<>(key, value));
        map.put(value, heap.size() - 1);
        decreaseKey(value, key);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {

        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null || newKey.compareTo(heap.get(map.get(value)).key) > 0) {
            throw new IllegalArgumentException();
        }

        heap.remove((int) map.get(value));
        heap.add(map.get(value), new Entry<>(newKey, value));

        int i = map.get(value);
        while ((map.get(value)) > 0 && (heap.get((i - 1) / 2).key).compareTo(heap.get(i).key) > 0) {
            Collections.swap(heap, i, (i - 1) / 2);

            map.replace(heap.get((i - 1) / 2).value, i);
            map.replace(heap.get(i).value, (i - 1) / 2);

            i = (i - 1) / 2;

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        Entry<Key, V> first, last;
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        first = heap.get(0);
        last = heap.get(heap.size() - 1);

        heap.remove(0);
        map.remove(first.value);
        if (size() != 0) {
            heap.add(0, last);
            heap.remove(heap.size() - 1);


            map.replace(last.value, 0);
            minHeapify(0);
        }


        return first;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        return map.keySet();
    }
}