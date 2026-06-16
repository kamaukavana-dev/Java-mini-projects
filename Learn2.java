public class StringBuild {
    public static void main(String[] args){
        java.lang.StringBuilder bd = new java.lang.StringBuilder();
        for(int i =0;i<10000;i++){
            bd.append(i);
        }
    }
}
