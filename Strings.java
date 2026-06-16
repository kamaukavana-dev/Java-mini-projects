public class Strings {
    public static void main(String[] args) {
        String a = "hello"; // Stored in the string pool.
        String b = "hello"; // Same literal — reuses the same pool object.
        String c = new String("hello"); // Explicitly creates a NEW object on the heap — bypasses the pool.

        System.out.println(a == b);        // Output: true  — same object in the pool
        System.out.println(a == c);        // Output: false — c is a new heap object
        System.out.println(a.equals(c));   // Output: true  — same character sequence
    }
}

