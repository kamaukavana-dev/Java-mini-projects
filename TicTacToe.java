import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Production-quality Tic-Tac-Toe CLI. Single file, zero dependencies.
 * 4 game modes, 3 AI difficulties, plain-text rendering, undo, session scoring.
 */
public class TicTacToe {

    // ANSI color helpers.

    static final class AnsiColors {
        static final String RESET      = "";
        static final String BOLD       = "";
        static final String RED        = "";
        static final String BLUE       = "";
        static final String CYAN       = "";
        static final String GRAY       = "";
        static final String DIM_GRAY   = "";
        static final String GREEN      = "";
        static final String YELLOW     = "";
        static final String WHITE      = "";
        static final String RED_ERR    = "";
        static final String WIN_BG     = "";
        static final String CLEAR      = "";

        private AnsiColors() {}

        /** Returns the ANSI color for the given mark. */
        static String markColor(char mark) {
            return mark == 'X' ? RED : BLUE;
        }
    }

    // Board state, clone, legal moves, and win checking.

    static final class Board {
        static final int SIZE = 3;
        static final int CELLS = SIZE * SIZE;
        static final char EMPTY = ' ';

        /** All 8 winning lines as flat-index triplets. */
        static final int[][] WIN_LINES = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        private final char[] cells;

        /** Creates an empty board. */
        Board() {
            cells = new char[CELLS];
            for (int i = 0; i < CELLS; i++) cells[i] = EMPTY;
        }

        private Board(char[] src) {
            cells = src.clone();
        }

        /** Returns a deep copy. AI simulations must use this. */
        Board copy() {
            return new Board(cells);
        }

        /** Returns the mark at flat index (0-8). */
        char get(int idx) { return cells[idx]; }

        /** Places a mark at flat index (0-8). */
        void set(int idx, char mark) { cells[idx] = mark; }

        /** Clears the cell at flat index. */
        void clear(int idx) { cells[idx] = EMPTY; }

        /** Returns true if the cell is empty. */
        boolean isEmpty(int idx) { return cells[idx] == EMPTY; }

        /** Converts row, col (0-based) to flat index. */
        static int toIndex(int row, int col) { return row * SIZE + col; }

        /** Returns true if board is full. */
        boolean isFull() {
            for (char c : cells) if (c == EMPTY) return false;
            return true;
        }

        /** Returns list of empty flat indices. */
        List<Integer> legalMoves() {
            List<Integer> moves = new ArrayList<>();
            for (int i = 0; i < CELLS; i++) if (cells[i] == EMPTY) moves.add(i);
            return moves;
        }

        /**
         * Returns the winning triple (3 flat indices) for the given mark,
         * or null if no win exists.
         */
        int[] checkWin(char mark) {
            for (int[] line : WIN_LINES) {
                if (cells[line[0]] == mark && cells[line[1]] == mark && cells[line[2]] == mark) {
                    return line;
                }
            }
            return null;
        }

        /** Returns true if the game is over (someone won or board full). */
        boolean isTerminal() {
            return checkWin('X') != null || checkWin('O') != null || isFull();
        }
    }

    // Move history stack with undo support.

    /** A single recorded move. */
    record Move(char mark, int cellIndex, int moveNumber) {}

    static final class MoveHistory {
        private final Deque<Move> stack = new ArrayDeque<>();
        private int counter = 0;

        /** Pushes a new move onto the history. */
        void push(char mark, int cellIndex) {
            stack.push(new Move(mark, cellIndex, ++counter));
        }

        /** Pops the last move, clearing it from the board. Returns the move, or null. */
        Move pop(Board board) {
            if (stack.isEmpty()) return null;
            Move m = stack.pop();
            board.clear(m.cellIndex());
            counter--;
            return m;
        }

        /** Returns true if there are moves to undo. */
        boolean canUndo() { return !stack.isEmpty(); }

        /** Returns the number of moves on the stack. */
        int size() { return stack.size(); }

        /** Resets for a new game. */
        void reset() { stack.clear(); counter = 0; }
    }

    // Session score tracker.

    static final class ScoreTracker {
        int xWins = 0;
        int oWins = 0;
        int ties  = 0;

