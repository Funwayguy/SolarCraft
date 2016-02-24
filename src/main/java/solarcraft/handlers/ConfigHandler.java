package solarcraft.handlers;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import solarcraft.world.Planet;

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

		String[] entityIgnoreDefault = new String[] {
			"Bat",
			"Chicken"
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
		SC_Settings.meteorShowers = config.getBoolean("Meteor Showers", Configuration.CATEGORY_GENERAL, true, "Makes it rain down flaming projectiles during storms");
		SC_Settings.machineUsage = config.getInt("Machine Use Cost", Configuration.CATEGORY_GENERAL, 10, 10, 1000, "The amount of RF/mB per tick required for a machine to function (x20 for recharge)");
		SC_Settings.gravityFact = 1F/config.getFloat("Gravity Factor", Configuration.CATEGORY_GENERAL, 1F, 0.01F, 100F, "Gravity strength factor");
		SC_Settings.spaceSkybox = config.getBoolean("Space Skybox", Configuration.CATEGORY_GENERAL, true, "Replaces the normal overworld skybox with only stars");
		SC_Settings.generateWormholes = config.getBoolean("Generate Wormholes", Configuration.CATEGORY_GENERAL, true, "Generates natural wormholes in all worlds (fairly rare)");
		SC_Settings.spaceDimName = config.getString("Space Dimension Name", Configuration.CATEGORY_GENERAL, "Space", "The name for the Space dimension");
		SC_Settings.spaceBiomeID = config.getInt("Space Biome ID", Configuration.CATEGORY_GENERAL, 24, 0, BiomeGenBase.getBiomeGenArray().length - 1, "The biome ID used in the scorched area of space");
		SC_Settings.ignoredEntites = config.getStringList("Ignored Entites", Configuration.CATEGORY_GENERAL, entityIgnoreDefault, "Set which Entities should be ignored in gravity processing");

		if(!config.hasCategory("Planets"))
		{
			config.getInt("Biome ID", "Planets.default", BiomeGenBase.forest.biomeID, 0, BiomeGenBase.getBiomeGenArray().length - 1, "The planet's biome");
			config.getInt("Dimension ID", "Planets.default", 2, Integer.MIN_VALUE, Integer.MAX_VALUE, "The planet's dimension ID");
			config.getString("Planet Name", "Planets.default", "Forest Planet", "The planet's technical name");
		}
		
		ConfigCategory rootPlanCat = config.getCategory("Planets");
		
		Planet.planets.clear();
		for(ConfigCategory planCat : rootPlanCat.getChildren())
		{
			int b = config.getInt("Biome ID", planCat.getQualifiedName(), 0, 0, BiomeGenBase.getBiomeGenArray().length - 1, "The planet's biome");
			int d = config.getInt("Dimension ID", planCat.getQualifiedName(), -100, Integer.MIN_VALUE, Integer.MAX_VALUE, "The planet's dimension ID");
			String name = config.getString("Planet Name", planCat.getQualifiedName(), "New Planet", "The planet's technical name");
			Planet.planets.put(d, new Planet(name, b, d));
		}
		SolarCraft.logger.log(Level.INFO, "Loaded " + Planet.planets.size() + " custom planet(s)");
		
		config.save();
		
		SolarCraft.logger.log(Level.INFO, "Loaded configs...");
	}
}
