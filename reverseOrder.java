import java.util.Scanner;
public class reverseOrder {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();

        int[] myArray = new int[n];
        System.out.print("Enter "+n+" elements: ");
        for(int m=0;m<n;m++){
            myArray[m] = sc.nextInt();
        }
        System.out.println("REVERSED ARRAY ELEMENT");
        // Start from the last index (length - 1) down to 0
        for(int i = myArray.length - 1; i >= 0; i--){
            System.out.println(myArray[i]);
        }
        sc.close();
    }
}
