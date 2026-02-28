import java.util.LinkedList;
public class LinkedList1 {
    public static void main(String[] args) {
        LinkedList<String> car = new LinkedList<String>();
        car.add("Audi");
        car.add("BMW");
        car.add("Ford");
        car.add("Chevy");
        System.out.println(car);
        System.out.println(car.indexOf("Audi"));
        System.out.println(car.indexOf("BMW"));
    }
}