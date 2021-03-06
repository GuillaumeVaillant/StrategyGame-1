package domain.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.Observateur;
import domain.Visiteur;

public class Map implements IDomainObject {

	private Game game;
	private List<Territory> listTerritories;
	List<Observateur> obs;

	public Map(Game game) {
		this.game = game;
		this.listTerritories = new ArrayList<>();
		this.obs = new ArrayList<>();
		this.inializeMap(10, 10);
	}

	public void inializeMap(final Integer tailleX, final Integer tailleY) {
		Integer nbCase = tailleX * tailleY;
		Integer nbMountain = 0;
		Integer nbField = 0;
		Integer nbPlain = 0;
		boolean territory = false;
		for (int i = 0; i < tailleX; i++) {
			for (int j = 0; j < tailleX; j++) {
				Random r = new Random();
				territory = false;
				while (territory == false) {
					Integer randomValue = r.nextInt(nbCase);
					if ((randomValue < (nbCase * 0.2)) && (nbMountain < (nbCase * 0.2))) {
						/**
						 * mountain
						 */
						this.listTerritories.add(new Mountain(i, j));
						nbMountain++;
						territory = true;
					} else if (randomValue < (nbCase * 0.5) && (nbPlain < (nbCase * 0.5))) {
						/**
						 * plain
						 */
						this.listTerritories.add(new Plains(i, j));
						nbPlain++;
						territory = true;
					} else {
						/**
						 * field
						 */
						this.listTerritories.add(new Field(i, j));
						nbField++;
						territory = true;
					}
				}
			}
		}
		System.out.println(this.listTerritories);
	}
	

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		this.notifier();
	}

	public List<Territory> getListTerritories() {
		return listTerritories;
	}

	public void setListTerritories(List<Territory> listTerritories) {
		this.listTerritories = listTerritories;
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
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Map [game=" + game + ", listTerritories=" + listTerritories + "]";
	}

}
