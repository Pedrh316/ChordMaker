package ChordMaker.Core;

import Controller.EditorMusicaController;
import Model.Musica;
import View.EditorMusica;
import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.swing.JFileChooser;

public class MidiExportImport {
    public void importar(EditorMusica view, Musica model) {
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
    }
    
    public void exportar(EditorMusica view, Musica model) {
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
    }
}
