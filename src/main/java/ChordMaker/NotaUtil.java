package ChordMaker;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class NotaUtil {
    private NotaUtil() {}
    
    private static String nomeNota(int noteNumber) {
        String[] notas = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
        };

        int oitava = (noteNumber / 12) - 1;
        String nota = notas[noteNumber % 12];

        return nota + oitava;
    }
    
    public static String descricaoMensagem(MidiMessage msg) {
        if (msg instanceof ShortMessage s_msg) {
            int cmd = s_msg.getCommand();
            int canal = s_msg.getChannel();
            int data1 = s_msg.getData1();
            int data2 = s_msg.getData2();

            return switch (cmd) {
                case ShortMessage.NOTE_ON ->
                    "NOTE ON - Ch: " + canal + " Nota: " + nomeNota(data1) + " Velocidade: " + data2;
                case ShortMessage.NOTE_OFF ->
                    "NOTE OFF - Ch: " + canal + " Nota: " + nomeNota(data1) + " Velocidade: " + data2;
                default ->
                    null;
            };
        }

        return null;
    }
}
