package domain.objects;


public class Field  extends Territory{

	public Field( int xAxis, int yAxis) {
		super(xAxis, yAxis);
	}
	
	public Field(){
		
	}

	@Override
	protected void applyEffect() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Field";
	}

	@Override
	public EnumTerritory getEnumTerritoryType() {
		
		return EnumTerritory.FIELD;
	}


}
