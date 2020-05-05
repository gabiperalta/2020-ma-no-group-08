package seguridad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashPassword {
    private static String algoritmo = "SHA-384";

    public static String calcular(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance(algoritmo);
            digest.reset();
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException x){
            throw new RuntimeException();
        }
    }

}