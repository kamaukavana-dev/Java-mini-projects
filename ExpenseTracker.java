import java.io.*;
import java.util.*;

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
            System.out.println("4. View Sorted Expenses");
            System.out.println("5. Exit");
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
                case 4 -> viewSortedExpenses(scanner); // ✅ new feature
                case 5 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
<<<<<<< HEAD
        } while (choice != 4); // ✅ loop exits cleanly when choice = 4
=======
        } while (choice != 5); // ✅ exit only when user chooses 5
>>>>>>> 1b96de5 (Adjusted new features into my mini projects)

        scanner.close();
    }

    // ------------------- ADD EXPENSE -------------------
    private static void addExpense(Scanner scanner) {
        try {
            System.out.print("Enter category: ");
            String category = scanner.nextLine().trim();

            if (category.isEmpty()) {
                System.out.println("Category cannot be empty!");
                return;
            }

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                System.out.println("Expense must be a positive number!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(category + " - " + amount);
                writer.newLine();
                System.out.println("Expense added successfully!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please enter a valid number.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // ------------------- VIEW EXPENSES -------------------
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

    // ------------------- CALCULATE TOTAL -------------------
    private static void calculateTotal() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
<<<<<<< HEAD
                if (parts.length == 2) {
=======
                if (parts.length == 2) { // ✅ safety check
>>>>>>> 1b96de5 (Adjusted new features into my mini projects)
                    total += Double.parseDouble(parts[1]);
                }
            }
            System.out.println("Total Expenses: " + total);
        } catch (FileNotFoundException e) {
            System.out.println("No expenses recorded yet.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }
    }

    // ------------------- VIEW SORTED EXPENSES -------------------
    private static void viewSortedExpenses(Scanner scanner) {
        List<String[]> expenses = new ArrayList<>();

        // Load expenses into a list
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    expenses.add(parts);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No expenses recorded yet.");
            return;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Ask user how to sort
        System.out.println("Sort by: 1. Category  2. Amount");
        int sortChoice;
        try {
            sortChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Defaulting to Category.");
            sortChoice = 1;
        }

        // Perform sorting
        if (sortChoice == 1) {
            // Sort alphabetically by category
            expenses.sort(Comparator.comparing(arr -> arr[0]));
        } else if (sortChoice == 2) {
            // Sort numerically by amount
            expenses.sort(Comparator.comparingDouble(arr -> Double.parseDouble(arr[1])));
        }

        // Display sorted expenses
        System.out.println("\n  🎰🎰 SORTED EXPENSES 🎰🎰  \n");
        for (String[] exp : expenses) {
            System.out.println(exp[0] + " - " + exp[1]);
        }
    }
}
