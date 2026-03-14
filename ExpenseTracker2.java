import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

// Expense class to hold details
class Expense {
    String description;
    double amount;
    LocalDateTime timestamp; // date + time

    Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.timestamp = LocalDateTime.now(); // capture current date/time
    }

    @Override
    public String toString() {
        // Format timestamp for readability
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return description + " | $" + amount + " | " + timestamp.format(formatter);
    }
}

public class ExpenseTracker2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Expense> expenses = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();

                    Expense e = new Expense(desc, amt);
                    expenses.add(e);
                    System.out.println("Expense added at " + e.timestamp);
                    break;

                case 2:
                    System.out.println("\n--- Expense List ---");
                    for (Expense exp : expenses) {
                        System.out.println(exp);
                    }
                    break;

                case 3:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
