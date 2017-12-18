package domain;

import domain.objects.City;
import domain.objects.Game;
import domain.objects.IDomainObject;
import domain.objects.Map;
import domain.objects.Player;
import domain.objects.Territory;

public abstract class Visiteur {
	
	public void visiter(IDomainObject o) {
		o.accepter(this);
	}

	abstract public void visiter(Player p);
	abstract public void visiter(Game p);
	abstract public void visiter(Territory p);
	abstract public void visiter(City c);
	abstract public void visiter(Map m);
}
