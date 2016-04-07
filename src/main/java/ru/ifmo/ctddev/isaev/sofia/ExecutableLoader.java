package ru.ifmo.ctddev.isaev.sofia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author iisaev
 */
public class ExecutableLoader {

    private static final String OS_ARCH = System.getProperty("os.arch");

    private static final String OS_NAME = System.getProperty("os.name").replaceAll("[^a-zA-Z0-9.-]", "_");

    private static final String FILE_NAME = "sofia-ml";

    private final File executable;


    public ExecutableLoader() {
        String folderName = String.format("%s_%s", OS_NAME, OS_ARCH);
        this.executable = Paths.get("src/main/resources", folderName, FILE_NAME).toFile();
        if (!this.executable.exists()) {
            throw new IllegalStateException(
                    String.format("Compiled executiable not found for system %s and architecture %s", OS_NAME, OS_ARCH)
            );
        }
    }

    public Process execute(String... args) {
        return execute(Arrays.asList(args));
    }

    public Process execute(List<String> args) {
        try {
            List<String> commands = new ArrayList<>(args.size() + 1);
            commands.add(executable.getAbsolutePath());
            commands.addAll(args);
            ProcessBuilder builder = new ProcessBuilder(commands);
            return builder.start();
        } catch (IOException e) {
            throw new IllegalStateException("IO error", e);
        }
    }
}
