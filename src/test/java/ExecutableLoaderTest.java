import org.junit.Test;
import ru.ifmo.ctddev.isaev.sofia.ExecutableLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author iisaev
 */
public class ExecutableLoaderTest {
    private final ExecutableLoader loader = new ExecutableLoader();

    @Test
    public void testExecutableLoader() {
        Process proc = loader.execute("a", "b", "c");
        BufferedReader sout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader serr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        sout.lines().forEach(System.out::println);
        System.out.println("_____________________");
        serr.lines().forEach(System.out::println);
    }
}
