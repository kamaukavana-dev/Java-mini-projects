import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class BufferRead {
    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("output.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
            System.out.println("File written successfully");
        }catch (Exception e){
            System.out.println("Error in reading file");
        }
    }
}