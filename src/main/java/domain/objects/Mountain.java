package domain.objects;

public class Mountain  extends Territory {

	public Mountain( int xAxis, int yAxis) {
		super( xAxis, yAxis);
	}
	
	public Mountain(){
		
	}

	@Override
	protected void applyEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumTerritory getEnumTerritoryType() {
		return EnumTerritory.MOUNTAIN;
	}

}
