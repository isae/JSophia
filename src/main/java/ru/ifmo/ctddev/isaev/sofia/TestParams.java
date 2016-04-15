package ru.ifmo.ctddev.isaev.sofia;

import java.util.ArrayList;
import java.util.List;


/**
 * @author iisaev
 */
public class TestParams {
    private final List<String> argsBuilder = new ArrayList<>();

    public List<String> getArgs() {
        return argsBuilder;
    }

    public TestParams() {
        argsBuilder.add("--stdin_test_data");
        argsBuilder.add("--test_file");
        argsBuilder.add("ololo");
        argsBuilder.add("--return_predictions");
        argsBuilder.add("--model_first_line");
    }

    private TestParams addArgument(String argName, String argValue) {
        argsBuilder.add(argName);
        argsBuilder.add(argValue);
        return this;
    }

    @Override
    public String toString() {
        return argsBuilder.toString();
    }
}
