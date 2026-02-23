import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class AppendFileOutput {
    public static void main(String[] args) throws IOException {
        String text = "Hello guys,I'm a java developer!";
        try(FileOutputStream fos = new FileOutputStream("output.txt",true)){
            fos.write(text.getBytes());
            System.out.println("File written successfully!");
        }
        catch(IOException e){
            System.out.println("File Write Error!");
        }
    }
}