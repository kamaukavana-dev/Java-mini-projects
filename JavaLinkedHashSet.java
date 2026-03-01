import java.util.LinkedHashSet;
public class JavaLinkedHashSet {
    public static void main(String[] args) {
        LinkedHashSet<String> names = new LinkedHashSet<>();
        names.add("Daniel");
        names.add("David");
        names.add("Kelvin");
        names.add("Richard");
        names.add("Daniel");//duplicate will not be printed
        names.add("Jefferson");
        for(String name : names){
            System.out.println(name);
        }

    }
}