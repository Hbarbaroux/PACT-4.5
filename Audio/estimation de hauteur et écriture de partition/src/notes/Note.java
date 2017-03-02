package notes;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.dfp.Dfp;
import org.apache.commons.math3.transform.*;

import comparaison.Tabnotes

import java.util.*;

public class Note {
	
	private int temps;
	private int freq; 
	public static final DftNormalization STANDARD;
	public static final TransformType FORWARD;
	public static final TransformType INVERSE;
	
	
	public Note(int temps, int freq) {  //Constructor
		this.temps=temps;
		this.freq=freq;
	}

	public int getTemps() {  /*Getters and Setters */
		return temps;
	}
	public void setTemps(int temps) {
		this.temps = temps;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public Complex[] toListComplex(ArrayList<Complex> signal)

	{
		Complex[] res = new Complex[signal.size()];
		for (int k=0; k<signal.size(); k++ )
		{
			res[k] = signal.get(k);
		}
		return res;
	}
	
	public ArrayList<Complex> toArrayListComplex(Complex[] signal)
	{
		ArrayList<Complex> res = new ArrayList<Complex>(signal.length);
		for (int k=0; k<signal.length; k++ )
		{
			res.add(signal[k]);
		}
		return res;
	}
	// Multiplies ArrayList by -1
	
	public ArrayList<Float> oppose(ArrayList<Float> echantillon)
	{
		for (int k=0; k<echantillon.size(); k++) 
		{
			float a = echantillon.get(k);
			echantillon.remove(k);
			echantillon.add(k, -1*a*a);
		}
	}
	
	// Multiplies term by term two ArrayLists
	public ArrayList<Complex> multTaT(ArrayList<Complex> signal, ArrayList<Complex> signalb) throws Exception
	{
		ArrayList<Complex> multTat = new ArrayList<Complex>();	
			for (int k=0; k<signal.size(); k++)
			{
				multTat.add(signal.get(k).multiply(signalb.get(k)));
			}
	}
	
	// fft adjusted to power of two (zero-padding)
	public ArrayList<Complex> fft(ArrayList<Float> signal)
	{
		int k = 4;
		Complex a = new Complex(0,0); // construction of a zero cof type complex
		while ((int)Math.pow(2,k) < 5*signal.size()) //finds first power of 2 higher then 5 times the size of the array
		{
			k++;
		} 
		Complex[] sig = new Complex[(int)Math.pow(2, k)];
		sig = (Complex[]) signal.toArray();
		for (int j = signal.size()+1; j<sig.length; j++)
		{
			sig[j] = a;
		}
		FastFourierTransformer fft = new FastFourierTransformer(STANDARD);
		sig = fft.transform(sig, FORWARD) ;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<Complex> res= new ArrayList(sig.length);
		res = toArrayListComplex(sig) ;
		
	}
	
	// reversed fft
	public ArrayList<Complex> ifft(ArrayList<Complex> signal)
	{
		int k = 4;
		Complex a = new Complex(0,0); // construction of a zero cof type complex
		while ((int)Math.pow(2,k) < 5*signal.size()) //finds first power of 2 higher then 5 times the size of the array
		{
			k++;
		} 
		org.apache.commons.math3.complex.Complex[] sig = new Complex[((int)Math.pow(2, k))];
		sig = toListComplex(signal);
		for (int j = signal.size()+1; j<sig.length; j++)
		{
			sig[j] = a;
		}
		FastFourierTransformer fft = new FastFourierTransformer(STANDARD);
		sig = fft.transform(sig, INVERSE); 
		return toArrayListComplex(sig);
		
	}
	
}

