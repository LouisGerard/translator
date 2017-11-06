package stats;

import java.util.Map;

public interface Stats {
    double alpha = 0.001;

    Map calculate();

    double perplexity(String text);
}
