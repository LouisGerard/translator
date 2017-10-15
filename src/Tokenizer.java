import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Tokenizer {
    private Node head;
    private List<Integer> tokens;

    public void init(String path) throws IOException {
        head = Utils.parse(path);
    }

    public List<Integer> tokenize(String sentence) {
        List<Integer> result = new ArrayList<>();
        while (true) {
            Pair<Integer, Integer> found = head.getWordIndex(sentence);

            result.add(found.getKey());
            int subIndex;
            if (found.getKey() == -1)
                subIndex = sentence.indexOf(' ')+1;   // todo multiple separators
            else
                subIndex = Math.min(found.getValue()+1, sentence.length());
            sentence = sentence.substring(subIndex);
            if (Objects.equals(sentence, ""))
                break;
        }
        tokens = result;
        return result;
    }

    public HashMap<Integer, Integer> count() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (Integer token : tokens) {
            if (result.containsKey(token))
                result.replace(token, result.get(token)+1);
            else
                result.put(token, 1);
        }
        return result;
    }
}
