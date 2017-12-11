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
            l = new TranslatorEnFr("res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/corpus_jouet_fr.txt",
                    "res/data_jouet/corpus_jouet_en.txt",
                    "res/data_jouet/table_trad_en2fr_10.txt");
            l.set("<s> the cool house is nice <s>");
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

    }
}
