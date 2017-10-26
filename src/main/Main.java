package main;

import model.Modeler1Gram;
import model.Modeler2Gram;
import stats.Stats2Gram;
import tokeniser.Tokenizer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        Modeler1Gram m1 = new Modeler1Gram(t);
        Modeler2Gram m2 = new Modeler2Gram(t);
        try {
            t.init("/home/louis/Documents/cours/fil-tp1/res/dico.txt"); // todo change dico
            m1.init("/home/louis/Documents/cours/fil-tp1/res/corpus.txt");
            m2.init("/home/louis/Documents/cours/fil-tp1/res/corpus.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Stats2Gram s = new Stats2Gram(m1, m2, t);
        s.calculate();
        System.out.println(s.perplexity("c'est pas le remboursement du mois hein je vous rassure hein"));
    }
}
