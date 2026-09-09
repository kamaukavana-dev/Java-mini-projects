import java.util.List;
import java.util.function.Predicate;

public class EmployeeReport {

    public static void main(String[] args) {
        // Use an immutable list directly
        List<Employee> employees = List.of(
                new Employee("Alice", "Engineering", 95000, 7),
                new Employee("Bob", "Marketing", 62000, 3),
                new Employee("Carol", "Engineering", 80000, 5),
                new Employee("David", "HR", 58000, 2),
                new Employee("Eve", "Engineering", 72000, 4),
                new Employee("Frank", "Marketing", 74000, 6)
        );

        // 1. Find all employees in Engineering
        List<Employee> engineers = employees.stream()
                .filter(e -> "Engineering".equals(e.getDepartment()))
                .toList();

        System.out.println("Engineering team:");
        engineers.forEach(e -> System.out.println("  " + e));

        // 2. Find employees earning over 70000
        List<Employee> highEarners = employees.stream()
                .filter(e -> e.getSalary() > 70000)
                .toList();

        System.out.println("\nHigh earners (salary > 70000):");
        highEarners.forEach(e -> System.out.println("  " + e));





