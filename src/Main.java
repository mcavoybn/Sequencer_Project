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

	final int INIT_TEMPO = 100;
	final int INIT_SUBSEQ_COUNT = 12;
	final int INIT_OCTAVE = 4;
	final String INIT_KEY = "harMin";
	final String INIT_ROOT_NOTE = "F";
	// this object can generate the neccesary values to fill the notes array
	my_sequencer seq;

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

		seq = new my_sequencer(INIT_ROOT_NOTE, INIT_KEY, INIT_OCTAVE, INIT_TEMPO, INIT_SUBSEQ_COUNT, this);

		seq.fill();
		// make possible modifications to sequencer here

		seq.sub_list.get(4).set_key_chord("AugC");
		seq.sub_list.get(4).set_octave(2);

		seq.sub_list.get(5).set_key_chord("AugC");
		seq.sub_list.get(5).set_octave(4);
		seq.update();

		out.pauseNotes();
		for (int i = 0; i < seq.notes.size(); i++) {
			out.playNote(seq.notes.get(i).time, seq.notes.get(i).duration,
					new my_instrument(this, seq.notes.get(i).note_freq, out));
		}
		out.resumeNotes();
	}

	// final int LOOP_FRAME_COUNT;
	float colorModR = 0;
	float colorModG = 255;
	float colorModB = 125;

	public void draw() {
		background(0);
		stroke(255);
		if (colorModR <= 255) {
			colorModR += 1;
		} else {
			colorModR = 0;
		}
		if (colorModG > 0) {
			colorModG -= 0.1;
		} else {
			colorModG = 0;
		}
		if (colorModB <= 255) {
			colorModB += 0.1;
		} else {
			colorModB = 0;
		}
		// draw the waveforms
		for (int i = 0; i < out.bufferSize() - 1; i++) {

			stroke(colorModR, colorModG, colorModB);
			line(i, 50 + out.left.get(i) * 50, i + 1, 50 + out.left.get(i + 1) * 50);
			line(i, 150 + out.right.get(i) * 50, i + 1, 150 + out.right.get(i + 1) * 50);
			line(i, 250 + out.left.get(i) * 50, i + 1, 250 + out.left.get(i + 1) * 50);
			line(i, 350 + out.right.get(i) * 50, i + 1, 350 + out.right.get(i + 1) * 50);
			line(i, 450 + out.left.get(i) * 50, i + 1, 450 + out.left.get(i + 1) * 50);
		}

	}

}
