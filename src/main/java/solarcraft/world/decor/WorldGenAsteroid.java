package solarcraft.world.decor;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAsteroid extends WorldGenerator
{
	Block blockType;
	int blockMeta;
	
	public WorldGenAsteroid(Block block, int meta)
	{
		this.blockType = block;
		this.blockMeta = meta;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		WorldGenMinable minable = new WorldGenMinable(blockType, blockMeta, rand.nextInt(32) + 16, Blocks.air);
		return minable.generate(world, rand, x, y, z);
	}
}
