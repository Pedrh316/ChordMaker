package Controller;

import ChordMaker.ValidadorDeEmail;
import Model.Auth;
import View.LoginView;
import View.RegistrarView;
import javax.swing.JOptionPane;

public class RegistrarController {
    
    private final Auth model;
    private final RegistrarView view;
   
    public RegistrarController(Auth model, RegistrarView view) {
        this.model = model;
        this.view = view;


        this.view.setAcaoRegistrar(e -> registrar());
        this.view.setAcaoLogin(e -> abrirLogin());
        
        this.view.setVisible(true);
    }
    
    private void registrar() {
        if (!ValidadorDeEmail.validar(view.getEmail())) {
            JOptionPane.showMessageDialog(view, "Email inválido");
            return;
        }
        
        model.setNome(view.getNome());
        model.setSenha(view.getSenha());
        model.setEmail(view.getEmail());
        model.setArtista(view.getArtista());
        
        var registrou = model.registrar();
        
        if (registrou) {
            JOptionPane.showMessageDialog(
                view, "Usuário registrado com sucesso"
            );
            
            abrirLogin();
        } else {
            JOptionPane.showMessageDialog(
                view, "Registro falhou"
            );
        }
    }
    
    private void abrirLogin() {
        view.dispose();
        
        var loginView = new LoginView();
        var authModel = new Auth();
        var controller = new LoginController(authModel, loginView);
    }

}
