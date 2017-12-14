package persistence;

import java.sql.SQLException;
import java.util.List;

import domain.Player;

public class FactoryPlayer implements Factory<List<Player>> {

	private Player player;
	private GameMapper mapper;
	
	public FactoryPlayer(final Player player) {
		this.player = player;
	}
	
	@Override
	public List<Player> create() throws ClassNotFoundException, SQLException {
		
		//return mapper.findListPlayers(this.player.getId());
		return null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	

}
