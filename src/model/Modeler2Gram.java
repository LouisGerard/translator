package model;

import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modeler2Gram implements Modeler {
    private List<List<Integer>> tokens = new ArrayList<>();
    private Tokenizer t;
    private int nbTokens = 0;

    public Modeler2Gram(Tokenizer t) {
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
            tokens.add(lineTokens);
        }
    }

    public HashMap<Integer, HashMap<Integer, Integer>> model() {
        HashMap<Integer, HashMap<Integer, Integer>> result = new HashMap<>();

        for (List<Integer> t : tokens) {
            Integer lastToken = 0;

            for (Integer token : t) {
                if (token == -1)
                    continue;

                if (result.containsKey(lastToken)) {
                    HashMap<Integer, Integer> next = result.get(lastToken);

                    if (next.containsKey(token))
                        next.replace(token, next.get(token) + 1);
                    else
                        next.put(token, 1);
                }
                else {
                    ++nbTokens;
                    HashMap<Integer, Integer> toInsert = new HashMap<>();
                    toInsert.put(token, 1);
                    result.put(lastToken, toInsert);
                }
                lastToken = token;
            }
        }

        return result;
    }

    public int getNbTokens() {
        return nbTokens;
    }
}
