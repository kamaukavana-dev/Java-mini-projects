public class VarArgs {
    public static void main(String[] args) {
        //You can pass different kind of arguments without passing diff overloaded methods
        System.out.println(average(12,13,13,14));
        System.out.println(sum(23,23,45,67,8,88,98));
        System.out.println(max(21,12,43,56));
        System.out.println(min(21,12,43,56));

    }
      //average method with
    public static double average(int... numbers) {//stores this in a dynamic array
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.length;
    }

    public static double sum(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum;
    }

    public static int max(int... numbers) {
        int max = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    public static int min(int... numbers) {
        int min = numbers[0];
        for (int number : numbers) {
            if (number > min) {
                min = number;
            }
        }
        return min;
    }
}
