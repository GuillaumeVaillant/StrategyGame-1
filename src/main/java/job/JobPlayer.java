package job;

import java.sql.SQLException;

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
		try {
			return new FactoryPlayer(id).create();
		} catch (Exception e){
			System.err.println(e.getMessage());
			return null;
		}
	}
	

}
