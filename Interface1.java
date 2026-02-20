interface Animal{
    public void move();
    public void sound();
    public void eat();
}
class Lion implements Animal{
    @Override
    public void move() {
        System.out.println("The Lion moves on four limbs");
    }

    @Override
    public void sound() {
        System.out.println("The lion roars");
    }

    @Override
    public void eat() {
        System.out.println("The lions feeds on flesh.");
    }
}
class Cat implements Animal{
    @Override
    public void move(){System.out.println("The Cat moves on four limbs.");}

    @Override
    public void sound() {
        System.out.println("The Cat meows.");
    }

    @Override
    public void eat() {
        System.out.println("The Cat feeds on milk.");
    }
}
class Eagle implements Animal{
    @Override
    public void move() {
        System.out.println("The Eagle flies");
    }

    @Override
    public void sound() {
        System.out.println("The Eagle screech's.");
    }

    @Override
    public void eat() {
        System.out.println("The Eagle feeds on flesh");
    }
}
public class Interface1 {
    public static void main(String[] args) {
        Animal l1 = new Lion(); //polimerphism
        Animal c1 = new Cat();
        Animal e1 = new Eagle();
        e1.eat();
        e1.move();
        c1.sound();
        c1.eat();
        l1.sound();
        l1.move();

    }
}