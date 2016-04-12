package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public enum EtaType {
    BASIC("basic"),
    PEGASOS("pegasos"),
    CONSTANT("constant");

    private final String arg;

    EtaType(String str) {
        this.arg = str;
    }

    public String getArg() {
        return arg;
    }
}
