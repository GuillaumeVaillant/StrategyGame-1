package persistence.factories;

import java.sql.SQLException;
import java.util.List;

import domain.objects.Player;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class FactoryPlayer implements Factory<Player> {

	private int id;
	
	public FactoryPlayer(int id) {
		this.id = id;
	}
	
	@Override
	public Player create() throws ClassNotFoundException, SQLException {
		
		return PlayerMapper.getInstance().findPlayerById(id);
	}
}
