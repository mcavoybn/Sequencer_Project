public class Interval_Generator {

	public Interval_Generator() {

	}

	public float[] generateNotes(int octaves, int notesPerOctave, float rootNote) {
		// make an array of notes to be populated
		float[] notes = new float[notesPerOctave * octaves];
		// set the root note
		notes[0] = rootNote;
		// find the interval ratio
		float intervalRatio = (float) nthroot(notesPerOctave, 2);

		for (int index = 1; index < notes.length; index++) {
			// if the note is an octave float the previous value to minimize
			// drift
			if (index + 1 % notesPerOctave == 0) {
				notes[index] = notes[index - notesPerOctave] * 2;
			} else {// if not this note is a product of the last note and the
					// interval ratio
				notes[index] = notes[index - 1] * intervalRatio;
			}
		}
		return notes;
	}

	public static float nthroot(int n, float x) {
		if (x < 0) {
			System.err.println("Negative!");
			return -1;
		}
		if (x == 0) {
			return 0;
		}
		float x1 = x;
		float x2 = x / n;
		while (Math.abs(x1 - x2) > 0.000000001) {
			x1 = x2;
			x2 = (float) (((n - 1.0) * x2 + x / Math.pow(x2, n - 1.0)) / n);
		}
		return x2;
	}

}