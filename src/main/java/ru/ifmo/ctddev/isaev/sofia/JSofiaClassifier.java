package ru.ifmo.ctddev.isaev.sofia;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.FileSystem;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author iisaev
 */
public class JSofiaClassifier {
    private boolean trained = false;

    private static final ExecutableLoader LOADER = new ExecutableLoader();

    private static final FileSystem FILE_SYSTEM = Jimfs.newFileSystem(Configuration.unix());

    private static final Random RANDOM = new Random();

    public void train(TrainParams trainParams, List<Instance> trainDs) {
        train(trainParams, trainDs.stream());
    }

    private String getRandomFileName() {
        return "/" + (new BigInteger(130, RANDOM).toString(32));
    }

    private String model;

    public void train(TrainParams trainParams, Stream<Instance> trainDs) {
        if (trained) {
            throw new IllegalStateException("Classifier has been already trained");
        }
        try {
            Process process = LOADER.execute(trainParams.getArgs());
            trainDs.forEach(inst -> {
                try {
                    process.getOutputStream().write(inst.toString().getBytes());
                    process.getOutputStream().write('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            process.getOutputStream().close();
            model = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine();
                    /*.lines()
                    .flatMap(s -> Stream.of(s.split("\\s+")))
                    .map(Double::valueOf)
                    .collect(Collectors.toList());*/
            int errCode = process.waitFor();
            System.out.println(String.format("Error code: %d", errCode));
            trained = true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to train on given dataset", e);
        }
    }

    public List<Integer> test(Stream<Instance> testDs) {
        if (!trained) {
            throw new IllegalStateException("Classifier is not trained yet");
        }
        try {
            Process process = LOADER.execute(new TestParams().getArgs());
            process.getOutputStream().write(model.getBytes());
            process.getOutputStream().write('\n');
            testDs.forEach(inst -> {
                try {
                    process.getOutputStream().write(inst.toString().getBytes());
                    process.getOutputStream().write('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            process.getOutputStream().close();
            return new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines()
                    .map(s -> s.split("\\s+"))
                    .map(arr -> Integer.valueOf(arr[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to train on given dataset", e);
        }
    }

    public List<Integer> test(List<Instance> testDs) {
        return test(testDs.stream());
    }
}
