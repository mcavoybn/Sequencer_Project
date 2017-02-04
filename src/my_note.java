/**
 * my_note
 * 
 * This class acts as a placeholder for the values neccesary to call the
 * AudioOutput.playNote() function
 **/
class my_note {

	public float get_time() {
		return time;
	}

	public void set_time(float time) {
		this.time = time;
	}

	public float get_duration() {
		return duration;
	}

	public void set_duration(float duration) {
		this.duration = duration;
	}

	public float get_note_freq() {
		return note_freq;
	}

	public void set_note_freq(float note_freq) {
		this.note_freq = note_freq;
	}

	float time;
	float duration;
	float note_freq;

	my_note(float t, float d, float n) {
		time = t;
		duration = d;
		note_freq = n;
	}
}