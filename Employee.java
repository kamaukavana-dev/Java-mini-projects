public class Employee {

    private String name;
    private String department;
    private double salary;
    private int yearsExperience;

    public Employee(String name, String department, double salary, int yearsExperience) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.yearsExperience = yearsExperience;
    }


    @Override
    public String toString() {
        return name + " | " + department + " | $" + salary + " | "
                + yearsExperience + " years";
    }
}

