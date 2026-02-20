import java.util.Arrays;
import java.util.Scanner;

public class Palindrome {
    // Check if an integer array is a palindrome
    public static boolean isPalindrome(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - 1 - i]) {
                return false; // mismatch  has been found
            }
        }
        return true; // no mismatch
    }

    // Check if a string is a palindrome
    public static boolean isPalindrome(String str) {
        // Normalize: remove spaces and lowercase
        String cleanStr = str.replaceAll("\\s+", "").toLowerCase();

        int left = 0;
        int right = cleanStr.length() - 1;

        while (left < right) {
            if (cleanStr.charAt(left) != cleanStr.charAt(right)) {
                return false; // mismatch found
            }
            left++;
            right--;
        }
        return true; // no mismatches
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Array Palindrome ---
        System.out.print("Enter the size of your array: ");
        int size = scanner.nextInt();

        if (size <= 0) {
            System.out.println("Array size must be greater than 0.");
            scanner.close();
            return;
        }

        int[] arr = new int[size];
        System.out.println("Enter " + size + " numbers:");
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("Your array: " + Arrays.toString(arr));
        if (isPalindrome(arr)) {
            System.out.println("The array is a palindrome!");
        } else {
            System.out.println("The array is NOT a palindrome.");
        }

        // --- String Palindrome ---
        scanner.nextLine(); // consume leftover newline
        System.out.print("Enter a word or phrase: ");
        String input = scanner.nextLine();

        if (isPalindrome(input)) {
            System.out.println("\"" + input + "\" is a palindrome!");
        } else {
            System.out.println("\"" + input + "\" is NOT a palindrome.");
        }

        scanner.close();
    }
}
