public class LongestStringChecker {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "kiwi", "strawberry", "pear"};

        String longest = fruits[0]; // start with the first fruit
        //loop through the array using a for loop
        for (String fruit : fruits) {
            if (longest.length()<fruit.length()) {
                longest = fruit; // updates longest if current fruit is longer
            }
        }

        System.out.println("The longest fruit is " + longest + " having " + longest.length() + " characters.");
    }
}
