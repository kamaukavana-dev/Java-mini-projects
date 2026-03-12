import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    private static final String AMBIGUOUS = "Il1O0oS5B8G6Z2|";

    private String runMain(String... args) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            PasswordGenerator.main(args);
        } finally {
            System.setOut(originalOut);
        }
        return baos.toString().trim();
    }

    private String firstLine(String output) {
        int idx = output.indexOf('\n');
        if (idx == -1) return output;
        return output.substring(0, idx);
    }

    @Test
    public void generatesRequestedLength() {
        String out = runMain("--length", "24");
        String pwd = firstLine(out);
        assertEquals(24, pwd.length());
    }

    @Test
    public void excludesAmbiguousWhenRequested() {
        String out = runMain("--length", "20", "--no-ambiguous");
        String pwd = firstLine(out);
        for (char c : pwd.toCharArray()) {
            assertFalse("Found ambiguous char: " + c, AMBIGUOUS.indexOf(c) >= 0);
        }
    }

    @Test
    public void excludesCustomCharacters() {
        String out = runMain("--length", "18", "--exclude", "abc123");
        String pwd = firstLine(out);
        for (char c : "abc123".toCharArray()) {
            assertFalse("Found excluded char: " + c, pwd.indexOf(c) >= 0);
        }
    }

    @Test
    public void noRepeatProducesUniqueCharacters() {
        String out = runMain("--length", "12", "--no-repeat");
        String pwd = firstLine(out);
        Set<Character> seen = new HashSet<>();
        for (char c : pwd.toCharArray()) {
            assertTrue("Character repeated: " + c, seen.add(c));
        }
    }

    @Test
    public void lengthGreaterThanPoolShowsError() {
        String out = runMain("--length", "80", "--no-repeat", "--symbols", "!@");
        assertTrue("Expected length error, got: " + out,
                out.contains("Length exceeds unique characters available"));
    }
}
