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






