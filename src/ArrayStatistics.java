public class ArrayStatistics {
    public static void main(String[] args){
        int [] myArray = {12, 45, 67, 23, 89, 34, 56, 78};
        int max = myArray[0];
        int min = myArray[0];
        int sum = 0;
        int average;
        for(int array : myArray){
            if(array > max){
                max = array;

            }
            else if (array < min){
                min = array;

            }
            sum = sum + array;

        }
        System.out.println("The largest number is :"+ max );
        System.out.println("The smallest number is :"+ min);
        average = sum/myArray.length;
        double ave = average;
        System.out.println("The sum of all the numbers is :"+ sum);
        System.out.println("The average of all the numbers is :"+ ave);
    }
}
