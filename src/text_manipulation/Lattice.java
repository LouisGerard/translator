package text_manipulation;

import stats.Stats2Gram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Lattice {
    private HashMap<Integer, HashMap<Integer, Double>> alpha;
    private HashMap<Integer, HashMap<Integer, Integer>> beta;
    private HashMap<Integer, HashMap<Integer, Integer>> tokens;
    private Stats2Gram stats;

    public Lattice(Stats2Gram s, String path) throws IOException {
        stats = s;
        read(path);
    }

    /*public List<Integer> viterbi() {
        double max;
        for (int col = 1; col < alpha.size(); ++col) {
            for (int line = 0; line < alpha.get(col).size(); ++line) {
                max = 0;
                int index = tokens.get(col).get(line);

                double value = alpha.get(col-1).get(max) + stats.getChances().get()
                alpha.get(col).put(index, 0.0);
            }
        }
    }*/

    private void read(String path) throws IOException {
        int colNum = 0;
        int lineNum = 0;

        BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            int colIndex = line.indexOf("%col");
            if (colIndex == 0) {
                colNum = Integer.parseInt(line.substring(colIndex + 1));
                alpha.put(colNum, new HashMap<>());
                beta.put(colNum, new HashMap<>());
                tokens.put(colNum, new HashMap<>());
                lineNum = 0;
                continue;
            }

            int cutIndex = line.indexOf(' ');
            int index = Integer.parseInt(line.substring(0, cutIndex));
            double alphaTmp = Double.parseDouble(line.substring(cutIndex + 1));
            alpha.get(colNum).put(index, alphaTmp);
            beta.get(colNum).put(index, 0);
            tokens.get(colNum).put(lineNum++, index);
        }
    }
}
