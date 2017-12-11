package trans;

import model.Modeler;
import model.Modeler2Gram;
import tokeniser.Tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TranslatorEnFr {
    private Tokenizer ten;
    private Modeler men;
    private Modeler mfr;
    private String transTable;

    public TranslatorEnFr(String lexiqueFr, String lexiqueEn, String corpusFr, String corpusEn, String transTable) throws IOException {
        Tokenizer tfr = new Tokenizer();
        tfr.init(lexiqueFr);
        Modeler mfr = new Modeler2Gram(tfr);
        mfr.init(corpusFr);

        this.mfr = mfr;

        Tokenizer ten = new Tokenizer();
        ten.init(lexiqueEn);
        Modeler men = new Modeler2Gram(ten);
        men.init(corpusEn);

        this.ten = ten;
        this.men = men;

        this.transTable = transTable;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getLattice(String sentence) throws IOException {
        List<Integer> sentenceTokens = ten.tokenize(sentence);
        HashMap<Integer, HashMap<Integer, Double>> lattice = new HashMap<>();

        for (int token : sentenceTokens) {
            BufferedReader br = new BufferedReader(new FileReader(this.transTable));
            boolean found = false;
            HashMap<Integer, Double> translatePossibilities = new HashMap<>();

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
                }
                else if (found)
                    break;
            }

            // handle not found
            if (translatePossibilities.size() == 0)
                translatePossibilities.put(-1, 10.0);

            lattice.put(token, translatePossibilities);
            br.close();
        }

        return lattice;
    }
}
