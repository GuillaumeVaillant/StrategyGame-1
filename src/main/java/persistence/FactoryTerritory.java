package persistence;

import java.sql.SQLException;
import java.util.List;

import domain.Territory;

public class FactoryTerritory implements Factory<List<Territory>> {

	private Territory territory;
	private GameMapper mapper;
	
	public FactoryTerritory(final Territory territory) {
		this.territory = territory;
	}
	
	@Override
	public List<Territory> create() throws ClassNotFoundException, SQLException {
		
		//return mapper.findListTerritories(this.territory.getId());
		return null;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	
}
