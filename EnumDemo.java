enum TrafficLight {
    RED("Stop"),
    YELLOW("Slow down"),
    GREEN("Go");

    private String action;

    // Constructor
    TrafficLight(String action) {
        this.action = action;
    }

    // getter Method
    public String getAction() {
        return action;
    }
}

public class EnumDemo{
    public static void main(String[] args) {
        TrafficLight signal = TrafficLight.RED;
        System.out.println("Signal: " + signal);
        System.out.println("Action: " + signal.getAction());
    }
}
