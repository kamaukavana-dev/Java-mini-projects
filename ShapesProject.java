// ABSTRACT CLASS: provides partial abstraction + shared behavior
abstract class Shape {
    // Abstract method: must be implemented by subclasses
    abstract double area();

    // Concrete method: shared by all subclasses
    void display() {
        System.out.println("This is a shape.");
    }
}

// INTERFACE: pure contract, no implementation (except default/static methods)
interface Drawable {
    void draw();
}

// SUBCLASS: Circle inherits from Shape and implements Drawable
class Circle extends Shape implements Drawable {
    int radius = 5;

    // Polymorphism via overriding: Circle defines its own area
    @Override
    double area() {
        return 3.14 * radius * radius;
    }

    // Polymorphism via interface: Circle defines how it draws
    @Override
    public void draw() {
        System.out.println("Drawing a circle.");
    }
}

// SUBCLASS: Square inherits from Shape and implements Drawable
class Square extends Shape implements Drawable {
    int side = 4;

    @Override
    double area() {
        return side * side;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a square.");
    }
}

// MAIN CLASS: Demonstrates polymorphism in action
public class ShapesProject {
    public static void main(String[] args) {
        // Polymorphism with abstract class reference
        Shape s1 = new Circle();   // declared as Shape, behaves as Circle
        Shape s2 = new Square();   // declared as Shape, behaves as Square

        System.out.println("Circle area: " + s1.area()); // Circle’s area
        System.out.println("Square area: " + s2.area()); // Square’s area

        // Polymorphism with interface reference
        Drawable d1 = new Circle();
        Drawable d2 = new Square();

        d1.draw(); // Circle’s draw
        d2.draw(); // Square’s draw
    }
}
