package ChordMaker;

import Controller.AuthController;
import Model.Auth;
import Model.Musica;
import Model.Usuario;
import View.LoginView;
import View.RegistrarView;

public class Main {

    public static void main(String[] args) {
        // Criar tabelas
        Usuario.criarTable();
        Musica.criarTable();
 
        var authModel = new Auth();
        var loginView = new LoginView();
        var registrarView = new RegistrarView();
        var authController = new AuthController(authModel, loginView, registrarView);
    }
}
