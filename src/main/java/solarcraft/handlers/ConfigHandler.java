package solarcraft.handlers;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import solarcraft.core.SolarCraft;

public class ConfigHandler
{
	public static Configuration config;
	static String[] defComs;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			SolarCraft.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
