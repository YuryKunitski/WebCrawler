package by.minsk.softeq.kunitski.servise;

public enum Term {
    TESLA("Tesla"),
    MUSK("Musk"),
    GIGAFACTORY("Gigafactory"),
    ELON_MASK("Elon Mask");

    private final String toString;

    Term(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
