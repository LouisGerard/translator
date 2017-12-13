package main;

import trans.Alignment;
import trans.Translator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Translator l = null;
        HashMap<Integer, HashMap<Integer, Double>> lattice = null;
        Alignment a = new Alignment();
        try {
            /*l = new Translator("res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/corpus_jouet_fr.txt",
                    "res/data_jouet/table_trad_en2fr_10.txt");*/
            a.em();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        /*List<Integer> test = l.translate("<s> the cool house is nice <s>");

        for (int token : test) {
            System.out.print(token);
            System.out.print(" ");
        }
        System.out.print('\n');*/

    }
}
