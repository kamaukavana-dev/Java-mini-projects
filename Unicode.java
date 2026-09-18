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


        // --------------------------------------------------
        // 4. Why char is not always enough
        // --------------------------------------------------
        String emoji = "🚀";

        System.out.println("\n4. char vs code point:");

        System.out.println("String: " + emoji);
        System.out.println("String length: " + emoji.length());
        System.out.println("Code point count: " + emoji.codePointCount(0, emoji.length()));


        // --------------------------------------------------
        // 5. Surrogate pair inspection
        // --------------------------------------------------
        System.out.println("\n5. Surrogate pair:");

        char high = emoji.charAt(0);
        char low = emoji.charAt(1);

        System.out.printf("First char: U+%04X%n", (int) high);
        System.out.printf("Second char: U+%04X%n", (int) low);

        System.out.println(
                "Combined code point: U+"
                        + Integer.toHexString(Character.toCodePoint(high, low)).toUpperCase()
        );


        // --------------------------------------------------
        // 6. Unicode properties
        // --------------------------------------------------
        int codePoint = 'A';

        System.out.println("\n6. Unicode character properties:");

        System.out.println("Character: " + (char) codePoint);
        System.out.println("Is letter: " + Character.isLetter(codePoint));
        System.out.println("Is digit: " + Character.isDigit(codePoint));
        System.out.println("Is uppercase: " + Character.isUpperCase(codePoint));
        System.out.println("Unicode type: " + Character.getType(codePoint));


        // --------------------------------------------------
        // 7. Iterate correctly over Unicode code points
        // --------------------------------------------------
        String symbols = "A🚀B☕C🌍";

        System.out.println("\n7. Correct Unicode iteration:");

        for (int i = 0; i < symbols.length(); ) {

            int cp = symbols.codePointAt(i);

            System.out.printf(
                    "Index %-2d | Character %-2s | U+%04X%n",
                    i,
                    new String(Character.toChars(cp)),
                    cp
            );

            i += Character.charCount(cp);
        }


        // --------------------------------------------------
        // 8. Unicode normalization
        // --------------------------------------------------
        String composed = "é";
        String decomposed = "e\u0301";

        System.out.println("\n8. Unicode normalization:");

        System.out.println("Composed:   " + composed);
        System.out.println("Decomposed: " + decomposed);

        System.out.println("Equal before normalization: "
                + composed.equals(decomposed));

        String normalizedComposed =
                Normalizer.normalize(composed, Normalizer.Form.NFC);

        String normalizedDecomposed =
                Normalizer.normalize(decomposed, Normalizer.Form.NFC);

        System.out.println("Equal after NFC normalization: "
                + normalizedComposed.equals(normalizedDecomposed));


        // --------------------------------------------------
        // 9. Convert Unicode code point -> String
        // --------------------------------------------------
        int earth = 0x1F30D;

        String earthCharacter = new String(Character.toChars(earth));

        System.out.println("\n9. Code point -> character:");
        System.out.println("U+1F30D = " + earthCharacter);


        // --------------------------------------------------
        // 10. Convert character -> Unicode code point
        // --------------------------------------------------
        String character = "🌍";

        int extractedCodePoint = character.codePointAt(0);

        System.out.printf(
                "\n10. Character -> code point:%n%s = U+%04X%n",
                character,
                extractedCodePoint
        );
