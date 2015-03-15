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
		
		SC_Settings.asteroidWeight = config.getFloat("Asteroid Weight", Configuration.CATEGORY_GENERAL, -30, -100F, 100F, "Weighted density of asteroids/landmasses");
		SC_Settings.genGrass = config.getBoolean("Generate Grass", Configuration.CATEGORY_GENERAL, false, "Whether the world should generate with a grassy top");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
