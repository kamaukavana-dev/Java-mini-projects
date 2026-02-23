import java.io.FileInputStream;
import java.io.IOException;
public class IoStream {
    public static void main(String[] args) throws IOException {
        try(FileInputStream file = new FileInputStream("/home/kavana-daniel/Documents/JavaNew/new.txt")){
            int i;//store each byte that is read
            while((i=file.read())!=-1){
                System.out.print((char)i);//convert byte back to char
            }
            System.out.println("\n");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    }
}