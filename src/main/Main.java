package main;

import model.Modeler;
import tokeniser.Tokenizer;

import java.io.IOException;

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
        System.out.println("1-gram :");
        System.out.println(m.oneGram());
        System.out.println("---------------------------------------------");
        System.out.println("2-gram :");
        System.out.println(m.twoGram());
    }
}
