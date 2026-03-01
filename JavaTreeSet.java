import java.util.TreeSet;
public class JavaTreeSet {
    public static void main(String[] args) {
        TreeSet<Integer> numbers = new TreeSet<Integer>();
        numbers.add(8);
        numbers.add(5);
        numbers.add(2);
        numbers.add(7);
        for(Integer number : numbers){
            if(number % 2 == 0){
                System.out.println("Even :"+number);
            }
            else{
                System.out.println("Odd :"+number);
            }
        }
        System.out.println("size of arrays :"+numbers.size());
    }
}