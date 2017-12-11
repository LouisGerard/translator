package trans;

import model.Modeler1Gram;
import model.Modeler2Gram;
import stats.Stats2Gram;
import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class TranslatorEnFr {
    private Tokenizer ten;
    private Stats2Gram stats;
    private String transTable;

    private ArrayList<ArrayList<Double>> alpha;
    private ArrayList<ArrayList<Integer>> beta;
    private ArrayList<ArrayList<Integer>> tokens;

    public TranslatorEnFr(String lexiqueFr, String lexiqueEn, String corpusFr, String corpusEn, String transTable) throws IOException {
        Tokenizer tfr = new Tokenizer();
        tfr.init(lexiqueFr);
        Modeler1Gram m1fr = new Modeler1Gram(tfr);
        m1fr.init(corpusFr);
        Modeler2Gram m2fr = new Modeler2Gram(tfr);
        m2fr.init(corpusFr);

        this.stats = new Stats2Gram(m1fr, m2fr, tfr);
        this.stats.calculate();

        Tokenizer ten = new Tokenizer();
        ten.init(lexiqueEn);
        this.ten = ten;

        this.transTable = transTable;
        this.alpha = new ArrayList<>();
        this.beta = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    public void set(String sentence) throws IOException {
        List<Integer> sentenceTokens = ten.tokenize(sentence);

        int colNum = 0;
        for (int token : sentenceTokens) {
            BufferedReader br = new BufferedReader(new FileReader(this.transTable));
            boolean found = false;
            HashMap<Integer, Double> translatePossibilities = new HashMap<>();
            alpha.add(new ArrayList<>());
            beta.add(new ArrayList<>());
            tokens.add(new ArrayList<>());

            while(true) {
                String line = br.readLine();
                if (line == null)
                    break;

                String[] lineSplitted = line.split(" ");
                int tokenEn = Integer.parseInt(lineSplitted[0]);
                int tokenFr = Integer.parseInt(lineSplitted[1]);
                double proba = Double.parseDouble(lineSplitted[2]);
                if (tokenEn == token) {
                    found = true;
                    translatePossibilities.put(tokenFr, proba);
                    alpha.get(colNum).add(proba);
                    beta.get(colNum).add(0);
                    tokens.get(colNum).add(tokenFr);
                }
                else if (found)
                    break;
            }

            // handle not found
            if (translatePossibilities.size() == 0)
                translatePossibilities.put(-1, 10.0);

            br.close();
            ++colNum;
        }
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
            alpha.get(0).set(line, alphaTmp + chance);
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

                beta.get(col).set(line, max);
                alpha.get(col).set(line, alphaValue);
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
}
