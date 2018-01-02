package job;

import java.util.List;

import domain.objects.Player;
import persistence.mapper.PlayerMapper;

public class JobPlayer {
	
	private PlayerMapper playerMapper = PlayerMapper.getInstance();
	
	public void insertPlayer(Player player){
		playerMapper.insert(player);
		
	}
	
	public void updatePlayer(Player player){
		playerMapper.update(player);
	}
	
	
	public Player findPlayer(int id){
		return playerMapper.findById(id);
	}
	
	
	public List<Player> findExistingPlayers(){
		return playerMapper.findExistingPlayers();
	}

}
