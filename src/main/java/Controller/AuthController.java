package Controller;

import ChordMaker.ValidadorDeEmail;
import Model.Auth;
import Model.Biblioteca;
import View.BibliotecaView;
import View.LoginView;
import View.RegistrarView;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class AuthController {

    private final Auth authModel;
    private final LoginView loginView;
    private final RegistrarView registrarView;
   
    public AuthController(Auth model, LoginView loginView, RegistrarView registrarView) {
        this.authModel = model;
        this.loginView = loginView;
        this.registrarView = registrarView;

        loginView.setAcaoLogin(e -> login());
        loginView.setAcaoRegistrar(e -> abrirRegistro());

        loginView.setVisible(true);

        registrarView.setAcaoRegistrar(e -> registrar());
        registrarView.setAcaoLogin(e -> abrirLogin());
    }

    private void login() {
        authModel.setEmail(loginView.getEmail());
        authModel.setSenha(loginView.getSenha());

        var u = authModel.login();

        if (u != null) {
            try {
                loginView.dispose();
                
                var bView = new BibliotecaView();
                var bModel = new Biblioteca(u);
                var bController = new BibliotecaController(bModel, bView);
                
            } catch (MidiUnavailableException ex) {
                System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
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

    private void registrar() {
        if (!ValidadorDeEmail.validar(registrarView.getEmail())) {
            JOptionPane.showMessageDialog(registrarView, "Email inválido");
            return;
        }
        
        authModel.setNome(registrarView.getNome());
        authModel.setSenha(registrarView.getSenha());
        authModel.setEmail(registrarView.getEmail());
        authModel.setArtista(registrarView.getArtista());
        
        var registrou = authModel.registrar();
        
        if (registrou) {
            JOptionPane.showMessageDialog(
                registrarView, "Usuário registrado com sucesso"
            );
            
            abrirLogin();
        } else {
            JOptionPane.showMessageDialog(
                registrarView, "Registro falhou"
            );
        }
    }
}
