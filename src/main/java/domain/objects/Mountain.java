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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mountain";
	}

}
