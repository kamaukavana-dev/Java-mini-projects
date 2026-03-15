import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final int SIZE = 3;
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    //use of enum
    private enum Mode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_AI_EASY,
        HUMAN_VS_AI_SMART
    }

    private static class Scoreboard {
        int xWins;
        int oWins;
        int draws;
         //Record who wins
        void recordWin(char mark) {
            if (mark == 'X') {
                xWins++;
            } else {
                oWins++;
            }
        }
       //Record the draw
        void recordDraw() {
            draws++;
        }

        void print() {
            System.out.println("Scoreboard -> X: " + xWins + " | O: " + oWins + " | Draws: " + draws);
        }
    }

    public static void main(String[] args) {
        Scoreboard scores = new Scoreboard();
        System.out.println("-----------------------");
        System.out.println("=== TIC TAC TOE CLI ===");
        System.out.println("-----------------------");
        System.out.println("q: quit at any menu, during a game type q to leave the round");

        boolean running = true;
        while (running) {
            Mode mode = promptMode();
            if (mode == null) {
                running = false;
                break;
            }

            boolean playAgain = true;
            while (playAgain) {
                playSingleGame(mode, scores);
                playAgain = askYesNo("Play again with the same mode? (y/n): ");
            }
        }

        System.out.println("Thanks for playing!");
    }

    private static void playSingleGame(Mode mode, Scoreboard scores) {
        char[][] board = initBoard();
        char current = 'X';
        char humanMark = 'X';
        char aiMark = 'O';

        if (mode != Mode.HUMAN_VS_HUMAN) {
            boolean humanFirst = askYesNo("Do you want to be X and go first? (y/n): ");
            humanMark = humanFirst ? 'X' : 'O';
            aiMark = (humanMark == 'X') ? 'O' : 'X';
            current = 'X';
        }

        boolean roundActive = true;
        while (roundActive) {
            printBoard(board);

            boolean humanTurn = mode == Mode.HUMAN_VS_HUMAN || current == humanMark;
            int move;
            if (humanTurn) {
                move = promptHumanMove(board, current);
                if (move == -1) {
                    System.out.println("Round cancelled.");
                    return;
                }
            } else {
                move = aiMove(board, aiMark, humanMark, mode);
                System.out.println("Computer chooses position " + move);
            }

            placeMark(board, move, current);

            if (checkWin(board, current)) {
                printBoard(board);
                System.out.println("Player " + current + " wins!");
                scores.recordWin(current);
                roundActive = false;
            } else if (isDraw(board)) {
                printBoard(board);
                System.out.println("It's a draw.");
                scores.recordDraw();
                roundActive = false;
            } else {
                current = (current == 'X') ? 'O' : 'X';
            }
        }

        scores.print();
    }

    private static Mode promptMode() {
        while (true) {
            System.out.println();
            System.out.println("Choose mode: 1) Human vs Human  2) Human vs AI (easy)  3) Human vs AI (smart)  q) Quit");
            String input = INPUT.nextLine().trim().toLowerCase();
            switch (input) {
                case "1":
                    return Mode.HUMAN_VS_HUMAN;
                case "2":
                    return Mode.HUMAN_VS_AI_EASY;
                case "3":
                    return Mode.HUMAN_VS_AI_SMART;
                case "q":
                    return null;
                default:
                    System.out.println("Invalid choice. Enter 1, 2, 3 or q.");
            }
        }
    }

    private static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = INPUT.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            if (input.equals("q")) return false;
            System.out.println("Please answer with y or n (or q to cancel).");
        }
    }

    private static int promptHumanMove(char[][] board, char mark) {
        while (true) {
            System.out.print("Player " + mark + " enter position (1-9, q to quit round): ");
            String input = INPUT.nextLine().trim().toLowerCase();
            if (input.equals("q")) return -1;
            int move;
            try {
                move = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Not a number. Try again.");
                continue;
            }

            if (!isValidMove(board, move)) {
                System.out.println("Invalid move. Choose an empty spot 1-9.");
                continue;
            }
            return move;
        }
    }

    private static int aiMove(char[][] board, char aiMark, char humanMark, Mode mode) {
        if (mode == Mode.HUMAN_VS_AI_EASY) {
            return randomMove(board);
        }
        return bestMoveStrong(board, aiMark, humanMark);
    }

    private static int randomMove(char[][] board) {
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            if (board[row][col] == EMPTY) {
                free.add(i + 1);
            }
        }
        return free.get(RANDOM.nextInt(free.size()));
    }

    // Unbeatable AI: immediate win/block, then alpha-beta minimax with center/corner priority
    private static int bestMoveStrong(char[][] board, char aiMark, char humanMark) {
        List<Integer> ordered = orderedMoves(board);

        // 1) Take a winning move if available
        for (int move : ordered) {
            int row = (move - 1) / SIZE;
            int col = (move - 1) % SIZE;
            board[row][col] = aiMark;
            boolean winNow = checkWin(board, aiMark);
            board[row][col] = EMPTY;
            if (winNow) return move;
        }

        // 2) Block opponent's immediate win
        for (int move : ordered) {
            int row = (move - 1) / SIZE;
            int col = (move - 1) % SIZE;
            board[row][col] = humanMark;
            boolean oppWins = checkWin(board, humanMark);
            board[row][col] = EMPTY;
            if (oppWins) return move;
        }

        // 3) Full search with alpha-beta, honoring our preferred ordering for tie breaks
        int bestScore = Integer.MIN_VALUE;
        int chosenMove = ordered.get(0);

        for (int move : ordered) {
            int row = (move - 1) / SIZE;
            int col = (move - 1) % SIZE;
            if (board[row][col] != EMPTY) continue;

            board[row][col] = aiMark;
            int score = minimax(board, 0, false, aiMark, humanMark, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board[row][col] = EMPTY;

            if (score > bestScore) {
                bestScore = score;
                chosenMove = move;
            }
        }
        return chosenMove;
    }

    private static List<Integer> orderedMoves(char[][] board) {
        // Preferred order: center, corners, then edges
        int[] preference = {4, 0, 2, 6, 8, 1, 3, 5, 7}; // zero-based indices
        List<Integer> moves = new ArrayList<>();
        for (int idx : preference) {
            int row = idx / SIZE;
            int col = idx % SIZE;
            if (board[row][col] == EMPTY) {
                moves.add(idx + 1); // convert to 1-9 position
            }
        }
        return moves;
    }

    private static int minimax(char[][] board, int depth, boolean isMaximizing, char aiMark, char humanMark, int alpha, int beta) {
        if (checkWin(board, aiMark)) return 10 - depth; // prefer faster wins
        if (checkWin(board, humanMark)) return depth - 10; // prefer slower losses
        if (isDraw(board)) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int move : orderedMoves(board)) {
                int row = (move - 1) / SIZE;
                int col = (move - 1) % SIZE;
                board[row][col] = aiMark;
                int score = minimax(board, depth + 1, false, aiMark, humanMark, alpha, beta);
                board[row][col] = EMPTY;
                best = Math.max(best, score);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break; // beta cut-off
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int move : orderedMoves(board)) {
                int row = (move - 1) / SIZE;
                int col = (move - 1) % SIZE;
                board[row][col] = humanMark;
                int score = minimax(board, depth + 1, true, aiMark, humanMark, alpha, beta);
                board[row][col] = EMPTY;
                best = Math.min(best, score);
                beta = Math.min(beta, best);
                if (beta <= alpha) break; // alpha cut-off
            }
            return best;
        }
    }

    private static boolean isValidMove(char[][] board, int move) {
        if (move < 1 || move > 9) return false;
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        return board[row][col] == EMPTY;
    }

    private static void placeMark(char[][] board, int move, char mark) {
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        board[row][col] = mark;
    }

    private static char[][] initBoard() {
        char[][] board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    private static void printBoard(char[][] board) {
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

    private static boolean checkWin(char[][] board, char mark) {
        // Rows
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) return true;
        }
        // Columns
        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] == mark && board[1][j] == mark && board[2][j] == mark) return true;
        }
        // Diagonals
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) return true;
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark) return true;

        return false;
    }

    private static boolean isDraw(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }
}
