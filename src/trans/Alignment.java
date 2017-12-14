package trans;

import tokeniser.Tokenizer;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Alignment {
    public final double EPSILON = 0.0001;

    private String lexiconSrc;
    private String lexiconDest;
    private String corpusSrc;
    private String corpusDest;
    private String output;

    private HashMap<Integer, HashMap<Integer, Double>> probas;

    public Alignment(String lexiconSrc, String lexiconDest, String corpusSrc, String corpusDest, String output) {
        this.lexiconSrc = "res/" + lexiconSrc;
        this.lexiconDest = "res/" + lexiconDest;
        this.corpusSrc = "res/" + corpusSrc;
        this.corpusDest = "res/" + corpusDest;
        this.output = "res/" + output;
        probas = new HashMap<>();
    }

    public void em() throws IOException {
        Tokenizer tSrc = new Tokenizer(lexiconSrc);
        Tokenizer tDest = new Tokenizer(lexiconDest);

        List<Integer> tokensSrc  = getTokens(lexiconSrc);
        List<Integer> tokensDest = getTokens(lexiconDest);

        initProbas(tSrc.getSize());
        double maxChange;
        HashMap<Integer, HashMap<Integer, Double>> nb = new HashMap<>();
        for (int tokenSrc : tokensSrc) {
            nb.put(tokenSrc, new HashMap<>());
        }
        HashMap<Integer, Double> total = new HashMap<>();

        do {
            for (int tokenSrc : tokensSrc)
                for (int tokenDest : tokensDest)
                    nb.get(tokenSrc).put(tokenDest, 0.0);

            for (int tokenDest : tokensDest)
                total.put(tokenDest, 0.0);

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

            maxChange = updateProba(tokensSrc, tokensDest, nb, total);
        } while (maxChange > EPSILON);

        writeTable(tokensSrc, tokensDest);
    }

    private void writeTable(List<Integer> tokensSrc, List<Integer> tokensDest) throws IOException {
        DecimalFormatSymbols changeComma = new DecimalFormatSymbols();
        changeComma.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.######", changeComma);
        df.setRoundingMode(RoundingMode.CEILING);

        BufferedWriter bwOutput = new BufferedWriter(new FileWriter(output));
        for (int tokenSrc : tokensSrc)
            for (int tokenDest : tokensDest) {
                double proba = probas.get(tokenSrc).get(tokenDest);
                if (Double.isNaN(proba) || proba == 0.0)
                    continue;

                double logprob = -Math.log(proba);
                bwOutput.write(tokenSrc + " " + tokenDest + " " + df.format(logprob));
                bwOutput.newLine();
            }
        bwOutput.close();
    }

    private double updateProba(List<Integer> tokensSrc,
                               List<Integer> tokensDest,
                               HashMap<Integer, HashMap<Integer, Double>> nb,
                               HashMap<Integer, Double> total) {
        double maxChange = 0;
        for (int tokenSrc : tokensSrc) {
            for (int tokenDest : tokensDest) {
                double oldValue = probas.get(tokenSrc).get(tokenDest);
                if (Double.isNaN(oldValue) || oldValue < EPSILON)
                    continue;

                double newValue = nb.get(tokenSrc).get(tokenDest) / total.get(tokenDest);
                probas.get(tokenSrc).put(tokenDest, newValue);

                double change = Math.abs(newValue - oldValue);
                if (change > maxChange)
                    maxChange = change;
            }
        }

        return maxChange;
    }

    private void initProbas(int srcSize) throws IOException {
        BufferedReader brSrc  = new BufferedReader(new FileReader(lexiconSrc));

        while(true) {
            String lineSrc = brSrc.readLine();
            if (lineSrc == null) break;

            int cutIndexSrc = lineSrc.indexOf(' ');
            int tokenSrc = Integer.parseInt(lineSrc.substring(0, cutIndexSrc));
            probas.put(tokenSrc, new HashMap<>());


            BufferedReader brDest = new BufferedReader(new FileReader(lexiconDest));
            while(true) {
                String lineDest = brDest.readLine();
                if (lineDest == null) break;

                int cutIndexDest = lineDest.indexOf(' ');
                int tokenDest = Integer.parseInt(lineDest.substring(0, cutIndexDest));
                probas.get(tokenSrc).put(tokenDest, 1.0/srcSize);
            }
            brDest.close();
        }

        brSrc.close();
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
        Alignment a = new Alignment(args[0], args[1], args[2], args[3], args[4]);
        try {
            a.em();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
