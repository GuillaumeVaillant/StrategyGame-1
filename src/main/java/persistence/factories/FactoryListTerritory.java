package persistence.factories;

import java.sql.SQLException;
import java.util.List;

import domain.objects.EnumTerritory;
import domain.objects.Field;
import domain.objects.Mountain;
import domain.objects.Plains;
import domain.objects.Player;
import domain.objects.Territory;
import persistence.mapper.GameMapper;
import persistence.mapper.TerritoryMapper;

public class FactoryListTerritory implements Factory<List<Territory>> {

	private int id;
	
	public FactoryListTerritory(int id) {
		this.id = id;
	}
	
	@Override
	public List<Territory> create() throws ClassNotFoundException, SQLException {
		
		return GameMapper.getInstance().findListTerritories(id);
	}

	
}
