package stats;

import java.util.Map;

public interface Stats {
    double alpha = 0.01;

    Map calculate();

    double perplexity(String text);
}
