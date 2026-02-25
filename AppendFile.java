import java.io.FileWriter;
import java.io.IOException;
public class AppendFile {
    public static void main(String[] args)  {
        try(FileWriter writer = new FileWriter("/home/kavana-daniel/Documents/JavaNew/new.txt",true)) {
            writer.write("So now i'm dealing with file handling in my java roadmap toward a java software dev and cloud expert.");
            System.out.println("File appended successfully.");
        }catch (IOException e) {
            System.out.println("Error writing to file." + e.getMessage());

        }

    }
}