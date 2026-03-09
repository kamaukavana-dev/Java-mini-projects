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
        questions.add(new QuizQuestion("Who wrote the novel \"1984\"?", Arrays.asList("Aldous Huxley", "George Orwell", "J.K. Rowling", "Ernest Hemingway"), 2, "George Orwell was the pen name of Eric Arthur Blair."));
        questions.add(new QuizQuestion("What is the largest animal currently on Earth?", Arrays.asList("African elephant", "Blue whale", "Giraffe", "Great white shark"), 2, "The blue whale is the largest known animal to have ever lived."));
        questions.add(new QuizQuestion("Which chemical element has the symbol O?", Arrays.asList("Osmium", "Gold", "Oxygen", "Oganesson"), 3, "O is the symbol for oxygen."));
        questions.add(new QuizQuestion("How many continents are there on Earth?", Arrays.asList("5", "6", "7", "8"), 3, "The commonly accepted count is seven."));
        questions.add(new QuizQuestion("Which programming language uses 'def' to declare a function?", Arrays.asList("Java", "Python", "C", "Go"), 2, "In Python, functions start with the def keyword."));
        questions.add(new QuizQuestion("What does HTTP status code 404 mean?", Arrays.asList("OK", "Moved Permanently", "Forbidden", "Not Found"), 4, "404 indicates the requested resource could not be found."));
        questions.add(new QuizQuestion("Which organ pumps blood throughout the human body?", Arrays.asList("Lungs", "Heart", "Liver", "Kidneys"), 2, "The heart pumps blood through the circulatory system."));
        questions.add(new QuizQuestion("What is the binary representation of the decimal number 5?", Arrays.asList("101", "110", "111", "100"), 1, "5 in binary is 101."));
        questions.add(new QuizQuestion("Which ocean is the largest by surface area?", Arrays.asList("Atlantic", "Indian", "Arctic", "Pacific"), 4, "The Pacific Ocean is the largest."));
        questions.add(new QuizQuestion("In what year was the first iPhone released?", Arrays.asList("2005", "2007", "2009", "2011"), 2, "Apple released the first iPhone in 2007."));
        questions.add(new QuizQuestion("Who painted the Mona Lisa?", Arrays.asList("Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Claude Monet"), 2, "Leonardo da Vinci painted the Mona Lisa."));
        questions.add(new QuizQuestion("What does CSS stand for in web development?", Arrays.asList("Computer Styled Sheets", "Creative Style System", "Cascading Style Sheets", "Content Style Syntax"), 3, "CSS stands for Cascading Style Sheets."));
        questions.add(new QuizQuestion("Which of these numbers is a prime?", Arrays.asList("21", "29", "39", "51"), 2, "29 is only divisible by 1 and itself."));
        questions.add(new QuizQuestion("Kathmandu is the capital city of which country?", Arrays.asList("Bhutan", "Nepal", "Laos", "Cambodia"), 2, "Kathmandu is the capital of Nepal."));
        questions.add(new QuizQuestion("What gas do plants primarily absorb during photosynthesis?", Arrays.asList("Oxygen", "Nitrogen", "Carbon dioxide", "Helium"), 3, "Plants take in carbon dioxide and release oxygen."));
        questions.add(new QuizQuestion("Which planet currently has the most known moons?", Arrays.asList("Earth", "Mars", "Jupiter", "Mercury"), 3, "Jupiter has the most confirmed moons discovered so far."));
        questions.add(new QuizQuestion("What is the boiling point of water at sea level in degrees Celsius?", Arrays.asList("90", "95", "100", "105"), 3, "Water boils at 100°C at standard atmospheric pressure."));

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
