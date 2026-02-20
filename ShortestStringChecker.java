public class ShortestStringChecker {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "Mangoes", "strawberry", "pear"};

        String shortest = fruits[0]; // start with the first fruit

        for (String fruit : fruits) {
            if (fruit.length() < shortest.length()) {
                shortest = fruit;
            }
        }

        System.out.println("The shortest fruit is " + shortest + " having " + shortest.length() + " characters.");
    }
}
