public class vowelCounter {
    public static void main(String[] args) {
        String greeting = "Hello  christopher";
        int vowelCount = 0;

        for (int i = 0; i < greeting.length(); i++) {
            char c = Character.toLowerCase(greeting.charAt(i)); // get each character
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowelCount++;
            }
        }

        System.out.println("Number of vowels: " + vowelCount);
    }
}
