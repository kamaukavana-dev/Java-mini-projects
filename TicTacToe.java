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



