import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence:");
        String sentence = scanner.nextLine().toLowerCase(); // make it case-insensitive

        // Split sentence into words
        String[] words = sentence.split(" ");

        // Map to store word counts
        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            if (wordCount.containsKey(word)) {//check if we've seen the value before
                wordCount.put(word, wordCount.get(word) + 1);//Then we increment it by 1 to add to its count
            } else {
                wordCount.put(word, 1);//if not we increment it just by one and its count will just be one
            }
        }

        // Print results
        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {//we use entryset to iterate through key and value pairs in the map
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}
