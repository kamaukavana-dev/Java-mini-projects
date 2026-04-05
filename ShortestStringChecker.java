public class ShortestStringChecker {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "Mangoes", "strawberry", "pear"};

        String shortest = fruits[0]; // start with the first fruit
        //loop through the array using for loop
        for (String fruit : fruits) {
            if (shortest.length()>fruit.length()) {
                shortest = fruit;
            }
        }

        System.out.println("The shortest fruit is " + shortest + " having " + shortest.length() + " characters.");
    }
}
