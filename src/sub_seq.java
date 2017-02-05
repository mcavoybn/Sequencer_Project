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

		note_freq_array = Frequency_Generator.generate_Freqs(9, 12, 16.35f);

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

		int[] key_intervals = Interval_Generator.gen_music_key_seq(key_chord, step_amt);
		notes = new my_note[step_amt];

		int cur_note_index = (octave * 12) + Interval_Generator.get_note_index(root_note);
		float step_time = ((float) 60.0 / tempo) * (note_length);
		float gate_time = step_time - (float) 0.005;
		float cur_time = start_time;

		switch (mode) {
		case "up":
			int interval_pos = 0;
			for (int i = 0; i < notes.length; i++) {
				this.notes[i] = new my_note(cur_time, gate_time, note_freq_array[cur_note_index]);
				cur_time += step_time;
				cur_note_index += key_intervals[interval_pos];
				interval_pos++;
			}
			break;
		}
	}

	public void update() {
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
