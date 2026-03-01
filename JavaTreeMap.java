import java.util.TreeMap;
public class JavaTreeMap {
    public static void main(String[] args) {
        TreeMap<String,Integer> grades = new TreeMap<>();
        grades.put("Kelvin",60);
        grades.put("Richard",76);
        grades.put("Daniel",95);
        grades.put("Jefferson",78);
        grades.put("Kelvin",85);//duplicate
        for(String name : grades.keySet()){
            System.out.println("name :"+name+" grade :"+grades.get(name));
        }
        System.out.println(grades.get("Kelvin"));
    }
}