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































