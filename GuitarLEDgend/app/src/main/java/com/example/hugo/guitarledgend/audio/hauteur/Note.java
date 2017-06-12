package com.example.hugo.guitarledgend.audio.hauteur;

import android.util.Log;

import java.util.*;
import com.example.hugo.guitarledgend.audio.comparaison.Tabnotes;
import com.example.hugo.guitarledgend.audio.attaque.Attaque;

import org.apache.commons.math3.complex.*;
import org.apache.commons.math3.dfp.*;
import org.apache.commons.math3.transform.*;

public class Note {

    private int temps;
    private int freq;

    public static final DftNormalization STANDARD = DftNormalization.STANDARD;
    public static final TransformType FORWARD = TransformType.FORWARD;
    public static final TransformType INVERSE = TransformType.INVERSE;


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

    private static int maxIndex(ArrayList<Float> signal) // finds index of max
    {
        if (signal.size()==0){
            return 0;
        }
        float a = signal.get(0);
        int res = 0;
        for (int k = 0; k<signal.size(); k++)
        {
            if (a<signal.get(k))
            {
                a = signal.get(k);
                res = k;
            }
        }
        return res;
    }

    private static float max(ArrayList<Float> signal) // finds max
    {
        float a = signal.get(0);
        for (int k = 0; k<signal.size(); k++)
        {
            if (a<signal.get(k))
            {
                a = signal.get(k);
            }
        }
        return a;
    }

    private static Complex[] toListComplex(ArrayList<Complex> signal) //takes an ArrayList<Complex> and gives the equivalent list of Complex

    {
        Complex[] res = new Complex[signal.size()];
        for (int k=0; k<signal.size(); k++ )
        {
            res[k] = signal.get(k);
        }
        return res;
    }

    private static Float[] toList(ArrayList<Float> signal) //takes an ArrayList<Complex> and gives the equivalent list of Complex

    {
        Float[] res = new Float[signal.size()];
        for (int k=0; k<signal.size(); k++ )
        {
            res[k] = signal.get(k);
        }
        return res;
    }

    private static ArrayList<Complex> toArrayListComplex(Complex[] signal) //takes a list if Complex and qives the equivalent ArrayList
    {
        ArrayList<Complex> res = new ArrayList<Complex>(signal.length);
        for (int k=0; k<signal.length; k++ )
        {
            res.add(signal[k]);
        }
        return res;
    }

    private static ArrayList<Complex> toComplex(ArrayList<Float> signal) // takes an ArrayList of Floats and give it with the complex type
    {
        ArrayList<Complex> res = new ArrayList<Complex>(signal.size());
        for (int k=0; k<signal.size(); k++ )
        {
            Complex a = new Complex (signal.get(k), 0);
            res.add(a);
        }
        return res;
    }

    private static ArrayList<Float> toArrayList(Float[] signal) //takes a list if Complex and gives the equivalent ArrayList
    {
        ArrayList<Float> res = new ArrayList<Float>(signal.length);
        for (int k=0; k<signal.length; k++ )
        {
            res.add(signal[k]);
        }
        return res;
    }

    private static ArrayList<Float> subTab(Float[] signal, int startIndex, int endIndex) //takes a portion of a list and turns it into an ArrayList
    {
        ArrayList<Float> subTab = new ArrayList<Float>();
        for (int k = startIndex; k < (endIndex + 1) ; k++)
        {
            subTab.add(signal[k]);
        }
        return subTab;
    }


    private static ArrayList<Complex> multTaT(ArrayList<Complex> signal, ArrayList<Complex> signalb) // Multiplies term by term two ArrayLists
    {
        ArrayList<Complex> multTat = new ArrayList<Complex>();
        for (int k=0; k<signal.size(); k++)
        {
            multTat.add(signal.get(k).multiply(signalb.get(k)));
        }
        return multTat;
    }

    private static ArrayList<Complex> fft(ArrayList<Float> signal) 	// fft adjusted to power of two (zero-padding)
    {
        int k = 4;
        Complex a = new Complex(0,0); // construction of a zero cof type complex
        while ((int)Math.pow(2,k) < 11*signal.size()) //finds first power of 2 higher then 5 times the size of the array
        {
            k++;
        }
        Complex[] sig = new Complex[(int)Math.pow(2, k)];
        Complex[] sigdeb = Note.toListComplex(Note.toComplex(signal));
        for (int j = 0; j<signal.size();j++){
            sig[j] = sigdeb[j];
        }
        for (int j = signal.size(); j<sig.length; j++)
        {
            sig[j] = a;
        }
        FastFourierTransformer fft = new FastFourierTransformer(STANDARD);
        sig = fft.transform(sig, FORWARD) ;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        ArrayList<Complex> res= new ArrayList(sig.length);
        res = toArrayListComplex(sig) ;
        return res;
    }

