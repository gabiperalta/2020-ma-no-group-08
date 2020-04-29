import java.io.File;
import java.util.Scanner;

public class ValidadorContrasenia {
    public static void main(String[] args) throws Exception {
        ValidadorContrasenia validador =  new ValidadorContrasenia();
        validador.esContraseniaValida("F@rd1coSports");

    }

    public boolean esContraseniaValida (String contrasenia) throws Exception {
        return this.noEstaEntrePeoresContrasenias(contrasenia) & this.cumpleRequisitosRegEx(contrasenia);
    }

    private boolean noEstaEntrePeoresContrasenias(String contrasenia) throws Exception {
        File file = new File("src/main/resources/worstPasswords");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){
            if (sc.nextLine().equalsIgnoreCase(contrasenia)) {
                return false;
            }
        }

        return true;
    }

    private boolean cumpleRequisitosRegEx(String contrasenia) {
        String strRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*](?=\\S+$).{8,15}$";

        return contrasenia.matches(strRegEx);
    }


} 