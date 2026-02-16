public class Class1 {
    private String name;
    private int age;
    private String course;

    // Full constructor
    public Class1(String name, int age, String course){
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // Overloaded constructor (default age = 18)
    public Class1(String name, String course){
        this.name = name;
        this.course = course;
        this.age = 18;   // default value
    }

    // Getters and Setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    public String getCourse(){
        return course;
    }
    public void setCourse(String course){
        this.course = course;
    }

    // Overloaded Study methods
    public void Study(){
        System.out.println(name + " is currently " + age + " years old, studying " + course + ".");
    }

    public void Study(int hours){
        System.out.println(name + " is currently " + age + " years old, studying " + course + " for " + hours + " hours.");
    }

    // Main method for testing
    public static void main(String[] args){
        // Using full constructor
        Class1 info = new Class1("Daniel Maina", 20, "Computer Science");
        info.Study();
        info.Study(4);

        // Using overloaded constructor (default age = 18)
        Class1 info1 = new Class1("John Maina", "IT");
        info1.Study();
        info1.Study(2);
    }
}
