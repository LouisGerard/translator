package main;

import model.Modeler1Gram;
import model.Modeler2Gram;
import stats.Stats2Gram;
import text_manipulation.Lattice;
import tokeniser.Tokenizer;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        Modeler1Gram m1 = new Modeler1Gram(t);
        Modeler2Gram m2 = new Modeler2Gram(t);
        try {
            t.init("res/lexique2.fr_parsed.txt");
            m1.init("res/corpus1.fr.txt");
            m2.init("res/corpus1.fr.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Stats2Gram s = new Stats2Gram(m1, m2, t);
        s.calculate();

        Lattice l = null;
        try {
            l = new Lattice(s, "res/treillis_test.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        l.testEmission();

        List<Integer> test = l.viterbi();

        for (int token : test) {
            System.out.print(token);
            System.out.print(", ");
        }
        System.out.print('\n');
    }
}
