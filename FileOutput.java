import java.io.FileOutputStream;
import java.io.IOException;
public class FileOutput {
    public static void main(String[] args) throws IOException {
        String text = "Hello World!";
        try(FileOutputStream file = new FileOutputStream("output.txt")){
            file.write(text.getBytes());//convert the text to bytes
            System.out.println("File Write Success!");
        }catch(IOException e){
            System.out.println("File Write Error!");
        }
    }
}