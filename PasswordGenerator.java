import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 // Small CLI for generating strong random passwords.

public class PasswordGenerator {

    private static final String DEFAULT_SYMBOLS = "!@#$%^&*()-_=+[]{};:,.?";
    private static final String AMBIGUOUS = "Il1O0oS5B8G6Z2|";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private static class Options {
        int length = 16;
        int count = 1;
        boolean useLower = true;
        boolean useUpper = true;
        boolean useDigits = true;
        boolean useSymbols = true;
        boolean allowRepeats = true;
        boolean excludeAmbiguous = false;
        boolean showEntropy = false;
        String symbols = DEFAULT_SYMBOLS;
        String excludeChars = "";
    }

    private static class CharSets {
        String lower;
        String upper;
        String digits;
        String symbols;
        String pool;                // concatenated, unique characters
        List<Character> poolList;   // same pool as list for no-repeat mode
    }

    public static void main(String[] args) {
        Options opts;
        try {
            opts = parseArgs(args);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            printUsage();
            return;
        }

        if (opts == null) { // help requested
            printUsage();
            return;
        }

        CharSets sets;
        try {
            sets = buildCharSets(opts);
            validate(opts, sets);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            printUsage();
            return;
        }

        if (opts.showEntropy) {
            double entropy = estimateEntropy(opts, sets);
            System.out.printf("Pool size: %d | Approx. entropy per password: %.2f bits%n", sets.pool.length(), entropy);
        }

        SecureRandom rng = new SecureRandom();
        for (int i = 0; i < opts.count; i++) {
            System.out.println(generatePassword(opts, sets, rng));
        }
    }

    private static Options parseArgs(String[] args) {
        Options opts = new Options();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--help":
                case "-h":
                    return null;
                case "--length":
                case "-l":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--length requires a number");
                    opts.length = parsePositiveInt(args[++i], "length");
                    break;
                case "--count":
                case "-c":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--count requires a number");
                    opts.count = parsePositiveInt(args[++i], "count");
                    break;
                case "--no-lower":
                    opts.useLower = false;
                    break;
                case "--no-upper":
                    opts.useUpper = false;
                    break;
                case "--no-digits":
                    opts.useDigits = false;
                    break;
                case "--no-symbols":
                    opts.useSymbols = false;
                    break;
                case "--symbols":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--symbols requires a value");
                    opts.symbols = args[++i];
                    opts.useSymbols = !opts.symbols.isEmpty();
                    break;
                case "--exclude":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--exclude requires a value");
                    opts.excludeChars = args[++i];
                    break;
                case "--no-ambiguous":
                    opts.excludeAmbiguous = true;
                    break;
                case "--no-repeat":
                case "--unique":
                    opts.allowRepeats = false;
                    break;
                case "--entropy":
                    opts.showEntropy = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        return opts;
    }

