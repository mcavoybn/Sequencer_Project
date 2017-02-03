import ddf.minim.*;
import ddf.minim.ugens.*;
import processing.core.PApplet;
import java.util.*;

public class Main extends PApplet{	
	
	public static void main(String[] args){
		PApplet.main("Main");
	}
	 
	Minim minim;
	AudioOutput out;

	final int INIT_TEMPO = 80;
	final int INIT_SUBSEQ_COUNT = 10;
	final int INIT_OCTAVE = 4;
	final String INIT_KEY = "min";
	//this object can generate the neccesary values to fill the notes array
	my_sequencer seq;
	 
	//this should have the current sequence of notes at any given time
	my_note[] notes;
	
	public void settings(){
		size(500, 500);
		}
	 
	public void setup() {
		background(0);
		fill(255);
		frameRate(60);
		//initialize the audio out using Minim
		minim = new Minim(this);
		out = minim.getLineOut();
		out.setTempo( INIT_TEMPO );
	  
		seq = new my_sequencer(INIT_KEY, INIT_TEMPO, INIT_SUBSEQ_COUNT, INIT_OCTAVE );
	    //make possible modifications to sequencer here
	 
	    notes = seq.get_notes();
	    out.pauseNotes();
	    for(int i=0; i<notes.length; i++){
	        out.playNote( notes[i].time, notes[i].duration, new my_instrument(notes[i].note_freq) );
	    }
	    out.resumeNotes();
	}
	 
	public void draw(){
		
//		out.pauseNotes();
//	    notes = (my_note[]) seq.get_notes();
//	    for(int i=0; i<notes.length; i++){
//	      out.playNote( notes[i].time, notes[i].duration, notes[i].note_freq );
//	    }
	 
	  //possible looping method
	  //if(out.mix.get(0)==0){
	     //out.resumeNotes();
	  //}
	 
	}
	 
	////////////////////////////////////////////////////////////////////
	///                     PRIVATE    CLASSES                       ///
	////////////////////////////////////////////////////////////////////
	 
	/**
	  my_instrument
	 
	  This class implements the Instrument abstract class from Minim
	  Its oscillator can be changed to Waves.SAW, Waves.SQUARE, Waves.SINE
	  WAVES.PHASOR (PM wave), and Waves.QUARTERPULSE (another PM wave)
	 
	  There is an Oscil object in my_insturment which has amplitude, frequency, offset,
	  and phase parameters.
	 
	  The Line object is used as an envelope for my_instrument.
	  duration — float: how long it should take, in seconds, to transition from the beginning value to the end value.
	  beginAmp — float: the value to begin at
	  endingAmp — float: the value to end at
	 
	**/
	class my_instrument implements Instrument{
	  Oscil wave;
	  Line ampEnv;
	 
	    my_instrument(float frequency)
	  {
	    // make a sine wave oscillator and patch the envelope to it
	    wave   = new Oscil( frequency, 0, Waves.SINE );
	    ampEnv = new Line();
	    ampEnv.patch( wave.amplitude );
	  }
	 
	  //duration is expressed in seconds.
	  public void noteOn( float duration )
	  {
	    ampEnv.activate( duration, 0.5f, 0 );
	    wave.patch( out );
	  }
	  
	  public void noteOff()
	  {
	    wave.unpatch( out );
	  }
	}
	 
	 
	/**
	  my_sequencer
	 
	  This class (will be) capable of generating a sequence of my_notes
	  which include the neccesary time, duration, and frequency values
	  for the Insturment.play() function
	 
	  The sequence is generated according to a series of sub sequences
	  which each have their own key or chord parameters (and hopefully other
	  parameters like waveform, duration, etc).
	 
	  ex: a sequence of  4 (Amaj) 3 (Amaj) 2(Amaj_pentatonic) would generate
	  the following: (A B C# D) (A B C) (A B C#)
	**/
	class my_sequencer {
	   
