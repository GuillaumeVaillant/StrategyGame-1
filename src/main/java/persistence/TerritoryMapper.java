package persistence;

import java.sql.Connection;

import domain.Territory;

public class TerritoryMapper extends DataMapper<Territory>{
	
	private static final Connection conn = Connexion.getConn();
	
	public TerritoryMapper() {
		super("Territory", DataAttributs.getAttributTerritory(), Territory.class);
	}

	static TerritoryMapper instance;
	

	public static TerritoryMapper getInstance() {
		if (instance == null)
			instance = new TerritoryMapper();
		return instance;
	}
	
	public Territory findTerritoryById(int id) {
        
        try {
        	return this.findById(id);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }
	

}
