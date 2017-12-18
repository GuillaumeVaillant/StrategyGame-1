package persistence.factories;

import java.sql.SQLException;
import java.util.List;

import domain.objects.Player;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class FactoryListPlayer implements Factory<List<Player>> {

	private int id;
	
	public FactoryListPlayer(int id) {
		this.id = id;
	}
	
	@Override
	public List<Player> create() throws ClassNotFoundException, SQLException {
		
		return GameMapper.getInstance().findListPlayers(id);
	}

	

}
