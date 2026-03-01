import java.util.ArrayList;
import java.util.Iterator;

public class ArrayList1 {
    public static void main(String[] args) {
        ArrayList<String> cars = new ArrayList<>();
        cars.add("Bmw");
        cars.add("Ford");
        cars.add("Toyota");
        cars.add("Audi");
        cars.add("BMW");//repeated actually
        cars.add("Ford");//this too
        //we iterate through
        for(String car : cars){
            System.out.println(car);
        }
        //iterators
        Iterator<String> iterator = cars.iterator();
        while(iterator.hasNext()){
            String car = iterator.next();
            System.out.println(car);
        }



    }
}