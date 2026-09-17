import java.text.Normalizer;
import java.util.Arrays;

public class Unicode {

    public static void main(String[] args) {

        // --------------------------------------------------
        // 1. Unicode escape sequences
        // --------------------------------------------------
        char romanEight = '\u2177';

        System.out.println("1. Unicode escape:");
        System.out.println("Character: " + romanEight);
        System.out.printf("Code point: U+%04X%n", (int) romanEight);


        // --------------------------------------------------
        // 2. Unicode characters from different scripts
        // --------------------------------------------------
        String multilingual = "English | 日本語 | العربية | हिन्दी | Ελληνικά | Kiswahili";

        System.out.println("\n2. Multiple writing systems:");
        System.out.println(multilingual);


        // --------------------------------------------------
        // 3. Unicode code points
        // --------------------------------------------------
        String text = "Java ☕ 🚀";

        System.out.println("\n3. Code points:");

        text.codePoints().forEach(codePoint -> {
            System.out.printf(
                    "Character: %-3s | Code point: U+%04X%n",
                    new String(Character.toChars(codePoint)),
                    codePoint
            );
        });

























