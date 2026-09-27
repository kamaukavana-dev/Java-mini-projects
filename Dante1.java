import java.util.Scanner;

public class Dante1 {
    //Find the highest number method
    public static int largeNum(int[] myArray) {
        int max = myArray[0];
        for (int num : myArray) {
            if (num > max) {
                max = num;
            }
        }
        return max;
            Scanner scanner = new Scanner(System.in);

            // Ask user for array size
            System.out.print("Enter the size of your array: ");
            int size = scanner.nextInt();
            if (size <= 0) { System.out.println("Array size must be greater than 0.");
                return; }

            // Create array with that size
            int[] arr = new int[size];
            int sum = 0;
            float average;

            // Fill array with user input
            System.out.println("Enter " + size + " numbers: ");
            for (int i = 0; i < size; i++) {
                arr[i] = scanner.nextInt();
                sum = sum + arr[i];
            }         //convert sum and array_length to float
            average = (float)sum/arr.length;







