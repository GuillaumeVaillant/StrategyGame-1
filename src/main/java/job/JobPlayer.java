package job;

import java.sql.SQLException;
import java.util.List;

import domain.objects.Player;
import persistence.factories.FactoryPlayer;
import persistence.mapper.PlayerMapper;

public class JobPlayer {
	
	private PlayerMapper playerMapper = PlayerMapper.getInstance();;
	
	public void insertPlayer(Player player){
		playerMapper.insertPlayer(player);
		
	}
	
	public void updatePlayer(Player player){
		playerMapper.updatePlayerById(player);
	}
	
	
	public Player findPlayer(int id){
		return playerMapper.findPlayerById(id);
	}
	
	
	public List<Player> findExistingPlayers(){
		return playerMapper.findExistingPlayers();
	}

}
