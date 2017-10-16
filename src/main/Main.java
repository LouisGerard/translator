package main;

import model.Modeler;
import stats.Stats1Gram;
import tokeniser.Tokenizer;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        Modeler m = new Modeler(t);
        try {
            t.init("/home/louis/Documents/cours/fil-tp1/res/dico.txt"); // todo change dico
            m.init("/home/louis/Documents/cours/fil-tp1/res/corpus.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Stats1Gram s = new Stats1Gram(m);
        HashMap<Integer, Double> chances = s.calculate();
        System.out.println(- Math.log(0.5) / Math.log(2));
        System.out.println(chances.get(77104));
        System.out.println(s.perplexity());
    }
}
