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
import java.util.Map.Entry;

import domain.objects.Game;
import domain.objects.Player;
import persistence.UnitOfWork;

public class PlayerMapper extends DataMapper<Player>{
	
	static PlayerMapper instance;
	
	public static PlayerMapper getInstance() {
		if (instance == null)
			instance = new PlayerMapper();
		return instance;
	}
	
	private PlayerMapper()
	{
		super("Player", DataAttributs.getAttributPlayer(), Player.class);
	}
	
	public Player findById(int id) {
		
		if (!hMap.containsKey(id)) {
		 String req = "select p.idPlayer, p.username, p.password from Player p";
	        
	        try {
	        	OracleConnection conn = OracleConnection.getInstance();
	            PreparedStatement pss = conn.createRequestPS(req);
	            ResultSet rs = pss.executeQuery();
	            Player player = null;
	            while (rs.next()) {
	            player = new Player(rs.getString(2), rs.getString(3));
	            
	            player.setIdPlayer(rs.getInt(1));
	            player.add(UnitOfWork.getInstance());
	            }
	            
	            hMap.put(id, player);
	            
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            
	        }
		}
			return hMap.get(id);
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
	
	
	public void insert(Player player) {
		
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

					Object object = method.invoke(player);
					
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

			hMap.put(statement.getInt(i), player);
			
			try {
				int value = statement.getInt(i);
				Method method = maClass.getMethod("setId" + table, int.class);
				method.invoke(player, value);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			statement.close();
			player.add(UnitOfWork.getInstance());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id) {
		
		try{
			
			Player player = this.findById(id);
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			statement.setInt(1, id);

			statement.executeQuery();

			hMap.remove(id);
			player.add(UnitOfWork.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Player player) {
		
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

					Object object = method.invoke(player);
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

				Object object = method.invoke(player);
				statement.setObject(j, object);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
