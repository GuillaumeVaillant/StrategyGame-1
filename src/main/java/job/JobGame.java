package job;

import domain.objects.Game;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class JobGame {
	
	private GameMapper gameMapper = GameMapper.getInstance();
	
	
	public Game createGame(String name, int currentPlayer, int turnNumber, String status, int turnRessources, int fieldRessources){
		Game game = new Game(name, currentPlayer, turnNumber, status, turnRessources, fieldRessources);
		gameMapper.insertGame(game);
		
		return game;
	}
	
	public Game joinGameOtherPlayers(){
		return null;
	}

	public Game joinYourGames(){
		return null;
	}
}
