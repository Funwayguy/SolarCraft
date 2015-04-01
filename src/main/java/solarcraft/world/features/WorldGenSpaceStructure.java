package solarcraft.world.features;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSpaceStructure implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.dimensionId != 0 || chunkX % 4 != 0 || chunkZ % 4 != 0)
		{
			return;
		}
		
		/*int x = chunkX * 16;
		int y = random.nextInt(192) + 32;
		int z = chunkZ * 16;
		
		if(random.nextInt(100) == 0)
		{
			StructureBase structure = new StructureStation(world, x, y, z);
			structure.Build();
		}*/
	}
}
