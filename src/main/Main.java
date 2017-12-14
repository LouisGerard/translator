package main;

import model.Modeler1Gram;
import model.Modeler2Gram;
import stats.Stats2Gram;
import tokeniser.Tokenizer;
import trans.Translator;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Translator l = null;
        Stats2Gram stats = null;
        try {
            /**/
            l = new Translator("res/data_ratp/lexique_ratp_fr.txt",
                    "res/data_ratp/lexique_ratp_en.txt",
                    "res/data_ratp/corpus_ratp_bilang.fr",
                    "res/data_ratp/table_ratp_en_fr_20iter.code");

            Tokenizer t = new Tokenizer("res/data_ratp/lexique_ratp_fr.txt");
            Modeler1Gram m1 = new Modeler1Gram(t);
            m1.init("res/data_ratp/corpus_ratp_bilang.en");

            Modeler2Gram m2 = new Modeler2Gram(t);
            m2.init("res/data_ratp/corpus_ratp_bilang.en");

            stats = new Stats2Gram(m1, m2, t);
            /*/
            l = new Translator("res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/corpus_jouet_en.txt",
                    "res/out/table.txt");

            Tokenizer t = new Tokenizer("res/data_jouet/lexique_jouet.txt");
            Modeler1Gram m1 = new Modeler1Gram(t);
            m1.init("res/data_jouet/corpus_jouet_fr.txt");

            Modeler2Gram m2 = new Modeler2Gram(t);
            m2.init("res/data_jouet/corpus_jouet_fr.txt");

            stats = new Stats2Gram(m1, m2, t);
            /** /
            l = new Translator("res/data_eval/lexique_eval.txt",
                    "res/data_eval/lexique_eval.txt",
                    "res/data_eval/corpus_alphabe.txt",
                    "res/out/table.txt");

            Tokenizer t = new Tokenizer("res/data_eval/lexique_eval.txt");
            Modeler1Gram m1 = new Modeler1Gram(t);
            m1.init("res/data_eval/corpus_chiffre.txt");

            Modeler2Gram m2 = new Modeler2Gram(t);
            m2.init("res/data_eval/corpus_chiffre.txt");

            stats = new Stats2Gram(m1, m2, t);
            /**/
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        stats.calculate();

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (Objects.equals(input, "quit"))
                break;

            System.out.println("perplexity : " + stats.perplexity(input));
            List<Integer> test = l.translate(input);

            for (int token : test) {
                System.out.print(token);
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }
}
