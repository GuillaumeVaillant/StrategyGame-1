package persistence.mapper;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import domain.objects.Game;
import domain.objects.Map;
import domain.objects.Player;
import domain.objects.Territory;
import persistence.UnitOfWork;

public class MapMapper extends DataMapper<Map> {

static MapMapper instance;
	
	public static MapMapper getInstance() {
		if (instance == null)
			instance = new MapMapper();
		return instance;
	}
	
	private MapMapper() {
		super("Map", DataAttributs.getAttributMap(), Map.class);
	}
	
	public Map findById(int id) {
		
		if (!hMap.containsKey(id)) {
			String req = "select m.idGame, m.idTerritory from Map m where m.idGame = ? ";
			try {
				OracleConnection conn = OracleConnection.getInstance();
	            PreparedStatement pss = conn.createRequestPS(req);
	            ResultSet rs = pss.executeQuery();
	            Game game = this.findCurrentGame(rs.getInt(1));
	            
	            
	            Map map = new Map(game);
	            
	            game.add(UnitOfWork.getInstance());
	            
	            
	            hMap.put(id, map);
	            
	          
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			}
			return hMap.get(id);
	}
	
	public Game findCurrentGame(int id){
		return GameMapper.getInstance().findById(id);
	}
	
	public List<Territory> findListTerritoriesById(int id){
		return TerritoryMapper.getInstance().findListTerritoryById(id);
		
	}
	
	
	public void insert(Map map) {
	
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
			Iterator<Entry<String, DataEnumType>> ite = stringClassMap.entrySet().iterator();

			
			for (int k = 0; k < stringClassMap.size(); k++) {
				Entry<String, DataEnumType> stringSet = ite.next();
				String attribut = stringSet.getKey();

				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(map);
					
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

			hMap.put(statement.getInt(i), map);
			
			try {
				int value = statement.getInt(i);
				Method method = maClass.getMethod("setId" + table, int.class);
				method.invoke(map, value);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			statement.close();
			map.add(UnitOfWork.getInstance());

		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	
	}
	
	public void delete(int id) {
		

		try{
		
		Map map = this.findById(id);
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

		OracleConnection conn = OracleConnection.getInstance();
		PreparedStatement statement = conn.createRequestPS(query.toString());

		statement.setInt(1, id);

		statement.executeQuery();

		hMap.remove(id);
		map.add(UnitOfWork.getInstance());
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}

	public void update(Map map) {
		
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

			for (Entry<String, DataEnumType> stringSet : stringClassMap.entrySet()) // ensembles
																						// des
																						// attributs
			{
				String attribut = stringSet.getKey();
				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(map);
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

				Object object = method.invoke(map);
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
