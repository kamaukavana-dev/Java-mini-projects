import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReport {

    public static List<Employee> filterEmployees(List<Employee> list, Predicate<Employee> filter) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list) {
            if (filter.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static List<String> transformEmployees(List<Employee> list, Function<Employee, String> mapper) {
        List<String> result = new ArrayList<>();
        for (Employee e : list) {
            result.add(mapper.apply(e));
        }
        return result;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee("Alice",   "Engineering", 95000, 7),
                new Employee("Bob",     "Marketing",   62000, 3),
                new Employee("Carol",   "Engineering", 80000, 5),
                new Employee("David",   "HR",          58000, 2),
                new Employee("Eve",     "Engineering", 72000, 4),
                new Employee("Frank",   "Marketing",   74000, 6)
        ));

        // Find all employees in Engineering
        Predicate<Employee> inEngineering = e -> e.getDepartment().equals("Engineering");
        List<Employee> engineers = filterEmployees(employees, inEngineering);
        System.out.println("Engineering team:");
        engineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Engineering team:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Find employees earning over 70000
        Predicate<Employee> highEarner = e -> e.getSalary() > 70000;
        List<Employee> highEarners = filterEmployees(employees, highEarner);
        System.out.println("\nHigh earners (salary > 70000):");
        highEarners.forEach(e -> System.out.println("  " + e));
        // Output:
        // High earners (salary > 70000):
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]
        //   Frank [Marketing, $74000.0, 6 yrs]

        // Combine predicates: Engineering AND high earner
        List<Employee> seniorEngineers = filterEmployees(employees, inEngineering.and(highEarner));
        System.out.println("\nSenior Engineers (Engineering + salary > 70000):");
        seniorEngineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Senior Engineers (Engineering + salary > 70000):
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Get a list of all names
        Function<Employee, String> toName = e -> e.getName();
        List<String> names = transformEmployees(employees, toName);
        System.out.println("\nAll employee names: " + names);
        // Output:
        // All employee names: [Alice, Bob, Carol, David, Eve, Frank]

        // Get formatted summary strings
        Function<Employee, String> toSummary = e ->
                e.getName() + " earns $" + e.getSalary() + " with " + e.getYearsExperience() + " years of experience";
        List<String> summaries = transformEmployees(employees, toSummary);
        System.out.println("\nEmployee summaries:");
        summaries.forEach(s -> System.out.println("  " + s));
        // Output:
        // Employee summaries:
        //   Alice earns $95000.0 with 7 years of experience
        //   Bob earns $62000.0 with 3 years of experience
        //   Carol earns $80000.0 with 5 years of experience
        //   David earns $58000.0 with 2 years of experience
        //   Eve earns $72000.0 with 4 years of experience
        //   Frank earns $74000.0 with 6 years of experience

        // Print all employees using forEach with a Consumer lambda
        System.out.println("\nFull employee roster:");
        employees.forEach(e -> System.out.println("  " + e));
        // Output:
        // Full employee roster:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Bob [Marketing, $62000.0, 3 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   David [HR, $58000.0, 2 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]
        //   Frank [Marketing, $74000.0, 6 yrs]
    }
}