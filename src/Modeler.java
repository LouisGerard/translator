import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Modeler {
    private List<List<Integer>> tokens = new ArrayList<>();
    private Tokenizer t;

    public Modeler(Tokenizer t) {
        this.t = t;
    }

    public void init(String corpusPath) throws IOException {
        // read corpus
        BufferedReader br = new BufferedReader(new FileReader(corpusPath));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            //get tokens
            tokens.add(t.tokenize(line));
        }
    }

    public String oneGram() {
        // count
        HashMap<Integer, Integer> count = new HashMap<>();
        for (List<Integer> t : tokens) {
            for (Integer token : t) {
                if (count.containsKey(token))
                    count.replace(token, count.get(token) + 1);
                else if (token != -1)
                    count.put(token, 1);
            }
        }

        // build String
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer, Integer> entry : count.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            sb.append(key);
            sb.append(' ');
            sb.append(value);
            sb.append('\n');
        }

        return sb.toString();
    }

    public String twoGram() {
        // count
        HashMap<Integer, HashMap<Integer, Integer>> count = new HashMap<>();
        for (List<Integer> t : tokens) {
            Integer lastToken = 0;
            for (Integer token : t) {
                if (token == -1)
                    continue;
                if (count.containsKey(lastToken)) {
                    HashMap<Integer, Integer> next = count.get(lastToken);
                    if (next.containsKey(token))
                        next.replace(token, next.get(token) + 1);
                    else
                        next.put(token, 1);
                }
                else {
                    HashMap<Integer, Integer> toInsert = new HashMap<>();
                    toInsert.put(token, 1);
                    count.put(lastToken, toInsert);
                }
                lastToken = token;
            }
        }


        // build String
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer, HashMap<Integer, Integer>> entry1 : count.entrySet()) {
            int key1 = entry1.getKey();
            HashMap<Integer, Integer> next = entry1.getValue();
            for(Map.Entry<Integer, Integer> entry2 : next.entrySet()) {
                int key2 = entry2.getKey();
                int value = entry2.getValue();

                sb.append(key1);
                sb.append(' ');
                sb.append(key2);
                sb.append(' ');
                sb.append(value);
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
