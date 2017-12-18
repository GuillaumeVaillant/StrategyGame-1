package domain.actions;

import domain.objects.Game;
import domain.objects.Territory;

public class MoveArmy extends Action {
		
	private Territory targetTerritory;
	private Territory territoireActuel;
	private Integer numberArmy;
	
	//TODO cost doit être calculé selon la formule du sujet
	public MoveArmy(Integer cost,String description,Territory current,Territory target,Integer numberArmy)
	{
		super(cost,description);
		targetTerritory = target;
		territoireActuel = current;
		this.numberArmy = numberArmy;
	}

	@Override
	public void execute(Game gm, Territory t) {
		if(isPossible(gm, cost)){
			territoireActuel.getArmy().replace(gm.getCurrentPlayer(), territoireActuel.getArmy().get(gm.getCurrentPlayer())-this.numberArmy);
			targetTerritory.getArmy().replace(gm.getCurrentPlayer(), targetTerritory.getArmy().get(gm.getCurrentPlayer())+this.numberArmy);
		}
		
	}

}
