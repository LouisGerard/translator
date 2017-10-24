package stats;

import model.Modeler2Gram;
import tokeniser.Tokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats2Gram implements Stats {
    private HashMap<Integer, HashMap<Integer, Integer>> counts;
    private HashMap<Integer, HashMap<Integer, Double>> chances;
    private int size;
    private Tokenizer t;

    public Stats2Gram(Modeler2Gram m, Tokenizer t) {
        counts = m.model();
        size = m.getNbTokens();
        this.t = t;
    }

    public HashMap<Integer, HashMap<Integer, Double>> calculate() {
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();

        for (Map.Entry<Integer, HashMap<Integer, Integer>> entry : counts.entrySet()) {
            int token1 = entry.getKey();
            HashMap<Integer, Integer> tokenCounts = entry.getValue();
            HashMap<Integer, Double> toInsert = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry2 : tokenCounts.entrySet()) {
                int token2 = entry2.getKey();
                int count = entry2.getValue();

                double chance = (count + alpha) / (size + size * alpha);
                toInsert.put(token2, chance);
            }

            result.put(token1, toInsert);
        }

        chances = result;
        return result;
    }

    public double perplexity(String text) {
        double sumLogProb = 0;
        List<Integer> tokens =  t.tokenize(text.toLowerCase());
        int lastToken = 0;

        for (int token : tokens) {
            double chance;
            if (chances.containsKey(lastToken) && chances.get(lastToken).containsKey(token))
                chance = chances.get(lastToken).get(token);
            else
                chance = alpha / (size + size * alpha);
            sumLogProb += -Math.log(chance);
        }

        double avgLogProb = sumLogProb / tokens.size();
        return Math.pow(2, avgLogProb);
    }
}
