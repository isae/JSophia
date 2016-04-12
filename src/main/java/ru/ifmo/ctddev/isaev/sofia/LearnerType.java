package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public enum LearnerType {
    PEGASOS("pegasos"),
    PASSIVE_AGGRESSIVE("passive-aggressive"),
    MARGIN_PERCEPTRON("margin-perceptron"),
    ROMMA("romma"),
    SGD_SVM("sgd-svm"),
    LEAST_MEAN_SQUARES("least-mean-squares"),
    LOGREG("logreg"),
    LOGREG_PEGASOS("logreg-pegasos");

    private final String arg;

    LearnerType(String str) {
        this.arg = str;
    }

    public String getArg() {
        return arg;
    }
}
