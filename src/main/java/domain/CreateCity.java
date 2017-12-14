package domain;

public class CreateCity extends Action  {
	
	public CreateCity(int cost, String description) {
		super(cost, description);
	}


	@Override
	public void execute(Game gm, Territory t) {
		if(isPossible(gm, cost)){
		t.setCity(new City(gm.getCurrentPlayer()));
		}
		
		
	}


}
