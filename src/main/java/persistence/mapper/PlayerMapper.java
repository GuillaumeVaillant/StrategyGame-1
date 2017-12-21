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
			Player player = this.findById(id);
			return player;
			
		} catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		
		return null;
	}
	
	public List<Player> findExistingPlayers() {
		
		
        String req = "select p.idPlayer, p.username, p.password from Player p";
        
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            ResultSet rs = pss.executeQuery();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
            Player player = new Player(rs.getString(2), rs.getString(3));
            
            player.setIdPlayer(rs.getInt(1));
          
            player.add(UnitOfWork.getInstance());
			players.add(player);
            }
            
            return players;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        return null;
	}
	
	public List<Game> findListGames(int id) throws ClassNotFoundException, SQLException 
	{
		Player player = this.findById(id);
		
        String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources "
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
            	
            	Player currentPlayer = PlayerMapper.getInstance().findById(rs.getInt(3));
            	Game game = new Game(rs.getString(2), currentPlayer, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            	
            	game.add(UnitOfWork.getInstance());
            	game.setIdGame(rs.getInt(1));
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
		player.add(UnitOfWork.getInstance());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void deletePlayerById(int id) {
		
		try{
			
		Player player = this.findById(id);
		this.delete(id);
		player.add(UnitOfWork.getInstance());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void updatePlayerById(Player player) {
		
		try{
		this.update(player);
		player.add(UnitOfWork.getInstance());
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
