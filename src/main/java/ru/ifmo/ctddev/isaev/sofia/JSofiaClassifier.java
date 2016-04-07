package ru.ifmo.ctddev.isaev.sofia;

import java.util.List;
import java.util.stream.Stream;


/**
 * @author iisaev
 */
public class JSofiaClassifier {
    private boolean trained = false;

    private final ExecutableLoader executableLoader = new ExecutableLoader();

    public void train(List<List<Double>> trainDs) {
        train(trainDs.stream());
    }

    public void train(Stream<List<Double>> trainDs) {
        if (trained) {
            throw new IllegalStateException("Classifier has been already trained");
        }
        try {
            trained = true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to train on given dataset", e);
        }
    }

    public List<Double> test(Void testDs) {
        if (!trained) {
            throw new IllegalStateException("Classifier is not trained yet");
        }
        return null;
    }
}
