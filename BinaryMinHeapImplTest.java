import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BinaryMinHeapImplTest {

    @Test
    public void testAddKey() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(1, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullKey() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddValueRepeat() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(1, "repeat");
        test.add(5, "repeat");
    }

    @Test(expected = NoSuchElementException.class)
    public void testDecreaseKeyValueNotInHeap() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(1, "repeat");
        test.decreaseKey("notInHeap", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyNewKeyNull() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(5, "repeat");
        test.decreaseKey("repeat", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseGreaterNewKey() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.add(5, "repeat");
        test.decreaseKey("repeat", 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekEmpty() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testExtractMinEmpty() {
        BinaryMinHeap<Integer, String> test = new BinaryMinHeapImpl<>();
        test.extractMin();
    }

    @Test
    public void testEmptyHeap() {
        BinaryMinHeap<Integer, String> empty = new BinaryMinHeapImpl<>();
        assertEquals(empty.size(), 0);
        assertTrue(empty.isEmpty());
    }

    @Test
    public void testAddMinimumElementToHeap() {
        BinaryMinHeap<Integer, String> bmh = new BinaryMinHeapImpl<>();
        bmh.add(2, "a");
        bmh.add(3, "b");
        bmh.add(4, "c");
        assertEquals("a", bmh.peek().value);
        assertEquals((Integer) 2, bmh.peek().key);

        bmh.add(1, "d");

        assertEquals("d", bmh.peek().value);
        assertEquals((Integer) 1, bmh.peek().key);
        assertFalse(bmh.isEmpty());
        assertEquals(4, bmh.size());


    }


    @Test
    public void testExtractMin() {
        BinaryMinHeap<Integer, String> bmh = new BinaryMinHeapImpl<>();
        bmh.add(1, "a");
        bmh.add(2, "b");
        bmh.add(3, "c");
        bmh.add(4, "d");

        BinaryMinHeap.Entry<Integer, String> min = bmh.extractMin();
        assertEquals((Integer) 1, min.key);
        assertEquals("a", min.value);

        assertFalse(bmh.containsValue("a"));
        assertTrue(bmh.containsValue("b"));
        assertTrue(bmh.containsValue("c"));
        assertTrue(bmh.containsValue("d"));
        assertEquals(3, bmh.size());

    }

    @Test
    public void testExtractMinSingleElement() {
        BinaryMinHeap<Integer, String> bmh = new BinaryMinHeapImpl<>();
        bmh.add(1, "a");


        BinaryMinHeap.Entry<Integer, String> min = bmh.extractMin();
        assertEquals((Integer) 1, min.key);
        assertEquals("a", min.value);

        assertFalse(bmh.containsValue("a"));
        assertTrue(bmh.isEmpty());
        assertEquals(0, bmh.size());
    }

    @Test
    public void testDecreaseKey() {
        BinaryMinHeap<Integer, String> bmh = new BinaryMinHeapImpl<>();
        bmh.add(5, "a");
        bmh.add(10, "b");
        bmh.decreaseKey("a", 1);
        bmh.decreaseKey("b", 2);

        BinaryMinHeap.Entry<Integer, String> min1 = bmh.extractMin();
        assertEquals((Integer) 1, min1.key);
        assertEquals("a", min1.value);

        BinaryMinHeap.Entry<Integer, String> min2 = bmh.extractMin();
        assertEquals((Integer) 2, min2.key);
        assertEquals("b", min2.value);
    }


}
