import java.util.Scanner;

public class QuizGame {
    public static void main(String[] args) {
        System.out.print("""
                >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                ******************************************
                                CAT 1 💬
                ******************************************
                >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                """);

        String[] questions = {
                "Question(1). What is mostly referred to as the brain of the computer?",
                "Question(2). What is a computer?",
                "Question(3). Which language is mainly used for AI/ML?",
                "Question(4). Which language is used in making webpages?",
                "Question(5). Which language is used in enterprise and critical ecosystems?"
        };

        String[][] choices = {
                {"A. CPU", "B. GPU", "C. ROM", "D. RAM"},
                {"A. A Storage device", "B. A communication device",
                        "C. A machine used to accept, process, store and output data",
                        "D. A device used to make excel and powerpoints in enterprise level"},
                {"A. JavaScript", "B. Java", "C. pandas", "D. Python"},
                {"A. Typescript", "B. React", "C. HTML", "D. CSS"},
                {"A. Java", "B. Spring Boot", "C. Fortran", "D. C++"}
        };

        char[] answers = {'A', 'C', 'D', 'C', 'A'};

        Scanner sc = new Scanner(System.in);
        boolean isRun = true;

        while (isRun) {
            int score = 0; // reset score each run

            for (int i = 0; i < questions.length; i++) {
                System.out.println("\n" + questions[i]);
                for (String choice : choices[i]) {
                    System.out.println(choice);
                }

                try {
                    System.out.print("Your choice: ");
                    char ans = sc.nextLine().toUpperCase().charAt(0);

                    if (ans == answers[i]) {
                        System.out.println("Correct!! 🎉\n");
                        score++;
                    } else {
                        System.out.println("Incorrect 😢");
                        System.out.println("Correct answer is: " + answers[i] + "\n");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input!! " + e.getMessage());
                }
            }

            System.out.println("Total marks: " + (score * 10) + " out of " + (answers.length * 10) + "\n");
            System.out.print("Do you wish to repeat the test? ['yes' or 'no']: ");
            String repeat = sc.nextLine();

            if (repeat.equalsIgnoreCase("no") || repeat.equalsIgnoreCase("n")) {
                isRun = false;
                System.out.println("Byeee............... 👋");
            }
        }

        sc.close();
    }
}
