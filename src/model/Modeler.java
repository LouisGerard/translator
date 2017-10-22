package model;

import java.io.IOException;
import java.util.Map;

public interface Modeler {
    void init(String corpusPath) throws IOException;

    Map model();

    int getNbTokens();
}
