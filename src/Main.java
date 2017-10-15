import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        try {
            t.init("/home/louis/Documents/cours/fil-tp1/res/dico.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println(t.tokenize("le aze le petit petit petit chat"));
        System.out.println(t.count());
    }
}
