package domain.actions;

import domain.objects.Game;
import domain.objects.Territory;

public class AttackCity extends Action {

	public AttackCity() {
		super(4, "Attack");
	}

	@Override
	public boolean isPossible(Game gm, Integer cost){
		return false;
	}

	@Override
	public void execute(Game gm, Territory t) {
		if(isPossible(gm, cost)){
			if(t.getCity() != null){
				if(t.getArmy().get(gm.getCurrentPlayer()) > 2*t.getArmy().get(t.getCity().getOwner())){
					t.setCity(null);
				}
			}
		}		
	}
}
