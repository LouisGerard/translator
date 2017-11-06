package text_manipulation;

import stats.Stats2Gram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Lattice {
    private HashMap<Integer, HashMap<Integer, Double>> alpha;
    private HashMap<Integer, HashMap<Integer, Integer>> beta;
    private HashMap<Integer, HashMap<Integer, Integer>> tokens;
    private Stats2Gram stats;

    public Lattice(Stats2Gram s, String path) throws IOException {
        stats = s;
        read(path);
    }

    public List<Integer> viterbi() {
        List<Integer> result = new ArrayList<>();
        int max;
        HashMap<Integer, HashMap<Integer, Double>> chances = stats.getChances();
        for (int col = 1; col < alpha.size(); ++col) {
            for (int line = 0; line < alpha.get(col).size(); ++line) {
                max = 0;
                int index2 = tokens.get(col).get(line);

                int k = 0;
                double alphaValue = Infinity;
                do {
                    int index1 = tokens.get(col - 1).get(k);
                    double alphaTmp = alpha.get(col - 1).get(k) + chances.get(index1).get(index2) + alpha.get(col).get(line);
                    if (alphaTmp < alphaValue) {
                        alphaValue = alphaTmp;
                        max = k;
                    }
                } while (k++ < alpha.get(col - 1).size());

                beta.get(col).put(line, max);
                result.add(tokens.get(col - 1).get(max));
                alpha.get(col).put(line, alphaValue);
            }
        }
        return result;
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
    }
}
