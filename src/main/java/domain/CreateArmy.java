package domain;

public class CreateArmy extends Action {
	
	public CreateArmy() {
		super(5,"create Army");
	}

	//Creer une armée
	public void putArmy()
	{
		
	}
	
	
	@Override
	public void execute(Game gm, Territory t) {
		if(this.isPossible(gm, cost)){
			t.getArmy().replace(gm.getCurrentPlayer(), t.getArmy().get(gm.getCurrentPlayer()) + 1);
		}
		
	}

	

}
