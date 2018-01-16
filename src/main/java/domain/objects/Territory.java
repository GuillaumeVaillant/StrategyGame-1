package domain.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.Observateur;
import domain.Visiteur;

public abstract class Territory implements IDomainObject {

	protected int id;
	protected int xAxis;
	protected int yAxis;
	protected City city;
	protected HashMap<Player, Integer> army;
	List<Observateur> obs;

	public Territory(int xAxis, int yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.city = null;
		this.army = new HashMap<Player, Integer>();
		this.obs = new ArrayList<Observateur>();
	}

	public Territory() {

	}

	public abstract EnumTerritory getEnumTerritoryType();

	public int getIdTerritory() {
		return id;
	}

	public void setIdTerritory(int id) {
		this.id = id;
		this.notifier();
	}

	public int getxAxis() {
		return xAxis;
	}

	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
		this.notifier();
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
		this.notifier();
	}

	public HashMap<Player, Integer> getArmy() {
		return army;
	}

	public void setArmy(HashMap<Player, Integer> army) {
		this.army = army;
		this.notifier();
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		this.notifier();
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

	// Activer l'effet du territoire
	protected abstract void applyEffect();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Territory [id=" + id + ", xAxis=" + xAxis + ", yAxis=" + yAxis + "]";
	}

}
