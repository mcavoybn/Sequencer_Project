import processing.core.PApplet;

public class sub_seq {

	float[] note_freq_array;
	// This object will point to the applet we want to send info to
	PApplet parent;
	// This array should contain a series all the neccesary note information in
	// any sub_seq instance
	my_note[] notes;
	// these values
	int[] key_intervals;

	int tempo;
	int octave;
	int step_amt;
	float cur_time;
	int mode;

	sub_seq(PApplet p, int step_amt, int[] key_intervals, int tempo, int octave, float cur_time, int mode) {

		Interval_Generator ig = new Interval_Generator();
		this.note_freq_array = ig.generateNotes(9, 12, 16.35f);
		this.parent = p;
		this.key_intervals = key_intervals;
		this.octave = octave;
		this.tempo = tempo;
		this.step_amt = step_amt;
		this.cur_time = cur_time;
		this.notes = new my_note[step_amt];
		this.mode = mode;

		// apply_mode(mode);
		fill();
	}

	public void fill() {
		int note_index = octave * 12;
		float step_time = (float) ((float) 60.0 / (float) tempo) * ((float) 0.5);
		float gate_time = (float) step_time - (float) 0.05;

		int interval_pos = 0;
		for (int i = 0; i < notes.length; i++) {
			notes[i] = new my_note(cur_time, gate_time, note_freq_array[note_index]);
			cur_time += step_time;
			note_index += key_intervals[interval_pos];
			interval_pos++;
		}
	}

	private void apply_mode(int mode) {

	}

	public int get_step_amt() {
		return step_amt;
	}

	public void set_step_amt(int step_amt) {
		this.step_amt = step_amt;
	}

	public my_note[] get_notes() {
		return notes;
	}

	public void set_notes(my_note[] notes) {
		this.notes = notes;
	}

	public int get_mode() {
		return mode;
	}

	public void set_mode(int mode) {
		this.mode = mode;
	}

	public int get_tempo() {
		return tempo;
	}

	public void set_tempo(int tempo) {
		this.tempo = tempo;
	}

	public int get_octave() {
		return octave;
	}

	public void set_octave(int octave) {
		this.octave = octave;
	}

	public float get_cur_time() {
		return cur_time;
	}

	public void set_cur_time(float cur_time) {
		this.cur_time = cur_time;
	}
}
