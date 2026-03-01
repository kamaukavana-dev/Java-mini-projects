import java.util.HashSet;

public class JavaHashSet {
    public static void main(String[] args) {
        HashSet<String> cities = new HashSet<String>();
        cities.add("New York");
        cities.add("London");
        cities.add("Paris");
        cities.add("San Francisco");
        cities.add("California");
        cities.add("Texas");
        cities.add("UK");
        for(String city : cities){
            System.out.println(city);
        }
        System.out.println(cities.contains("New York"));
    }
}