class Person{
    public String name = "Daniel";
    public int age = 19;
    public void Introduce(){
        System.out.println("Heeey!! My name is "+name+" and Iam "+age+" year's old.");
    }
}
class Student extends Person{
    String course = "Computer Science";
    @Override
    public void Introduce() {
        System.out.println("Heeey!! My name is "+name+" and Iam "+age+" year's old,pursuing "+course+" course.");
    }
}
public class Inheritance1 {
    public static void main(String[] args){
        Person p1 = new Person();
        Person s1 = new Student();
        p1.Introduce();
        s1.Introduce();
    }
}