import java.util.LinkedHashMap;
public class JavaLinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> students = new LinkedHashMap<>();
        students.put("Kelvin", 1);
        students.put("Daniel", 2);
        students.put("Dennis", 3);
        students.put("Maurice", 4);
        students.put("Morris", 5);
        students.put("Kathleen", 6);
        for(String name : students.keySet()){
            System.out.println("name: " + name+"-position: " + students.get(name)+" overall");
        }
    }
}