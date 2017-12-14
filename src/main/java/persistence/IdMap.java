package persistence;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class IdMap<T> 
{
	public HashMap<Integer, WeakReference<T>> hMap;
	
	public IdMap() {
		this.hMap = new HashMap<Integer,WeakReference<T>>();
	}
	
	public void put(int id,T obj)
	{
		this.hMap.put(id, new WeakReference<T>(obj));
	}
	
	public T get(int id)
	{
		return this.hMap.get(id).get();
	}
	
	public void del (int id)
	{
		this.hMap.remove(id);
	}
	
}