package solarcraft.world;

import java.util.Random;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenWormhole implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(!SC_Settings.generateWormholes || world.isRemote || random.nextInt(1000) != 0)
		{
			return;
		}
		
		world.setBlock(chunkX * 16 + random.nextInt(16), random.nextInt(256), chunkZ * 16 + random.nextInt(16), SolarCraft.wormhole);
	}
}
