
public class CompTable {
	
	private boolean[] evalnotes;
	
	private int entrop;
	
	public CompTable (boolean[] evalnotes, int entrop){
		this.evalnotes=evalnotes;
		this.entrop=entrop;
	}
	
	public boolean[] getevalnotes(){
		return evalnotes;
	}
	
	public int getentrop(){
		return entrop;
	}
}
