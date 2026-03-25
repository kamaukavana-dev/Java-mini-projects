//Abstract class
abstract class Vehicle {
    //abstract method
    public abstract void drive();
    //abstract method
    public void fuel() {
        System.out.println("Vehicle needs fuel!!");
    }
}
//extending abstract class
class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving....");
    }
}
//extending abstract class
class Bike extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Bike is riding....");
    }
}
//extending abstract class
class Boat extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Boat is soaring....");
    }

}
//main class
public class Class3 {
    public static void main(String[] args) {
        Vehicle v1 = new Car();   // polymorphic reference
        Vehicle v2 = new Bike();
        Vehicle v3 = new Boat();

        v1.fuel();
        v1.drive();

        v2.fuel();
        v2.drive();

        v3.fuel();
        v3.drive();
    }
}
