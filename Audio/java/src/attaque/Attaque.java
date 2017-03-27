package attaque;
public class Attaque {
	
	public static Float[] filter(Float[] b, Float[] a, Float[] x){
		//la fonction filter de matlab
		int la = a.length;
		int lb = b.length;
		int lx = x.length;
		int minb = 0;
		int mina = 0;
		Float[] y = new Float[lx];
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
	public static Float[] enveloppe (Float[] signal, float a){
		//prend un signal en entr�e et renvoie son enveloppe de param�tre a
		
		for (int i=0; i<signal.length; i++){
			signal[i]=signal[i]*signal[i];
			}
		Float[] B = {(float) 1};
		Float[] A = {(float) 1,-a};
		Float[] enveloppe= filter(B,A,signal);
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
		Float[] deriv= new Float[signal.length-2*N+1];
		for (int i=0; i<signal.length-2*N+1 ; i++){
			deriv[i]=sum(signal,i,i+N-1)-sum(signal,i+N,i+2*N-1);
		}
		return deriv;
	}
	
	public static Float[] decfreq(Float[] signal, float fech, float newfreq){
		//diminue la fr�quence d'�chatillonage de signal � environ newfreq en supprimant des valeurs
		int step = (int)Math.floor(fech/newfreq);
		int newlength = (int)Math.ceil(signal.length/step);
		Float[] newsignal = new Float[newlength];
		int i = 0;
		int j = 0;
		while (i<newlength){
			newsignal[j]=signal[i];
			j++;
			i+=step;
		}
		return newsignal;
	}
	
	public static Float[] dB (Float[] signal){
		Float[] signaldb = new Float[signal.length];
		for (int i=0; i<signal.length; i++){
			signaldb[i]=(10*(float)Math.log10(signal[i]));
		}
		return signaldb;
	}
	
	public static Float[] attaque (Float[] signal, float fech, float a){
		//prend en entr�e un signal et sa fr�quence d'�chantillonage et renvoie son attaque 
		Float[] B = {(float)0.3370 , (float)-0.6740, (float)0.3370};
		Float[] A = {(float)1, (float)-0.1712, (float)0.1768};
		signal = filter(B,A,signal);
		//on applique un filtre passe-haut au signal, avec comme fr�quence de coupure fech/2
		Float[] env = enveloppe(signal, a);
		env = decfreq(env,fech,400);
		//on estime qu'une enveloppe n'a pas beaucoup de fr�quences >200 Hz, donc on se permet
		//de diminuer sa fr�quence d'�chantillonage � 400 Hz
		env = dB(env);
		//on passe en log
		Float[] derenv = derivLarge(env, 40);
		Float[] aderenv = new Float[derenv.length];
		for (int i = 0; i<derenv.length; i++){
			aderenv[i]=Math.max(0,derenv[i]);
		}
		return aderenv;
	}
	
}
