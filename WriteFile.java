import java.io.FileWriter;
import java.io.IOException;
public class WriteFile {
    public static void main(String[] args)  {
        try(FileWriter writer = new FileWriter("/home/kavana-daniel/Documents/JavaNew/new.txt")) {
            writer.write("Hey there.My name is Daniel and I'm a first year student currently pursuing Bachelors in  Computer science."+"Im a java developer btw"+2);
            System.out.println("File written successfully.");
        }catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());

        }

    }
}