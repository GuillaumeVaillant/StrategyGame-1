package domain.objects;

public class Plains extends Territory {

	public Plains(int xAxis, int yAxis) {
		super( xAxis, yAxis);
	}
	
	public Plains(){
		
	}

	@Override
	protected void applyEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumTerritory getEnumTerritoryType() {
		return EnumTerritory.PLAIN;
	}

}
