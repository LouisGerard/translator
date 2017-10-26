package tokeniser;

import javafx.util.Pair;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tokenizer {
    private Node head;
    //private List<Integer> tokens;

    public void init(String path) throws IOException {
        head = Utils.parse(path);
    }

    public List<Integer> tokenize(String sentence) {
        sentence = sentence.toLowerCase();
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

        //tokens = result;
        return result;
    }
}
