import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ValidadorContrasenia {

    public boolean esContraseniaValida (String contrasenia, ArrayList<String> contrasenias) throws Exception {
        return this.noEstaEntrePeoresContrasenias(contrasenia) & this.cumpleRequisitosRegEx(contrasenia) & this.esNuevaContrasenia(contrasenia,contrasenias);
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
        String strRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

        return contrasenia.matches(strRegEx);
    }

    private boolean esNuevaContrasenia(String contrasenia, ArrayList<String> contrasenias) {
        return !contrasenias.contains(contrasenia);
    }

} 