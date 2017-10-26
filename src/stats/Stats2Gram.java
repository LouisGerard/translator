package stats;

import model.Modeler1Gram;
import model.Modeler2Gram;
import tokeniser.Tokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats2Gram implements Stats {
    private HashMap<Integer, Integer> counts1;
    private HashMap<Integer, HashMap<Integer, Integer>> counts2;
    private HashMap<Integer, HashMap<Integer, Double>> chances;
    private Tokenizer t;
    private int size;

    public Stats2Gram(Modeler1Gram m1, Modeler2Gram m2, Tokenizer t) {
        counts1 = m1.model();
        counts2 = m2.model();
        size = m1.getNbTokens();
        this.t = t;
    }

    public HashMap<Integer, HashMap<Integer, Double>> calculate() {
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();

        for (Map.Entry<Integer, HashMap<Integer, Integer>> entry : counts2.entrySet()) {
            int token1 = entry.getKey();
            HashMap<Integer, Integer> tokenCounts = entry.getValue();
            HashMap<Integer, Double> toInsert = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry2 : tokenCounts.entrySet()) {
                int token2 = entry2.getKey();
                int count2 = entry2.getValue();

                double chance;
                if (token1 == 0) {
                    chance = ((double) count2 /*+ alpha*/) / (size /*+ size * alpha*/); // todo fix smoothing
                }
                else {
                    int count1 = counts1.get(token1);
                    chance = ((double) count2 /*+ alpha*/) / (count1 /*+ size * alpha*/);   // todo fix smoothing
                }

                toInsert.put(token2, chance);
            }

            result.put(token1, toInsert);
        }

        chances = result;
        return result;
    }

    public double perplexity(String text) {
        text = text.toLowerCase();
        double sumLogProb = 0;
        List<Integer> tokens =  t.tokenize(text);
        int lastToken = 0;

        for (int token : tokens) {
            double chance;
            if (chances.containsKey(lastToken) && chances.get(lastToken).containsKey(token))
                chance = chances.get(lastToken).get(token);
            else
                chance = alpha / (size + size * alpha);
            sumLogProb += -Math.log(chance);

            lastToken = token;
        }

        double avgLogProb = sumLogProb / tokens.size();
        return Math.exp(avgLogProb);
    }
}
