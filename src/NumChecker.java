import java.util.Scanner;
public class NumChecker {
    public static int largeNum(int[] myArray) {
        int max = myArray[0];
        for (int num : myArray) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static int smallNum(int[] myArray) {
        int min = myArray[0];
        for (int num : myArray) {
            if (num<min) {
                min = num;
            }
        }
        return min;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for array size
        System.out.print("Enter the size of your array: ");
        int size = scanner.nextInt();
        if (size <= 0) { System.out.println("Array size must be greater than 0."); return; }

        // Create array with that size
        int[] arr = new int[size];
        int sum = 0;
        float average;

        // Fill array with user input
        System.out.println("Enter " + size + " numbers: ");
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextInt();
            sum = sum + arr[i];
        }
        average = (float)sum/arr.length;

        // Call method to find largest or smallest number or the sum and the average
        System.out.println("The largest number is = " + largeNum(arr));
        System.out.println("The smallest number is = " + smallNum(arr));
        System.out.println("The sum of all the numbers is = "+ sum);
        System.out.println("The average of all the numbers is = "+ average);

        scanner.close();
    }
}
