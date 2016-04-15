import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ctddev.isaev.sofia.*;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;


/**
 * @author iisaev
 */
public class WrapperAgainstPureTest {
    private final ExecutableLoader loader = new ExecutableLoader();

    @Before
    public void setUp() {
        File tempDir = new File("src/test/temp");
        if (tempDir.exists() && tempDir.isDirectory()) {
            Arrays.asList(tempDir.listFiles()).forEach(File::delete);
        }
    }

    public List<Instance> readDataset(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName)).lines()
                .map(s -> s.split("\\s+"))
                .map(arr -> {
                    int clazz = Integer.valueOf(arr[0]);
                    Map<Integer, Integer> features = new LinkedHashMap<>();
                    IntStream.range(1, arr.length).forEach(i -> {
                        String[] feature = arr[i].split(":");
                        features.put(Integer.valueOf(feature[0]), Integer.valueOf(feature[1]));
                    });
                    return new Instance(clazz, features);
                }).collect(Collectors.toList());
    }

    @Test
    public void testWrapperAgainstPure() throws IOException, InterruptedException {
        Process proc = loader.execute("--learner_type", "pegasos",
                "--loop_type", "stochastic",
                "--lambda", "0.1",
                "--iterations", "100000",
                "--dimensionality", "150000",
                "--training_file", "src/main/native/sofia-ml/demo/demo.train",
                "--model_out", "src/test/temp/model");
        BufferedReader sout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        sout.lines().forEach(System.out::println);
        System.out.println("_____________________");
        proc.waitFor();

        Process proc2 = loader.execute(
                "--model_in", "src/test/temp/model",
                "--test_file", "src/main/native/sofia-ml/demo/demo.test",
                "--results_file", "src/test/temp/predictions"
        );
        BufferedReader sout2 = new BufferedReader(new InputStreamReader(proc2.getInputStream()));
        sout2.lines().forEach(System.out::println);
        System.out.println("_____________________");
        proc2.waitFor();

        File resultsFile = new File("src/test/temp/predictions");
        List<Integer> runResults = new BufferedReader(new FileReader(resultsFile))
                .lines()
                .map(s -> s.split("\t")[1].trim())
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Instance> trainInstances = readDataset("src/main/native/sofia-ml/demo/demo.train");
        PrintWriter writer = new PrintWriter(new FileWriter("src/test/temp/temp.train"));
        trainInstances.forEach(writer::println);
        writer.close();
        List<Instance> testInstances = readDataset("src/main/native/sofia-ml/demo/demo.test");

        JSofiaClassifier classifier = new JSofiaClassifier();
        classifier.train(new TrainParams()
                .learnerType(LearnerType.PEGASOS)
                .loopType(LoopType.STOCHASTIC)
                .lambda(0.1)
                .iterations(100000)
                .dimensionality(150000), trainInstances);
        List<Integer> results = classifier.test(testInstances);

        assertArrayEquals("Results of simple call are equal to predefined",
                testInstances.stream().map(Instance::getClazz).toArray(), runResults.toArray());
        assertArrayEquals("Results of wrapper call are equal to predefined",
                testInstances.stream().map(Instance::getClazz).toArray(), results.toArray());
    }
}
