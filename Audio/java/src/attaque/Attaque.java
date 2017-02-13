package attaque;
import java.util.ArrayList;

public abstract class Attaque {
	
	public ArrayList<Float> enveloppe (ArrayList<Float> signal, float a){
		//prend un signal en entrée et renvoie son enveloppe de paramètre a
		ArrayList<Float> enveloppe= new ArrayList<Float>();
		enveloppe.set(0,(1-a)*signal.get(1)*signal.get(1));
		for (int i=1 ; i<signal.size(); i++){
			enveloppe.add(a*signal.get(i-1)+(1-a)*signal.get(i));}
		return enveloppe;
		}
	
	public Float sum(ArrayList<Float> list, int lowerindex, int higherindex){
		//oui, il faut implémenter ça...
		Float sum = new Float(0);
		for (int i=lowerindex;i<=higherindex;i++){
			sum+=list.get(i);
		}
		return sum;
	}
	
	public ArrayList<Float> derivLarge (ArrayList<Float> signal, int N){
		//prend un signal en entrée et renvoie sa dérivée large (de paramètre N)
		ArrayList<Float> deriv= new ArrayList<Float>();
		for (int i=0; i<signal.size()-2*N+1 ; i++){
			deriv.add(sum(signal,i,i+N-1)-sum(signal,i+N,i+2*N-1));
		}
		return deriv;
	}
	
	public ArrayList<Float> attaque (ArrayList<Float> signal){
		
	}
	
}
