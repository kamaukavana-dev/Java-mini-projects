import java.io.*;
import java.nio.file.*;
import java.util.*;

public class QuizCliApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz CLI App!");
        boolean shuffle = false;
        boolean letters = false;
        boolean strict = false;
        int limit = 0;
        String fileArg = null;
        // simple arg parsing
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            switch (a) {
                case "--shuffle": shuffle = true; break;
                case "--letters": letters = true; break;
                case "--strict": strict = true; break;
                case "--limit":
                    if (i + 1 < args.length) {
                        try { limit = Integer.parseInt(args[++i]); } catch (NumberFormatException e) { System.out.println("Invalid limit value, ignoring."); }
                    } else {
                        System.out.println("--limit requires a number");
                    }
                    break;
                default:
                    if (!a.startsWith("-") && fileArg == null) fileArg = a;
                    break;
            }
        }

        List<QuizQuestion> questions = new ArrayList<>();
        // embedded default questions
        questions.add(new QuizQuestion("What is the capital of France?", Arrays.asList("Berlin", "London", "Paris", "Rome"), 3, "Paris is the capital of France."));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?", Arrays.asList("Earth", "Mars", "Jupiter", "Saturn"), 2, "Mars appears red due to iron oxide on its surface."));
        questions.add(new QuizQuestion("What is 2 + 2?", Arrays.asList("3", "4", "5"), 2, "Basic arithmetic."));

        if (fileArg != null) {
            Path p = Paths.get(fileArg);
            if (Files.exists(p) && Files.isReadable(p)) {
                try (BufferedReader br = Files.newBufferedReader(p)) {
                    String line;
                    int lineNo = 0;
                    boolean hadError = false;
                    while ((line = br.readLine()) != null) {
                        lineNo++;
                        Optional<QuizQuestion> maybe = QuizQuestion.parseLine(line, lineNo);
                        if (maybe.isPresent()) {
                            questions.add(maybe.get());
                        } else {
                            String msg = "Skipping invalid or empty line " + lineNo + " in file: " + p;
                            System.out.println(msg);
                            hadError = true;
                            if (strict) {
                                System.out.println("Strict mode enabled - aborting due to parse error.");
                                return;
                            }
                        }
                    }
                    if (questions.isEmpty()) {
                        System.out.println("No questions loaded from file (and no embedded questions). Exiting.");
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("Failed to read file: " + e.getMessage());
                    if (strict) return;
                }
            } else {
                System.out.println("Question file not found or not readable: " + p);
                if (strict) return;
            }
        }

        QuizRunner runner = new QuizRunner();
        runner.addQuestions(questions);
        Scanner scanner = new Scanner(System.in);
        QuizRunner.Config cfg = new QuizRunner.Config(letters, shuffle, limit);
        runner.runInteractive(scanner, System.out, cfg);
        scanner.close();
        System.out.println("Thanks for playing!");
    }
}
