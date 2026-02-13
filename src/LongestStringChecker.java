public class array {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "kiwi", "strawberry", "pear"};

        String longest = fruits[0]; // start with the first fruit

        for (String fruit : fruits) {
            if (fruit.length() > longest.length()) {
                longest = fruit; // update longest if current fruit is longer
            }
        }

        System.out.println("The longest fruit is " + longest + " having " + longest.length() + " characters.");
    }
}