        /** Records a win for the given mark. */
        void recordWin(char mark) { if (mark == 'X') xWins++; else oWins++; }

        /** Records a draw. */
        void recordTie() { ties++; }

        /** Returns total games played. */
        int total() { return xWins + oWins + ties; }
    }

    // AI player with minimax search.

    static final class AIPlayer {
        private static final Random RNG = new Random();
        /** Move ordering: center, corners, edges - better pruning. */
        private static final int[] ORDER = {4, 0, 2, 6, 8, 1, 3, 5, 7};

        private AIPlayer() {}

        /** Selects a cell index (0-8) based on difficulty. */
        static int chooseMove(Board board, char aiMark, char oppMark, Difficulty diff) {
            return switch (diff) {
                case EASY      -> moveRandom(board);
                case MEDIUM    -> moveMedium(board, aiMark, oppMark);
                case HARD      -> moveHard(board, aiMark, oppMark);
                case VERY_HARD -> moveVeryHard(board, aiMark, oppMark);
                case NIGHTMARE -> moveNightmare(board, aiMark, oppMark);
            };
        }

        /** Random legal move. */
        private static int moveRandom(Board board) {
            List<Integer> moves = board.legalMoves();
            return moves.get(RNG.nextInt(moves.size()));
        }

        /** Wins immediately, blocks opponent, else random. */
        private static int moveMedium(Board board, char ai, char opp) {
            int win = findInstantWin(board, ai);
            if (win != -1) return win;
            int block = findInstantWin(board, opp);
            if (block != -1) return block;
            return moveRandom(board);
        }

        /** Full minimax + alpha-beta. Unbeatable. */
        private static int moveHard(Board board, char ai, char opp) {
            int bestScore = Integer.MIN_VALUE;
            int bestMove = -1;
            for (int idx : ORDER) {
                if (!board.isEmpty(idx)) continue;
                Board sim = board.copy();
                sim.set(idx, ai);
                int score = minimax(sim, 0, false, ai, opp, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (score > bestScore) { bestScore = score; bestMove = idx; }
            }
            return bestMove;
        }

        /** Minimax with alpha-beta pruning on a cloned board. */
        private static int minimax(Board b, int depth, boolean maximizing,
                                   char ai, char opp, int alpha, int beta) {
            if (b.checkWin(ai)  != null) return 10 - depth;
            if (b.checkWin(opp) != null) return depth - 10;
            if (b.isFull()) return 0;

            if (maximizing) {
                int best = Integer.MIN_VALUE;
                for (int idx : ORDER) {
                    if (!b.isEmpty(idx)) continue;
                    Board sim = b.copy();
                    sim.set(idx, ai);
                    best = Math.max(best, minimax(sim, depth + 1, false, ai, opp, alpha, beta));
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) break;
                }
                return best;
            } else {
                int best = Integer.MAX_VALUE;
                for (int idx : ORDER) {
                    if (!b.isEmpty(idx)) continue;
                    Board sim = b.copy();
                    sim.set(idx, opp);
                    best = Math.min(best, minimax(sim, depth + 1, true, ai, opp, alpha, beta));
                    beta = Math.min(beta, best);
                    if (beta <= alpha) break;
                }
                return best;
            }
        }

        /** Returns the cell index that gives mark an instant win, or -1. */
        private static int findInstantWin(Board board, char mark) {
            for (int idx : ORDER) {
                if (!board.isEmpty(idx)) continue;
                Board sim = board.copy();
                sim.set(idx, mark);
                if (sim.checkWin(mark) != null) return idx;
            }
            return -1;
        }

        /** Very Hard: minimax plus fork-threat preference among ties. */
        private static int moveVeryHard(Board board, char ai, char opp) {
            int bestScore = Integer.MIN_VALUE;
            int bestForks = -1;
            int bestMove = -1;
            for (int idx : ORDER) {
                if (!board.isEmpty(idx)) continue;
                Board sim = board.copy();
                sim.set(idx, ai);
                int score = minimax(sim, 0, false, ai, opp, Integer.MIN_VALUE, Integer.MAX_VALUE);
                int forks = countThreats(sim, ai);
                if (score > bestScore || (score == bestScore && forks > bestForks)) {
                    bestScore = score;
                    bestForks = forks;
                    bestMove = idx;
                }
            }
            return bestMove;
        }

