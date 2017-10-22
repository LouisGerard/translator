package stats;

import model.Modeler;

import java.util.HashMap;
import java.util.Map;

public class Stats2Gram {
    private HashMap<Integer, HashMap<Integer, Integer>> counts;
    private HashMap<Integer, HashMap<Integer, Double>> chances;
    private int size;
    private double alpha = 0.1;

    public Stats2Gram(Modeler m) {
        counts = m.twoGram();
        size = m.getNb2Tokens();
    }

    public HashMap<Integer, HashMap<Integer, Double>> calculate() {
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();
        for (Map.Entry<Integer, HashMap<Integer, Integer>> entry : counts.entrySet()) {
            int token1 = entry.getKey();
            HashMap<Integer, Integer> count = entry.getValue();
            HashMap<Integer, Double> toInsert = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry2 : count.entrySet()) {
                int token2 = entry2.getKey();

                double chance = - Math.log((entry2.getValue() + alpha) / (size + size*alpha));
                toInsert.put(token2, chance);
            }
            result.put(token1, toInsert);
        }

        chances = result;
        return result;
    }

    public double perplexity() {
        double sumLogProb = 0;
        int size = 0;
        for (Map.Entry<Integer, HashMap<Integer, Double>> entry : chances.entrySet())
            for (Map.Entry<Integer, Double> entry2 : entry.getValue().entrySet()) {
                sumLogProb += entry2.getValue();
                ++size;
            }
        double avgLogProb = sumLogProb / size;
        return Math.pow(2, avgLogProb);
    }
}
