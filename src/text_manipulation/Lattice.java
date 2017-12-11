package text_manipulation;

import stats.Stats2Gram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Lattice { /* TEST CLASS -> new version in trans.Translator
    private HashMap<Integer, HashMap<Integer, Double>> alpha;
    private HashMap<Integer, HashMap<Integer, Integer>> beta;
    private HashMap<Integer, HashMap<Integer, Integer>> tokens;
    private Stats2Gram stats;

    public Lattice(Stats2Gram s, String path) throws IOException {
        stats = s;
        alpha = new HashMap<>();
        beta = new HashMap<>();
        tokens = new HashMap<>();
        read(path);
    }

    public List<Integer> viterbi() {
        LinkedList<Integer> result = new LinkedList<>();
        int max;
        HashMap<Integer, HashMap<Integer, Double>> chances = stats.getChances();
        for (int line = 0; line < alpha.get(0).size(); ++line) {
            double alphaTmp = alpha.get(0).get(line);
            double chance = Infinity;
            int token = tokens.get(0).get(line);
            if (chances.get(0).containsKey(token)) {
                chance = chances.get(0).get(token);
            }
            alpha.get(0).put(line, alphaTmp + chance);
        }

        for (int col = 1; col < alpha.size(); ++col) {
            for (int line = 0; line < alpha.get(col).size(); ++line) {
                max = 0;
                int index2 = tokens.get(col).get(line);

                int k = 0;
                double alphaValue = Infinity;
                do {
                    int index1 = tokens.get(col - 1).get(k);
                    double chance = Infinity;
                    if (chances.containsKey(index1) && chances.get(index1).containsKey(index2)) {
                        chance = chances.get(index1).get(index2);
                    }
                    double alphaTmp = alpha.get(col - 1).get(k) + chance + alpha.get(col).get(line);
                    if (alphaTmp < alphaValue) {
                        alphaValue = alphaTmp;
                        max = k;
                    }
                } while (++k < alpha.get(col - 1).size());

                beta.get(col).put(line, max);
                alpha.get(col).put(line, alphaValue);
            }
        }

        int maxLastLine = 0;
        double maxValue = Infinity;
        for (int line = 0; line < alpha.get(alpha.size() - 1).size(); ++line) {
            double alphaValue = alpha.get(alpha.size() - 1).get(line);
            if (maxValue > alphaValue) {
                maxValue = alphaValue;
                maxLastLine = line;
            }
        }

        for (int col = alpha.size() - 1; col >= 0; --col) {
            result.addFirst(tokens.get(col).get(maxLastLine));
            maxLastLine = beta.get(col).get(maxLastLine);
        }
        return result;
    }

    public void testEmission() {
        for (int col = 0; col < alpha.size(); ++col) {
            int maxToken = 0;
            double maxValue = Infinity;
            for (int line = 0; line < alpha.get(col).size(); ++line) {
                double alphaValue = alpha.get(col).get(line);
                if (maxValue > alphaValue) {
                    maxValue = alphaValue;
                    maxToken = tokens.get(col).get(line);
                }
            }
            System.out.print(maxToken);
            System.out.print(' ');
        }
        System.out.print('\n');
    }

    private void read(String path) throws IOException {
        int colNum = 0;
        int lineNum = 0;

        BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            if (line.indexOf("%col") == 0) {
                int cutIndex = line.indexOf(' ');
                colNum = Integer.parseInt(line.substring(cutIndex + 1));
                alpha.put(colNum, new HashMap<>());
                beta.put(colNum, new HashMap<>());
                tokens.put(colNum, new HashMap<>());
                lineNum = 0;
                continue;
            }

            int cutIndex = line.indexOf(' ');
            int index = Integer.parseInt(line.substring(0, cutIndex));
            double alphaTmp = Double.parseDouble(line.substring(cutIndex + 1));
            alpha.get(colNum).put(lineNum, alphaTmp);
            beta.get(colNum).put(lineNum, 0);
            tokens.get(colNum).put(lineNum++, index);
        }
    }*/
}
