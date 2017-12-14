package persistence;

import domain.City;

public class CityMapper extends DataMapper<City> {

	public CityMapper()
	{
		super("City",DataAttributs.getAttributCity(),City.class);
	}
	
	public City findCityById(int id) {
		
		try{
		return this.findById(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		return null;
	}
	
	public void insertPlayer(City city) {
		
		try{
		this.insert(city);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteCityById(int id) {
		
		try{
		this.delete(id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void updateCityById(City city) {
		
		try{
		this.update(city);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
