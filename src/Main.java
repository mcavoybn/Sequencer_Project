import java.util.ArrayList;

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

	final int INIT_TEMPO = 203;
	final int INIT_SUBSEQ_COUNT = 1;
	final int INIT_OCTAVE = 3;
	final String INIT_KEY = "majPent";
	final String INIT_ROOT_NOTE = "C";
	// this object can generate the neccesary values to fill the notes array
	my_sequencer seq;

	double LOOP_FRAME_COUNT;
	double LOOP_FRAME_COUNT_OFFSET;
	ArrayList<Thread> loopingThreads = new ArrayList<Thread>();

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

		// seq.update();

		float lastNote = seq.sub_list
				.get(seq.sub_list.size() - 1).notes[seq.sub_list.get(seq.sub_list.size() - 1).notes.length - 1].time;
		float lastNoteLength = seq.sub_list.get(
				seq.sub_list.size() - 1).notes[seq.sub_list.get(seq.sub_list.size() - 1).notes.length - 1].duration;

		LOOP_FRAME_COUNT = (lastNote + lastNoteLength) * frameRate;
		LOOP_FRAME_COUNT_OFFSET = (LOOP_FRAME_COUNT + 1) - LOOP_FRAME_COUNT;
		LOOP_FRAME_COUNT = Math.ceil(LOOP_FRAME_COUNT);

		out.pauseNotes();
		for (int i = 0; i < seq.notes.size(); i++) {
			out.playNote(seq.notes.get(i).time + (float) LOOP_FRAME_COUNT_OFFSET, seq.notes.get(i).duration,
					new my_instrument(this, seq.notes.get(i).note_freq, out));
		}
		out.resumeNotes();
	}

	// final int LOOP_FRAME_COUNT;
	float colorModR = 0;
	float colorModG = 255;
	float colorModB = 125;

	public void draw() {
		if (frameCount % LOOP_FRAME_COUNT == 0) {
			for (int i = 0; i < seq.notes.size(); i++) {
				out.playNote(seq.notes.get(i).time + (float) LOOP_FRAME_COUNT_OFFSET, seq.notes.get(i).duration,
						new my_instrument(this, seq.notes.get(i).note_freq, out));
			}
			out.resumeNotes();
		}
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
