import java.util.Arrays;
import java.util.List;

public class LambdaComparisonCLI {
    public static void main(String[] args) {
        System.out.println("=== Lambda Comparison Demo ===");

        // -----------------------------
        // 1. THREADS
        // -----------------------------

        // Old style: Runnable with anonymous class
        Runnable oldTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Old style thread running!");
            }
        };
        new Thread(oldTask).start();

        // Lambda style: Runnable with lambda
        Runnable lambdaTask = () -> System.out.println("Lambda thread running!");
        new Thread(lambdaTask).start();

        // -----------------------------
        // 2. COLLECTIONS (forEach)
        // -----------------------------
        List<String> names = Arrays.asList("Daniel", "Alice", "Bob", "Charlie");

        // Old style: for-each loop
        for (String n : names) {
            System.out.println("Old style name: " + n);
        }

        // Lambda style: forEach with lambda
        names.forEach(n -> System.out.println("Lambda name: " + n));

        // -----------------------------
        // 3. STREAMS (filtering)
        // -----------------------------

        // Old style: manual filtering
        for (String n : names) {
            if (n.startsWith("C")) {
                System.out.println("Old style filtered: " + n);
            }
        }

        // Lambda style: stream + filter
        names.stream()
                .filter(n -> n.startsWith("C"))
                .forEach(n -> System.out.println("Lambda filtered: " + n));

        System.out.println("=== Demo Complete ===");
    }
}
