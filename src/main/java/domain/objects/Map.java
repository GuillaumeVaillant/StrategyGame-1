package domain.objects;

import java.util.ArrayList;
import java.util.List;

import domain.Observateur;
import domain.Visiteur;
import persistence.UnitOfWork;

public class Map implements IDomainObject {
	
	private Game game;
	private List<Territory> listTerritories;
	List<Observateur> obs;
	
	public Map(Game game, List<Territory> listTerritories){
		this.game = game;
		this.listTerritories = listTerritories;
		this.obs = new ArrayList<Observateur>();
	}
	
	public Map(){
		
	}
	
	/*public List<Territory> getTableTerritory()
	{
		List<Territory> tabTerritory = new Territory[getMaxX(this.listTerritories)][getMaxY(this.listTerritories)];
		
		for (int i = 0; i < tabTerritory.length; i++) {
			for (int j = 0; j < tabTerritory[i].length; j++) {
				for (Territory territories : listTerritories) {
					if(i == territories.getCoordX() && j == territories.getCoordY())
					{
						tabTerritory[i][j] = territories;
					}
				}
			}
		}
		
		return tabTerritory;
	}
	
	public int getMaxX(List<Territory> list)
	{
	    int max = Integer.MIN_VALUE;
	    
	    for(int i=0; i<list.size(); i++)
	    {
	        if(list.get(i).getCoordX() > max){
	            max = list.get(i).getCoordX();
	        }
	    }
	    
	    return max;
	}
	
	public int getMaxY(List<Territory> list)
	{
	    int max = Integer.MIN_VALUE;
	    
	    for(int i=0; i<list.size(); i++)
	    {
	        if(list.get(i).getCoordY() > max){
	            max = list.get(i).getCoordY();
	        }
	    }
	    
	    return max;
	}*/

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Territory> getListTerritories() {
		return listTerritories;
	}

	public void setListTerritories(List<Territory> listTerritories) {
		this.listTerritories = listTerritories;
	}

	@Override
	public void add(Observateur o) {
		obs.add(o);
	}

	@Override
	public void notifier() {
		for (Observateur o : obs) {
			o.action(this);
		}
	}

	@Override
	public void accepter(Visiteur v) {
		v.visiter(this);
		
	}
	
	

}
