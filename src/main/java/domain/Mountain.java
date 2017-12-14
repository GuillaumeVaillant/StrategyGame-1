package domain;

public class Mountain  extends Territory {

	public Mountain( int xAxis, int yAxis) {
		super( xAxis, yAxis);
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
