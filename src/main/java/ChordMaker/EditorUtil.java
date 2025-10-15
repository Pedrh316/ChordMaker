package ChordMaker;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

public class EditorUtil {

    private EditorUtil() {
    }

    /**
     * Transforma uma nota em string para seu número no formato MIDI
     * 
     * @param nota String - Nota em String
     * @return int - Nota no formato MIDI
     */
    public static int notaParaNumero(String nota) {
        var base = nota.substring(0, nota.length() - 1);
        var oitava = Integer.parseInt(nota.substring(nota.length() - 1));

        String[] notas = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        var index = -1;

        for (int i = 0; i < notas.length; i++) {
            if (notas[i].equals(base)) {
                index = i;
                break;
            }
        }

        return (oitava + 1) * 12 + index;
    }

    /**
     * Transforma comandos MIDI em string para uma Sequence
     * 
     * @param texto String - Comandos MIDI
     * @return Sequence - Sequence MIDI criada a partir dos comandos
     */
    public static Sequence textoParaSequence(String texto) {
        try {
            var seq = new Sequence(Sequence.PPQ, 480);
            var track = seq.createTrack();

            var tinhas = texto.split("\\R");
            for (String line : tinhas) {
                line = line.trim();

                if (!line.startsWith("Tick:")) {
                    continue;
                }

                // Ex: Tick: 119 | NOTE ON - Ch: 0 Nota: C5 Velocidade: 80
                var partes = line.split("\\|");
                var tick = Long.parseLong(partes[0].replace("Tick:", "").trim());

                var event = partes[1].trim();

                if (event.startsWith("NOTE")) {
                    var on = event.startsWith("NOTE ON");
                    var tokens = event.split("\\s+");
                    var canal = Integer.parseInt(tokens[4]);
                    var nota = tokens[6];
                    var velocidade = Integer.parseInt(tokens[8]);

                    int noteNumber = notaParaNumero(nota);
                    ShortMessage msg = new ShortMessage();
                    msg.setMessage(on ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, canal, noteNumber, velocidade);
                    track.add(new MidiEvent(msg, tick));
                }
            }

            return seq;
        } catch (NumberFormatException | InvalidMidiDataException | ArrayIndexOutOfBoundsException e) {
            // System.err.println("Erro parseando texto: " + e.getMessage());
            return null;
        }
    }

}
