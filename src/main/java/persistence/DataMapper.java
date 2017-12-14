package persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DataMapper<T> {

	private String table;
	private Map<String, DataEnumType> stringClassMap;
	private Class<T> maClass;
	private IdMap idmap;
	
	public DataMapper(String table, Map<String, DataEnumType> stringClassMap, Class<T> maClass) {
		this.idmap = new IdMap(); // comment on s'en sert ?
		this.table = table;
		this.stringClassMap = stringClassMap;
		this.maClass = maClass;
	}


	public void insert(T obj) throws Exception {

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ").append(table).append(" VALUES(seq_id_").append(table).append(".nextval");

		for (int i = 0; i < stringClassMap.size(); i++) 
		{
			query.append(",?");
		}

		query.append(")");

		try {
			Connexion conn = Connexion.getInstance();
			PreparedStatement statement = conn.createRequest(query.toString());

			int j = 1;
			
			for (Map.Entry<String, DataEnumType> stringSet : stringClassMap.entrySet()) 
			{
				String attribut = stringSet.getKey();
				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(obj);
					statement.setObject(j, object);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				j++;
			}

			statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(T obj) throws ClassNotFoundException 
	{
		StringBuilder query = new StringBuilder();
		query.append("update ").append(table).append(" set ");

		List<String> listStrings = new LinkedList<>();
		
		for (Entry<String, DataEnumType> attribute : stringClassMap.entrySet()) 
		{
			listStrings.add(attribute.getKey() + " = ?");
		}
		
		query.append(String.join(", ", listStrings));

		query.append(" where ").append("id_").append(table).append(" = ?");

		try {
			Connexion conn = Connexion.getInstance();
			PreparedStatement statement = conn.createRequest(query.toString());

			int j = 1;
			
			for (Map.Entry<String, DataEnumType> stringSet : stringClassMap.entrySet()) // ensembles des attributs
			{
				String attribut = stringSet.getKey();
				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try
				{
					Method method = maClass.getMethod(name);

					Object object = method.invoke(obj);
					statement.setObject(j, object);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				j++;
			}
			
			// where id ..
			String attribut = "id_" + table;
			String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
			
			try
			{
				Method method = maClass.getMethod(name);

				Object object = method.invoke(obj);
				statement.setObject(j, object);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int id) throws ClassNotFoundException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ").append(table).append(" where ").append("id_").append(table).append(" = ?");
		
		try{
			Connexion conn = Connexion.getInstance();
			PreparedStatement statement = conn.createRequest(query.toString());
	
			statement.setInt(1, id);
			
			statement.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public T findById(int id) throws SQLException, IllegalAccessException, InstantiationException,
			NoSuchMethodException, InvocationTargetException, ClassNotFoundException 
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");

		List<String> results = new ArrayList<String>();

		int index = 0;
		
		for (Map.Entry<String, DataEnumType> typeEntry : stringClassMap.entrySet()) 
		{
			if (index != 0)
				stringBuilder.append(",");
			stringBuilder.append(typeEntry.getKey());

			results.add(typeEntry.getKey());
			index++;
		}

		stringBuilder.append(" FROM ").append(table).append(" WHERE id_").append(table).append(" = ? ");
		
		Connexion conn = Connexion.getInstance();
		PreparedStatement statement = conn.createRequest(stringBuilder.toString());

		statement.setInt(1, id);

		T obj = maClass.newInstance();

		ResultSet resultSet = statement.executeQuery();

		resultSet.next();
		
		int j = 1;
		
		for (int i = 0; i < results.size(); i++) 
		{
			String attribut = results.get(i);
			String name = "set" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
			Method method = null; 
			
			switch (stringClassMap.get(results.get(i))) 
			{
				case STRING: 
				{
					method = maClass.getMethod(name,String.class);
					String value = resultSet.getString(j);
	
					method.invoke(obj, value);
				}
					break;
				case INTEGER: 
				{
					method = maClass.getMethod(name,int.class);
					int value = resultSet.getInt(j);
	
					method.invoke(obj, value);
				}
					break;
				case DOUBLE: 
				{
					method = maClass.getMethod(name,Double.class);
					Double value = resultSet.getDouble(j);
	
					method.invoke(obj, value);
				}
					break;
				case LONG: 
				{
					method = maClass.getMethod(name,Long.class);
					Long value = resultSet.getLong(j);
	
					method.invoke(obj, value);
				}
					break;
				default:
					System.err.println("Aucun objet de ce type !");
			}
			j++;
		}
		return obj;
	}
	
	public List<T> findByIdList(int id) throws SQLException, IllegalAccessException, InstantiationException,
	NoSuchMethodException, InvocationTargetException, ClassNotFoundException 
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");
		
		List<String> results = new ArrayList<String>();
		
		int index = 0;
		
		for (Map.Entry<String, DataEnumType> typeEntry : stringClassMap.entrySet()) 
		{
			if (index != 0)
				stringBuilder.append(",");
			stringBuilder.append(typeEntry.getKey());
		
			results.add(typeEntry.getKey());
			index++;
		}
		
		stringBuilder.append(" FROM ").append(table).append(" WHERE id_").append(table).append(" = ? ");
		
		Connexion conn = Connexion.getInstance();
		PreparedStatement statement = conn.createRequest(stringBuilder.toString());
		
		statement.setInt(1, id);
		
		List<T> listObj = new ArrayList<T>(); 
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			listObj.add(maClass.newInstance());
			
			int j = 1;
			
			for (int i = 0; i < results.size(); i++) 
			{
				String attribut = results.get(i);
				String name = "set" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
				Method method = null; 
				
				switch (stringClassMap.get(results.get(i))) 
				{
					case STRING: 
					{
						method = maClass.getMethod(name,String.class);
						String value = resultSet.getString(j);
			
						method.invoke(listObj.get(i), value);
					}
						break;
					case INTEGER: 
					{
						method = maClass.getMethod(name,int.class);
						int value = resultSet.getInt(j);
			
						method.invoke(listObj.get(i), value);
					}
						break;
					case DOUBLE: 
					{
						method = maClass.getMethod(name,Double.class);
						Double value = resultSet.getDouble(j);
			
						method.invoke(listObj.get(i), value);
					}
						break;
					case LONG: 
					{
						method = maClass.getMethod(name,Long.class);
						Long value = resultSet.getLong(j);
			
						method.invoke(listObj.get(i), value);
					}
						break;
					default:
						System.err.println("Aucun objet de ce type !");
				}
				j++;
			}
		}
		return listObj;
	}
}