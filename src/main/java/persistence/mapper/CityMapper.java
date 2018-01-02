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
import java.util.Map;
import java.util.Map.Entry;

import domain.objects.City;
import domain.objects.Game;
import domain.objects.Player;
import persistence.UnitOfWork;

public class CityMapper extends DataMapper<City> {

	static CityMapper instance;
	
	public static CityMapper getInstance() {
		if (instance == null)
			instance = new CityMapper();
		return instance;
	}
	
	
	private CityMapper()
	{
		super("City",DataAttributs.getAttributCity(),City.class);
	}
	
	public City findById(int id) {
		
		if (!hMap.containsKey(id)) {
		String req = "select c.idCity, c.townOwner from city c where c.idCity = ? ";
        try {
        	OracleConnection conn = OracleConnection.getInstance();
            PreparedStatement pss = conn.createRequestPS(req);
            pss.setInt(1, id);
            ResultSet rs = pss.executeQuery();
            
            Player player = this.findOwner(rs.getInt(2));
            
            City city = new City(player);
            
           
            city.add(UnitOfWork.getInstance());
            
            hMap.put(id, city);
            
            return city;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		}
        return hMap.get(id);
	}
	
	public Player findOwner(int id){
		return PlayerMapper.getInstance().findById(id);
	}
	
	public void insert(City city) {
		
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

					Object object = method.invoke(city);
					
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

			hMap.put(statement.getInt(i), city);
			
			try {
				int value = statement.getInt(i);
				Method method = maClass.getMethod("setId" + table, int.class);
				method.invoke(city, value);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			
			statement.close();
			city.add(UnitOfWork.getInstance());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		
		try{
			
			City city = this.findById(id);
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			statement.setInt(1, id);

			statement.executeQuery();

			hMap.remove(id);
			city.add(UnitOfWork.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(City city) {
		
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

					Object object = method.invoke(city);
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

				Object object = method.invoke(city);
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
