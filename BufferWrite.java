import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class BufferWrite {
    public static void main(String[] args) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("BufferWrite.java"))){
            bw.write("Hello World");
            bw.newLine();
            bw.write("I'm a Software dev");
            System.out.println("Written successfully");
        }catch(IOException ioe){
            System.out.println("Error writing to file");
        }

    }
}