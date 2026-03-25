import java.util.Arrays;

public class ArrayStatistics1 {
    public static void main(String[] args){
        int[] myArray = {12, 45, 67, 23, 89, 34, 56, 78};
        int max = Arrays.stream(myArray).max().getAsInt();
        int min = Arrays.stream(myArray).min().getAsInt();
        int sum = Arrays.stream(myArray).sum();
        double average = Arrays.stream(myArray).average().getAsDouble();

        System.out.println("The largest number is: " + max);
        System.out.println("The smallest number is: " + min);
        System.out.println("The sum of all the numbers is: " + sum);
        System.out.println("The average of all the numbers is: " + average);
    }
}
