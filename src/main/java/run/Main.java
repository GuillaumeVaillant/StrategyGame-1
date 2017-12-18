package run;

import java.sql.SQLException;
import java.util.List;

import domain.objects.Game;
import domain.objects.Player;
import persistence.factories.FactoryListPlayer;
import persistence.factories.FactoryPlayer;
import persistence.mapper.PlayerMapper;

public class Main {

	public static void main(String[] args) throws Exception {
		
		PlayerMapper playerMapper = PlayerMapper.getInstance();
		
		
		
		
		try {
			List<Player> players = new FactoryListPlayer(1).create();
			Game game = new Game();
			game.setListPlayers(players);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
	}

}
