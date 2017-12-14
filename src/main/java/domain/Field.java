package domain;


public class Field  extends Territory{

	public Field( int xAxis, int yAxis) {
		super(xAxis, yAxis);
	}

	@Override
	protected void applyEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumTerritory getEnumTerritoryType() {
		
		return EnumTerritory.FIELD;
	}


}
