import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class ReadFile {
    public static void main(String[] args) {
        String defaultPath = "/home/kavana-daniel/Downloads/Lesson04-Arrays and Functions.pdf";
        Path filePath = args.length == 0 ? Paths.get(defaultPath) : Paths.get(args[0]);

        if (args.length == 0) {
            System.out.println("No path provided; reading default file: " + filePath);
        }

        if (!Files.exists(filePath)) {
            System.err.println("File not found: " + filePath.toAbsolutePath());
            System.exit(1);
        }

        String extension = getExtension(filePath.getFileName().toString());

        try {
            if ("pdf".equals(extension)) {
                readPdf(filePath);
            } else {
                readText(filePath);
            }
        } catch (MalformedInputException e) {
            System.err.println("Could not read file as text (encoding issue): " + e.getMessage());
            System.exit(1);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String getExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot == -1 ? "" : fileName.substring(lastDot + 1).toLowerCase(Locale.ROOT);
    }

    private static void readText(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // Uses the system `pdftotext` command to extract text from PDFs.
    private static void readPdf(Path filePath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("pdftotext", "-layout", filePath.toString(), "-");
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("pdftotext exited with code " + exitCode);
        }
    }
}
