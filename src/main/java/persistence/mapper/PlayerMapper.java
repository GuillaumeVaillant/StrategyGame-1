package persistence.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.objects.Game;
import domain.objects.Player;
import persistence.UnitOfWork;

public class PlayerMapper extends DataMapper<Player>{
	
	static PlayerMapper instance;
	private static final Connection conn = OracleConnection.getConn();
	
	public static PlayerMapper getInstance() {
		if (instance == null)
			instance = new PlayerMapper();
		return instance;
	}
	
	private PlayerMapper()
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
	
	public List<Game> findListGames(int id) throws ClassNotFoundException, SQLException 
	{
		Player player = this.findById(id);
		
        String req = "select g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources "
        		+ "from Game g "
        		+ "join Game_player gp on gp.idGame = g.idGame "
        		+ "where gp.idPlayer = ? ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            List<Game> games = new ArrayList<>();
            while (rs.next()) {
            	Game game = new Game(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
            	
            	game.add(UnitOfWork.getInstance());
    			games.add(game);
          
            }
            
            player.setListGames(games);
            return player.getListGames();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        return null;
	}
	
	public List<Player> findListPlayersById(int id){
		try{
			return this.findByIdList(id);
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
