package ChordMaker;

import Controller.LoginController;
import Model.Auth;
import Model.Musica;
import Model.Usuario;
import View.LoginView;

public class Main {

    public static void main(String[] args) {
        // Criar tabelas
        Usuario.criarTable();
        Musica.criarTable();
 
        var authModel = new Auth();
        var loginView = new LoginView();
        var loginController = new LoginController(authModel, loginView);
    }
}
