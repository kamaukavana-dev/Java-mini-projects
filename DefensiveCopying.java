import java.util.Arrays;

public class DefensiveCopying {
    private int [] myArr;

    private DefensiveCopying(int [] myArr){
        this.myArr = Arrays.copyOf(myArr, myArr.length);//Defensive copying: creates a copy of the input array to prevent external modifications from affecting the internal state of the object
    }

    private int [] getMyArr(){
        return Arrays.copyOf(myArr, myArr.length);//Defensive copying out
    }
    public static void main(String[] args){
        DefensiveCopying c = new DefensiveCopying(new int[]{1,2,3});
        int [] arr = c.getMyArr();
        arr[0] = 100; // Modifying the returned array does not affect the internal state of the DefensiveCopying instance
        System.out.println(Arrays.toString(c.getMyArr())); // Output: [1, 2, 3]
    }
}
