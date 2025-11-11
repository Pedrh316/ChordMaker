package Controller;

import ChordMaker.Core.MidiExportImport;
import ChordMaker.Core.MidiPlayer;
import ChordMaker.Util.EditorUtil;
import Model.Musica;
import View.EditorMusica;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class EditorMusicaController {

    private final Musica model;
    private final EditorMusica view;

    private final MidiPlayer player;
    private final MidiExportImport IO;

    public EditorMusicaController(Musica musica, EditorMusica eView) throws MidiUnavailableException {
        this.model = musica;
        this.view = eView;

        this.IO = new MidiExportImport();
        this.player = new MidiPlayer();

        view.setMusicaNome(model.getTitulo());
        view.setArtistaNome(model.getArtista().getNome());

        view.setMusicaNomeDocumentListener(new DocumentListener() {
            /*
             * Atualizar título da música de campo editável
             */
            private void updateMusica(DocumentEvent e) {
                var document = e.getDocument();

                try {
                    model.setTitulo(document.getText(0, document.getLength()));
                } catch (BadLocationException ex) {
                    System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateMusica(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateMusica(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateMusica(e);
            }
        });

        view.setSequence(model.getFaixa());

        view.setBotaoPlay(e -> {
            new Thread(() -> {
                try {
                    var seq = EditorUtil.parsearNotas(view);

                    if (seq == null) {
                        return;
                    }

                    model.setFaixa(seq);
                    player.play(seq);
                } catch (InvalidMidiDataException | MidiUnavailableException ex) {
                    System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }).start();
        });

        view.setBotaoStop(e -> player.stop());

        view.setBotaoSalvar(e -> {
            var seq = EditorUtil.parsearNotas(view);

            if (seq == null) {
                return;
            }

            model.setFaixa(seq);
            model.salvar();
        });

        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                player.close();
            }
        });

        view.setBotaoAdicionarTrack(e -> {
            var abaSelecionada = view.getAbaSelecionada();

            model.getFaixa().createTrack();
            view.setSequence(model.getFaixa());

            view.setAbaSelecionada(abaSelecionada);

            if (view.getAbasContagem() > 1) {
                view.setAtivarBotaoRemoverTrack();
            }
        });

        view.setBotaoRemoverTrack(e -> {
            var abaSelecionada = view.getAbaSelecionada();

            var faixa = model.getFaixa();

            if (faixa.getTracks().length > 1) {
                faixa.deleteTrack(faixa.getTracks()[abaSelecionada]);
                view.setSequence(model.getFaixa());

                var novaAba = Math.min(abaSelecionada, view.getAbasContagem() - 1);
                view.setAbaSelecionada(novaAba);
            }

            if (view.getAbasContagem() <= 1) {
                view.setDesativarBotaoRemoverTrack();
            }
        });

        view.setBotaoAdicionarNota(e -> {
            try {
                var canal = view.getCanal();
                var velocidade = view.getVelocidade();
                var duracao = view.getDuracao();
                var oitava = view.getOitava();
                var nomeNota = view.getNota();
                var tick = view.getTick();

                var numeroNota = EditorUtil.notaParaNumero(nomeNota + oitava);

                var onMsg = new ShortMessage();
                var offMsg = new ShortMessage();

                onMsg.setMessage(ShortMessage.NOTE_ON, canal, numeroNota, velocidade);
                offMsg.setMessage(ShortMessage.NOTE_OFF, canal, numeroNota, 0);

                var trackNum = view.getAbaSelecionada();
                var track = model.getFaixa().getTracks()[trackNum];

                track.add(new MidiEvent(onMsg, tick));
                if (duracao > 0) {
                    track.add(new MidiEvent(offMsg, tick + duracao));
                }

                view.setSequence(model.getFaixa());
                view.setAbaSelecionada(trackNum);
            } catch (InvalidMidiDataException ex) {
                System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        view.setBotaoImportar(e -> this.IO.importar(view, model));
        view.setBotaoExportar(e -> this.IO.exportar(view, model));

        if (view.getAbasContagem() <= 1) {
            view.setDesativarBotaoRemoverTrack();
        }

        view.setVisible(true);
    }
}
