package persistence;

import domain.Visiteur;
import domain.objects.City;
import domain.objects.Game;
import domain.objects.Map;
import domain.objects.Player;
import domain.objects.Territory;
import persistence.mapper.CityMapper;
import persistence.mapper.GameMapper;
import persistence.mapper.MapMapper;
import persistence.mapper.PlayerMapper;
import persistence.mapper.TerritoryMapper;

public class Committer extends Visiteur {

	@Override
	public void visiter(Player p) {
		PlayerMapper.getInstance().update(p);
		
	}

	@Override
	public void visiter(Game g) {
		GameMapper.getInstance().update(g); 
	}

	@Override
	public void visiter(Territory p) {
		TerritoryMapper.getInstance().update(p);
	}

	@Override
	public void visiter(City c) {
		CityMapper.getInstance().update(c);
		
	}

	@Override
	public void visiter(Map m) {
		MapMapper.getInstance().update(m);
		
	}
	

	
}