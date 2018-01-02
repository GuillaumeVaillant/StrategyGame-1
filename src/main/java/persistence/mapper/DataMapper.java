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

import domain.objects.Player;

public abstract class DataMapper<T> {

	protected String table;
	protected Map<String, DataEnumType> stringClassMap;
	protected Class<T> maClass;
	protected WeakHashMap<Integer, T> hMap;

	public DataMapper(String table, Map<String, DataEnumType> stringClassMap, Class<T> maClass) {
		this.table = table;
		this.stringClassMap = stringClassMap;
		this.maClass = maClass;
		this.hMap = new WeakHashMap<Integer, T>();
	}

	public abstract void insert(T obj);

	public abstract void update(T obj);

	public abstract void delete(int id);

	public abstract T findById(int id);

}