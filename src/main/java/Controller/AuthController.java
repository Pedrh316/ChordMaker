package Controller;


import Model.Usuario;
import View.LoginView;
import View.RegistrarView;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AuthController {
    private final Usuario usuarioModel;
    private final LoginView loginView;
    private final RegistrarView registrarView;
    
    public AuthController() {
        usuarioModel = new Usuario();
        loginView = new LoginView();
        registrarView = new RegistrarView();
        
        
        loginView.setAcaoLogin(e -> login());
        loginView.setAcaoRegistrar(e -> abrirRegistro());
        
        loginView.setVisible(true);
        
        registrarView.setAcaoRegistrar(e -> register());
        registrarView.setAcaoLogin(e -> abrirLogin());
    }
    
    private void login() {
        var username = loginView.getUsuario();
        var password = loginView.getSenha();


        if (usuarioModel.login(username, password)) {
            JOptionPane.showMessageDialog(loginView, "Login com sucesso.");
        } else {
            JOptionPane.showMessageDialog(loginView, "Usuário ou senha inválidos");
        }
    }
    
    private void abrirRegistro() {
        loginView.dispose();
        registrarView.setVisible(true);
    }
    
    private void abrirLogin() {
        registrarView.dispose();
        loginView.setVisible(true);
    }
    
    private void register() {
        var username = registrarView.getUsuario();
        var password = registrarView.getSenha();
        var email = registrarView.getEmail();

        var emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+"
                + "(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*"
                + "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        var pattern = Pattern.compile(emailFormat);

        if (pattern.matcher(email).matches()) {
            if (usuarioModel.registrar(username, password, email)) {
                JOptionPane.showMessageDialog(
                        registrarView, "Usuário registrado com sucesso"
                );
                registrarView.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        registrarView, "Registro falhou"
                );
            }
        } else {
            JOptionPane.showMessageDialog(registrarView, "Email inválido");
        }
    }
}