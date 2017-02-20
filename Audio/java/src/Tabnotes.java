import exceptions.InvalidSheetException;

public class Tabnotes {
	
	private final float[] temps;
	
	private final float[] freq;
	
	public Tabnotes (float[] temps, float[] freq) throws InvalidSheetException{
		if (temps.length!=freq.length){
			throw new InvalidSheetException();
		}
		this.temps=temps;
		this.freq=freq;
	}
	
	public float[] gettemps(){
		return temps;
	}
	
	public float[] getfreq(){
		return freq;
	}
	
	public CompTable compare(Tabnotes tabnotes){
		int na = tabnotes.gettemps().length;
		int ns = temps.length;
		float diff=this.minecart()/2;
		boolean[] evalnotes = new boolean[ns];
		for (int i=0;i<na;i++){
			float lb=tabnotes.gettemps()[i]-diff;
			float lh=tabnotes.gettemps()[i]+diff;
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
	
	public float minecart (){
		//calcule l'écart minimal entre deux notes du tableau Tabnotes
		float min = temps[temps.length-1];
		for (int i=1;i<temps.length;i++){
			float e = temps[i]-temps[i-1];
			if (e<min){
				min=e;
			}
			
		}
		return min;
	}
}
