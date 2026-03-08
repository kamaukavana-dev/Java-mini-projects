import java.io.PrintStream;
import java.util.*;

public class QuizRunner {
    private final List<QuizQuestion> questions = new ArrayList<>();

    public void addQuestions(Collection<QuizQuestion> qs) {
        if (qs != null) questions.addAll(qs);
    }

    public static class Config {
        public final boolean useLetters; // accept A/B/C answers
        public final boolean shuffle;
        public final int limit; // <=0 means no limit

        public Config(boolean useLetters, boolean shuffle, int limit) {
            this.useLetters = useLetters;
            this.shuffle = shuffle;
            this.limit = limit;
        }

        public static Config defaultConfig() { return new Config(false, false, 0); }
    }

    public QuizResult runInteractive(Scanner in, PrintStream out) {
        return runInteractive(in, out, Config.defaultConfig());
    }

    public QuizResult runInteractive(Scanner in, PrintStream out, Config cfg) {
        if (in == null) in = new Scanner(System.in);
        if (out == null) out = System.out;
        List<QuizQuestion> pool = new ArrayList<>(questions);
        if (cfg.shuffle) Collections.shuffle(pool);
        if (cfg.limit > 0 && cfg.limit < pool.size()) pool = pool.subList(0, cfg.limit);

        List<QuestionResult> results = new ArrayList<>();
        int questionNumber = 0;
        for (QuizQuestion q : pool) {
            questionNumber++;
            out.println();
            out.println("Question " + questionNumber + ": " + q.getPrompt());
            List<String> choices = q.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                String label = cfg.useLetters ? ("" + (char)('A' + i)) : Integer.toString(i + 1);
                out.println("  " + label + ") " + choices.get(i));
            }
            Integer answer = null;
            while (true) {
                out.print(cfg.useLetters ? "Your answer (A-" + (char)('A' + choices.size() - 1) + ", or 'q' to quit): " : "Your answer (1-" + choices.size() + ", or 'q' to quit): ");
                if (!in.hasNextLine()) {
                    // EOF
                    out.println();
                    break;
                }
                String line = in.nextLine().trim();
                if (line.equalsIgnoreCase("q") || line.equalsIgnoreCase("quit")) {
                    break;
                }
                if (line.isEmpty()) {
                    out.println("Please enter a choice.");
                    continue;
                }
                if (cfg.useLetters) {
                    String s = line.toUpperCase(Locale.ROOT);
                    if (s.length() != 1) {
                        out.println("Invalid input. Enter the letter of your choice (e.g., A).");
                        continue;
                    }
                    char c = s.charAt(0);
                    int idx = c - 'A' + 1; // 1-based
                    if (idx < 1 || idx > choices.size()) {
                        out.println("Letter out of range. Try again.");
                        continue;
                    }
                    answer = idx;
                    break;
                } else {
                    try {
                        int v = Integer.parseInt(line);
                        if (v < 1 || v > choices.size()) {
                            out.println("Number out of range. Try again.");
                            continue;
                        }
                        answer = v;
                        break;
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Enter the number of your choice.");
                    }
                }
            }
            if (answer == null) {
                // user quit or EOF
                break;
            }
            boolean correct = (answer == q.getCorrectIndex());
            results.add(new QuestionResult(q, answer, correct));
            String correctLabel = cfg.useLetters ? String.valueOf((char)('A' + q.getCorrectIndex() - 1)) : Integer.toString(q.getCorrectIndex());
            out.println(correct ? "Correct!" : "Incorrect. The correct answer: " + correctLabel + ") " + q.getChoices().get(q.getCorrectIndex() - 1));
            Optional<String> expOpt = q.getExplanation();
            if (expOpt.isPresent()) out.println("Explanation: " + expOpt.get());
        }
        int answered = results.size();
        int correct = 0;
        for (QuestionResult r : results) if (r.isCorrect()) correct++;
        QuizResult qr = new QuizResult(pool.size(), answered, correct, results);
        // summary
        out.println();
        out.println("Quiz finished. Score: " + correct + " / " + pool.size() + " (answered " + answered + ")");
        double pct = pool.isEmpty() ? 0.0 : (100.0 * correct / pool.size());
        out.printf("Percentage: %.1f%%\n", pct);
        return qr;
    }

    public static class QuestionResult {
        private final QuizQuestion question;
        private final int givenAnswer; // 1-based
        private final boolean correct;

        public QuestionResult(QuizQuestion question, int givenAnswer, boolean correct) {
            this.question = question;
            this.givenAnswer = givenAnswer;
            this.correct = correct;
        }

        public QuizQuestion getQuestion() { return question; }
        public int getGivenAnswer() { return givenAnswer; }
        public boolean isCorrect() { return correct; }
    }

    public static class QuizResult {
        private final int totalQuestions;
        private final int answered;
        private final int correct;
        private final List<QuestionResult> results;

        public QuizResult(int totalQuestions, int answered, int correct, List<QuestionResult> results) {
            this.totalQuestions = totalQuestions;
            this.answered = answered;
            this.correct = correct;
            this.results = Collections.unmodifiableList(new ArrayList<>(results));
        }

        public int getTotalQuestions() { return totalQuestions; }
        public int getAnswered() { return answered; }
        public int getCorrect() { return correct; }
        public List<QuestionResult> getResults() { return results; }
    }
}
