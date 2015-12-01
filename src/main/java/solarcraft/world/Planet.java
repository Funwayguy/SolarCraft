package solarcraft.world;

import java.util.HashMap;

public class Planet
{
	public static HashMap<Integer,Planet> planets = new HashMap<Integer,Planet>();
	
	public int biomeID = 0;
	public int dimensionID = -100;
	public String name = "New Planet";
	
	public Planet(String name, int biomeID, int dimensionID)
	{
		this.name = name;
		this.biomeID = biomeID;
		this.dimensionID = dimensionID;
	}
}
