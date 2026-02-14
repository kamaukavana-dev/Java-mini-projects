import java.util.Arrays;
import java.util.Scanner;

public class Palindrome {
    public static boolean isPalindrome(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - 1 - i]) {
                return false; // mismatch found
            }
        }
        return true; // no mismatches, it's a palindrome
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of your array: ");
        int size = scanner.nextInt();

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

        scanner.close();
    }
}
