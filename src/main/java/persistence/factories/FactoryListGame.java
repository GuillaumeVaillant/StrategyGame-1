package persistence.factories;

import java.sql.SQLException;
import java.util.List;

import domain.objects.Game;
import domain.objects.Player;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class FactoryListGame implements Factory<List<Game>> {

	private int id;
	
	public FactoryListGame(int id) {
		this.id = id;
	}
	
	@Override
	public List<Game> create() throws ClassNotFoundException, SQLException {
		
		return PlayerMapper.getInstance().findListGames(id);
	}
}
