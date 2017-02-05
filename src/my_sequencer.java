import java.util.ArrayList;

import processing.core.PApplet;

/**
 * my_sequencer
 * 
 * This class (will be) capable of generating a sequence of my_notes which
 * include the neccesary time, duration, and frequency values for the
 * Insturment.play() function
 * 
 * The sequence is generated according to a series of sub sequences which each
 * have their own key or chord parameters (and hopefully other parameters like
 * waveform, duration, etc).
 * 
 * ex: a sequence of 4 (Amaj) 3 (Amaj) 2(Amaj_pentatonic) would generate the
 * following: (A B C# D) (A B C) (A B C#)
 **/
public class my_sequencer {

	PApplet parent;

	String root_note;
	String key_chord;

	int octave;
	float tempo;
	int num_sub_seqs;

	ArrayList<my_note> notes;

	ArrayList<sub_seq> sub_list;

	float cur_time;

	final int INIT_STEP_AMT = 12;
	final String INIT_MODE = "up";
	final float INIT_NOTE_LENGTH = (float) (1.0 / 6.0);

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
		sub_list.clear();
		for (int i = 0; i < num_sub_seqs; i++) {
			sub_seq a = new sub_seq(parent, root_note, key_chord, INIT_MODE, octave, INIT_STEP_AMT, tempo,
					INIT_NOTE_LENGTH, cur_time);
			a.fill();
			sub_list.add(a);
			for (int j = 0; j < a.notes.length; j++) {
				notes.add(a.notes[j]);
			}
			cur_time += a.step_amt * ((60.0 / tempo) * (a.note_length));
		}
	}

	void update() {
		notes.clear();
		for (int i = 0; i < num_sub_seqs; i++) {
			for (int j = 0; j < sub_list.get(i).notes.length; j++) {
				sub_list.get(i).update();
				notes.add(sub_list.get(i).notes[j]);
			}
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