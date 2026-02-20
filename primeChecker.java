public class primeChecker {
    public static void main(String[] args) {
        int[] num = {2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int n : num) {
            if (isPrime(n)) {
                System.out.println(n);
            }
        }
    }

    // Helper method to check prime
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
