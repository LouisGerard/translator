package trans;

import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class Alignment {
    public final double EPSILON = 0.0001;

    private String lexiconSrc  = "res/data_jouet/lexique_jouet.txt";
    private String lexiconDest = "res/data_jouet/lexique_jouet.txt";
    private String corpusSrc  = "res/data_jouet/corpus_jouet_en.txt";
    private String corpusDest = "res/data_jouet/corpus_jouet_fr.txt";
    private HashMap<Integer, HashMap<Integer, Double>> probas;

    public void em() throws IOException {
        Tokenizer tSrc = new Tokenizer(lexiconSrc);
        Tokenizer tDest = new Tokenizer(lexiconDest);

        List<Integer> tokensSrc  = getTokens(lexiconSrc);
        List<Integer> tokensDest = getTokens(lexiconDest);

        probas = initProbas(tSrc.getSize());
        double maxChange;

        do {
            HashMap<Integer, HashMap<Integer, Double>> nb = initNb(tokensSrc, tokensDest);
            HashMap<Integer, Double> total = initTotal(tokensDest);

            BufferedReader brCorpusScr  = new BufferedReader(new FileReader(corpusSrc));
            BufferedReader brCorpusDest = new BufferedReader(new FileReader(corpusDest));

            while (true) {
                String lineSrc = brCorpusScr.readLine();
                String lineDest = brCorpusDest.readLine();
                if (lineSrc == null || lineDest == null) break;

                List<Integer> corpusTokensSrc = tSrc.tokenize(lineSrc);
                List<Integer> corpusTokensDest = tDest.tokenize(lineDest);

                HashMap<Integer, Double> subTotal = normFactors(corpusTokensSrc, corpusTokensDest);

                for (int tokenSrc : corpusTokensSrc) {
                    if (tokenSrc == -1)  // todo improve unknown tokens handling
                        continue;

                    for (int tokenDest : corpusTokensDest) {
                        if (tokenDest == -1)  // todo improve unknown tokens handling
                            continue;

                        double oldNb = nb.get(tokenSrc).get(tokenDest);
                        double oldTotal = total.get(tokenDest);
                        double add = (probas.get(tokenSrc).get(tokenDest) / subTotal.get(tokenSrc));
                        nb.get(tokenSrc).put(tokenDest, oldNb + add);
                        total.put(tokenDest, oldTotal + add);
                    }
                }
            }

            brCorpusScr.close();
            brCorpusDest.close();

            maxChange = 0;
            for (int tokenSrc : tokensSrc) {
                for (int tokenDest : tokensDest) {
                    double oldValue = probas.get(tokenSrc).get(tokenDest);
                    if (oldValue < EPSILON)
                        continue;

                    double newValue = nb.get(tokenSrc).get(tokenDest) / total.get(tokenDest);
                    probas.get(tokenSrc).put(tokenDest, newValue);

                    double change = Math.abs(newValue - oldValue);
                    if (change > maxChange)
                        maxChange = change;
                }
            }
        } while (maxChange > EPSILON);

        System.out.println("Ok");
    }

    private HashMap<Integer, HashMap<Integer, Double>> initProbas(int srcSize) throws IOException {
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();

        BufferedReader brSrc  = new BufferedReader(new FileReader(lexiconSrc));

        while(true) {
            String lineSrc = brSrc.readLine();
            if (lineSrc == null) break;

            int cutIndexSrc = lineSrc.indexOf(' ');
            int tokenSrc = Integer.parseInt(lineSrc.substring(0, cutIndexSrc));
            result.put(tokenSrc, new HashMap<>());

            BufferedReader brDest = new BufferedReader(new FileReader("res/data_jouet/lexique_jouet.txt"));
            while(true) {
                String lineDest = brDest.readLine();
                if (lineDest == null) break;

                int cutIndexDest = lineDest.indexOf(' ');
                int tokenDest = Integer.parseInt(lineDest.substring(0, cutIndexDest));
                result.get(tokenSrc).put(tokenDest, 1.0/srcSize);
            }
            brDest.close();
        }

        brSrc.close();

        return result;
    }

    private HashMap<Integer, HashMap<Integer, Double>> initNb(List<Integer> tokensSrc, List<Integer> tokensDest) {
        HashMap<Integer, HashMap<Integer, Double>> result = new HashMap<>();

        for (int tokenSrc : tokensSrc) {
            result.put(tokenSrc, new HashMap<>());
            for (int tokenDest : tokensDest)
                result.get(tokenSrc).put(tokenDest, 0.0);
        }

        return result;
    }

    private HashMap<Integer, Double> initTotal(List<Integer> tokensDest) {
        HashMap<Integer, Double> result = new HashMap<>();

        for (int tokenDest : tokensDest)
            result.put(tokenDest, 0.0);

        return result;
    }

    private HashMap<Integer, Double> normFactors(List<Integer> tokensSrc, List<Integer> tokensDest) {
        HashMap<Integer, Double> result = new HashMap<>();

        for (int tokenSrc : tokensSrc) {
            double sum = 0.0;
            for (int tokenDest : tokensDest)
                if (tokenSrc != -1 && tokenDest != -1)  // todo improve unknown tokens handling
                    sum += probas.get(tokenSrc).get(tokenDest);
            result.put(tokenSrc, sum);
        }

        return result;
    }

    private List<Integer> getTokens(String lexicon) throws IOException {
        List<Integer> result = new LinkedList<>();

        BufferedReader br = new BufferedReader(new FileReader(lexicon));

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            int cutIndex = line.indexOf(' ');
            int token = Integer.parseInt(line.substring(0, cutIndex));
            result.add(token);
        }

        br.close();
        return result;
    }

    public static void main(String[] args) {

    }
}
