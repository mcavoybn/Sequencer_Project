import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import processing.core.PApplet;

//TODO:
// modularize each class so that it can be manipulated
//figure out how to finish the rest of the sequencer implementation

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	Minim minim;
	AudioOutput out;

	final int INIT_TEMPO = 80;
	final int INIT_SUBSEQ_COUNT = 3;
	final int INIT_OCTAVE = 4;
	final String INIT_KEY = "maj";
	// this object can generate the neccesary values to fill the notes array
	my_sequencer seq;

	// this should have the current sequence of notes at any given time
	my_note[] notes;

	public void settings() {
		size(500, 500);
	}

	public void setup() {
		background(0);
		fill(255);
		frameRate(60);
		// initialize the audio out using Minim
		minim = new Minim(this);
		out = minim.getLineOut();
		out.setTempo(INIT_TEMPO);

		seq = new my_sequencer(INIT_KEY, INIT_OCTAVE, INIT_TEMPO, INIT_SUBSEQ_COUNT, this);
		// make possible modifications to sequencer here

		// this initializes the sequence loop
		notes = seq.get_notes();
		out.pauseNotes();
		for (int i = 0; i < notes.length; i++) {
			out.playNote(notes[i].time, notes[i].duration, new my_instrument(this, notes[i].note_freq, out));
		}
		out.resumeNotes();
	}

	public void draw() {
		background(0);
		stroke(255);

		float colorModR = 0;
		float colorModG = 0;
		float colorModB = 0;

		// draw the waveforms
		for (int i = 0; i < out.bufferSize() - 1; i++) {
			colorModR = map(i, 0, out.bufferSize(), 0, 1 * (255 / 3));
			colorModG = map(i, 0, out.bufferSize(), 1 * (255 / 3), 2 * (255 / 3));
			colorModB = map(i, 0, out.bufferSize(), 2 * (255 / 3), 3 * (255 / 3));
			stroke(colorModR, colorModG, colorModB);
			line(i, 50 + out.left.get(i) * 50, i + 1, 50 + out.left.get(i + 1) * 50);
			line(i, 150 + out.right.get(i) * 50, i + 1, 150 + out.right.get(i + 1) * 50);
			line(i, 250 + out.left.get(i) * 50, i + 1, 250 + out.left.get(i + 1) * 50);
			line(i, 350 + out.right.get(i) * 50, i + 1, 350 + out.right.get(i + 1) * 50);
			line(i, 450 + out.left.get(i) * 50, i + 1, 450 + out.left.get(i + 1) * 50);
		}

	}

}
