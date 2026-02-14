public class Methods {
    static boolean isEven(int a){
        return a % 2 == 0;
    }

    public static void main(String[] args){
        int [] myArray = {1,2,3,4,5,6,7,8,9,10};
        for(int num : myArray){
            if (isEven(num)) {
                System.out.println(num + " is even.");
            } else {
                System.out.println(num + " is odd.");
            }
        }
    }
}
