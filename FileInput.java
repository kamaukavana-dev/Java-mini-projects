import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class FileInput {
    public static void main(String[] args) throws IOException {
        try(FileInputStream file = new FileInputStream("/home/kavana-daniel/Pictures/16674_61.jpg");
            FileOutputStream fileOut = new FileOutputStream("new.jpg")){//copy to new.jpg
            int c;
            while(((c = file.read()) != -1)){
                fileOut.write(c);//write the raw byte to new file
            }
            System.out.println("File written successfully.");

        }catch (IOException e2){
            System.out.println("File not found.");
        }
    }
}