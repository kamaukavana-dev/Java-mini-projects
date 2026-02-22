import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class ReadFile {
    public static void main(String[] args) throws FileNotFoundException {
        File myFile = new File("/home/kavana-daniel/Documents/JavaNew/new.txt");
        try(Scanner sc = new Scanner(myFile)){
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found."+e.getMessage());
        }
    }
}