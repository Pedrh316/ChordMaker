package ChordMaker;

import Controller.AuthController;
import Controller.EditorMusicaController;
import Model.Album;
import Model.Artista;
import Model.Auth;
import Model.Musica;
import Model.Usuario;
import View.EditorMusica;
import View.LoginView;
import View.RegistrarView;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

public class Main {

    public static void main(String[] args) {
        Usuario.criarTable();

        var authModel = new Auth();
        var loginView = new LoginView();
        var registrarView = new RegistrarView();
        var authController = new AuthController(authModel, loginView, registrarView);

        /*

        try {
            var artista = new Artista("Teste artista");
            var musica = new Musica(
                    artista,
                    MidiSystem.getSequence(Main.class.getResource("/test.mid"))
            );
            var musicaView = new EditorMusica();
            var editorController = new EditorMusicaController(musica, musicaView);
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
       
         */
    }

}
