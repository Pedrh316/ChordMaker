package Controller;

import Model.Auth;
import Model.Usuario;
import View.LoginView;
import View.RegistrarView;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AuthController {

    private final Auth authModel;
    private final LoginView loginView;
    private final RegistrarView registrarView;
    
    private Usuario usuarioLogado;

    //private Usuario usuarioModel;
    public AuthController() {
        authModel = new Auth();
        
        loginView = new LoginView();
        registrarView = new RegistrarView();

        loginView.setAcaoLogin(e -> login());
        loginView.setAcaoRegistrar(e -> abrirRegistro());

        loginView.setVisible(true);

        registrarView.setAcaoRegistrar(e -> registrar());
        registrarView.setAcaoLogin(e -> abrirLogin());
    }

    private void login() {
        authModel.setEmail(loginView.getEmail());
        authModel.setSenha(loginView.getSenha());

        Usuario u = authModel.login();

        if (u != null) {
            JOptionPane.showMessageDialog(loginView, "Login com sucesso.");
            usuarioLogado = u;
            loginView.dispose();
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
    
    private boolean validarEmail() {
        var emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+"
                + "(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*"
                + "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        var pattern = Pattern.compile(emailFormat);
        
        return pattern.matcher(registrarView.getEmail()).matches();
    }

    private void registrar() {
        if (!validarEmail()) {
            JOptionPane.showMessageDialog(registrarView, "Email inválido");
            return;
        }
        
        authModel.setNome(registrarView.getNome());
        authModel.setSenha(registrarView.getSenha());
        authModel.setEmail(registrarView.getEmail());
        
        Usuario u = authModel.registrar();
        
        if (u != null) {
            JOptionPane.showMessageDialog(
                registrarView, "Usuário registrado com sucesso"
            );
            usuarioLogado = u;
            registrarView.dispose();
        } else {
            JOptionPane.showMessageDialog(
                registrarView, "Registro falhou"
            );
        }
    }
}
