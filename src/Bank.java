import java.util.Scanner;

class BankAccount {
    private final int accountNumber;
    private int balance;
    private final String accountName;
    private final int password;

    BankAccount(int accountNumber, int balance, String accountName, int password) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountName = accountName;
        this.password = password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getPassword() {
        return password;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
            System.out.println("New Account balance: $" + balance);
        } else {
            System.out.println("Deposit amount must be above zero!");
        }
    }

    public void withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount);
            System.out.println("New Account balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }
}

public class Bank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create multiple accounts
        BankAccount account1 = new BankAccount(111222, 30000, "Daniel Maina", 1234);
        BankAccount account2 = new BankAccount(555222, 50000, "John Maina", 4321);

        System.out.print("Enter Account name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        int number = scanner.nextInt();
        System.out.print("Enter Account password: ");
        int password = scanner.nextInt();

        BankAccount activeAccount = null;

        // Login check
        if (name.equalsIgnoreCase(account1.getAccountName()) &&
                number == account1.getAccountNumber() &&
                password == account1.getPassword()) {
            activeAccount = account1;
        } else if (name.equalsIgnoreCase(account2.getAccountName()) &&
                number == account2.getAccountNumber() &&
                password == account2.getPassword()) {
            activeAccount = account2;
        } else {
            System.out.println("Login failed! Wrong credentials.");
            System.exit(0);
        }

        System.out.println("\nWELCOME TO EQUITY BANK\n");

        boolean isAccount = true;
        while (isAccount) {
            System.out.print("""
                    1) Check Balance
                    2) Deposit to Account
                    3) Withdraw from Account
                    4) Exit
                    """);
            System.out.print("Choose option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Balance: $" + activeAccount.getBalance());
                case 2 -> {
                    System.out.print("Enter amount to deposit: ");
                    int amount = scanner.nextInt();
                    activeAccount.deposit(amount);
                }
                case 3 -> {
                    System.out.print("Enter amount to withdraw: ");
                    int amount = scanner.nextInt();
                    activeAccount.withdraw(amount);
                }
                case 4 -> {
                    isAccount = false;
                    System.out.println("Exiting Bank menu...");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}
