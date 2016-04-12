package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public enum PredictionType {
    LINEAR("linear"),
    LOGISTIC("logistic");

    private final String arg;

    PredictionType(String str) {
        this.arg = str;
    }

    public String getArg() {
        return arg;
    }
}
