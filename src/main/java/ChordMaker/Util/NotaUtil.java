package ChordMaker.Util;

import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
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
