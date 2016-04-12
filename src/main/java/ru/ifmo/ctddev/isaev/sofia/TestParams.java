package ru.ifmo.ctddev.isaev.sofia;

/**
 * @author iisaev
 */
public class TestParams {
    private final StringBuilder argsBuilder = new StringBuilder();

    public TestParams() {
        argsBuilder.append("--stdin_test_data");
        argsBuilder.append("--return_predictions");
    }

    private TestParams addArgument(String argName, String argValue) {
        argsBuilder.append(argName).append(" ").append(argValue);
        return this;
    }

    public TestParams model(String model) {
        return addArgument("--model_param", model);
    }

    @Override
    public String toString() {
        return argsBuilder.toString();
    }
}
