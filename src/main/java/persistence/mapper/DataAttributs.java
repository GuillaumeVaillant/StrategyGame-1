package persistence.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataAttributs {
	
	public static Map<String, DataEnumType> getAttributPlayer()
	{
		Map<String, DataEnumType> attributMap = new LinkedHashMap<>();
		attributMap.put("idPlayer", DataEnumType.INTEGER);
		attributMap.put("username", DataEnumType.STRING);
		attributMap.put("password", DataEnumType.STRING);
		
		return attributMap;
	}
	
	public static Map<String, DataEnumType> getAttributGame()
	{
		Map<String, DataEnumType> attributMap = new LinkedHashMap<>();
		
		attributMap.put("idGame", DataEnumType.INTEGER);
		attributMap.put("name", DataEnumType.STRING);
		attributMap.put("currentPlayer", DataEnumType.INTEGER);
		attributMap.put("turnNumber", DataEnumType.INTEGER);
		attributMap.put("status", DataEnumType.STRING);
		attributMap.put("turnRessources", DataEnumType.INTEGER);
		attributMap.put("fieldRessources", DataEnumType.INTEGER);

		return attributMap;
	}
	
	public static Map<String, DataEnumType> getAttributCity()
	{
		Map<String, DataEnumType> attributMap = new LinkedHashMap<>();
		
		attributMap.put("idTown", DataEnumType.INTEGER);
		attributMap.put("townOwner", DataEnumType.INTEGER);	
		attributMap.put("name", DataEnumType.STRING);
		
		return attributMap;
	}
	
	public static Map<String, DataEnumType> getAttributTerritory()
	{
		Map<String, DataEnumType> attributMap = new LinkedHashMap<>();
		
		attributMap.put("idTerritory", DataEnumType.INTEGER);
		attributMap.put("xAxis", DataEnumType.INTEGER);
		attributMap.put("yAxis", DataEnumType.INTEGER);
		attributMap.put("territoryType", DataEnumType.STRING);
		attributMap.put("idTown", DataEnumType.INTEGER);
		
		
		return attributMap;
	}
	
	public static Map<String, DataEnumType> getAttributMap()
	{
		Map<String, DataEnumType> attributMap = new LinkedHashMap<>();
		
		attributMap.put("idGame", DataEnumType.INTEGER);
		attributMap.put("idTerritory", DataEnumType.INTEGER);
		
		return attributMap;
		
	}

}
