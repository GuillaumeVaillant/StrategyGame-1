package domain;

public abstract class Visiteur {
	
	public void visiter(IDomainObject o) {
		o.accepter(this);
	}

	abstract public void visiter(Player p);
	abstract public void visiter(Game p);
	abstract public void visiter(Territory p);
}
