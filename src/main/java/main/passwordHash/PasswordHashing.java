package main.passwordHash;

import org.mindrot.jbcrypt.BCrypt;
public class PasswordHashing {
    public static String HashPassword(String password){
//        Convert password into hash
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        boolean passwordsMatch = BCrypt.checkpw(password, hashedPassword);
        return passwordsMatch? hashedPassword:null;
    }
    public static Boolean checkPass(String inputPassword, String hashPassword){
        return BCrypt.checkpw(inputPassword, hashPassword);
    }
}
