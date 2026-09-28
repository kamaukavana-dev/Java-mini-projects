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











