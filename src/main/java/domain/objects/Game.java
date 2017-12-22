package domain.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.Observateur;
import domain.Visiteur;
import persistence.factories.FactoryListPlayer;
import persistence.factories.FactoryListTerritory;
import persistence.factories.FactoryPlayer;
import persistence.factories.VirtualProxyBuilder;


public class Game implements IDomainObject {
	
	
	private int id;
	private String name;
	private Player currentPlayer;
	private int turnNumber;
	private String status;
	private int turnRessources;
	private int fieldRessources;
	private List<Player> listPlayers;
	private HashMap<Player,Integer> playerResources;
	private List<Territory> listTerritories;
	private List<Observateur> obs;
	
	public Game(String name, Player currentPlayer, int turnNumber, String status, int turnRessources, int fieldRessources)
	{
		this.name = name;
		this.currentPlayer = currentPlayer;
		this.turnNumber = turnNumber;
		this.status = status;
		this.playerResources = new HashMap<>();
		this.turnRessources = turnRessources;
		this.fieldRessources = fieldRessources;
		this.listPlayers = new VirtualProxyBuilder<List<Player>>(List.class, new FactoryListPlayer(id)).getProxy();
		this.listTerritories = new  VirtualProxyBuilder<List<Territory>>(List.class, new FactoryListTerritory(id)).getProxy();
		this.obs = new ArrayList<Observateur>();
		
	}
	
	public Game(){
		
	}
	
	

	public int getIdGame() {
		return id;
	}



	public void setIdGame(int id) {
		this.id = id;
		this.listPlayers = new VirtualProxyBuilder<List<Player>>(List.class, new FactoryListPlayer(id)).getProxy();
		this.listTerritories = new  VirtualProxyBuilder<List<Territory>>(List.class, new FactoryListTerritory(id)).getProxy();
	}
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	
	

	public HashMap<Player, Integer> getPlayerResources() {
		return playerResources;
	}



	public void setPlayerResources(HashMap<Player, Integer> playerResources) {
		this.playerResources = playerResources;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}



	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	
	

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
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

	public String toString()
	{
		return getName();
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
	
	public Territory[][] getTableTerritory()
	{
		
		Territory[][] tabTerritory = new Territory[getMaxX(getListTerritories())][getMaxY(getListTerritories())];
		
		for (int i = 0; i < tabTerritory.length; i++) {
			for (int j = 0; j < tabTerritory[i].length; j++) {
				for (Territory territories : listTerritories) {
					if(i == territories.getxAxis() && j == territories.getyAxis())
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
	        if(list.get(i).getxAxis() > max)
	        {
	            max = list.get(i).getxAxis();
	        }
	    }
	    
	    return max;
	}
	
	public int getMaxY(List<Territory> list)
	{
	    int max = Integer.MIN_VALUE;
	    
	    for(int i=0; i<list.size(); i++)
	    {
	        if(list.get(i).getyAxis() > max){
	            max = list.get(i).getyAxis();
	        }
	    }
	    
	    return max;
	}
}
