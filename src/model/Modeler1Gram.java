package model;

import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modeler1Gram implements Modeler {
    private List<List<Integer>> tokens = new ArrayList<>();
    private Tokenizer t;
    private int nbTokens = 0;

    public Modeler1Gram(Tokenizer t) {
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
            List<Integer> lineTokens = t.tokenize(line);
            nbTokens += lineTokens.size();
            tokens.add(lineTokens);
        }
        br.close();
    }

    public HashMap<Integer, Integer> model() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (List<Integer> t : tokens) {
            for (Integer token : t) {
                if (token == -1)
                    continue;

                if (result.containsKey(token))
                    result.replace(token, result.get(token) + 1);
                else
                    result.put(token, 1);
            }
        }

        return result;
    }

    public int getNbTokens() {
        return nbTokens;
    }
}
