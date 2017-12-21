package persistence.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import domain.objects.Field;
import domain.objects.Game;
import domain.objects.Mountain;
import domain.objects.Plains;
import domain.objects.Player;
import domain.objects.Territory;
import persistence.UnitOfWork;
import persistence.factories.VirtualProxyBuilder;

public class GameMapper extends DataMapper<Game> {

	static GameMapper instance;

	public static GameMapper getInstance() {
		if (instance == null)
			instance = new GameMapper();
		return instance;
	}

	private GameMapper() {
		super("Game", DataAttributs.getAttributGame(), Game.class);
	}

	public Game findGameById(int id) {

		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources from game g where g.idGame = ? ";
		try {
			OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            ResultSet rs = pss.executeQuery();
            Player player = this.findCurrentPlayerById(rs.getInt(3));
            Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            game.setIdGame(rs.getInt(1));
            	
            game.add(UnitOfWork.getInstance());
          
			return game;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Player findCurrentPlayerById(int id){
		return PlayerMapper.getInstance().findById(id);
	}
	
	public List<Game> findAllGamesWaiting() {

		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources from game g where g.status = 'WAITING' ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            ResultSet rs = pss.executeQuery();
            List<Game> games = new ArrayList<>();
            
            while (rs.next()) {
            	Player player = this.findCurrentPlayerById(rs.getInt(3));
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            	game.setIdGame(rs.getInt(1));
            	
            	game.add(UnitOfWork.getInstance());
    			games.add(game);
          
            }
            
            
            return games;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public List<Game> findAllGamesOwned(int id) {

		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources "
				+ "from game g "
				+ "where g.currentPlayer = ? and g.status = 'WAITING' ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            List<Game> games = new ArrayList<>();
            
            while (rs.next()) {
            	Player player = this.findCurrentPlayerById(rs.getInt(3));
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            	game.setIdGame(rs.getInt(1));
            	
            	game.add(UnitOfWork.getInstance());
    			games.add(game);
          
            }
            
            
            return games;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public List<Game> findAllGamesNotOwned(int id) {

		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources "
				+ "from game g "
				+ "join game_player gp on g.idGame = gp.idGame "
				+ "where g.currentPlayer <> ? and gp.idPlayer <> ? and g.status = 'WAITING' ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            pss.setInt(2, id);
            ResultSet rs = pss.executeQuery();
            List<Game> games = new ArrayList<>();
            
            while (rs.next()) {
            	Player player = this.findCurrentPlayerById(rs.getInt(3));
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            	game.setIdGame(rs.getInt(1));
            	
            	game.add(UnitOfWork.getInstance());
    			games.add(game);
          
            }
            
            
            return games;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public void joinGame(int idPlayer, int idGame) {

		String req = "insert into game_player values(?, ?, 20) ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, idPlayer);
            pss.setInt(2, idGame);
            pss.executeQuery();
            
            pss.close();
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}

	public List<Player> findListPlayers(int id) throws ClassNotFoundException, SQLException 
	{
		Game game = findGameById(id);
		
        String req = "select p.username, p.password from Player p "
        		+ "join Game_player gp using(idPlayer) "
        		+ "where idGame = ? ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
            	Player player = new Player(rs.getString(1), rs.getString(2));
            	
            	player.add(UnitOfWork.getInstance());
    			players.add(player);
          
            }
            
            game.setListPlayers(players);
            return game.getListPlayers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}

	public List<Territory> findListTerritories(int id) {

		Game game = findGameById(id);
		String req = "select t.xAxis, t.yAxis, t.territoryType from territory t join map m using(idTerritory) "
				+ "join game g using(idGame) where g.idGame = ? ;";
		try {
			OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
			pss.setInt(1, id);
			ResultSet rs = pss.executeQuery();
			
			List<Territory> territories = new ArrayList<>();
			while (rs.next()) {

				switch (rs.getString(3)) {
				case "MOUNTAIN": {
					Territory territory = new Mountain(rs.getInt(1), rs.getInt(2));
					territory.add(UnitOfWork.getInstance());
					territories.add(territory);
				}
					break;
				case "PLAIN": {
					Territory territory = new Plains(rs.getInt(1), rs.getInt(2));
					territory.add(UnitOfWork.getInstance());
					territories.add(territory);
				}
					break;
				case "FIELD": {
					Territory territory = new Field(rs.getInt(1), rs.getInt(2));
					territory.add(UnitOfWork.getInstance());
					territories.add(territory);
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
	}

	public void insertGame(Game game) {

		
		try {
			this.insert(game);
			this.joinGame(game.getCurrentPlayer().getIdPlayer(), game.getIdGame());
			game.add(UnitOfWork.getInstance());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteGameById(int id) {

		try {
			Game game = this.findGameById(id);
			this.delete(id);
			game.add(UnitOfWork.getInstance());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateGameById(Game game) {

		
	}

}
