abstract class Vehicle {
    public abstract void drive();

    public void fuel() {
        System.out.println("Vehicle needs fuel!!");
    }
}

class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving....");
    }
}

class Bike extends Vehicle {
    @Override
    public void drive() {
        System.out.println("Bike is driving....");
    }
}

public class Class3 {
    public static void main(String[] args) {
        Vehicle v1 = new Car();   // polymorphic reference
        Vehicle v2 = new Bike();

        v1.fuel();
        v1.drive();

        v2.fuel();
        v2.drive();
    }
}
