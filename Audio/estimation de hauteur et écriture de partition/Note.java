
import org.apache.commons.maths3.Complex.*;
import org.apache.commons.maths3.Dfp;
import org.apache.commons.math3.transform;
import java.util.*;
import comparaison.Tabnotes;



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

	public int getTemps() {  //Getters and Setters 
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
	
	private int maxIndex(ArrayList<Float> signal) // finds index of max
	{
		float a = signal.get(0);
		int res = 0;
		for (int k = 0; k<signal.size(); k++)
		{
			if (res<signal.get(k))
			{
				a = signal.get(k);
				res = k;
			}
		}
		return a;
	}
	
	private float max(ArrayList<Float> signal) // finds max
	{
		float a = signal.get(0);
		for (int k = 0; k<signal.size(); k++)
		{
			if (res<signal.get(k))
			{
				a = signal.get(k);
			}
		}
		return a;
	}
	
	private Complex[] toListComplex(ArrayList<Complex> signal) //takes an ArrayList<Complex> and gives the equivalent list of Complex

	{
		Complex[] res = new Complex[signal.size()];
		for (int k=0; k<signal.size(); k++ )
		{
			res[k] = signal.get(k);
		}
		return res;
	}
	
	private Float[] toList(ArrayList<Float> signal) //takes an ArrayList<Complex> and gives the equivalent list of Complex

	{
		Float[] res = new Float[signal.size()];
		for (int k=0; k<signal.size(); k++ )
		{
			res[k] = signal.get(k);
		}
		return res;
	}
	
	private ArrayList<Complex> toArrayListComplex(Complex[] signal) //takes a list if Complex and qives the equivalent ArrayList
	{
		ArrayList<Complex> res = new ArrayList<Complex>(signal.length);
		for (int k=0; k<signal.length; k++ )
		{
			res.add(signal[k]);
		}
		return res;
	}
	
	private ArrayList<Float> toArrayList(Float[] signal) //takes a list if Complex and gives the equivalent ArrayList
	{
		ArrayList<Float> res = new ArrayList<Float>(signal.length);
		for (int k=0; k<signal.length; k++ )
		{
			res.add(signal[k]);
		}
		return res;
	}
	
	private ArrayList<Float> subTab(Float[] signal, int startIndex, int endIndex) //takes a portion of a list and turns it into an ArrayList
	{
		ArrayList<Float> subTab = new ArrayList();
		for (int k = startIndex; k < (endIndex + 1))
		{
			subTab.add(signal[k]);
			k++;
		}
		return subTat;
	}
	
	private ArrayList<Float> oppose(ArrayList<Float> echantillon) // Multiplies ArrayList by -1
	{
		for (int k=0; k<echantillon.size(); k++) 
		{
			float a = echantillon.get(k);
			echantillon.remove(k);
			echantillon.add(k, -1*a*a);
		}
	}
	
	private ArrayList<Complex> multTaT(ArrayList<Complex> signal, ArrayList<Complex> signalb) // Multiplies term by term two ArrayLists
	{
		ArrayList<Complex> multTat = new ArrayList<Complex>();	
			for (int k=0; k<signal.size(); k++)
			{
				multTat.add(signal.get(k).multiply(signalb.get(k)));
			}
	}
	
	private ArrayList<Complex> fft(ArrayList<Float> signal) 	// fft adjusted to power of two (zero-padding)
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
	
	private ArrayList<Complex> ifft(ArrayList<Complex> signal) // reversed fft
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
	 
	private float findFreq(ArrayList<Float> signal) //finds frequency by auto-corelation method
	{
		ArrayList<Float> frequencies = new ArrayList<Float>();
		ArrayList<Complex> correl = ifft(multTat(fft(signal)),fft(oppose(signal))));
		float j = (float) 82.5;
		while (j<2000)
		{
			float z = correl.get((Math.floor(44100/j));
			frequencies.add(z);
			j* = Math.pow(2,(1/12));	
		}
		return (float) Math.pow((82.5*2), ((z+7)/12));
	}

	public Float[] sheet(Float[] signal) // takes a played song and writes the sheet
	{
		ArrayList<Float> att = toArrayList(attaque(signal, 44100, 0.999));
		ArrayList<Integer> mont = new ArrayList<Float>();
		ArrayList<Integer> lim = new ArrayList<Float>();
		ArrayList<Float> sheet = new ArrayList<Float>();
		int i = 0;
		float M = max(att);
		while (i < att.size())
		{
			while (att.get(i) < (float) O.2*M && i < att.size())
			{
				i++;
			}
			lim.add(i);
			while (att.get(i) >= (float) O.2*M && i < att.size())
			{
				i++;
			}
			lim.add(i);
		}
		for (int k = 0; k < (lim.size()/2); k++)
		{
			mont.add(lim.get(2*k-1));
		}
		for (int j = 0; j < mont.size(); j++)
		{
			if (lim.get(2*j) > lim.get(2*j-1))
			{
				ArrayList<Float> echantillon = subTab(signal, (lim.get(2*j-1)*100), (lim.get(2*j)*100));
				float freq = findFreq(echantillon);
				if (freq > 50 && freq < 2000)
				{
					sheet.add(freq);
					sheet.add(mont.get(j)/44100);
				}
			}
		}
		return toList(sheet);
	}

}


