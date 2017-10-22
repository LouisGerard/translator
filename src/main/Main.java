package main;

import model.Modeler2Gram;
import stats.Stats2Gram;
import tokeniser.Tokenizer;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        Modeler2Gram m = new Modeler2Gram(t);
        try {
            t.init("/home/louis/Documents/cours/fil-tp1/res/dico.txt"); // todo change dico
            m.init("/home/louis/Documents/cours/fil-tp1/res/corpus.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Stats2Gram s = new Stats2Gram(m);
        HashMap<Integer, HashMap<Integer, Double>> chances = s.calculate();
        System.out.println(chances.get(77104).get(81900));
        System.out.println(s.perplexity());
    }
}
