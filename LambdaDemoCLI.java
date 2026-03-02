import java.util.Arrays;
import java.util.List;

public class LambdaDemoCLI {
    public static void main(String[] args) {
        System.out.println("=== Lambda Demo CLI ===");

        // 1. Using lambda with Thread
        Thread t = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Lambda thread step " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        // 2. Using lambda with Collections
        List<String> names = Arrays.asList("Daniel", "Alice", "Bob", "Charlie");

        // Print each name using lambda
        names.forEach(n -> System.out.println("Name: " + n));

        // Filter names starting with 'C' using lambda + stream
        names.stream()
                .filter(n -> n.startsWith("C"))
                .forEach(n -> System.out.println("Filtered: " + n));

        try {
            t.join(); // wait for thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=== Demo Complete ===");
    }
}
