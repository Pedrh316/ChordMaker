package ChordMaker;

import java.util.regex.Pattern;

public class ValidadorDeEmail {
    private ValidadorDeEmail() {}
    
    public static boolean validar(String email) {
        var emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+"
                + "(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*"
                + "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        var pattern = Pattern.compile(emailFormat);
        
        return pattern.matcher(email).matches();
    }
}
