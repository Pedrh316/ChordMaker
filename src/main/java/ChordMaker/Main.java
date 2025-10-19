package ChordMaker;

import Controller.AuthController;
import Controller.BibliotecaController;
import Controller.EditorMusicaController;
import Model.Album;
import Model.Artista;
import Model.Auth;
import Model.Biblioteca;
import Model.Musica;
import Model.Usuario;
import View.BibliotecaView;
import View.EditorMusica;
import View.LoginView;
import View.RegistrarView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

public class Main {

    public static void main(String[] args) {
        Usuario.criarTable();

        /* Testar login */
 /*
        var authModel = new Auth();
        var loginView = new LoginView();
        var registrarView = new RegistrarView();
        var authController = new AuthController(authModel, loginView, registrarView);
         */
 /* Testar editor */
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
 /* Testar biblioteca */
        try {
            var artista = new Artista(0, "Teste artista");
            var artista2 = new Artista(1, "Teste artista 2");
            var biblioteca = new Biblioteca(artista);

            for (int i = 0; i < 100; i++) {
                biblioteca.addBiblioteca(new Musica(
                        i,
                        Math.random() > 0.5 ? artista : artista2,
                        "Musica " + i,
                        gerarSequenceAleatoria()
                ));
            }

            var bibliotecaView = new BibliotecaView();
            var bibliotecaController = new BibliotecaController(biblioteca, bibliotecaView);
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static Sequence gerarSequenceAleatoria() throws InvalidMidiDataException {

        var sequence = new Sequence(Sequence.PPQ, 480);
        var track = sequence.createTrack();
        var rand = new Random();

        int channel = 0;
        int startTick = 0;

        // Generate between 3 and 8 notes
        int numNotes = 3 + rand.nextInt(6);

        for (int i = 0; i < numNotes; i++) {
            int note = 60 + rand.nextInt(24); // MIDI notes between C4 and B5
            int velocity = 60 + rand.nextInt(40);
            int duration = 240 + rand.nextInt(240); // between 240 and 480 ticks

            // NOTE ON
            track.add(new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity),
                    startTick
            ));

            // NOTE OFF
            track.add(new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_OFF, channel, note, 0),
                    startTick + duration
            ));

            startTick += duration; // advance time for next note
        }

        return sequence;

    }

}
