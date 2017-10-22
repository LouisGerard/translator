package stats;

import java.util.Map;

public interface Stats {
    double alpha = 0.1;

    Map calculate();

    double perplexity();
}
