package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistence.UnitOfWork;


public class Game implements IDomainObject {
	
	
	private int id;
	private String name;
	private Player currentPlayer;
	private String status;
	private int turnRessources;
	private int fieldRessources;
	private List<Player> listPlayers;
	private HashMap<Player,Integer> playerResources;
	private List<Territory> listTerritories;
	private List<Observateur> obs;
	
	public Game(int id, String name, String status, int turnRessources, int fieldRessources)
	{
		this.id = id;
		this.name = name;
		this.status = status;
		this.playerResources = new HashMap<>();
		this.turnRessources = turnRessources;
		this.fieldRessources = fieldRessources;
		this.obs = new ArrayList<Observateur>();
		obs.add(UnitOfWork.getInstance());
	}
	
	

	/*public Territory[][] getTableTerritory()
	{
		Territory[][] tabTerritory = new Territory[getMaxX(this.listTerritories)][getMaxY(this.listTerritories)];
		
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
	


	public HashMap<Player, Integer> getPlayerResources() {
		return playerResources;
	}



	public void setPlayerResources(HashMap<Player, Integer> playerResources) {
		this.playerResources = playerResources;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	



	public Player getCurrentPlayer() {
		return currentPlayer;
	}



	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	

	public int getTurnRessources() {
		return turnRessources;
	}



	public void setTurnRessources(int turnRessources) {
		this.turnRessources = turnRessources;
	}



	public int getFieldRessources() {
		return fieldRessources;
	}



	public void setFieldRessources(int fieldRessources) {
		this.fieldRessources = fieldRessources;
	}



	public List<Territory> getListTerritories() {
		return listTerritories;
	}



	public void setListTerritories(List<Territory> listTerritories) {
		this.listTerritories = listTerritories;
	}


	//TODO  Main method ? 
	public void start()
	{
		//setState(true);
		//this.listPlayers.get(0).setTurn(true);
	}

	public List<Player> getListPlayers() {
		return listPlayers;
	}

	public void setListPlayers(List<Player> listPlayers) {
		this.listPlayers = listPlayers;
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
