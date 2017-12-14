package trans;

import model.Modeler1Gram;
import model.Modeler2Gram;
import stats.Stats2Gram;
import structures.TableTuple;
import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Translator {
    private Tokenizer tsrc;
    private Stats2Gram stats;

    private ArrayList<ArrayList<Double>> alpha;
    private ArrayList<ArrayList<Integer>> beta;
    private ArrayList<ArrayList<Integer>> tokens;
    private LinkedList<TableTuple> transTable;

    public Translator(String lexiconDest, String lexiconSrc, String corpusDest, String transTable) throws IOException {
        Tokenizer tdest = new Tokenizer(lexiconDest);
        Modeler1Gram m1dest = new Modeler1Gram(tdest);
        m1dest.init(corpusDest);
        Modeler2Gram m2dest = new Modeler2Gram(tdest);
        m2dest.init(corpusDest);

        this.stats = new Stats2Gram(m1dest, m2dest, tdest);
        this.stats.calculate();

        this.tsrc = new Tokenizer(lexiconSrc);

        this.transTable = new LinkedList<>();
        initTransTable(transTable);
    }

    private void initTransTable(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        while(true) {
            String line = br.readLine();
            if (line == null)
                break;

            String[] lineSplitted = line.split(" ");
            int tokenSrc = Integer.parseInt(lineSplitted[0]);
            int tokenDest = Integer.parseInt(lineSplitted[1]);
            double proba = Double.parseDouble(lineSplitted[2]);
            transTable.add(new TableTuple(tokenSrc, tokenDest, proba));
        }
        br.close();
    }

    public List<Integer> translate(String sentence) {
        alpha = new ArrayList<>();
        beta = new ArrayList<>();
        tokens = new ArrayList<>();

        initLattice(sentence);
        viterbi();
        return getViterbiResult();
    }

    private void initLattice(String sentence) {
        List<Integer> sentenceTokens = tsrc.tokenize(sentence);

        int colNum = 0;
        for (int token : sentenceTokens) {
            boolean found = false;
            alpha.add(new ArrayList<>());
            beta.add(new ArrayList<>());
            tokens.add(new ArrayList<>());

            for (TableTuple tuple : transTable) {
                if (tuple.getTokenSrc() == token) {
                    found = true;
                    alpha.get(colNum).add(tuple.getProba());
                    beta.get(colNum).add(0);
                    tokens.get(colNum).add(tuple.getTokenDest());
                }
                else if (found)
                    break;
            }

            // handle not found
            if (alpha.get(colNum).size() == 0) {
                alpha.get(colNum).add(1.0);
                beta.get(colNum).add(0);
                tokens.get(colNum).add(-1);
            }

            ++colNum;
        }
    }

    private void viterbi() {
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
    }

    private List<Integer> getViterbiResult() {
        LinkedList<Integer> result = new LinkedList<>();

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

    public Tokenizer getTsrc() {
        return tsrc;
    }
}
