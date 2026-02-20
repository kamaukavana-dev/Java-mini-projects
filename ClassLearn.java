public class ClassLearn {
    String Employee_name;
    String position;
    String work;
    int hire_year;
    int salary ;

    // Constructor
    public ClassLearn(String Employee_name, String position, String work, int hire_year, int salary) {
        this.Employee_name = Employee_name;
        this.position = position;
        this.work = work;
        this.hire_year = hire_year;
        this.salary = salary;
    }

    // Method
    public void Info_detail() {
        System.out.println("The employee's name is " + Employee_name +
                ", position: " + position +
                ", work: " + work +
                ", hired in the year " + hire_year +
                ", earning $" + salary + " per year.");
    }

    public static void main(String[] args) {
        ClassLearn info1 = new ClassLearn("Daniel Maina", "Manager", "Supervision", 2021, 100000);
        ClassLearn info2 = new ClassLearn("John Maina", "Co-worker", "Regular job", 2025, 10000);

        info1.Info_detail();
        info2.Info_detail();
    }
}
