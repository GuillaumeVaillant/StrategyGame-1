package persistence.mapper;

import domain.objects.Map;
import domain.objects.Player;

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
	
	public Map findMapByIdGame(int id) {
		
		try{
		return this.findById(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		return null;
	}
	
	public void insertMap(Map map) {
		
		try{
		this.insert(map);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteMapByIdGame(int id) {
		
		try{
		this.delete(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void updateMapByIdGame(Map map) {
		
		try{
		this.update(map);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
}
