package comparaison;

public class CompTable {
	
	private Boolean[] evalnotes;
	
	private int entrop;
	
	public CompTable (Boolean[] evalnotes, int entrop){
		this.evalnotes=evalnotes;
		this.entrop=entrop;
	}
	
	public Boolean[] getevalnotes(){
		return evalnotes;
	}
	
	public int getentrop(){
		return entrop;
	}
}
