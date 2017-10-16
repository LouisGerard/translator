package main;

import tokeniser.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static Node parse(String path) throws IOException {
        Node n = new Node(' ');
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            int cutIndex = line.indexOf(' ');
            int index = Integer.parseInt(line.substring(0, cutIndex));
            String word = line.substring(cutIndex+1);
            word = word.replace('_', ' ');
            n.insertWord(word, index);
        }
        return n.getL();
    }

    public static <T> boolean arrayContains(T[] array, T needle) {
        for (T item : array)
            if (needle == item)
                return true;
        return false;
    }
}
