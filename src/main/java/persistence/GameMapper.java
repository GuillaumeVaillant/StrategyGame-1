package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domain.Field;
import domain.Game;
import domain.Mountain;
import domain.Plains;
import domain.Player;
import domain.Territory;

public class GameMapper extends DataMapper<Game>{
	
	static GameMapper instance;
	private static final Connection conn = Connexion.getConn();
	
	public static GameMapper getInstance() {
		if (instance == null)
			instance = new GameMapper();
		return instance;
	}
	
	public GameMapper()
	{
		super("Game",DataAttributs.getAttributGame(),Game.class);
	}
	
	public Game findGameById(int id) {
        
        try {
        	return this.findById(id);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }
	
	/*public List<Player> findListPlayers(int id) throws ClassNotFoundException, SQLException 
	{
		Game game = findGameById(id);
		
        String req = "select p.idPlayer, p.username, p.password from Player p"
        		+ "join Game_player gp using(idPlayer) "
        		+ "join Game g using(idGame) "
        		+ "where g.idGame = ? ;";
        try {
        	
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
                Player player = new Player(rs.getInt(1), rs.getString(2), rs.getString(3));
                game.setListPlayers(new VirtualProxyBuilder<List<Player>>(List.class, new FactoryPlayer(player)).getProxy());
            }
            return game.getListPlayers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public List<Territory> findListTerritories(int id)
	{
	
		Game game = findGameById(id);
        String req = "select t.idTerritory, t.xAxis, t.yAxis, t.territoryType from territory t join map m using(idTerritory) "
        		+ "join game g using(idGame) where g.idGame = ? ;";
        try {
        	
            PreparedStatement pss = conn.prepareStatement(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            while (rs.next()) {
            	
            	switch(rs.getString(2)){
            	case "Mountain" :
            	{
            		Territory territory = new Mountain(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            		game.setListTerritories(new VirtualProxyBuilder<List<Territory>>(List.class, new FactoryTerritory(territory)).getProxy());
            	}
            	break;
            	case "Plain" :
            	{
            		Territory territory = new Plains(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            		game.setListTerritories(new VirtualProxyBuilder<List<Territory>>(List.class, new FactoryTerritory(territory)).getProxy());
            	}
            	break;
            	case "Field":
            	{
            		Territory territory = new Field(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            		game.setListTerritories(new VirtualProxyBuilder<List<Territory>>(List.class, new FactoryTerritory(territory)).getProxy());
            	}
            
            	default:
            		System.err.println("Erreur de type territoire");
            	}
            }
            return game.getListTerritories();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}*/
	
	public void insertGame(Game game) {
		
		try{
		this.insert(game);
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

	public void updatePlayerById(Game game) {
		
		try{
		this.update(game);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