	  float C0, Cs0, D0, Ds0, E0, F0, Fs0, G0, Gs0, A0, As0, B0, 
	      C1, Cs1, D1, Ds1, E1, F1, Fs1, G1, Gs1, A1, As1, B1, 
	      C2, Cs2, D2, Ds2, E2, F2, Fs2, G2, Gs2, A2, As2, B2, 
	      C3, Cs3, D3, Ds3, E3, F3, Fs3, G3, Gs3, A3, As3, B3, 
	      C4, Cs4, D4, Ds4, E4, F4, Fs4, G4, Gs4, A4, As4, B4, 
	      C5, Cs5, D5, Ds5, E5, F5, Fs5, G5, Gs5, A5, As5, B5, 
	      C6, Cs6, D6, Ds6, E6, F6, Fs6, G6, Gs6, A6, As6, B6,
	      C7, Cs7, D7, Ds7, E7, F7, Fs7, G7, Gs7, A7, As7, B7, 
	      C8, Cs8, D8, Ds8, E8, F8, Fs8, G8, Gs8, A8, As8, B8;
	      
	  float[] note_freq_array = {
	   C0  = (float)16.35,  Cs0  = (float)17.32,  D0    = (float)18.35,  Ds0 = (float)19.45,
	   E0    = (float)20.60,  F0    = (float)21.83,  Fs0 =     (float)23.12,  G0    = (float)24.50,
	   Gs0 = (float)25.96,  A0    = (float)27.50,  As0 =     (float)29.14,  B0    = (float)30.87,
	   C1    = (float)32.70,  Cs1=      (float)34.65,  D1    = (float)36.71,  Ds1=  (float)38.89,
	   E1    = (float)41.20,  F1    = (float)43.65,  Fs1=  (float)46.25,  G1    = (float)49.00,
	   Gs1   =   (float)51.91,  A1    = (float)55.00,  As1   =   (float)58.27,
	   B1    = (float)61.74,  C2    = (float)65.41,  Cs2   =   (float)69.30,  D2    = (float)73.42,
	   Ds2    =      (float)77.78,  E2    = (float)82.41,  F2    = (float)87.31,   Fs2=     (float)92.50,
	   G2    = (float)98.00,   Gs2=     (float)103.83,  A2    = (float)110.00,   As2=     (float)116.54,
	   B2    = (float)123.47,  C3    = (float)130.81,   Cs3=     (float)138.59,  D3    = (float)146.83,
	    Ds3=     (float)155.56,  E3    = (float)164.81,  F3    = (float)174.61,   Fs3=  (float)185.00,
	   G3    = (float)196.00,   Gs3=      (float)207.65,  A3    = (float)220.00,   As3=     (float)233.08,
	   B3    = (float)246.94,  C4    = (float)261.63,   Cs4=     (float)277.18,  D4    = (float)293.66,
	    Ds4=     (float)311.13,  E4    = (float)329.63,  F4    = (float)349.23,   Fs4=     (float)369.99,
	   G4    = (float)392.00,   Gs4=     (float)415.30,  A4    = (float)440.00,   As4=     (float)466.16,
	   B4    = (float)493.88,  C5    = (float)523.25,   Cs5=     (float)554.37,  D5    = (float)587.33,
	    Ds5=     (float)622.25,  E5    = (float)659.25,  F5    = (float)698.46,   Fs5=     (float)739.99,
	   G5    = (float)783.99,   Gs5=     (float)830.61,  A5    = (float)880.00,   As5=     (float)932.33,
	   B5    = (float)987.77,  C6    = (float)1046.50,   Cs6=     (float)1108.73,  D6    = (float)1174.66,
	    Ds6=     (float)1244.51,  E6    = (float)1318.51,  F6    = (float)1396.91,   Fs6=     (float)1479.98,
	   G6    = (float)1567.98,   Gs6=     (float)1661.22,  A6    = (float)1760.00,   As6=     (float)1864.66,
	   B6    = (float)1975.53,  C7    = (float)2093.00,   Cs7=     (float)2217.46,  D7    = (float)2349.32,
	    Ds7=     (float)2489.02,  E7    = (float)2637.02,  F7    = (float)2793.83,   Fs7=     (float)2959.96,
	   G7    = (float)3135.96,   Gs7=     (float)3322.44,  A7    = (float)3520.00,   As7=     (float)3729.31,
	   B7    = (float)3951.07,  C8    = (float)4186.01,   Cs8=     (float)4434.92,  D8    = (float)4698.63,
	    Ds8=     (float)4978.03,  E8    = (float)5274.04,  F8    = (float)5587.65,   Fs8=     (float)5919.91,
	   G8    = (float)6271.93,   Gs8=     (float)6644.88,  A8    = (float)7040.00,   As8=     (float)7458.62,
	   B8    = (float)7902.13
	  };
	 
