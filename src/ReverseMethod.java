import java.util.Arrays;
import java.util.Scanner;

public class ReverseMethod {
    public static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of your array:");
        int size = scanner.nextInt();

        int[] myArray = new int[size];
        System.out.println("Enter " + size + " numbers:");
        for (int i = 0; i < size; i++) {
            myArray[i] = scanner.nextInt();
        }

        System.out.println("Normal arrangement: " + Arrays.toString(myArray));
        System.out.println("Reverse arrangement: " + Arrays.toString(reverseArray(myArray)));

        scanner.close();
    }
}
