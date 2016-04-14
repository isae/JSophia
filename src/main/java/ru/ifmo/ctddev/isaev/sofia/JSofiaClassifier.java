package ru.ifmo.ctddev.isaev.sofia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;


/**
 * @author iisaev
 */
public class JSofiaClassifier {
    private boolean trained = false;

    private static final ExecutableLoader LOADER = new ExecutableLoader();

    public void train(TrainParams trainParams, List<Instance> trainDs) {
        train(trainParams, trainDs.stream());
    }

    public void train(TrainParams trainParams, Stream<Instance> trainDs) {
        if (trained) {
            throw new IllegalStateException("Classifier has been already trained");
        }
        try {
            Process process = LOADER.execute(trainParams.getArgs());
            OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.US_ASCII);
            new Thread(() -> {
                trainDs.forEach(inst -> {
                    String tmp = inst.toString();
                    try {
                        System.out.println("1");
                        writer.write(tmp);
                        System.out.println("2");
                        writer.write('\n');
                    } catch (IOException e) {
                        System.out.println(String.format("Failed to write string %s", tmp));
                        throw new RuntimeException(e);
                    }
                });
            }).start();
            writer.close();
            process.waitFor();
            BufferedReader sout2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            sout2.lines().forEach(System.out::println);
            //List<Double> model = sout2.lines().flatMap(s -> Stream.of(s.split("\\s+"))).map(Double::valueOf).collect(Collectors.toList());
            //model.forEach(System.out::println);

            trained = true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to train on given dataset", e);
        }
    }

    public List<Integer> test(Stream<Instance> testDs) {
        if (!trained) {
            throw new IllegalStateException("Classifier is not trained yet");
        }
        return null;
    }

    public List<Integer> test(List<Instance> testDs) {
        return test(testDs.stream());
    }
}
