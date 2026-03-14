public class Recursive {
    public static int Rec(int n){
        if(n>1){
            return n*Rec(n-1);
        }
        else{
            return 1;
        }
    }
    public static void main(String[] args) {
        System.out.println( Rec(5));
    }
}