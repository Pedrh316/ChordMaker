package Controller;

import ChordMaker.EditorUtil;
import Model.Musica;
import View.EditorMusica;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class EditorMusicaController {

    private final Musica model;
    private final EditorMusica view;

    final private Sequencer sequencer;

    public EditorMusicaController(Musica model, EditorMusica view) throws MidiUnavailableException {
        this.model = model;
        this.view = view;

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

        view.setBotaoPlay(e -> {
            new Thread(() -> {
                try {
                    var seq = new Sequence(Sequence.PPQ, 480);
                    
                    for (int i = 0; i < view.getContagemTabs(); i++) {
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
                            return;
                        }
                    }
                    
                    model.setFaixa(seq);
                    
                    if (sequencer.isRunning()) {
                        sequencer.stop();
                    }
                    
                    sequencer.setSequence(model.getFaixa());
                    sequencer.setTickPosition(0);
                    sequencer.start();
                } catch (InvalidMidiDataException ex) {
                    System.getLogger(EditorMusicaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

            }).start();
        });

        view.setBotaoStop(e -> {
            if (sequencer.isRunning()) {
                sequencer.stop();
            }
        });

        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sequencer.close();
            }

        });

        view.setVisible(true);
    }
}
