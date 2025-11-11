package ChordMaker;

import Controller.BibliotecaController;
import Controller.EditorMusicaController;
import Controller.LoginController;
import Controller.RegistrarController;
import Model.Auth;
import Model.Biblioteca;
import Model.Musica;
import Model.Usuario;
import View.BibliotecaView;
import View.EditorMusica;
import View.LoginView;
import View.RegistrarView;
import javax.sound.midi.MidiUnavailableException;

public class Navegador {
    private static Navegador instancia;
    
    private Navegador() {}
    
    public static Navegador getNavegador() {
        if (instancia == null) {
            instancia = new Navegador();
        }
        
        return instancia;
    }
    
    public BibliotecaController abrirBiblioteca(Usuario usuario) throws MidiUnavailableException {
        var view = new BibliotecaView();
        var model = new Biblioteca(usuario);

        return new BibliotecaController(model, view);
    }
   
    public LoginController abrirLogin() {
        var view = new LoginView();
        var model = new Auth();
        
        return new LoginController(model, view);
    }
    
    public RegistrarController abrirRegistro() {
        var view = new RegistrarView();
        var model = new Auth();
        
        return new RegistrarController(model, view);
    }
    
    public EditorMusicaController abrirEditorMusica(Musica musica) throws MidiUnavailableException {
        var view = new EditorMusica();
        
        return new EditorMusicaController(musica, view);
    }
    
    
    
}
