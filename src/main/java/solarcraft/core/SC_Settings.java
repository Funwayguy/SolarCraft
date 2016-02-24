package solarcraft.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * A container for all the configurable settings in the mod
 */
public class SC_Settings
{
	public static boolean debugAir = false;
	public static boolean badAir = false;
	public static int spaceBiomeID = 24;
	public static float asteroidWeight = -35F;
	public static boolean genGrass = false;
	public static int asteroidSize = 32;
	public static boolean genOres = true;
	public static int scorchedArea = 128;
	public static float spawnIsland = 24F;
	public static int scorchedBoundry = 16;
	public static String[] cakeLayers = new String[]{Block.blockRegistry.getNameForObject(Blocks.stained_hardened_clay) + ":15"};
	public static boolean meteorShowers = true;
	public static int machineUsage = 10;
	public static float gravityFact = 1F;
	public static boolean spaceSkybox = true;
	public static boolean generateWormholes = true;
	public static String[] ignoredEntites = new String[] {};
}