        /** Nightmare: minimax plus fork maximization and fewer opponent replies. */
        private static int moveNightmare(Board board, char ai, char opp) {
            int bestScore = Integer.MIN_VALUE;
            int bestTrap  = Integer.MIN_VALUE;
            int bestMove  = -1;
            for (int idx : ORDER) {
                if (!board.isEmpty(idx)) continue;
                Board sim = board.copy();
                sim.set(idx, ai);
                int score = minimax(sim, 0, false, ai, opp, Integer.MIN_VALUE, Integer.MAX_VALUE);
                int trap  = trapScore(sim, ai, opp);
                if (score > bestScore || (score == bestScore && trap > bestTrap)) {
                    bestScore = score;
                    bestTrap  = trap;
                    bestMove  = idx;
                }
            }
            return bestMove;
        }

        /**
         * Composite trap score: own threats up, opponent threats down,
         * opponent safe responses down. Higher means a more punishing position.
         */
        private static int trapScore(Board board, char ai, char opp) {
            int aiThreats    = countThreats(board, ai);
            int oppThreats   = countThreats(board, opp);
            int safeResp     = countSafeResponses(board, ai, opp);
            return aiThreats * 100 - oppThreats * 50 - safeResp * 30;
        }

        /** Counts lines where mark has 2 cells + 1 empty (immediate winning threat). */
        private static int countThreats(Board board, char mark) {
            int threats = 0;
            for (int[] line : Board.WIN_LINES) {
                int markCount = 0, emptyCount = 0;
                for (int idx : line) {
                    if (board.get(idx) == mark) markCount++;
                    else if (board.get(idx) == Board.EMPTY) emptyCount++;
                }
                if (markCount == 2 && emptyCount == 1) threats++;
            }
            return threats;
        }

        /** Counts opponent moves that do not result in a forced loss. Fewer means more trapped. */
        private static int countSafeResponses(Board board, char ai, char opp) {
            int safe = 0;
            for (int idx : ORDER) {
                if (!board.isEmpty(idx)) continue;
                Board sim = board.copy();
                sim.set(idx, opp);
                int score = minimax(sim, 0, true, ai, opp, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (score <= 0) safe++;
            }
            return safe;
        }
    }

    // Renderer for all print logic.

    static final class Renderer {
        private static final int FLASH_COUNT = 3;
        private static final int FLASH_MS    = 150;

        private Renderer() {}

        /** Clears the terminal. */
        static void clear() {
            // No-op in plain-text mode.
        }

        /** Renders the header with scores. */
        static void header(ScoreTracker sc) {
            System.out.println("  TIC TAC TOE");
            System.out.println("  X: " + sc.xWins + "   Ties: " + sc.ties + "   O: " + sc.oWins);
        }

        /** Renders a status line below the header. */
        static void status(String msg) {
            System.out.println("  " + msg);
            System.out.println();
        }

        /** Renders the full board with ASCII separators. */
        static void board(Board board, int[] highlight) {
            java.util.Set<Integer> hl = new java.util.HashSet<>();
            if (highlight != null) for (int i : highlight) hl.add(i);

            System.out.println("      1   2   3");
            for (int row = 0; row < Board.SIZE; row++) {
                System.out.print("  " + (row + 1) + " |");
                for (int col = 0; col < Board.SIZE; col++) {
                    int idx = Board.toIndex(row, col);
                    char mark = board.get(idx);
                    System.out.print(cellString(mark, idx, hl.contains(idx)));
                    System.out.print("|");
                }
                System.out.println();
                if (row < Board.SIZE - 1) {
                    System.out.println("    ---+---+---");
                }
            }
            System.out.println();
        }

        /** Returns the 3-char content string for a single cell. */
        private static String cellString(char mark, int idx, boolean highlighted) {
            if (mark == ' ') {
                return " " + (idx + 1) + " ";
            }
            if (highlighted) {
                return "[" + mark + "]";
            }
            return " " + mark + " ";
        }

