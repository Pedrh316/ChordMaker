package ChordMaker.Core;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MidiPlayer {
    private final Sequencer sequencer;

    public MidiPlayer(Sequencer seq) throws MidiUnavailableException {
        this.sequencer = seq;
        this.sequencer.open();
    }
    
    public MidiPlayer() throws MidiUnavailableException {
        this.sequencer = MidiSystem.getSequencer();
        this.sequencer.open();
    }
    
    public void play(Sequence seq) throws MidiUnavailableException, InvalidMidiDataException {
        if (seq == null) return;
        
        if (!sequencer.isOpen()) {
            sequencer.open();
        }

        this.stop();

        sequencer.setSequence(seq);
        sequencer.setTickPosition(0);

        sequencer.start();
    }

    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
    }
    
    public void close() {
        if (sequencer.isOpen()) {
            sequencer.close();
        }
    }

    public Sequencer getSequencer() {
        return sequencer;
    }
    
    
}
