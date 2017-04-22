package attaque;

import hauteur.Note;

import java.util.ArrayList;
import java.util.Arrays;

public class Attaque {
	
	public static Float[] filter(Float[] b, Float[] a, Float[] x){
		//la fonction filter de matlab
		int la = a.length;
		int lb = b.length;
		int lx = x.length;
		int minb = 0;
		int mina = 0;
		Float[] y = new Float[lx];
		Arrays.fill(y,new Float(0.0));
		for (int i=0 ; i<lx ; i++) {
			minb = Math.min(i,lb-1);
			mina = Math.min(i,la-1);
			int j = 0;
			while (j<=minb){
				y[i]+=x[i-j]*b[j];
				j++;}
			j = 1;
			while (j<=mina){
				y[i]-=y[i-j]*a[j];
				j++;}
			y[i]/=a[0];
		}
		return y;
	}
	public static Float[] enveloppe (Float[] signal, float a){
		//prend un signal en entr�e et renvoie son enveloppe de param�tre a
		Float[] enveloppe = new Float[signal.length];
		enveloppe[0] = signal[0]*signal[0];
		for (int i=1; i<signal.length; i++){
			enveloppe[i]=a*enveloppe[i-1]+(1-a)*signal[i]*signal[i];
			}
		for (int i =1;i<enveloppe.length;i++){
			enveloppe[i]=enveloppe[i]*1000;
			}
		return enveloppe;
		}
	
	public static Float sum(Float[] list, int lowerindex, int higherindex){
		//oui, il faut impl�menter �a...
		Float sum = (float) 0;
		for (int i=lowerindex;i<=higherindex;i++){
			sum+=list[i];
		}
		return sum;
	}
	
	public static Float[] derivLarge (Float[] signal, int N){
		//prend un signal en entr�e et renvoie sa d�riv�e large (de param�tre N)
		//faite sans appel � filter car cela revient au m�me (et c'est compliqu� 
		//d'impl�menter ones dans java...
		Float[] deriv= new Float[signal.length];
		for (int i=0; i<signal.length; i++){
			if (i<2*N-1 && i>=N){
				deriv[i]=sum(signal,i-N+1,i)-sum(signal,0,i-N);
			}
			else if (i<N){
				deriv[i]=sum(signal,0,i);
			}
			else {
				deriv[i]=sum(signal,i-N+1,i)-sum(signal,i-2*N+1,i-N);
			}
			
		}
		return deriv;
	}
	
	public static Float[] decfreq(Float[] signal, float fech, float newfreq){
		//diminue la fr�quence d'�chatillonage de signal � environ newfreq en supprimant des valeurs
		int step = (int)Math.floor(fech/newfreq);
		int newlength = (int)Math.ceil((float)signal.length/step);
		Float[] newsignal = new Float[newlength];
		int i = 0;
		int j = 0;
		while (j<newlength){
			newsignal[j]=signal[i];
			j++;
			i+=step;
		}
		return newsignal;
	}
	
	public static Float[] dB (Float[] signal){
		Float[] signaldb = new Float[signal.length];
		Arrays.fill(signaldb, new Float(0.0));
		for (int i=0; i<signal.length; i++){
			signaldb[i]=(20*(float)Math.log10(signal[i]));
		}
		return signaldb;
	}
	private static float max(Float[] signal) // finds max
	{
		float a = signal[0];
		for (int k = 0; k<signal.length; k++)
		{
			if (a<signal[k])
			{
				a = signal[k];
			}
		}
		return a;
	}
	public static Float[] attaque (Float[] signal, float fech, float a){
		//prend en entr�e un signal et sa fr�quence d'�chantillonage et renvoie son attaque 
		//on applique un filtre passe-haut au signal, avec comme fr�quence de coupure fech/2
		Float[] B = {(float)0.3370 , (float)-0.6740, (float)0.3370};
		Float[] A = {(float)1, (float)-0.1712, (float)0.1768};
		signal = filter(B,A,signal);
		//Float [] sigp = Arrays.copyOfRange(signal,0,100000);
		//print(sigp);

		Float[] env = enveloppe(signal, a);
		System.out.println(max(env));
		env = decfreq(env,fech,441);
		//print(env);
		//on estime qu'une enveloppe n'a pas beaucoup de fr�quences >200 Hz, donc on se permet
		//de diminuer sa fr�quence d'�chantillonage � 400 Hz
		env=dB(env);
		//print(env);
		Float[] derenv = derivLarge(env, 20);
		//print(derenv);
		Float[] aderenv = new Float[derenv.length];
		for (int i = 0; i<derenv.length; i++){
			aderenv[i]=Math.max(0,derenv[i]);
		}
		//print(aderenv);
		return aderenv;
	}
	
	public static void print(Float[] env){
		String rep = "";
		for (int i=0;i<env.length;i++){
			if (i==0) {
				rep=rep+"["+env[i];
			}
			else if (i==env.length-1){
				rep=rep+";"+env[i]+"]";
			}
			else {
				rep=rep+";"+env[i];
			}
		}
		System.out.println(rep);
	}
	
}
