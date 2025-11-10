package Controller;

import ChordMaker.EditorUtil;
import Model.Musica;
import View.EditorMusica;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class EditorMusicaController {

    private final Musica model;
    private final EditorMusica view;

    final private Sequencer sequencer;

    public EditorMusicaController(Musica musica, EditorMusica eView) throws MidiUnavailableException {
        this.model = musica;
        this.view = eView;

        this.sequencer = MidiSystem.getSequencer();
        this.sequencer.open();

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

        view.setBotaoPlay((var e) -> {
            new Thread(() -> {
                try {
                    if (!sequencer.isOpen()) {
                        sequencer.open();
                        Thread.sleep(100);
                    }
                    
                    if (sequencer.isRunning()) {
                        sequencer.stop();
                        Thread.sleep(100);
                    }
                    
                    
                    var seq = parsearNotas();

                    if (seq == null) {
                        System.out.println("null seq");
                        return;
                    }

                    model.setFaixa(seq);

                    sequencer.setSequence(model.getFaixa());
                    sequencer.setTickPosition(0);
                    
                    Thread.sleep(100);
                    
                    sequencer.start();
                } catch (InvalidMidiDataException | MidiUnavailableException | InterruptedException ex) {
                    System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

            }).start();
        });

        view.setBotaoStop(e -> {
            if (sequencer.isRunning()) {
                sequencer.stop();
            }
        });

        view.setBotaoSalvar(e -> {
            try {
                var seq = parsearNotas();

                if (seq == null) {
                    return;
                }

                model.setFaixa(seq);
                model.salvar();
            } catch (InvalidMidiDataException ex) {
                System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sequencer.close();
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
        
        view.setBotaoImportar(e -> {
            try {
                var filePicker = view.getImportarFilePicker();
                var resultado = filePicker.showOpenDialog(view);
                
                if (resultado != JFileChooser.APPROVE_OPTION)
                    return;
                
                var arquivo = filePicker.getSelectedFile();
                var novaSeq = MidiSystem.getSequence(arquivo);
                
                model.setFaixa(novaSeq);
                view.setSequence(novaSeq);
            } catch (InvalidMidiDataException | IOException ex) {
                System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        
        view.setBotaoExportar(e -> {
            try {
                var filePicker = view.getExportarFilePicker();
                filePicker.setSelectedFile(new File(
                        model.getArtista().getNome() + " - " + model.getTitulo() + ".mid"
                ));
                
                var resultado = filePicker.showSaveDialog(view);
                
                if (resultado != JFileChooser.APPROVE_OPTION)
                    return;
                
                var arquivo = filePicker.getSelectedFile();
                var seq = model.getFaixa();
                
                MidiSystem.write(seq, MidiSystem.getMidiFileTypes(seq)[0], arquivo);
            } catch (IOException ex) {
                System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        

        if (view.getAbasContagem() <= 1) {
            view.setDesativarBotaoRemoverTrack();
        }

        view.setVisible(true);
    }

    public Sequence parsearNotas() throws InvalidMidiDataException {
        var seq = new Sequence(Sequence.PPQ, 480);

        for (int i = 0; i < view.getAbasContagem(); i++) {
            var texto = view.getTextoTrack(i);

            try {
                var parseado = EditorUtil.textoParaSequence(texto);
                if (parseado == null) {
                    throw new InvalidMidiDataException();
                }

                var trackParseado = parseado.getTracks()[0];
                var nTrack = seq.createTrack();

                for (int j = 0; j < trackParseado.size(); j++) {
                    nTrack.add(trackParseado.get(j));
                }

                view.setTrackCor(i, Color.WHITE);
            } catch (InvalidMidiDataException ex) {
                view.setTrackCor(i, Color.RED);
                return null;
            }
        }

        return seq;
    }
}
