package persistence;

import domain.Game;
import domain.Player;
import domain.Territory;
import domain.Visiteur;

public class Committer extends Visiteur {

	@Override
	public void visiter(Player p) {
		
		DataMapper<Player> dataMap = new DataMapper<Player>("Coo_Player", DataAttributs.getAttributPlayer(), Player.class);
		
		try {
			dataMap.update(p);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void visiter(Game p) {
		DataMapper<Game> dataMap = new DataMapper<Game>("Coo_Parties", DataAttributs.getAttributGame(), Game.class);
		
		try {
			dataMap.update(p);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void visiter(Territory p) {
		DataMapper<Territory> dataMap = new DataMapper<Territory>("Coo_Territory", DataAttributs.getAttributTerritory(), Territory.class);
		
		try {
			dataMap.update(p);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	

	
}