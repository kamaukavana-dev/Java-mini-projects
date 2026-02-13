import java.util.Scanner;

public class quiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean playAgain = true;

        while (playAgain) {
            System.out.println("""
                    1) CHOOSE YOUR LUCKY NUMBER BETWEEN [1 & 20]
                    2) WELCOME TO NUMBER GUESSING GAME.
                    3) YOU HAVE ONLY THREE CHANCES.
                    4) NO REFUND UPON LOSS.
                    5) GOOD LUCK.....
                    """);

            int lucky_num = (int)(Math.random() * 20) + 1; // random number 1–20
            int tries = 3;

            while (tries > 0) {
                System.out.print("Choose your lucky number: ");
                int num = scanner.nextInt();

                if (num == lucky_num) {
                    System.out.println("You Won!!! 🎉");
                    break;
                } else if (Math.abs(lucky_num - num) == 2) {
                    System.out.println("You are nearly there...");
                } else if (num > lucky_num) {
                    System.out.println("Your number was too high!!!");
                } else {
                    System.out.println("Your number was too low!!");
                }

                tries--;
            }

            if (tries == 0) {
                System.out.println("Game Over! The lucky number was: " + lucky_num);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String ans = scanner.next().toLowerCase();

            if (ans.equals("no")) {
                playAgain = false;
                System.out.println("Exiting... Goodbye!");
            }
        }

        scanner.close();
    }
}
