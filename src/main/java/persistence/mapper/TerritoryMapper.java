package persistence.mapper;

import java.sql.Connection;
import java.util.List;

import domain.objects.Player;
import domain.objects.Territory;

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

	
	public Territory findTerritoryById(int id) {
        
        try {
        	return this.findById(id);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }
	
	public List<Territory> findListTerritoryById(int id) {
        
        try {
        	return this.findByIdList(id);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }
	
	public void insertTerritory(Territory territory) {
		
		try{
		this.insert(territory);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteTerritoryById(int id) {
		
		try{
		this.delete(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void updateTerritoryById(Territory territory) {
		
		try{
		this.update(territory);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

}
