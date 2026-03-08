import java.util.*;

// Quiz question model
public class QuizQuestion {
    private final String prompt;
    private final List<String> choices;
    private final int correctIndex; // 1-based index
    private final String explanation; // optional

    public QuizQuestion(String prompt, List<String> choices, int correctIndex) {
        this(prompt, choices, correctIndex, null);
    }

    public QuizQuestion(String prompt, List<String> choices, int correctIndex, String explanation) {
        if (prompt == null || prompt.trim().isEmpty()) throw new IllegalArgumentException("Prompt cannot be empty");
        if (choices == null || choices.size() < 2) throw new IllegalArgumentException("There must be at least two choices");
        if (correctIndex < 1 || correctIndex > choices.size()) throw new IllegalArgumentException("correctIndex out of range");
        this.prompt = prompt.trim();
        this.choices = Collections.unmodifiableList(new ArrayList<>(choices));
        this.correctIndex = correctIndex;
        this.explanation = (explanation == null || explanation.trim().isEmpty()) ? null : explanation.trim();
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public Optional<String> getExplanation() { return Optional.ofNullable(explanation); }

    @Override
    public String toString() {
        return "QuizQuestion{" + "prompt='" + prompt + '\'' + ", choices=" + choices + ", correctIndex=" + correctIndex +
                (explanation != null ? ", explanation='" + explanation + "'" : "") + '}';
    }

    // Parse a line in the simple file format:
    // prompt | choice1;choice2;... | correctIndex(1-based) [| explanation]
    // Lines starting with # or blank are ignored by caller.
    public static Optional<QuizQuestion> parseLine(String line, int lineNumber) {
        if (line == null) return Optional.empty();
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("#")) return Optional.empty();
        // split on literal '|' character
        String[] parts = trimmed.split("\\|", -1);
        if (parts.length < 3) {
            return Optional.empty();
        }
        String prompt = parts[0].trim();
        String choicesPart = parts[1].trim();
        String indexPart = parts[2].trim();
        String explanation = parts.length >= 4 ? parts[3].trim() : null;
        if (prompt.isEmpty() || choicesPart.isEmpty() || indexPart.isEmpty()) return Optional.empty();
        String[] choiceArray = choicesPart.split(";");
        List<String> choices = new ArrayList<>();
        for (String c : choiceArray) {
            String s = c.trim();
            if (!s.isEmpty()) choices.add(s);
        }
        if (choices.size() < 2) return Optional.empty();
        try {
            int idx = Integer.parseInt(indexPart);
            // expecting 1-based index
            if (idx < 1 || idx > choices.size()) return Optional.empty();
            return Optional.of(new QuizQuestion(prompt, choices, idx, explanation));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
