package Controller;

import Model.Biblioteca;
import Model.Musica;
import View.BibliotecaView;
import View.EditorMusica;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class BibliotecaController {

    private final Biblioteca model;
    private final BibliotecaView view;

    private Sequencer sequencer;

    public BibliotecaController(Biblioteca biblioteca, BibliotecaView bView) throws MidiUnavailableException {
        this.model = biblioteca;
        this.view = bView;

        sequencer = MidiSystem.getSequencer();
        sequencer.open();

        sequencer.addMetaEventListener(e -> {
            if (e.getType() == 47) {
                view.setTocandoAgora(null);
            }
        });

        view.setNome(model);

        view.atualizarLista(model, e -> {
            var id = e.getActionCommand();
            model.getBiblioteca()
                    .stream()
                    .filter(m -> Integer.parseInt(id) == m.getId())
                    .findFirst()
                    .ifPresent(this::tocarMusica);
        }, e -> {
            var id = e.getActionCommand();
            model.getBiblioteca()
                    .stream()
                    .filter(m -> Integer.parseInt(id) == m.getId())
                    .findFirst()
                    .ifPresent(this::editarMusica);
        });

        view.setBotaoStop(e -> {
            sequencer.stop();
            view.setTocandoAgora(null);
        });

        view.setTocandoAgora(null);

        view.setVisible(true);
    }

    private void editarMusica(Musica m) {
        try {
            view.dispose();
            
            var view = new EditorMusica();
            var controller = new EditorMusicaController(m, view);
        } catch (MidiUnavailableException ex) {
            System.getLogger(BibliotecaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    private void tocarMusica(Musica m) {
        new Thread(() -> {
            try {
                if (sequencer.isRunning()) {
                    sequencer.stop();
                }

                sequencer.setSequence(m.getFaixa());
                sequencer.setTickPosition(0);
                sequencer.start();

                view.setTocandoAgora(m.getTitulo() + " de " + m.getArtista().getNome());
            } catch (InvalidMidiDataException ex) {
                System.getLogger(BibliotecaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }).start();
    }

}