	  ArrayList<my_note> notes;
	  ArrayList<sub_seq> sub_list;
	  int num_sub_seqs;  
	  int tempo;
	  int octave;
	  int[] key_intervals;
	  float cur_time;
	  
	 
	  my_sequencer(String k, int tempo, int num_sub_seqs, int octave){
	    //initialize all the class parameters
	    notes = new ArrayList<my_note>();
	    sub_list = new ArrayList<sub_seq>();
	    key_intervals = gen_music_key_seq(k);
	    this.tempo = tempo;
	    cur_time = 0;
	    
	    //initialize sub sequences
	    this.num_sub_seqs = num_sub_seqs;
	    for(int i=0; i<num_sub_seqs; i++){
	        sub_seq a = new sub_seq(5, key_intervals, tempo, octave, cur_time);
	        sub_list.add(a);
	        my_note[] sub_seq_notes = a.get_notes();
	        for(int j=0; j<sub_seq_notes.length; j++){
	        	notes.add(sub_seq_notes[j]);
	        }
	        cur_time+=(float)5*(((float)tempo/60.0)*(0.5));
	      }
	   }
	
	
	public float[] get_note_freq_array() {
		return note_freq_array;
	}
	public void set_note_freq_array(float[] note_freq_array) {
		this.note_freq_array = note_freq_array;
	}
	public ArrayList<my_note> getNotes() {
		return notes;
	}
	public void set_notes(ArrayList<my_note> notes) {
		this.notes = notes;
	}
	public ArrayList<sub_seq> get_sub_list() {
		return sub_list;
	}
	public void set_sub_list(ArrayList<sub_seq> sub_list) {
		this.sub_list = sub_list;
	}
	public int get_num_sub_seqs() {
		return num_sub_seqs;
	}
	public void set_num_sub_seqs(int num_sub_seqs) {
		this.num_sub_seqs = num_sub_seqs;
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
	public int[] getKey_intervals() {
		return key_intervals;
	}
	public void setKey_intervals(int[] key_intervals) {
		this.key_intervals = key_intervals;
	}

	public my_note[] get_notes(){
		  my_note[] note_array = new my_note[notes.size()];
		  for(int i=0; i<notes.size(); i++){
			  note_array[i] = notes.get(i);
		  }
		  return note_array;
	  }
	//This function accepts a string value k which is a formatted key sign
	int[] gen_music_key_seq(String k){
	    switch(k){
	      case "maj":
	        int[] majorScale = { 2, 2, 1, 2, 2, 2, 1 };
	        return majorScale;	   
	      case "min":
	        int[] minorScale = { 2, 1, 2, 2, 1, 2, 2 };
	        return minorScale;
	      }
	    return new int[1];
	  }
	  
	 
	 
	  class sub_seq{
		  
		public float get_cur_time() {
			return cur_time;
		}

		public void set_cur_time(float cur_time) {
			this.cur_time = cur_time;
		}
		int step_amt;
		my_note[] notes;
		int[] key_intervals;
		int mode;
		int tempo;
		int octave;
		int interval_pos;
		float cur_time;
		float step_time;
		float gate_time;
		
		
		sub_seq(int step_amt, int[] key_intervals, int tempo, int octave, float cur_time){
			this.octave = octave;
			this.step_amt = step_amt;
			this.key_intervals = key_intervals;
			this.cur_time = cur_time;
			this.notes = new my_note[step_amt];
			int note_index = octave*12;			
			//this.mode = mode;			
			
			//set the step time to be the length of 1 beat * the fraction of the time we want
			step_time = (float)((float)60.0/(float)tempo)*((float)0.5); //0.5 = half notes
			gate_time = (float)step_time - (float)0.05;		
			
		    interval_pos=0;	       
	        for(int i = 0; i < notes.length; i++){
	           notes[i] = new my_note(cur_time, gate_time, note_freq_array[note_index]);
	           cur_time+=step_time;
	           note_index+=key_intervals[interval_pos];
	           interval_pos++;
	        }
	     
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
		} 
	}
	 
	/**
	  my_note
	 
	  This class acts as a placeholder for the values neccesary to call the
	  AudioOutput.playNote() function
	**/
	class my_note{
	 
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
	   
	  my_note(float t, float d, float n){
	   time = t;
	   duration = d;
	   note_freq = n;
	  }
	}
}
