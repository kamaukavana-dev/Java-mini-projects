public class reverseOrder {
    public static void main(String[] args){
        int[] myArray = {1, 2, 3, 4, 5};

        // Start from the last index (length - 1) down to 0
        for(int i = myArray.length - 1; i >= 0; i--){
            System.out.println(myArray[i]);
        }
    }
}
