package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public enum LoopType {
    STOCHASTIC("stochastic"),
    BALANCED_STOCHASTIC("balanced-stochastic"),
    ROC("roc"),
    RANK("rank"),
    QUERY_NORM_RANK("query-norm-rank"),
    COMBINED_RANKING("combined-ranking"),
    COMBINED_ROC("combined-roc");

    private final String arg;

    LoopType(String str) {
        this.arg = str;
    }

    public String getArg() {
        return arg;
    }
}
