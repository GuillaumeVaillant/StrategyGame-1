package domain.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.Observateur;
import domain.Visiteur;

public class City implements IDomainObject {
	
	private HashMap<Integer, Player> city;
	private int id;
	private Player owner;
	List<Observateur> obs;
	
	public City(Player owner)
	{
		this.owner = owner;
		this.obs = new ArrayList<Observateur>();
	}
	
	public City(){
		
	}
	
	public Player getOwner()
	{
		return this.owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	/**
	 * @return the city
	 */
	public HashMap<Integer, Player> getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(HashMap<Integer, Player> city) {
		this.city = city;
	}
	
	
	

}
