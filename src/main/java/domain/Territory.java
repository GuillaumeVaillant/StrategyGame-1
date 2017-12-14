package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistence.UnitOfWork;

public abstract class Territory implements IDomainObject{
	
	protected int id;
	protected int xAxis;
	protected int yAxis;
	protected City city;
	protected HashMap<Player, Integer> army;
	List<Observateur> obs;
	
	public Territory(int xAxis, int yAxis)
	{
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.obs = new ArrayList<Observateur>();
		this.obs.add(UnitOfWork.getInstance());
	}
	

	public abstract EnumTerritory getEnumTerritoryType();
	

	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}




	public int getxAxis() {
		return xAxis;
	}





	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}





	public int getyAxis() {
		return yAxis;
	}





	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}


	public HashMap<Player, Integer> getArmy() {
		return army;
	}


	public void setArmy(HashMap<Player, Integer> army) {
		this.army = army;
	}


	public City getCity() {
		return city;
	}





	public void setCity(City city) {
		this.city = city;
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

	
	//Activer l'effet du territoire 
	protected abstract void applyEffect();
	

}
