import java.io.File;
import java.util.Scanner;

public class ReadFromFileUsingScanner {
    public static void main(String[] args) throws Exception {
        ReadFromFileUsingScanner reader =  new ReadFromFileUsingScanner();
        reader.isGoodPassword("dasfdghjk");

    }

    public boolean isGoodPassword(String password) throws Exception {
        File file = new File("src/main/resources/worstPasswords");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){

            if (sc.nextLine().equalsIgnoreCase(password)) {
                System.out.print("es mala contraseña");
                return false;
            }
        }
         System.out.print("es buena contraseña");

        return true;
    }

} 