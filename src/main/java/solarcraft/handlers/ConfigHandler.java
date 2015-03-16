package solarcraft.handlers;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;

public class ConfigHandler
{
	public static Configuration config;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			SolarCraft.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
		
		config.renameProperty(Configuration.CATEGORY_GENERAL, "Generate Grass", "Use Old Biomes");
		
		SC_Settings.asteroidWeight = config.getFloat("Asteroid Weight", Configuration.CATEGORY_GENERAL, -35, -100F, 100F, "Weighted density of asteroids/landmasses");
		SC_Settings.genGrass = config.getBoolean("Use Old Biomes", Configuration.CATEGORY_GENERAL, false, "Use the overworlds old biomes instead. Makes for a good 'blown apart world' look");
		SC_Settings.asteroidSize = config.getInt("Asteroid Size", Configuration.CATEGORY_GENERAL, 64, 16, 64, "Maximum size of an asteroid");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
