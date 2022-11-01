import org.junit.Test;

import java.util.*;
import static org.junit.Assert.*;

public class HuffmanTest {
    @Test(expected = IllegalArgumentException.class)
    public void testHuffmanNullSeed() {
        String s = null;
        Huffman h = new Huffman(s);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffman1Character1Count() {
        Huffman h = new Huffman("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffman1CharacterMultiplieCount() {
        Huffman h = new Huffman("aaaaa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffmanNullAlphabet() {
        Map<Character, Integer> test = null;
        Huffman h = new Huffman(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffmanEmptyAlphabet() {
        Map<Character, Integer> test = new HashMap<>();
        Huffman h = new Huffman(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffmanAlphabetContains1Character() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        Huffman h = new Huffman(test);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testHuffmanAlphabetNegativeFrequency() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', -5);
        Huffman h = new Huffman(test);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCompressInputNull() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', 5);
        test.put('b', 4);
        Huffman h = new Huffman(test);
        h.compress(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompressInputContainsOtherCharacters() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', 5);
        test.put('c', 4);
        Huffman h = new Huffman(test);
        h.compress("abcd");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecompressInputNull() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', 5);
        test.put('b', 4);
        Huffman h = new Huffman(test);
        h.decompress(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecompressInputContainsOtherCharacters() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', 5);
        test.put('c', 4);
        Huffman h = new Huffman(test);
        h.decompress("01201");

    }

    @Test(expected = IllegalStateException.class)
    public void testCompressionRatioCompressionCalledFalse() {
        Map<Character, Integer> test = new HashMap<>();
        test.put('a', 2);
        test.put('b', 5);
        test.put('c', 4);
        Huffman h = new Huffman(test);
        double cr = h.compressionRatio();

    }


    @Test
    public void testStringOfCharactersCount1() {
        Huffman test = new Huffman("abcd");
        test.decompress(test.compress("abcd"));
    }

    @Test
    public void testMapConstructorWithMap() {
        Map<Character, Integer> m = new HashMap<>();
        m.put('a', 1);
        m.put('b', 1);
        m.put('c', 1);
        m.put('d', 1);
        Huffman test = new Huffman(m);
        test.decompress(test.compress("abcd"));
    }

    @Test
    public void testCompressionRatio() {
        Huffman h = new Huffman("aabbbcccccdddddddd");

        assertEquals("ababacd", h.decompress(h.compress("ababacd")));
        assertEquals(18.0 / (7.0 * 16.0), h.compressionRatio(), .00001);

    }

    @Test
    public void testExpectedEncodingLength() {
        Huffman h = new Huffman("aabbbcccccdddddddd");
        double expecL = (2.0 * 3.0 + 3.0 * 3.0 + 5.0 * 2.0 + 8.0) / 18.0;
        assertEquals(expecL, h.expectedEncodingLength(), .00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecompressNotDecodable() {
        Huffman h = new Huffman("aabbccdd");
        h.decompress("001");
    }


}