    private static ArrayList<Complex> ifft(ArrayList<Complex> signal) // reversed fft
    {
        int k = 4;
        Complex a = new Complex(0,0); // construction of a zero cof type complex
        while ((int)Math.pow(2,k) < 5*signal.size()) //finds first power of 2 higher then 5 times the size of the array
        {
            k++;
        }
        Complex[] sig = new Complex[((int)Math.pow(2, k))];
        sig = toListComplex(signal);
        for (int j = signal.size()+1; j<sig.length; j++)
        {
            sig[j] = a;
        }
        FastFourierTransformer fft = new FastFourierTransformer(STANDARD);
        sig = fft.transform(sig, INVERSE);
        return toArrayListComplex(sig);

    }


    private static float findFreq(ArrayList<Float> signal) //finds frequency by autocorrelation method
    {
        ArrayList<Complex> correl = ifft(multTaT(fft(signal),conjugate(fft(signal))));
        ArrayList<Float> frequencies = new ArrayList<Float>();
        float j = (float) 82.5;
        float q = 0;
        float z = 0;
        while (j<2000)
        {
            z = (float) correl.get((int) Math.floor(44100/j)).getReal();
            frequencies.add(z);
            j *= Math.pow(2,(float)1/12);
        }
        q = maxIndex(frequencies);
        return (float) (82.5*Math.pow(2, ((q)/12)));
    }

    private static ArrayList<Float> freqtable;

    private static void initFreqTable(){
        float a = (float)82.5;
        freqtable.add(a);
        for (int i=0; i<50; i++){
            freqtable.add(a);
            a=a*(float)Math.pow(2, (double)1/12);
        }
    }

    public static Tabnotes sheet(Float[] signal) // takes a played song and writes the sheet
    {
        ArrayList<Float> att = toArrayList(Attaque.attaque(signal, (float)44100, (float)0.999));
        ArrayList<Integer> mont = new ArrayList<Integer>();
        ArrayList<Integer> lim = new ArrayList<Integer>();
        ArrayList<Float> timetable = new ArrayList<Float>();
        ArrayList<Float> freqtable = new ArrayList<Float>();
//        Log.e("att",Integer.toString(att.size()));
        int i = 0;
        float M = max(att);
//        Log.e("M",Float.toString(M));
        while (i < (att.size()-1))
        {
            while (att.get(i) < (float)0.2*M && i < (att.size()-1))
            {
                i++;
            }
            lim.add(i);
            //Log.e("lim",Integer.toString(lim.size()));
            while (att.get(i) >= (float)0.2*M && i < (att.size()-1))
            {
                i++;
            }
            lim.add(i);
        }
        for (int k = 0; k < (lim.size()/2); k++)
        {
            mont.add(lim.get(2*k));
        }
        for (int j = 0; j < mont.size(); j++)
        {
            if (lim.get(2*j+1) > lim.get(2*j))
            {
                ArrayList<Float> echantillon = subTab(signal, (lim.get(2*j)*100), (lim.get(2*j+1)*100));
                float freq = findFreq(echantillon);
                if (freq > 50 && freq < 2000)
                {
                    freqtable.add(freq);
                    timetable.add((float)mont.get(j)/441);
                }
            }
        }
        Float[] freqlist = toList (freqtable);
        Float[] timelist = toList(timetable);
        Tabnotes sheet = new Tabnotes (timelist, freqlist);
        Log.e("ntime",Integer.toString(timelist.length));
        Log.e("nfreq",Integer.toString(freqlist.length));
        return sheet;
    }


    public static final ArrayList<Complex> conjugate(ArrayList<Complex> input){

        ArrayList<Complex> result = new ArrayList<>();
        for (int i = 0 ; i < input.size() ; i++) result.add(input.get(i).conjugate());
        return result ;
    }

    public static final Float[] getReal(ArrayList<Complex> input){

        Float[] res = new Float[input.size()];
        for (int i = 0 ; i < input.size() ; i++) res[i] = (float)input.get(i).getReal()  ;
        return res ;

    }

}