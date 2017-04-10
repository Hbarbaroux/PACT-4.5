package comparaison;

public class Tabnotes {
	
	private final Float[] temps;
	
	private final Float[] freq;
	
	public Tabnotes (Float[] temps, Float[] freq){
		this.temps=temps;
		this.freq=freq;
	}
	
	public Float[] gettemps(){
		return temps;
	}
	
	public Float[] getfreq(){
		return freq;
	}
	
	public CompTable compare(Tabnotes tabnotes){
		int na = tabnotes.gettemps().length;
		int ns = temps.length;
		Float diff=this.minecart()/2;
		Boolean[] evalnotes = new Boolean[ns];
		for (int i=0;i<na;i++){
			Float lb=tabnotes.gettemps()[i]-diff;
			Float lh=tabnotes.gettemps()[i]+diff;
			for (int j=0;j<ns;j++){
				if ((temps[j]<=lh) && (temps[j]>=lb) && (freq[j]==tabnotes.getfreq()[j]) && (evalnotes[j]==false)){
					evalnotes[j]=true;
				}
			}
		}
		int entrop = Math.max(0,na-ns);
		CompTable comptable = new CompTable(evalnotes, entrop);
		return comptable;
	}
	
	public Float minecart (){
		//calcule l'écart minimal entre deux notes du tableau Tabnotes
		Float min = temps[temps.length-1];
		for (int i=1;i<temps.length;i++){
			Float e = temps[i]-temps[i-1];
			if (e<min){
				min=e;
			}
			
		}
		return min;
	}
}
