import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initBoard();
        char currentPlayer = 'X';
        boolean gameOver = false;

        System.out.println("=== TIC TAC TOE ===");

        while (!gameOver) {
            printBoard();
            System.out.println("Player " + currentPlayer + ", enter your move (1-9): ");
            int move;
            try {
                move = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 1-9.");
                continue;
            }

            if (!makeMove(move, currentPlayer)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            if (checkWin(currentPlayer)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (isDraw()) {
                printBoard();
                System.out.println("It's a draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }

        scanner.close();
    }

    // Initialize board with empty spaces
    private static void initBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Print the board
    private static void printBoard() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char cell = board[i][j];
                if (cell == EMPTY) {
                    int pos = i * SIZE + j + 1;
                    System.out.print(" " + pos + " ");
                } else {
                    System.out.print(" " + cell + " ");
                }
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---+---+---");
        }
        System.out.println();
    }

    // Make a move
    private static boolean makeMove(int move, char player) {
        if (move < 1 || move > 9) return false;
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        if (board[row][col] != EMPTY) return false;
        board[row][col] = player;
        return true;
    }

    // Check win condition
    private static boolean checkWin(char player) {
        // Rows
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
        }
        // Columns
        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) return true;
        }
        // Diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;

        return false;
    }

    // Check draw
    private static boolean isDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }
}
