import  java.util.HashMap;


public class JavaHashMap {
    public static void main(String[] args) {
        HashMap<String,String> StudentInfo = new HashMap<String,String>();
        StudentInfo.put("Kelvin","grade 9");
        StudentInfo.put("Richard","grade 8");
        StudentInfo.put("Daniel","grade 7");
        StudentInfo.put("Jefferson","grade 6");
        StudentInfo.put("Kelvin","grade 5");//duplicate key
        StudentInfo.put("Samson","grade 9");//duplicate value
        //print key sets only
        for(String name : StudentInfo.keySet()){
            System.out.println(name);
        }
        //print values of the keysets only
        for(String name : StudentInfo.values()){
            System.out.println(name);
        }
        //print both the keysets and values
        for(String name : StudentInfo.keySet()){
            System.out.println(name+" : "+StudentInfo.get(name));
        }

    }
}