    private static int parsePositiveInt(String value, String name) {
        try {
            int v = Integer.parseInt(value);
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + name + " value: " + value);
        }
    }

    private static CharSets buildCharSets(Options opts) {
        CharSets cs = new CharSets();
        cs.lower = filterAndDedupe(LOWER, opts);
        cs.upper = filterAndDedupe(UPPER, opts);
        cs.digits = filterAndDedupe(DIGITS, opts);
        cs.symbols = filterAndDedupe(opts.symbols, opts);

        StringBuilder poolBuilder = new StringBuilder();
        if (opts.useLower) poolBuilder.append(cs.lower);
        if (opts.useUpper) poolBuilder.append(cs.upper);
        if (opts.useDigits) poolBuilder.append(cs.digits);
        if (opts.useSymbols) poolBuilder.append(cs.symbols);

        cs.pool = dedupe(poolBuilder.toString());
        cs.poolList = toCharList(cs.pool);
        return cs;
    }

    private static void validate(Options opts, CharSets sets) {
        if (!opts.useLower && !opts.useUpper && !opts.useDigits && !opts.useSymbols) {
            throw new IllegalArgumentException("At least one character set must be enabled");
        }
        if (opts.useLower && sets.lower.isEmpty()) {
            throw new IllegalArgumentException("Lowercase set is empty after exclusions");
        }
        if (opts.useUpper && sets.upper.isEmpty()) {
            throw new IllegalArgumentException("Uppercase set is empty after exclusions");
        }
        if (opts.useDigits && sets.digits.isEmpty()) {
            throw new IllegalArgumentException("Digits set is empty after exclusions");
        }
        if (opts.useSymbols && sets.symbols.isEmpty()) {
            throw new IllegalArgumentException("Symbols set is empty after exclusions");
        }

        int required = (opts.useLower ? 1 : 0)
                + (opts.useUpper ? 1 : 0)
                + (opts.useDigits ? 1 : 0)
                + (opts.useSymbols ? 1 : 0);
        if (opts.length < required) {
            throw new IllegalArgumentException("Length must be at least " + required + " to include all selected sets");
        }
        if (sets.pool.isEmpty()) {
            throw new IllegalArgumentException("Character pool is empty after exclusions");
        }
        if (!opts.allowRepeats && opts.length > sets.pool.length()) {
            throw new IllegalArgumentException("Length exceeds unique characters available; enable repeats or shorten length");
        }
    }

    private static String generatePassword(Options opts, CharSets sets, SecureRandom rng) {
        List<Character> chars = new ArrayList<>();

        if (opts.allowRepeats) {
            if (opts.useLower) chars.add(randomChar(sets.lower, rng));
            if (opts.useUpper) chars.add(randomChar(sets.upper, rng));
            if (opts.useDigits) chars.add(randomChar(sets.digits, rng));
            if (opts.useSymbols) chars.add(randomChar(sets.symbols, rng));

            for (int i = chars.size(); i < opts.length; i++) {
                chars.add(randomChar(sets.pool, rng));
            }
        } else {
            List<Character> available = new ArrayList<>(sets.poolList);
            if (opts.useLower) pickAndRemove(sets.lower, available, chars, rng);
            if (opts.useUpper) pickAndRemove(sets.upper, available, chars, rng);
            if (opts.useDigits) pickAndRemove(sets.digits, available, chars, rng);
            if (opts.useSymbols) pickAndRemove(sets.symbols, available, chars, rng);

            for (int i = chars.size(); i < opts.length; i++) {
                int idx = rng.nextInt(available.size());
                chars.add(available.remove(idx));
            }
        }

        Collections.shuffle(chars, rng);

        StringBuilder password = new StringBuilder(opts.length);
        for (char c : chars) password.append(c);
        return password.toString();
    }

    private static char randomChar(String source, SecureRandom rng) {
        return source.charAt(rng.nextInt(source.length()));
    }

    private static void pickAndRemove(String source, List<Character> available, List<Character> out, SecureRandom rng) {
        char c = randomChar(source, rng);
        removeFirst(available, c);
        out.add(c);
    }

    private static void removeFirst(List<Character> list, char target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == target) {
                list.remove(i);
                return;
            }
        }
        // Fallback: should not happen because available is derived from pool of unique chars.
        if (!list.isEmpty()) list.remove(0);
    }

    private static String filterAndDedupe(String source, Options opts) {
        StringBuilder sb = new StringBuilder();
        Set<Character> seen = new HashSet<>();
        for (char c : source.toCharArray()) {
            if (opts.excludeAmbiguous && AMBIGUOUS.indexOf(c) >= 0) continue;
            if (!opts.excludeChars.isEmpty() && opts.excludeChars.indexOf(c) >= 0) continue;
            if (seen.add(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String dedupe(String source) {
        StringBuilder sb = new StringBuilder();
        Set<Character> seen = new HashSet<>();
        for (char c : source.toCharArray()) {
            if (seen.add(c)) sb.append(c);
        }
        return sb.toString();
    }

    private static List<Character> toCharList(String s) {
        List<Character> list = new ArrayList<>(s.length());
        for (char c : s.toCharArray()) list.add(c);
        return list;
    }

    private static double estimateEntropy(Options opts, CharSets sets) {
        int poolSize = sets.pool.length();
        if (opts.allowRepeats) {
            return opts.length * log2(poolSize);
        }
        // entropy for permutations without replacement: log2(nPr)
        double bits = 0.0;
        for (int i = 0; i < opts.length; i++) {
            bits += log2(poolSize - i);
        }
        return bits;
    }

    private static double log2(double v) {
        return Math.log(v) / Math.log(2);
    }

    private static void printUsage() {
        System.out.println("PasswordGenerator CLI");
        System.out.println("Usage: java PasswordGenerator [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -l, --length <n>      Password length (default 16)");
        System.out.println("  -c, --count <n>       Number of passwords to generate (default 1)");
        System.out.println("      --no-lower        Exclude lowercase letters");
        System.out.println("      --no-upper        Exclude uppercase letters");
        System.out.println("      --no-digits       Exclude digits");
        System.out.println("      --no-symbols      Exclude symbols");
        System.out.println("      --symbols <set>   Custom symbol set (overrides default)");
        System.out.println("      --exclude <chars> Remove listed characters from pool");
        System.out.println("      --no-ambiguous    Remove lookalike chars (Il1O0oS5B8G6Z2|)");
        System.out.println("      --no-repeat       Do not reuse characters in a password");
        System.out.println("      --entropy         Print estimated entropy before generation");
        System.out.println("  -h, --help            Show this help message");
    }
}
