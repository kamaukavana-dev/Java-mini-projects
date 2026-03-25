class Printer{
    public void print(String text){
        System.out.println(text);
    }
    public void print(int number){
        System.out.println(number);
    }
}
class Colorprinter extends Printer{
    //method overriding
    @Override
    public void print(String text){
        System.out.println("in color "+text );
    }
    //method overriding
    @Override
    public void print(int number){
        System.out.println("in color "+number );
    }
}
//main class
public class Overrideload {
    public static void main(String[] args){
        Printer p1 = new Printer();
        Printer p2 = new Colorprinter();
        p1.print("Red");
        p1.print(6);
        p2.print("Blue");
        p2.print(7);
    }
}