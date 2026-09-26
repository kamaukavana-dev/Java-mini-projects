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




    }
}





