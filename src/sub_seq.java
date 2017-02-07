import java.util.Random;

import processing.core.PApplet;

public class sub_seq {

	PApplet parent;

	float[] note_freq_array;

	String root_note;
	String key_chord;
	String mode;

	int octave;
	int step_amt;
	float tempo;
	float note_length;

	float start_time;

	my_note[] notes;

	sub_seq(PApplet p, String root_note, String key_chord, String mode, int octave, int step_amt, float tempo,
			float note_length, float start_time) {
		this.parent = p;

		note_freq_array = Frequency_Generator.generate_Freqs(9, 12, 16.5f);

		this.root_note = root_note;
		this.key_chord = key_chord;
		this.mode = mode;

		this.octave = octave;
		this.step_amt = step_amt;
		this.tempo = tempo;
		this.note_length = note_length;

		this.start_time = start_time;

	}

	void fill() {
		// Load an array of intervals from the static Interval_Generator class
		// based on the key_chord and step_amt
		int[] key_intervals = Interval_Generator.gen_music_key_seq(key_chord, step_amt);
		// initialize the note array
		notes = new my_note[step_amt];
		// generate the interval value for the root note to be used within the
		// note_freq_array
		// this points to the start fot the interval sequence
		int cur_note_index = (octave * 12) + Interval_Generator.get_note_index(root_note);

		// generate the actual step time amt
		float step_time = ((float) 60.0 / tempo) * (note_length);
		// set the gate time to be used as the duration value in each my_note
		float gate_time = step_time - (float) 0.005;

		// set the time that the sequence should start
		float cur_time = start_time;

		// this is where every sequence interval mode should be added
		// mode ideas: up up, up down, down up, up down, random down, random up,
		// random random
		// multi chord modes? idk
		switch (mode) {
		case "up":
			// this value is keeps track of the index we are in in the
			// interval_pos array
			int interval_pos = 0;
			for (int i = 0; i < notes.length; i++) {
				this.notes[i] = new my_note(cur_time, gate_time, note_freq_array[cur_note_index]);
				// step the time track forward the right amt
				cur_time += step_time;
				// step through the note_freq_array based on the current
				// interval amt
				cur_note_index += key_intervals[interval_pos];
				interval_pos++;
			}
			break;
		case "rand":
			Random r = new Random();
			int randInt1;
			int randInt2;
			interval_pos = 0;
			for (int i = 0; i < notes.length; i++) {
				if (cur_note_index < 0) {
					cur_note_index = 0;
				}
				this.notes[i] = new my_note(cur_time, gate_time, note_freq_array[cur_note_index]);
				cur_time += step_time;
				cur_note_index += key_intervals[interval_pos];

				randInt1 = r.nextInt(2);
				randInt2 = r.nextInt(4);
				if (randInt2 <= 1) {
					cur_note_index += randInt1;
				} else {
					cur_note_index -= randInt1;
				}

				interval_pos++;

			}
			break;
		}
	}

	public void update() {
		note_freq_array = Frequency_Generator.generate_Freqs(9, 12, 20f);

		fill();
	}

	public void set_root_note(String root_note) {
		this.root_note = root_note;
	}

	public void set_key_chord(String key_chord) {
		this.key_chord = key_chord;
	}

	public void set_mode(String mode) {
		this.mode = mode;
	}

	public void set_octave(int octave) {
		this.octave = octave;
	}

	public void set_step_amt(int step_amt) {
		this.step_amt = step_amt;
	}

	public void set_tempo(float tempo) {
		this.tempo = tempo;
	}

	public void set_note_length(float note_length) {
		this.note_length = note_length;
	}

	public void set_notes(my_note[] notes) {
		this.notes = notes;
	}

}
