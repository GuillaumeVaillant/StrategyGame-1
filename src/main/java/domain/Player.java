package domain;

import java.util.ArrayList;
import java.util.List;

import persistence.UnitOfWork;

public class Player implements IDomainObject{
	
	private int id;
	private String username;
	private String password;
	private boolean isAlive;
	private boolean turn;
	private List<Game> listGames;
	List<Observateur> obs;
	
	public Player(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.isAlive = true;
		this.turn = false;
		this.obs = new ArrayList<Observateur>();
		this.obs.add(UnitOfWork.getInstance());
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(int isAlive) {
		this.isAlive = isAlive == 1 ? true : false;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn == 1 ? true : false;
	}
	
	public List<Game> getListGames() {
		return listGames;
	}

	public void setListGames(List<Game> listGames) {
		this.listGames = listGames;
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
