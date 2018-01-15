package job;

import java.util.List;

import domain.objects.Game;
import domain.objects.Player;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class JobGame {
	
	private GameMapper gameMapper = GameMapper.getInstance();
	
	
	public Game createGame(String name, Player currentPlayer, int turnNumber, String status, int turnRessources, int fieldRessources){
		Game game = new Game(name, currentPlayer, turnNumber, status, turnRessources, fieldRessources);
		gameMapper.insert(game);
		
		return game;
	}
	
	public Game findGameById(int id){
		return gameMapper.findById(id);
	}
	
	public List<Game> getGamesWaiting(){
		return gameMapper.findAllGamesWaiting();
	}
	
	public List<Game> getGamesOwned(int id){
		return gameMapper.findAllGamesOwned(id);
	}
	
	public List<Game> getGamesNotOwned(int id){
		return gameMapper.findAllGamesNotOwned(id);
	}

	public void joinGame(int idPlayer, int idGame){
		gameMapper.joinGame(idPlayer, idGame);
	}
	
	public void updateGame(Game game){
		gameMapper.update(game);
	}
	
	public List<Game> findGameByStatus(int idPlayer, String status){
		return gameMapper.findAllGamesByStatus(idPlayer, status);
	}
}
