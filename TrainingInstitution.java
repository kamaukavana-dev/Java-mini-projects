/*
 * ============================================================
 *  UNIVERSITY : MERU UNIVERSITY OF SCIENCE AND TECHNOLOGY
 *  COURSE     : COMPUTER SCIENCE
 *  REG. NO.   : CT201/121593/25
 *  UNIT       : CIT 3203 OOP 2 JAVA
 *  YEAR       : YEAR 1 SEMESTER TWO
 * ============================================================
 *  PROJECT TITLE   : Training Institution Trainee Records
 *  PURPOSE         : Demonstrates arrays (1D, 2D) and control structures
 *                  (if/else, switch, for, enhanced for, while, do-while,
 *                  break) in one console application.
 * ============================================================
 */

import java.util.Scanner;

public class TrainingInstitution {

    // ---- Fill these in ----
    static final String UNIVERSITY = "MERU UNIVERSITY OF SCIENCE AND TECHNOLOGY";
    static final String COURSE     = "COMPUTER SCIENCE";
    static final String REG_NO     = "CT201/121593/25";
    static final String UNIT       = "CIT 3203 OOP 2 JAVA";
    static final String YEAR       = "YEAR 1 SEMESTER TWO";

    // ---- Data (arrays) ----
    static final int MAX_TRAINEES = 5;                        // arrays have fixed size
    static final String[] UNITS = {"Java", "Databases", "Networking"};
    static String[] names = new String[MAX_TRAINEES];         // 1D array: trainee names
    static int[][] marks = new int[MAX_TRAINEES][UNITS.length]; // 2D array: [trainee][unit]
    static int count = 0;                                     // number of trainees registered

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        int choice;
        do {                                                  // do-while: menu runs at least once
            printMenu();
            choice = readInt("Choice: ", 1, 6);
            switch (choice) {                                 // switch: discrete menu options
                case 1 -> register();
                case 2 -> viewAll();
                case 3 -> unitStatistics();
                case 4 -> search();
                case 5 -> ranking();
                case 6 -> System.out.println("Goodbye.");
            }
        } while (choice != 6);
    }

    // ---------- Display ----------
    static void printBanner() {
        System.out.println("University : " + UNIVERSITY);
        System.out.println("Course     : " + COURSE);
        System.out.println("Reg. No.   : " + REG_NO);
        System.out.println("Unit       : " + UNIT);
        System.out.println("Year       : " + YEAR);
    }

    static void printMenu() {
        System.out.println("\n=== TRAINING INSTITUTION MENU ===");
        System.out.println("1. Register trainee");
        System.out.println("2. View all trainees");
        System.out.println("3. Unit statistics");
        System.out.println("4. Search trainee");
        System.out.println("5. Ranking (best to worst)");
        System.out.println("6. Exit");
    }

    // ---------- Input with validation ----------
    static int readInt(String prompt, int min, int max) {
        while (true) {                                        // while: repeat until valid
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            System.out.println("Enter a whole number between " + min + " and " + max + ".");
        }
    }

    // ---------- Features ----------
    static void register() {
        if (count == MAX_TRAINEES) {                          // guard: array is full
            System.out.println("Class is full (" + MAX_TRAINEES + " trainees).");
            return;
        }
        String name;
        do {
            System.out.print("Trainee name: ");
            name = sc.nextLine().trim();
        } while (name.isEmpty());

        names[count] = name;
        for (int u = 0; u < UNITS.length; u++) {              // for: known number of units
            marks[count][u] = readInt("  " + UNITS[u] + " mark (0-100): ", 0, 100);
        }
        count++;
        System.out.println("Registered " + name + ".");
    }

    static double average(int trainee) {
        int sum = 0;
        for (int m : marks[trainee]) {                        // enhanced for: read every element
            sum += m;
        }
        return (double) sum / UNITS.length;                   // cast avoids integer division
    }

    static String grade(double avg) {
        if (avg >= 70)      return "A";                       // if-else chain: ranges
        else if (avg >= 60) return "B";
        else if (avg >= 50) return "C";
        else if (avg >= 40) return "D";
        else                return "E";
    }

    static void viewAll() {
        if (count == 0) {
            System.out.println("No trainees registered yet.");
            return;
        }
        System.out.printf("%-12s", "NAME");
        for (String u : UNITS) System.out.printf("%-12s", u);
        System.out.printf("%-8s%s%n", "AVG", "GRADE");

        for (int i = 0; i < count; i++) {
            System.out.printf("%-12s", names[i]);
            for (int m : marks[i]) System.out.printf("%-12d", m);
            double avg = average(i);
            System.out.printf("%-8.1f%s%n", avg, grade(avg));
        }
    }

    static void unitStatistics() {
        if (count == 0) {
            System.out.println("No data.");
            return;
        }
        for (int u = 0; u < UNITS.length; u++) {
            int sum = 0, high = marks[0][u], low = marks[0][u], passed = 0;
            for (int i = 0; i < count; i++) {
                int m = marks[i][u];
                sum += m;
                if (m > high) high = m;
                if (m < low)  low = m;
                if (m >= 50)  passed++;
            }
            System.out.printf("%-11s avg %.1f | high %d | low %d | passed %d/%d%n",
                    UNITS[u], (double) sum / count, high, low, passed, count);
        }
    }

    static void search() {
        System.out.print("Name to find: ");
        String key = sc.nextLine().trim();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (names[i].equalsIgnoreCase(key)) {             // equalsIgnoreCase, never ==
                double avg = average(i);
                System.out.printf("%s | average %.1f | grade %s%n", names[i], avg, grade(avg));
                found = true;
                break;                                        // break: stop once found
            }
        }
        if (!found) System.out.println("Trainee not found.");
    }


