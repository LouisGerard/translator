package tokeniser;

import javafx.util.Pair;
import structures.Node;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tokenizer {
    private Node head;
    private int size;

    public Tokenizer(String path) throws IOException {
        size = 0;
        Node n = new Node(' ');
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            int cutIndex = line.indexOf(' ');
            int index = Integer.parseInt(line.substring(0, cutIndex));
            String word = line.substring(cutIndex + 1);
            word = word.replace('_', ' ');
            n.insertWord(word, index);
            ++size;
        }
        head = n.getL();
    }

    public List<Integer> tokenize(String sentence) {
        List<Integer> result = new ArrayList<>();

        while (true) {
            Pair<Integer, Integer> found = head.getWordIndex(sentence);
            result.add(found.getKey());
            int subIndex;

            if (found.getKey() == -1) {
                subIndex = Utils.multiIndexOf(sentence, Node.SEPARATORS) + 1;
                if (subIndex == 0)
                    sentence = "";
            } else
                subIndex = Math.min(found.getValue() + 1, sentence.length());

            sentence = sentence.substring(subIndex);

            if (Objects.equals(sentence, ""))
                break;
        }

        return result;
    }

    public int getSize() {
        return size;
    }
}
