package persistence.mapper;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

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

	public Game findById(int id) {

		if (!hMap.containsKey(id)) {
		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources from game g where g.idGame = ? ";
		try {
			OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            ResultSet rs = pss.executeQuery();
            Player player = this.findCurrentPlayerById(rs.getInt(3));
            Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
            game.setIdGame(rs.getInt(1));
            	
            game.add(UnitOfWork.getInstance());
            
            hMap.put(id, game);
          
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
		return hMap.get(id);
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
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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

	public List<Player> findListPlayers(int id)
	{
		Game game = findById(id);
		
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

		Game game = findById(id);
		
		String req = "select t.idTerritory, t.xAxis, t.yAxis, t.territoryType, t.idcity from territory t "
				+ "join map m on t.idTerritory = m.idTerritory "
				+ "where m.idGame = ?";
		
		try {
			
			OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
			pss.setInt(1, id);
			ResultSet rs = pss.executeQuery();
			
			List<Territory> territories = new ArrayList<>();
			
			while (rs.next()) 
			{
				String territoire = rs.getString(4);
				Territory territory = null;
				
				if(territoire != null)
				{
					switch (territoire) {
					case "MOUNTAIN": 
						territory = new Mountain(rs.getInt(2), rs.getInt(3));
						territory.add(UnitOfWork.getInstance());
						territory.setIdTerritory(rs.getInt(1));
						territories.add(territory);
						break;
					case "PLAIN": 
						territory = new Plains(rs.getInt(2), rs.getInt(3));
						territory.add(UnitOfWork.getInstance());
						territory.setIdTerritory(rs.getInt(1));
						territories.add(territory);
						break;
					case "FIELD": 
						territory = new Field(rs.getInt(2), rs.getInt(3));
						territory.add(UnitOfWork.getInstance());
						territory.setIdTerritory(rs.getInt(1));
						territories.add(territory);
						break;
					default:
						territory = new Field(rs.getInt(2), rs.getInt(3));
						territory.add(UnitOfWork.getInstance());
						territory.setIdTerritory(rs.getInt(1));
						territories.add(territory);
						System.err.println("Erreur de type territoire");
					}
				}
				game.setListTerritories(territories);
			}
			return game.getListTerritories();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void insert(Game game) {

		StringBuilder query = new StringBuilder();
		query.append("BEGIN INSERT INTO ").append(table).append(" VALUES(seq_id_").append(table).append(".nextval");

		int i;
		for (i = 1; i < stringClassMap.size(); i++) {
			query.append(",?");
		}

		query.append(")");
		query.append("RETURNING id").append(table).append(" INTO ?; END;");

		try {
			OracleConnection conn = OracleConnection.getInstance();

			CallableStatement statement = conn.createRequestCS(query.toString());

			int j = 0;
			Iterator<Map.Entry<String, DataEnumType>> ite = stringClassMap.entrySet().iterator();

			
			for (int k = 0; k < stringClassMap.size(); k++) {
				Map.Entry<String, DataEnumType> stringSet = ite.next();
				String attribut = stringSet.getKey();

				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(game);
					
					if(object instanceof Player){
						Player p = (Player) object;
						object = p.getIdPlayer();
					}
					
					//System.out.println(object + " " + name);
					statement.setObject(j, object);

				} catch (Exception e) {
				}

				j++;
				
			}
			
			
			statement.registerOutParameter(i, Types.NUMERIC);
			
			statement.executeQuery();

			hMap.put(statement.getInt(i), game);
			
			try {
				int value = statement.getInt(i);
				Method method = maClass.getMethod("setId" + table, int.class);
				method.invoke(game, value);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			statement.close();
			this.joinGame(game.getCurrentPlayer().getIdPlayer(), game.getIdGame());
			game.add(UnitOfWork.getInstance());

		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}

	public void delete(int id) {

			try{
			
			Game game = this.findById(id);
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			statement.setInt(1, id);

			statement.executeQuery();

			hMap.remove(id);
			game.add(UnitOfWork.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Game game) {

		StringBuilder query = new StringBuilder();
		query.append("update ").append(table).append(" set ");

		List<String> listStrings = new LinkedList<>();

		for (Entry<String, DataEnumType> attribute : stringClassMap.entrySet()) {
			listStrings.add(attribute.getKey() + " = ?");
		}

		query.append(String.join(", ", listStrings));

		query.append(" where ").append("id").append(table).append(" = ?");

		try {
			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			int j = 1;

			for (Map.Entry<String, DataEnumType> stringSet : stringClassMap.entrySet()) // ensembles
																						// des
																						// attributs
			{
				String attribut = stringSet.getKey();
				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(game);
					statement.setObject(j, object);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				j++;
			}

			// where id ..
			String attribut = "id" + table;
			String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

			try {
				Method method = maClass.getMethod(name);

				Object object = method.invoke(game);
				statement.setObject(j, object);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public List<Game> findAllGamesByStatus(int id,String status) {

		String req = "select g.idGame, g.name, g.currentPlayer, g.turnNumber, g.status, g.turnRessources, g.fieldRessources "
				+ "from game g "
				+ "where g.currentPlayer = ? and g.status = ? ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            pss.setString(2, status);
            ResultSet rs = pss.executeQuery();
            List<Game> games = new ArrayList<>();
            
            while (rs.next()) {
            	Player player = this.findCurrentPlayerById(rs.getInt(3));
            	Game game = new Game(rs.getString(2), player, rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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

}
