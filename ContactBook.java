import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Simple CLI contact book that persists contacts to disk.
 * Data is stored in a tab-separated file located next to the program (contact_book.tsv).
 */
public class ContactBook {
    private static final Path STORE = Paths.get("contact_book.tsv");
    private final List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        ContactBook app = new ContactBook();
        app.loadContacts();
        app.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Contact Book ===");
            System.out.println("1. Add contact");
            System.out.println("2. List contacts");
            System.out.println("3. Search contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            choice = parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addContact(scanner);
                case 2 -> listContacts();
                case 3 -> searchContacts(scanner);
                case 4 -> deleteContact(scanner);
                case 5 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private void addContact(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        contacts.add(new Contact(name, phone, email));
        persist();
        System.out.println("Contact saved.");
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts yet.");
            return;
        }

        System.out.println("\n--- Contacts ---");
        int index = 1;
        for (Contact contact : contacts) {
            System.out.printf("%d. %s%n", index++, contact);
        }
    }

    private void searchContacts(Scanner scanner) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to search.");
            return;
        }

        System.out.print("Enter name, phone, or email to search: ");
        String query = scanner.nextLine().toLowerCase(Locale.ROOT);

        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.matches(query)) {
                if (!found) {
                    System.out.println("\nMatches:");
                }
                System.out.println(contact);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matches found.");
        }
    }

    private void deleteContact(Scanner scanner) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to delete.");
            return;
        }

        System.out.print("Enter name of contact to delete: ");
        String target = scanner.nextLine().toLowerCase(Locale.ROOT);

        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.name.equalsIgnoreCase(target)) {
                iterator.remove();
                persist();
                System.out.println("Deleted " + contact.name + ".");
                return;
            }
        }

        System.out.println("No contact found with that name.");
    }

    private void loadContacts() {
        if (!Files.exists(STORE)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(STORE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\t", -1);
                String name = parts.length > 0 ? parts[0] : "";
                String phone = parts.length > 1 ? parts[1] : "";
                String email = parts.length > 2 ? parts[2] : "";

                if (!name.isEmpty()) {
                    contacts.add(new Contact(name, phone, email));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read contacts: " + e.getMessage());
        }
    }

    private void persist() {
        try (BufferedWriter writer = Files.newBufferedWriter(STORE)) {
            for (Contact contact : contacts) {
                writer.write(contact.toTsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save contacts: " + e.getMessage());
        }
    }

    private int parseInt(String raw) {
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static final class Contact {
        private final String name;
        private final String phone;
        private final String email;

        Contact(String name, String phone, String email) {
            this.name = name.trim();
            this.phone = phone.trim();
            this.email = email.trim();
        }

        boolean matches(String query) {
            String lower = query.toLowerCase(Locale.ROOT);
            return name.toLowerCase(Locale.ROOT).contains(lower)
                    || phone.toLowerCase(Locale.ROOT).contains(lower)
                    || email.toLowerCase(Locale.ROOT).contains(lower);
        }

        String toTsv() {
            return escape(name) + "\t" + escape(phone) + "\t" + escape(email);
        }

        private String escape(String value) {
            return value.replace("\t", " ").trim();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(name);
            if (!phone.isEmpty()) {
                builder.append(" | ").append(phone);
            }
            if (!email.isEmpty()) {
                builder.append(" | ").append(email);
            }
            return builder.toString();
        }
    }
}
