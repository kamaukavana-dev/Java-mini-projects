class Vehicles {
    String brand;
    String name;
    int cost;
      //constructor
    Vehicles(String brand, String name, int cost) {
        this.brand = brand;
        this.name = name;
        this.cost = cost;
    }
    void info(){
        System.out.printf("%s %s is worth %,d",brand,name,cost);
    }
    void detail(){
        System.out.println(brand + name);
    }
}

class Cars extends Vehicles {
    String model;
     //constructor
    Cars(String brand, String name, int cost, String model) {
        super(brand, name, cost);
        this.model = model;
    }
    @Override
    void detail() {
        super.detail();
        System.out.println("Brand : "+brand+" name : "+name+" model : "+model);
    }
}
class Truck extends Vehicles{
    String type;
    //constructor
    Truck(String brand,String name, int cost, String type) {
        super(brand,name, cost);
        this.type= type;
    }
    @Override
    void info() {
        super.info();
        System.out.printf("\nThis %s carry's %s loads.\n",name,type);
    }

    @Override
    void detail() {
        super.detail();
        System.out.println("Brand : "+brand+" name : "+name+" cost : $"+cost);
    }
}

public class SuperClass {
    public static void main(String[] args) {
        Cars c = new Cars("Ford", "Ranger", 12000000, "Hilux");
        Truck t = new Truck("Transist","Truck",19450000,"heavy");

        System.out.printf("%s %s worth $%,d is a %s model.%n   \n", c.brand, c.name, c.cost, c.model);
        t.info();
        c.detail();
        t.detail();

    }
}
