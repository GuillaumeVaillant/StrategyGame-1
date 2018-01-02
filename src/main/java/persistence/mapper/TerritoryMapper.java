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

import domain.objects.Field;
import domain.objects.Game;
import domain.objects.Mountain;
import domain.objects.Plains;
import domain.objects.Player;
import domain.objects.Territory;
import persistence.UnitOfWork;

public class TerritoryMapper extends DataMapper<Territory>{
	
	static TerritoryMapper instance;
	
	public static TerritoryMapper getInstance() {
		if (instance == null)
			instance = new TerritoryMapper();
		return instance;
	}
	
	private TerritoryMapper() {
		super("Territory", DataAttributs.getAttributTerritory(), Territory.class);
	}

	
	public Territory findById(int id) {
        
		if (!hMap.containsKey(id)) {
		String req = "select t.idTerritory, t.xAxis, t.yAxis, t.territoryType, t.idCity from territory t where t.idTerritory = ? ";
		try {
			OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            ResultSet rs = pss.executeQuery();
            
            String territoire = rs.getString(4);
			Territory territory = null;
			
			if(territoire != null)
			{
				switch (territoire) {
				case "MOUNTAIN": 
					territory = new Mountain(rs.getInt(2), rs.getInt(3));
					territory.add(UnitOfWork.getInstance());
					territory.setIdTerritory(rs.getInt(1));
					break;
				case "PLAIN": 
					territory = new Plains(rs.getInt(2), rs.getInt(3));
					territory.add(UnitOfWork.getInstance());
					territory.setIdTerritory(rs.getInt(1));
					break;
				case "FIELD": 
					territory = new Field(rs.getInt(2), rs.getInt(3));
					territory.add(UnitOfWork.getInstance());
					territory.setIdTerritory(rs.getInt(1));
					break;
				default:
					territory = new Field(rs.getInt(2), rs.getInt(3));
					territory.add(UnitOfWork.getInstance());
					territory.setIdTerritory(rs.getInt(1));
					System.err.println("Erreur de type territoire");
				}
			}
            
            
            territory.setIdTerritory(rs.getInt(1));
            	
            territory.add(UnitOfWork.getInstance());
          
            hMap.put(id, territory);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		}
		return hMap.get(id);
    }
	
	public List<Territory> findListTerritoryById(int id) {
        
		String req = "select t.idTerritory, t.xAxis, t.yAxis, t.territoryType, t.idcity from territory t where t.idTerritory = ? ";
		
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
						System.err.println("Erreur de type territoire");
					}
				}
			}
			return territories;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	
    }
	
	public void insert(Territory territory) {
		
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

					Object object = method.invoke(territory);
					
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

			hMap.put(statement.getInt(i), territory);
			
			try {
				int value = statement.getInt(i);
				Method method = maClass.getMethod("setId" + table, int.class);
				method.invoke(territory, value);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			statement.close();
			territory.add(UnitOfWork.getInstance());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		
		try{
			
			Territory territory = this.findById(id);
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			statement.setInt(1, id);

			statement.executeQuery();

			hMap.remove(id);
			territory.add(UnitOfWork.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Territory territory) {
		
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

					Object object = method.invoke(territory);
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

				Object object = method.invoke(territory);
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
