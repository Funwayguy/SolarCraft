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
		
		String[] layerDefault = new String[]
		{
			"minecraft:stained_hardened_clay:15",
	        "minecraft:stained_hardened_clay:14",
	        "minecraft:stained_hardened_clay:13",
	        "minecraft:stained_hardened_clay:12",
	        "minecraft:stained_hardened_clay:11",
	        "minecraft:stained_hardened_clay:10",
	        "minecraft:stained_hardened_clay:9",
	        "minecraft:stained_hardened_clay:8",
	        "minecraft:stained_hardened_clay:7",
	        "minecraft:stained_hardened_clay:6",
	        "minecraft:stained_hardened_clay:5",
	        "minecraft:stained_hardened_clay:4",
	        "minecraft:stained_hardened_clay:3",
	        "minecraft:stained_hardened_clay:2",
	        "minecraft:stained_hardened_clay:1",
	        "minecraft:stained_hardened_clay"
		};
		
		SC_Settings.asteroidWeight = config.getFloat("Asteroid Weight", Configuration.CATEGORY_GENERAL, -35, -100F, 80F, "Weighted density of asteroids/landmasses");
		SC_Settings.spawnIsland = config.getFloat("Spawn Size", Configuration.CATEGORY_GENERAL, 24F, 16F, 1024F, "Size of the spawn island (Always created at maximum weight & size)");
		SC_Settings.genGrass = config.getBoolean("Use Old Biomes", Configuration.CATEGORY_GENERAL, false, "Use the overworlds old biomes instead. Makes for a good 'blown apart world' look");
		SC_Settings.asteroidSize = config.getInt("Asteroid Size", Configuration.CATEGORY_GENERAL, 64, 16, 64, "Maximum size of an asteroid");
		SC_Settings.scorchedArea = config.getInt("Scorched Earth", Configuration.CATEGORY_GENERAL, 128, -1, 4096, "Range of area around spawn where solar radiation has burnt away plant life");
		SC_Settings.scorchedBoundry = config.getInt("Scorched Boundry", Configuration.CATEGORY_GENERAL, 16, 0, 4096, "The amount of empty space between scorched and living land");
		SC_Settings.genGrass = config.getBoolean("Use Old Biomes", Configuration.CATEGORY_GENERAL, false, "Use the overworlds old biomes instead. Makes for a good 'blown apart world' look");
		SC_Settings.genOres = config.getBoolean("Generate Ores", Configuration.CATEGORY_GENERAL, true, "Set whether ores should generate inside the asteroids");
		SC_Settings.cakeLayers = config.getStringList("Scorched Layers", Configuration.CATEGORY_GENERAL, layerDefault, "Set whether ores should generate inside the asteroids");
		SC_Settings.badAir = config.getBoolean("Bad Space Air", Configuration.CATEGORY_GENERAL, false, "[WIP] Without an air emitter the player will slowly take damage");
		SC_Settings.debugAir = config.getBoolean("Debug Air", Configuration.CATEGORY_GENERAL, false, "Enables rendering space air as glass blocks. Used for debugging");
		SC_Settings.airInterval = config.getInt("Air Interval", Configuration.CATEGORY_GENERAL, 10, 0, 300, "The interval between air updates. Set to 0 to tick randomly");
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
