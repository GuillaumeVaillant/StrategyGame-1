package persistence.mapper;

import java.lang.reflect.InvocationTargetException;
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
import java.util.WeakHashMap;

public class DataMapper<T> {

	private String table;
	private Map<String, DataEnumType> stringClassMap;
	private Class<T> maClass;
	private WeakHashMap<Integer, T> hMap;

	public DataMapper(String table, Map<String, DataEnumType> stringClassMap, Class<T> maClass) {
		this.table = table;
		this.stringClassMap = stringClassMap;
		this.maClass = maClass;
		this.hMap = new WeakHashMap<Integer, T>();
	}

	public void insert(T obj) {
		StringBuilder query = new StringBuilder();
		query.append("BEGIN INSERT INTO ").append(table).append(" VALUES(seq_id_").append(table).append(".nextval");

		for (int i = 1; i < stringClassMap.size(); i++) {
			query.append(",?");
		}

		query.append(")");
		query.append("RETURNING id").append(table).append(" INTO ?; END;");

		try {
			OracleConnection conn = OracleConnection.getInstance();
			CallableStatement statement = conn.createRequestCS(query.toString());

			int j = 0;
			Iterator<Map.Entry<String, DataEnumType>> ite = stringClassMap.entrySet().iterator();

			for (int i = 0; i < stringClassMap.size(); i++) {
				Map.Entry<String, DataEnumType> stringSet = ite.next();
				String attribut = stringSet.getKey();
				
				
				String name = "get" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);

				try {
					Method method = maClass.getMethod(name);

					Object object = method.invoke(obj);
					statement.setObject(j, object);

				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

				j++;
			}

			statement.registerOutParameter(3, Types.NUMERIC);

			statement.executeQuery();

			hMap.put(statement.getInt(3), obj);
			try {
				Method method = maClass.getMethod("getId" + table);
				Object object = method.invoke(obj);
				statement.setObject(statement.getInt(3), object);
			} catch (Exception e){
				e.printStackTrace();
			}

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(T obj) throws ClassNotFoundException {
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

					Object object = method.invoke(obj);
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

	public void delete(int id) {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ").append(table).append(" where ").append("id").append(table).append(" = ?");

		try {
			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(query.toString());

			statement.setInt(1, id);

			statement.executeQuery();

			hMap.remove(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public T findById(int id) {

		if (!hMap.containsKey(id)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT ");

			List<String> results = new ArrayList<String>();

			int index = 0;

			for (Map.Entry<String, DataEnumType> typeEntry : stringClassMap.entrySet()) {
				if (index != 0)
					stringBuilder.append(",");
				stringBuilder.append(typeEntry.getKey());

				results.add(typeEntry.getKey());
				index++;
			}

			stringBuilder.append(" FROM ").append(table).append(" WHERE id").append(table).append(" = ? ");

			try {
				OracleConnection conn = OracleConnection.getInstance();
				PreparedStatement statement = conn.createRequestPS(stringBuilder.toString());

				//System.out.println(stringBuilder.toString());

				statement.setInt(1, id);

				Class<?> c = Class.forName(maClass.getCanonicalName());

				@SuppressWarnings("unchecked")
				T obj = (T) c.newInstance();

				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {

					hMap.put(id, obj);

					int j = 1;

					for (int i = 0; i < results.size(); i++) {
						String attribut = results.get(i);
						String name = "set" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
						Method method = null;

						switch (stringClassMap.get(results.get(i))) {
						case STRING: {
							method = maClass.getMethod(name, String.class);
							String value = resultSet.getString(j);

							method.invoke(obj, value);
						}
							break;
						case INTEGER: {
							method = maClass.getMethod(name, int.class);
							int value = resultSet.getInt(j);

							method.invoke(obj, value);
						}
							break;
						case DOUBLE: {
							method = maClass.getMethod(name, Double.class);
							Double value = resultSet.getDouble(j);

							method.invoke(obj, value);
						}
							break;
						case LONG: {
							method = maClass.getMethod(name, Long.class);
							Long value = resultSet.getLong(j);

							method.invoke(obj, value);
						}
							break;
						default:
							System.err.println("Aucun objet de ce type !");
						}
						j++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return hMap.get(id);

	}

	public List<T> findByIdList(int id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");

		List<String> results = new ArrayList<String>();

		int index = 0;

		for (Map.Entry<String, DataEnumType> typeEntry : stringClassMap.entrySet()) {
			if (index != 0)
				stringBuilder.append(",");
			stringBuilder.append(typeEntry.getKey());

			results.add(typeEntry.getKey());
			index++;
		}

		stringBuilder.append(" FROM ").append(table).append(" WHERE id").append(table).append(" = ? ");

		try {
			OracleConnection conn = OracleConnection.getInstance();
			PreparedStatement statement = conn.createRequestPS(stringBuilder.toString());

			statement.setInt(1, id);

			List<T> listObj = new ArrayList<T>();

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				listObj.add(maClass.newInstance());

				int j = 1;

				for (int i = 0; i < results.size(); i++) {
					String attribut = results.get(i);
					String name = "set" + attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
					Method method = null;

					switch (stringClassMap.get(results.get(i))) {
					case STRING: {
						method = maClass.getMethod(name, String.class);
						String value = resultSet.getString(j);

						method.invoke(listObj.get(i), value);
					}
						break;
					case INTEGER: {
						method = maClass.getMethod(name, int.class);
						int value = resultSet.getInt(j);

						method.invoke(listObj.get(i), value);
					}
						break;
					case DOUBLE: {
						method = maClass.getMethod(name, Double.class);
						Double value = resultSet.getDouble(j);

						method.invoke(listObj.get(i), value);
					}
						break;
					case LONG: {
						method = maClass.getMethod(name, Long.class);
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
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;

	}
}