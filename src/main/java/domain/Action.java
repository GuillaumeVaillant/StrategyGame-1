package domain;



public abstract class Action {
	
	//Coût en ressource d'utilisation d'une action
	protected int cost;
	protected String description;
	
	public Action (int cost, String description)
	{
		this.cost = cost;
		this.description = description;
	}
	
	public abstract void execute(Game gm, Territory t);
	
	
	public boolean isPossible(Game gm, Integer cost) {
		
		if(gm.getPlayerResources().get(gm.getCurrentPlayer()) >= cost){
			gm.getPlayerResources().replace(gm.getCurrentPlayer(), gm.getPlayerResources().get(gm.getCurrentPlayer())-cost);
			return true;
		}
		return false;
	}

	
}