        /** Renders the complete game screen: clear, header, status, board. */
        static void gameScreen(Board board, ScoreTracker sc, String statusMsg, int[] highlight) {
            clear();
            header(sc);
            status(statusMsg);
            board(board, highlight);
        }

        /** Flashes the winning line 3 times then holds. */
        static void flashWin(Board board, ScoreTracker sc, String statusMsg, int[] winLine) {
            for (int i = 0; i < FLASH_COUNT; i++) {
                gameScreen(board, sc, statusMsg, winLine);
                sleep(FLASH_MS);
                gameScreen(board, sc, statusMsg, null);
                sleep(FLASH_MS);
            }
            // Hold final highlighted state
            gameScreen(board, sc, statusMsg, winLine);
        }

        /** Prints the main menu. */
        static void mainMenu() {
            clear();
            System.out.println("  TIC TAC TOE");
            System.out.println();
            System.out.println("  Select Game Mode:");
            System.out.println("    [1] Player vs Player");
            System.out.println("    [2] Player vs AI");
            System.out.println("    [3] AI vs Player");
            System.out.println("    [4] AI vs AI");
            System.out.println("    [q] Quit");
            System.out.println();
        }

        /** Prints the difficulty menu. */
        static void difficultyMenu() {
            clear();
            System.out.println("  Select AI Difficulty:");
            System.out.println("    [1] Easy      - random moves");
            System.out.println("    [2] Medium    - wins/blocks, else random");
            System.out.println("    [3] Hard      - minimax alpha-beta (unbeatable)");
            System.out.println("    [4] Very Hard - fork traps, aggressive");
            System.out.println("    [5] Nightmare - high pressure");
            System.out.println("    [q] Back");
            System.out.println();
        }

        /** Prints a prompt without newline. */
        static void prompt(String msg) {
            System.out.print("  > " + msg);
        }

        /** Prints an error line (no board redraw). */
        static void error(String msg) {
            System.out.println("  ERROR: " + msg);
        }

        /** Prints an info line. */
        static void info(String msg) {
            System.out.println("  " + msg);
        }

