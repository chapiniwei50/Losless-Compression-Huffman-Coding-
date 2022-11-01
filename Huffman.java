

import java.util.*;

/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 */


public class Huffman {

    private Map<Character, Integer> frequencyMap;
    // character = character, Integer = frequency of the character
    private BinaryMinHeap<Integer, Node> heap;
    // Character = node character, Integer = frequency of the character
    private Map<Character, StringBuilder> alphabetEncoded;
    // Character = character, StringBuilder = encoded bits
    private Node tree;
    private double inputLength, outputLength;
    private boolean compressCalled;
    private int decompressIndex;


    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     *                                  1 character
     */
    public Huffman(String seed) {
        if (seed == null || seed.isEmpty()) {
            throw new IllegalArgumentException();
        }
        inputLength = 0;
        outputLength = 0;
        heap = new BinaryMinHeapImpl<>();
        frequencyMap = new HashMap<>();
        alphabetEncoded = new HashMap<>();

        for (int i = 0; i < seed.length(); i++) {

            if (frequencyMap.containsKey(seed.charAt(i))) {
                frequencyMap.replace(seed.charAt(i), frequencyMap.get(seed.charAt(i)) + 1);

            } else {
                frequencyMap.put(seed.charAt(i), 1);
            }
        }
        if (frequencyMap.size() == 1) {
            throw new IllegalArgumentException();
        }
        tree = mapToHeapReturnRoot(frequencyMap);

    }

    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     *                                  or has any non-positive frequencies
     */
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null || alphabet.isEmpty() || alphabet.size() < 2) {
            throw new IllegalArgumentException();
        }
        for (Integer i : alphabet.values()) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
        }
        inputLength = 0;
        outputLength = 0;
        heap = new BinaryMinHeapImpl<>();
        alphabetEncoded = new HashMap<>();
        frequencyMap = alphabet;
        tree = mapToHeapReturnRoot(frequencyMap);

    }

    public Node mapToHeapReturnRoot(Map<Character, Integer> map) {
        for (Character c : map.keySet()) {
            heap.add(map.get(c), new Node(c));
        }
        while (heap.size() > 1) {
            BinaryMinHeap.Entry<Integer, Node> firstLowest = heap.extractMin();
            BinaryMinHeap.Entry<Integer, Node> secondLowest = heap.extractMin();
            Node firstLowestN = firstLowest.value;
            Node secondLowestN = secondLowest.value;
            Node combinedNode = new Node(firstLowestN, secondLowestN);
            heap.add(firstLowest.key + secondLowest.key, combinedNode);
        }
        Node t = heap.extractMin().value;


        if (heap.isEmpty()) {
            heapToTree(t, new StringBuilder());
        }

        return t;

    }

    public void heapToTree(Node node, StringBuilder bits) {
        StringBuilder lT, rT;
        if (node.isLeaf) {
            alphabetEncoded.put(node.character, bits);
        }
        if (node.left != null) {
            lT = new StringBuilder(bits).append(0);
            heapToTree(node.left, lT);
        }
        if (node.right != null) {
            rT = new StringBuilder(bits).append(1);
            heapToTree(node.right, rT);
        }


    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     *                                  that are not compressible
     */
    public String compress(String input) {
        compressCalled = true;
        StringBuilder output;
        if (input == null) {
            throw new IllegalArgumentException();
        }
        output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (!alphabetEncoded.containsKey(input.charAt(i))) {
                throw new IllegalArgumentException();
            } else {
                output.append(alphabetEncoded.get(input.charAt(i)));
            }
        }
        inputLength += input.length();
        outputLength += output.length();
        return output.toString();

    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     *              matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     *                                  that are NOT 0 or 1, or input contains a sequence of bits
     *                                  that is not decodable
     */
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        decompressIndex = 0;
        StringBuilder result = new StringBuilder();

        while (decompressIndex < input.length()) {
            decompressIndex = decompressRecursion(tree, input, result, decompressIndex);
        }
        return result.toString();
    }

    public int decompressRecursion(Node n, String input, StringBuilder out, int index) {

        if (!n.isLeaf && index == input.length()) {
            throw new IllegalArgumentException();
        }

        if (n.isLeaf) {
            out.append(n.character);
            return index;

        }

        if (input.charAt(index) == '0') {
            return decompressRecursion(n.left, input, out, index + 1);
        } else if (input.charAt(index) != '1' && input.charAt(index) != '0') {
            throw new IllegalArgumentException();
        } else {
            return decompressRecursion(n.right, input, out, index + 1);
        }


    }

    /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     *                               calling this method
     */
    public double compressionRatio() {
        if (!compressCalled) {
            throw new IllegalStateException();
        }
        return (outputLength / (inputLength * 16.0));
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * <p>
     * The expected encoding length is simply the sum of the length of the encoding of each
     * character multiplied by the probability that character occurs.
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */
    public double expectedEncodingLength() {
        double expec = 0;
        double totCount = 0;
        for (Character c : alphabetEncoded.keySet()) {
            totCount += frequencyMap.get(c);
        }

        for (Character c : alphabetEncoded.keySet()) {
            expec += alphabetEncoded.get(c).length() * (frequencyMap.get(c) / totCount);
        }
        return expec;
    }

    class Node {
        Character character;
        Node left, right;
        boolean isLeaf;

        public Node(Character c) {
            isLeaf = true;
            character = c;


        }

        public Node(Node l, Node r) {
            isLeaf = false;
            left = l;
            right = r;
        }
    }
}


