import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListSorting {
    public static void main(String[] args) {
        ArrayList<String> cars = new ArrayList<>();
        cars.add("BMW");
        cars.add("Audi");
        cars.add("Ford");
        cars.add("Lexus");
        cars.add("Mercedes");
        Collections.sort(cars);//sorted the cars
        Collections.sort(cars, Collections.reverseOrder());//arranges in reverse order
        for(String car : cars){
            System.out.println(car);
        }
    }
}