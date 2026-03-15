import java.util.logging.Logger;

public class LoggingDemo {
    private static final Logger logger = Logger.getLogger(LoggingDemo.class.getName());

    public static void main(String[] args) {
        logger.info("Application started"); // Info log
        try {
            int x = 10 / 0; // logical Error
        } catch (Exception e) {
            logger.severe("Error occurred: " + e.getMessage()); // Error log
        }
        logger.info("Application finished");
    }
}
