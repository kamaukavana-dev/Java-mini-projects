import java.io.*;
import java.util.Scanner;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n  🎰🎰 EXPENSE TRACKER 🎰🎰  \n");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Calculate Total");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> addExpense(scanner);
                case 2 -> viewExpenses();
                case 3 -> calculateTotal();
                case 4 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice <= 4);

        scanner.close();
    }

    private static void addExpense(Scanner scanner) {
        try {
            System.out.print("Enter category: ");
            String category = scanner.nextLine();

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount < 0) {
                throw new IllegalArgumentException("Expense cannot be negative!");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(category + " - " + amount);
                writer.newLine();
                System.out.println("Expense added successfully!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n  🎰🎰 EXPENSE TRACKER 🎰🎰  \n");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No expenses recorded yet.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void calculateTotal() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                total += Double.parseDouble(parts[1]);
            }
            System.out.println("Total Expenses: " + total);
        } catch (FileNotFoundException e) {
            System.out.println("No expenses recorded yet.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }
    }
}
