package Controller;

import ChordMaker.Core.Navegador;
import Model.Auth;
import View.LoginView;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JOptionPane;

public class LoginController {
    private final Auth model;
    private final LoginView view;
   
    public LoginController(Auth model, LoginView view) {
        this.model = model;
        this.view = view;

        this.view.setAcaoLogin(e -> login());
        this.view.setAcaoRegistrar(e -> abrirRegistro());

        this.view.setVisible(true);
    }
    
    private void login() {
        model.setEmail(view.getEmail());
        model.setSenha(view.getSenha());

        var u = model.login();

        if (u != null) {
            try {
                view.dispose();
                
                Navegador.getNavegador().abrirBiblioteca(u);
            } catch (MidiUnavailableException ex) {
                System.getLogger(LoginController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Usuário ou senha inválidos");
        }
    }
        
    private void abrirRegistro() {
        view.dispose();
        
        Navegador.getNavegador().abrirRegistro();
    }
}