        /** Sleeps, swallowing interrupts. */
        static void sleep(int ms) {
            try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    // Enums.

    enum GameMode {
        PVP("Player vs Player"),
        PVC("Player vs AI"),
        CVP("AI vs Player"),
        CVC("AI vs AI");
        final String label;
        GameMode(String l) { this.label = l; }
    }

    enum Difficulty {
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard"),
        VERY_HARD("Very Hard"),
        NIGHTMARE("Nightmare");

        final String label;
        Difficulty(String l) { this.label = l; }
    }

    // Game engine orchestrates the flow.

    static final class GameEngine {
        private final Scanner scanner;
        private final ScoreTracker scores;

        GameEngine(Scanner scanner, ScoreTracker scores) {
            this.scanner = scanner;
            this.scores = scores;
        }

        /** Plays a full game. Returns normally; caller handles rematch/menu. */
        void play(GameMode mode, Difficulty diff) {
            Board board = new Board();
            MoveHistory history = new MoveHistory();

            boolean xIsHuman = (mode == GameMode.PVP || mode == GameMode.PVC);
            boolean oIsHuman = (mode == GameMode.PVP || mode == GameMode.CVP);
            boolean undoAllowed = (mode != GameMode.CVC);

            char current = 'X';

            while (true) {
                boolean humanTurn = (current == 'X') ? xIsHuman : oIsHuman;
                String turnLabel = humanTurn
                        ? AnsiColors.markColor(current) + "Player " + current + "'s turn" + AnsiColors.RESET
                        : AnsiColors.markColor(current) + "AI " + current + " thinking..." + AnsiColors.RESET;

                Renderer.gameScreen(board, scores, turnLabel, null);

                int cellIndex;

                if (humanTurn) {
                    int result = humanMove(board, current, history, mode, diff, undoAllowed);
                    if (result == -1) {
                        // Quit to menu
                        return;
                    }
                    cellIndex = result;
                } else {
                    if (mode == GameMode.CVC) Renderer.sleep(800);
                    char opp = (current == 'X') ? 'O' : 'X';
                    cellIndex = AIPlayer.chooseMove(board, current, opp, diff);
                    int r = cellIndex / Board.SIZE + 1;
                    int c = cellIndex % Board.SIZE + 1;
                    Renderer.info("AI " + current + " plays row " + r + ", col " + c);
                }

                board.set(cellIndex, current);
                history.push(current, cellIndex);

                // Check win
                int[] winLine = board.checkWin(current);
                if (winLine != null) {
                    scores.recordWin(current);
                    String msg = (((current == 'X') ? xIsHuman : oIsHuman) ? "Player" : "AI") +
                            " " + current + " wins!";
                    Renderer.flashWin(board, scores, msg, winLine);
                    waitForEnter();
                    return;
                }

                // Check draw
                if (board.isFull()) {
                    scores.recordTie();
                    String msg = "It's a tie!";
                    Renderer.gameScreen(board, scores, msg, null);
                    waitForEnter();
                    return;
                }

                current = (current == 'X') ? 'O' : 'X';
            }
        }

        /**
         * Prompts the human for row then col (1-3 each).
         * Returns flat cell index (0-8), -1 for quit.
         * Handles 'u' for undo, invalid input errors without board redraw.
         */
        private int humanMove(Board board, char mark, MoveHistory history,
                              GameMode mode, Difficulty diff, boolean undoAllowed) {
            while (true) {
                // Prompt for row
                String undoHint = undoAllowed ? ", 'u' undo" : "";
                Renderer.prompt("Player " + mark + " - row (1-3" + undoHint + ", 'q' quit): ");
                String rowInput = scanner.nextLine().trim().toLowerCase();

                if (rowInput.equals("q")) return -1;

                if (rowInput.equals("u")) {
                    if (!undoAllowed) { Renderer.error("Undo not available in this mode."); continue; }
                    if (!history.canUndo()) { Renderer.error("Nothing to undo."); continue; }
                    performUndo(board, history, mode, diff, mark);
                    continue;
                }

                int row;
                try { row = Integer.parseInt(rowInput); }
                catch (NumberFormatException e) { Renderer.error("Enter a number 1-3."); continue; }
                if (row < 1 || row > 3) { Renderer.error("Row must be 1-3."); continue; }

                // Prompt for col
                Renderer.prompt("Player " + mark + " - col (1-3): ");
                String colInput = scanner.nextLine().trim().toLowerCase();

                if (colInput.equals("q")) return -1;

                if (colInput.equals("u")) {
                    if (!undoAllowed) { Renderer.error("Undo not available in this mode."); continue; }
                    if (!history.canUndo()) { Renderer.error("Nothing to undo."); continue; }
                    performUndo(board, history, mode, diff, mark);
                    continue;
                }

                int col;
                try { col = Integer.parseInt(colInput); }
                catch (NumberFormatException e) { Renderer.error("Enter a number 1-3."); continue; }
                if (col < 1 || col > 3) { Renderer.error("Column must be 1-3."); continue; }

                int idx = Board.toIndex(row - 1, col - 1);
                if (!board.isEmpty(idx)) { Renderer.error("Cell (" + row + "," + col + ") is taken."); continue; }

                return idx;
            }
        }

        /** Performs undo. In PvC/CvP, undoes two moves (AI + human). */
        private void performUndo(Board board, MoveHistory history,
                                 GameMode mode, Difficulty diff, char currentMark) {
            if (mode == GameMode.PVC || mode == GameMode.CVP) {
                if (history.size() >= 2) {
                    history.pop(board);
                    history.pop(board);
                } else if (history.size() == 1) {
                    history.pop(board);
                } else {
                    Renderer.error("Nothing to undo.");
                    return;
                }
            } else {
                history.pop(board);
            }

            String turnLabel = "Player " + currentMark + "'s turn";
            Renderer.gameScreen(board, scores, turnLabel, null);
            Renderer.info("Move undone.");
        }

        /** Blocks until the user presses Enter. */
        private void waitForEnter() {
            Renderer.prompt("Press Enter to continue...");
            scanner.nextLine();
        }
    }

