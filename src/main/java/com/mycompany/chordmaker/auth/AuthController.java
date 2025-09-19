package com.mycompany.chordmaker.auth;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AuthController {
    private final Usuario usuarioModel;
    private final AuthView loginView;
    private final RegisterView registerView;
    
    public AuthController() {
        usuarioModel = new Usuario();
        loginView = new AuthView();
        registerView = new RegisterView();
        
        
        loginView.setLoginAction(e -> login());
        loginView.setRegisterAction(e -> openRegister());
        
        loginView.setVisible(true);
        
        registerView.setSubmitAction(e -> register());
        registerView.setLoginAction(e -> openLogin());
    }
    
    private void login() {
        var username = loginView.getUsername();
        var password = loginView.getPassword();


        if (usuarioModel.validateUser(username, password)) {
            JOptionPane.showMessageDialog(loginView, "Login com sucesso.");
        } else {
            JOptionPane.showMessageDialog(loginView, "Usuário ou senha inválidos");
        }
    }
    
    private void openRegister() {
        loginView.dispose();
        registerView.setVisible(true);
    }
    
    private void openLogin() {
        registerView.dispose();
        loginView.setVisible(true);
    }
    
    private void register() {
        var username = registerView.getUsername();
        var password = registerView.getPassword();
        var email = registerView.getEmail();

        var emailFormat = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+"
                + "(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*"
                + "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        var pattern = Pattern.compile(emailFormat);

        if (pattern.matcher(email).matches()) {
            if (usuarioModel.registerUser(username, password, email)) {
                JOptionPane.showMessageDialog(
                        registerView, "Usuário registrado com sucesso"
                );
                registerView.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        registerView, "Registro falhou"
                );
            }
        } else {
            JOptionPane.showMessageDialog(registerView, "Email inválido");
        }
    }
}
