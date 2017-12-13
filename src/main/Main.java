package main;

import trans.Translator;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Translator l = null;
        try {
            l = new Translator("res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/lexique_jouet.txt",
                    "res/data_jouet/corpus_jouet_fr.txt",
                    "res/out/table.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (Objects.equals(input, "quit"))
                break;

            List<Integer> test = l.translate(input);

            for (int token : test) {
                System.out.print(token);
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }
}
