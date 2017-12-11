package structures;

public class TableTuple {
    private int tokenSrc, tokenDest;
    private double proba;

    public TableTuple(int tokenSrc, int tokenDest, double proba) {
        this.tokenSrc = tokenSrc;
        this.tokenDest = tokenDest;
        this.proba = proba;
    }

    public int getTokenSrc() {
        return tokenSrc;
    }

    public int getTokenDest() {
        return tokenDest;
    }

    public double getProba() {
        return proba;
    }
}
