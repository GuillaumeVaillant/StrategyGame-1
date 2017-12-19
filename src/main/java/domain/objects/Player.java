package domain.objects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Observateur;
import domain.Visiteur;
import persistence.UnitOfWork;
import persistence.factories.FactoryListPlayer;
import persistence.factories.FactoryListGame;
import persistence.factories.VirtualProxyBuilder;


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
		this.listGames = new VirtualProxyBuilder<List<Game>>(List.class, new FactoryListGame(this.id)).getProxy();
		this.obs = new ArrayList<Observateur>();
		this.obs.add(UnitOfWork.getInstance());
	}
	
	public Player(){
		
	}
	

	public int getIdPlayer(){
		return this.id;
	}

	public void setIdPlayer(int id) {
		this.id = id;
		this.listGames = new VirtualProxyBuilder<List<Game>>(List.class, new FactoryListGame(id)).getProxy();
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
		return this.listGames;
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
