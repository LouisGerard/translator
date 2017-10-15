import javafx.util.Pair;

public class Node {
    public final static Character[] SEPARATORS = {' ', ',', '!', '?', '.', ':'};
    private int index = -1;
    private char info;
    private Node l;
    private Node r;

    public Node(char info) {
        this.info = info;
    }

    public Node(char info, int index) {
        this.info = info;
        this.index = index;
    }

    public void setL(Node l) {
        this.l = l;
    }

    public void setR(Node r) {
        this.r = r;
    }

    public char getInfo() {
        return info;
    }

    public int getIndex() {
        return index;
    }

    public Node getL() {
        return l;
    }

    public Node getR() {
        return r;
    }

    public void insertWord(String word, int index) {
        if (word.length() == 0) return;
        char firstLetter = word.charAt(0);

        // Left
        if (firstLetter != info) {
            if (l == null) {
                l = new Node(firstLetter);
            }
            l.insertWord(word, index);
            return;
        }

        // Right
        if (word.length() != 1) {
            if (r == null) {
                r = new Node(word.charAt(1));
            }
            r.insertWord(word.substring(1), index);
        }
        else
            this.index = index;
    }

    public Pair<Integer, Integer> getWordIndex(String word) {
        return getWordIndex(word, -1, -1, 1);
    }

    private Pair<Integer, Integer> getWordIndex(String word, int lastIndexFound, int lastLength, int cpt) {
        if (word.length() == 0) return new Pair<>(lastIndexFound, lastLength);
        char firstLetter = word.charAt(0);

        // Right
        if (firstLetter == info) {
            if (index != -1) {
                if (word.length() == 1 || Utils.arrayContains(SEPARATORS, word.charAt(1))) {
                    lastIndexFound = index;
                    lastLength = cpt;
                }
            }
            if (r != null)
                return r.getWordIndex(word.substring(1), lastIndexFound, lastLength, cpt+1);
            return new Pair<>(lastIndexFound, lastLength); // word found
        }

        // Left
        if (l != null)
            return l.getWordIndex(word, lastIndexFound, lastLength, cpt);
        return new Pair<>(lastIndexFound, lastLength); // word found
    }
}
