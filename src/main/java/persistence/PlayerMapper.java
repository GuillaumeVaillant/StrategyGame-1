package persistence;

import domain.Player;

public class PlayerMapper extends DataMapper<Player>{
	
	static PlayerMapper instance;
	
	public static PlayerMapper getInstance() {
		if (instance == null)
			instance = new PlayerMapper();
		return instance;
	}
	
	public PlayerMapper()
	{
		super("Player", DataAttributs.getAttributPlayer(), Player.class);
	}
	
	public Player findPlayerById(int id) {
		
		try{
		return this.findById(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		return null;
	}
	
	public void insertPlayer(Player player) {
		
		try{
		this.insert(player);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void deletePlayerById(int id) {
		
		try{
		this.delete(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void updatePlayerById(Player player) {
		
		try{
		this.update(player);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
