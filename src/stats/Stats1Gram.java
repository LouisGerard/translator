package stats;

import model.Modeler1Gram;

import java.util.HashMap;
import java.util.Map;

public class Stats1Gram implements Stats {
    private HashMap<Integer, Integer> counts;
    private HashMap<Integer, Double> chances;
    private int size;

    public Stats1Gram(Modeler1Gram m) {
        counts = m.model();
        size = m.getNbTokens();
    }

    public HashMap<Integer, Double> calculate() {
        double sizeLog = size + size * alpha;

        HashMap<Integer, Double> result = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int token = entry.getKey();
            int count = entry.getValue();

            double chance = -Math.log((count + alpha) / sizeLog);
            result.put(token, chance);
        }

        chances = result;
        return result;
    }

    public double perplexity() {
        double sumLogProb = 0;
        for (Map.Entry<Integer, Double> entry : chances.entrySet())
            sumLogProb += entry.getValue();
        double avgLogProb = sumLogProb / chances.size();
        return Math.pow(2, avgLogProb);
    }
}
