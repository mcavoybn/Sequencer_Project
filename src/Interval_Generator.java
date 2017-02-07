
public class Interval_Generator {
	// This function accepts a string value k which is a formatted key sign

	static int[] gen_music_key_seq(String k, int step_amt) {
		int[] scale = get_scale(k);

		int[] ret_array = new int[step_amt];
		int j = 0;
		for (int i = 0; i < step_amt; i++) {
			if (j >= scale.length) {
				j = 0;
			}
			ret_array[i] = scale[j];
			j++;

		}
		return ret_array;
	}

	static int[] get_scale(String k) {
		switch (k) {
		// Main Scales
		case "maj":
			int[] majorScale = { 2, 2, 1, 2, 2, 2, 1 };
			return majorScale;
		case "melMin":
			int[] melMinorScale = { 2, 1, 3, 2, 1, 3 }; //
			return melMinorScale;
		case "harMin":
			int[] harMinorScale = { 2, 1, 2, 2, 1, 2, 1 }; //
			return harMinorScale;

		// Pentatonic Scales
		case "majPent":
			int[] majPentScale = { 2, 2, 3, 2, 3 };
			return majPentScale;
		case "minPent":
			int[] minPentScale = { 3, 2, 2, 3, 2 };
			return minPentScale;

		// Modes
		case "Aeolian":
			int[] aeolianMode = { 2, 1, 2, 2, 1, 2, 2 }; //
			return aeolianMode;
		case "Dorian":
			int[] dorianMode = { 2, 1, 3, 2, 2, 1, 2 }; //
			return dorianMode;
		case "Locrian":
			int[] locrianMode = { 1, 2, 2, 1, 2, 2, 2 };
			return locrianMode;
		case "Lydian":
			int[] lydianMode = { 2, 2, 2, 1, 2, 2, 1 };
			return lydianMode;
		case "Mixolydian":
			int[] mixolydianMode = { 2, 2, 1, 2, 2, 1, 2 };
			return mixolydianMode;
		case "Phyrigian":
			int[] phrygianMode = { 1, 2, 2, 2, 1, 2, 2 };
			return phrygianMode;

		// Augmented Chords (C just stands for chord not the note C)
		case "AugC":
			int[] augC = { 4 };
			return augC;
		case "AugMaj7C":
			int[] augMaj7C = { 4, 4, 3, 1 };
			return augMaj7C;
		case "Aug7C":
			int[] aug7C = { 4, 4, 2, 2 };
			return aug7C;
		case "Maj7s11C":
			int[] maj7s11C = { 4, 4, 3, 7, 6 };
			return maj7s11C;

		// Bitonal Chords

		// Diminished Chords

		// Suspended chord

		// Just Chords

		// Major Chords

		// Minor chords

		}
		return new int[1];
	}

	static int get_note_index(String n) {
		switch (n) {
		case "C":
			return 0;
		case "Cs":
			return 1;
		case "Db":
			return 1;
		case "D":
			return 2;
		case "Ds":
			return 3;
		case "Eb":
			return 3;
		case "E":
			return 4;
		case "F":
			return 5;
		case "Fs":
			return 6;
		case "Gb":
			return 6;
		case "G":
			return 7;
		case "Gs":
			return 8;
		case "Ab":
			return 8;
		case "A":
			return 9;
		case "As":
			return 10;
		case "B":
			return 11;
		}
		return 0;
	}

}
