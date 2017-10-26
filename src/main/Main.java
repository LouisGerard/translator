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
            t.init("res/dico2_parsed.txt");
            m1.init("res/corpus.txt");
            m2.init("res/corpus.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Stats2Gram s = new Stats2Gram(m1, m2, t);
        s.calculate();

        System.out.println("je va à Paris : ");
        System.out.println(s.perplexity("je va à Paris "));
        System.out.println("-----------------------");
        System.out.println("on va à Paris : ");
        System.out.println(s.perplexity("on va à Paris"));
        System.out.println("-----------------------");
        System.out.println("je vais à Paris : ");
        System.out.println(s.perplexity("je vais à Paris"));
        System.out.println("-----------------------");
        System.out.println("on vais à Paris : ");
        System.out.println(s.perplexity("on vais à Paris"));
        System.out.println("-----------------------");
        System.out.println("tu vas à Paris : ");
        System.out.println(s.perplexity("tu vas à Paris"));
        System.out.println("-----------------------");
        System.out.println("elle vais à Paris : ");
        System.out.println(s.perplexity("elle vais à Paris"));
        System.out.println("-----------------------");
        System.out.println("elle va à Paris : ");
        System.out.println(s.perplexity("elle va à Paris"));
        System.out.println("-----------------------");
        System.out.println("tu vais à Paris");
        System.out.println(s.perplexity("tu vais à Paris"));
    }
}
