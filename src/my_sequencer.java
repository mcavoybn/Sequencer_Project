import java.util.ArrayList;

import processing.core.PApplet;

/**
 * my_sequencer
 * 
 * This class is capable of generating a sequence of my_notes which include the
 * neccesary time, duration, and frequency values for the Insturment.play()
 * function. This is accomplished by generating a series of sub_seq objects and
 * loading their note arrays into the my_sequencer note array after sub_seq is
 * initialized.
 * 
 * The sequence is generated according to a series of sub sequences which each
 * have their own key/chord parameters, as well as step time and length params
 * which are all adjustable.
 * 
 * ex: a sequence of 4 (Amaj) 3 (Amaj) 2(Amaj_pentatonic) would generate the
 * following: (A B C# D) (A B C) (A B C#)
 **/
public class my_sequencer {
	// This object manages the PApplet window we want to draw to
	PApplet parent;
	// These values are plugged into the Interval_Generator class in order to
	// generate a
	// series of chord or key intervals based on a particular root note
	String root_note;
	String key_chord;

	int octave;
	float tempo;
	int num_sub_seqs;

	ArrayList<my_note> notes;

	ArrayList<sub_seq> sub_list;
	// this value is used as a marker within the my_sequencer, the playNote()
	// function
	// isn't based on system time it is based on an offset so when you use
	// pauseNotes()
	// and start adding things with playNote() the time param is just an offset
	// from the time you paused
	float cur_time;
	// these values are all neccessary for the sub_seq but not for my_sequencer
	final int INIT_STEP_AMT = 12;
	final String INIT_MODE = "up";
	final float INIT_NOTE_LENGTH = (float) (1);

	my_sequencer(String root_note, String key_chord, int octave, float tempo, int num_sub_seqs, PApplet p) {
		parent = p;

		this.root_note = root_note;
		this.key_chord = key_chord;

		this.octave = octave;
		this.tempo = tempo;
		this.num_sub_seqs = num_sub_seqs;

		notes = new ArrayList<my_note>();

		sub_list = new ArrayList<sub_seq>();
		cur_time = 0;
	}

	void fill() {
		for (int i = 0; i < num_sub_seqs; i++) {
			// initialize a new sub_seq
			sub_seq a = new sub_seq(parent, root_note, key_chord, INIT_MODE, octave, INIT_STEP_AMT, tempo,
					INIT_NOTE_LENGTH, cur_time);
			// load it with values based on the initialization parameters
			a.fill();
			// add it to my_sequencers list of sub_sequences
			sub_list.add(a);
			// grab the note array from the newly generated sub_seq
			for (int j = 0; j < a.notes.length; j++) {
				notes.add(a.notes[j]);
			}
			// advance the time tracking forward
			// the time it takes for 1 beat (60.0 s / bpm )
			// times the length of 1 note in the sequence
			cur_time += a.step_amt * ((60.0 / tempo) * (a.note_length));
		}
	}

	/*
	 * This method is similar to the fill method except instead of generating a
	 * new sub sequence array, it just re loads the current array in my_sequence
	 * into the notes array
	 */
	void update() {
		// clear the notes array
		notes.clear();
		// reset the time marker
		cur_time = 0;
		for (int i = 0; i < num_sub_seqs; i++) {
			// line up the new list with the appropriate time
			sub_list.get(i).start_time = cur_time;
			// load the newly changed values into the sub_seq note array
			sub_list.get(i).update();
			for (int j = 0; j < sub_list.get(i).notes.length; j++) {
				notes.add(sub_list.get(i).notes[j]);
			}
			// advance the cur_time the appropriate amount, see the fill()
			// method for more info
			cur_time += sub_list.get(i).step_amt * ((60.0 / tempo) * (sub_list.get(i).note_length));
		}
	}

	public void set_root_note(String root_note) {
		this.root_note = root_note;
	}

	public void set_key_chord(String key_chord) {
		this.key_chord = key_chord;
	}

	public void set_octave(int octave) {
		this.octave = octave;
	}

	public void set_tempo(float tempo) {
		this.tempo = tempo;
	}

	public void set_num_sub_seqs(int num_sub_seqs) {
		this.num_sub_seqs = num_sub_seqs;
	}

	public void set_notes(ArrayList<my_note> notes) {
		this.notes = notes;
	}

}