package stats;

import model.Modeler;

import java.util.HashMap;
import java.util.Map;

public class Stats1Gram {
    private HashMap<Integer, Integer> counts;
    private HashMap<Integer, Double> chances;
    private int size;
    private double alpha = 0.1;

    public Stats1Gram(Modeler m) {
        counts = m.oneGram();
        size = m.getNbTokens();
    }

    public HashMap<Integer, Double> calculate() {
        double sizeLog = Math.log(size + size*alpha);

        HashMap<Integer, Double> result = new HashMap<>();
        for(Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int token = entry.getKey();
            int count = entry.getValue();

            result.put(token, - Math.log(count + alpha) - sizeLog);
        }

        chances = result;
        return result;
    }

    public double perplexity() {
        double sumLogProb = 0;
        for(Map.Entry<Integer, Double> entry : chances.entrySet())
            sumLogProb += entry.getValue();
        double avgLogProb = sumLogProb / chances.size();
        return Math.pow(2, avgLogProb);
    }
}
