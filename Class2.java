class University {
    static int studentCount;
    final String UNIVERSITY_NAME = "Meru University";  // constant

    public University() {
        studentCount++;
    }

    static void showTotalStudents() {
        System.out.println("Students present: " + studentCount);
    }
}

public class Class2 {
    public static void main(String[] args) {
        University c1 = new University();
        University c2 = new University();
        University c3 = new University();


        // Call static method using class name
        University.showTotalStudents();  // Output: Students present: 3
    }
}
