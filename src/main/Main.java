package main;

import trans.TranslatorEnFr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TranslatorEnFr l = null;
        HashMap<Integer, HashMap<Integer, Double>> lattice = null;
        try {
            l = new TranslatorEnFr("res/lexique2.fr_parsed.txt",
                    "res/lexique.en_parsed.txt",
                    "res/corpus2.fr.txt",
                    "res/corpus2.en.txt",
                    "res/table-traduction.en.fr.txt");
            l.set("I 'm going to Paris");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<Integer> test = l.viterbi();

        for (int token : test) {
            System.out.print(token);
            System.out.print(" ");
        }
        System.out.print('\n');

        System.out.println("The end\nLa fin\n");

    }
}
