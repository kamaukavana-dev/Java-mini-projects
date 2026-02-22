import java.io.File;
public class DeleteFile {
    public static void main(String[] args) {
        File myNew = new File("/home/kavana-daniel/Documents/JavaNew/todolist1.txt");
        if(myNew.delete()) {
            System.out.println("File deleted successfully : "+myNew.getName());
        }
        else  {
            System.out.println("An error occurred while deleting the file : "+myNew.getName());
        }
    }
}