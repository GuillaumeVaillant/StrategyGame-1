package domain;

public class City {
	
	private int id;
	private Player owner;
	
	public City(Player p)
	{
		owner = p;
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
	
	
	

}
