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
