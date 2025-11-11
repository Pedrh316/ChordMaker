package Controller;

import ChordMaker.Navegador;
import Model.Artista;
import Model.Biblioteca;
import Model.Musica;
import View.BibliotecaView;
import View.EditorMusica;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

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

        view.setBotaoStop(e -> {
            sequencer.stop();
            view.setTocandoAgora(null);
        });

        if (model.getUsuario() instanceof Artista art) {
            view.setBotaoAdicionar(e -> {
                try {
                    Navegador.getNavegador().abrirEditorMusica(new Musica(art));
                } catch (MidiUnavailableException ex) {
                    System.getLogger(BibliotecaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            });
        } else {
            view.removerBotaoAdicionar();
        }

        view.setBotaoAtualizar(e -> {
            atualizarLista();
        });

        view.setTocandoAgora(null);
        view.setVisible(true);

        atualizarLista();
    }

    private void atualizarLista() {
        model.carregarBiblioteca();

        view.atualizarLista(
                model,
                e -> {
                    botaoAction(e, this::tocarMusica);
                },
                e -> {
                    botaoAction(e, this::editarMusica);
                }
        );
    }

    private void botaoAction(ActionEvent e, Consumer<Musica> l) {
        var id = e.getActionCommand();
        model.getBiblioteca()
                .stream()
                .filter(m -> Integer.parseInt(id) == m.getId())
                .findFirst()
                .ifPresent(l);
    }

    private void editarMusica(Musica m) {
        try {
            Navegador.getNavegador().abrirEditorMusica(m);
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
