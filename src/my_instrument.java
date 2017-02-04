import ddf.minim.AudioOutput;
import ddf.minim.ugens.Instrument;
import ddf.minim.ugens.Line;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import processing.core.PApplet;

/**
 * my_instrument
 * 
 * This class implements the Instrument abstract class from Minim Its oscillator
 * can be changed to Waves.SAW, Waves.SQUARE, Waves.SINE WAVES.PHASOR (PM wave),
 * and Waves.QUARTERPULSE (another PM wave)
 * 
 * There is an Oscil object in my_insturment which has amplitude, frequency,
 * offset, and phase parameters.
 * 
 * The Line object is used as an envelope for my_instrument. duration — float:
 * how long it should take, in seconds, to transition from the beginning value
 * to the end value. beginAmp — float: the value to begin at endingAmp — float:
 * the value to end at
 * 
 **/
public class my_instrument implements Instrument {
	Oscil wave;
	Line ampEnv;
	PApplet parent; // The parent PApplet that we will render ourselves onto
	AudioOutput out;

	my_instrument(PApplet p, float frequency, AudioOutput out) {
		// point to the parent PApplet in the constructor
		parent = p;
		// point to the parent PApplet AudioOutput
		this.out = out;
		// make a sine wave oscillator and patch the envelope to it
		wave = new Oscil(frequency, 0, Waves.SINE);
		ampEnv = new Line();
		ampEnv.patch(wave.amplitude);
	}

	// duration is expressed in seconds.
	public void noteOn(float duration) {
		ampEnv.activate(duration, 0.5f, 0);
		wave.patch(out);
	}

	public void noteOff() {
		wave.unpatch(out);
	}
}