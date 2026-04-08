import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Person1 {
    String name;

    Person1(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                  // same reference
        if (!(o instanceof Person1)) return false;    // must be same type
        Person1 p = (Person1) o;
        return Objects.equals(name, p.name);         // compare by name
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);                   // consistent with equals
    }
}

public class HashCode{
    public static void main(String[] args) {
        Set<Person1> set = new HashSet<>();

        Person1 p1 = new Person1("Daniel");
        Person1 p2 = new Person1("Daniel");


