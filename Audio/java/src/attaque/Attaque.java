package attaque;
public abstract class Attaque {
	
	public float[] filter(float[] b, float[] a, float[] x){
		//la fonction filter de matlab
		int la = a.length;
		int lb = b.length;
		int lx = x.length;
		int minb = 0;
		int mina = 0;
		float[] y = new float[lx];
		for (int i=0 ; i<lx ; i++) {
			minb = Math.min(i+1,lb);
			mina = Math.min(i+1,la);
			int j = 0;
			while (j<minb){
				y[i]+=x[i-j]*b[j];
				j++;}
			j = 1;
			while (j<mina){
				y[i]-=x[i-j]*a[j];
				j++;}
			y[i]/=a[0];
		}
		return y;
	}
	public float[] enveloppe (float[] signal, float a){
		//prend un signal en entrée et renvoie son enveloppe de paramètre a
		
		for (int i=0; i<signal.length; i++){
			signal[i]=signal[i]*signal[i];
			}
		float[] B = {1};
		float[] A = {1,-a};
		float[] enveloppe= filter(B,A,signal);
		return enveloppe;
		}
	
	public float sum(float[] list, int lowerindex, int higherindex){
		//oui, il faut implémenter ça...
		float sum = 0;
		for (int i=lowerindex;i<=higherindex;i++){
			sum+=list[i];
		}
		return sum;
	}
	
	public float[] derivLarge (float[] signal, int N){
		//prend un signal en entrée et renvoie sa dérivée large (de paramètre N)
		//faite sans appel à filter car cela revient au même (et c'est compliqué 
		//d'implémenter ones dans java...
		float[] deriv= new float[signal.length-2*N+1];
		for (int i=0; i<signal.length-2*N+1 ; i++){
			deriv[i]=sum(signal,i,i+N-1)-sum(signal,i+N,i+2*N-1);
		}
		return deriv;
	}
	
	public float[] decfreq(float[] signal, float fech, float newfreq){
		//diminue la fréquence d'échatillonage de signal à environ newfreq en supprimant des valeurs
		int step = (int)Math.floor(fech/newfreq);
		int newlength = (int)Math.ceil(signal.length/step);
		float[] newsignal = new float[newlength];
		int i = 0;
		int j = 0;
		while (i<newlength){
			newsignal[j]=signal[i];
			j++;
			i+=step;
		}
		return newsignal;
	}
	
	public float[] dB (float[] signal){
		float[] signaldb = new float[signal.length];
		for (int i=0; i<signal.length; i++){
			signaldb[i]=(10*(float)Math.log10(signal[i]));
		}
		return signaldb;
	}
	
	public float[] attaque (float[] signal, float fech, float a){
		//prend en entrée un signal et sa fréquence d'échantillonage et renvoie son attaque 
		//A FAIRE !!!!! FILTRER LE SIGNAL
		float[] env = enveloppe(signal, a);
		env = decfreq(env,fech,400);
		//on estime qu'une enveloppe n'a pas beaucoup de fréquences >200 Hz, donc on se permet
		//de diminuer sa fréquence d'échantillonage à 400 Hz
		env = dB(env);
		//on passe en log
		float[] derenv = derivLarge(env, 40);
		float[] aderenv = new float[derenv.length];
		for (int i = 0; i<derenv.length; i++){
			aderenv[i]=Math.max(0,derenv[i]);
		}
		return aderenv;
	}
	
}
