package stats;

import model.Modeler1Gram;
import tokeniser.Tokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats1Gram implements Stats {
    private HashMap<Integer, Integer> counts;
    private HashMap<Integer, Double> chances;
    private int size;
    private Tokenizer t;

    public Stats1Gram(Modeler1Gram m, Tokenizer t) {
        counts = m.model();
        size = m.getNbTokens();
        this.t = t;
    }

    public HashMap<Integer, Double> calculate() {
        HashMap<Integer, Double> result = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int token = entry.getKey();
            int count = entry.getValue();

            double chance = (count + alpha) / (size + size * alpha);
            result.put(token, chance);
        }

        chances = result;
        return result;
    }

    public double perplexity(String text) {
        double sumLogProb = 0;
        List<Integer> tokens =  t.tokenize(text);

        for (int token : tokens) {
            double chance;
            if (chances.containsKey(token))
                chance = chances.get(token);
            else
                chance = alpha / (size + size * alpha);
            sumLogProb += -Math.log(chance);
        }

        double avgLogProb = sumLogProb / chances.size();
        return Math.pow(2, avgLogProb);
    }
}
