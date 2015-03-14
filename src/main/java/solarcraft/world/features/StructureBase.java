package solarcraft.world.features;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class StructureBase
{
	public World world;
	public int posX;
	public int posY;
	public int posZ;
	
	public StructureBase(World world, int posX, int posY, int posZ)
	{
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public abstract void Build();
	
	public void Place(int x, int y, int z, Block block, int meta)
	{
		this.world.setBlock(posX + x, posY + y, posZ + z, block, meta, 2);
	}
	
	public Block GetBlock(int x, int y, int z)
	{
		return this.world.getBlock(posX + x, posY + y, posZ + z);
	}
	
	public int GetMeta(int x, int y, int z)
	{
		return this.world.getBlockMetadata(posX + x, posY + y, posZ + z);
	}
	
	public void Fill(int sx, int sy, int sz, int ex, int ey, int ez, Block block1, Block block2, int meta1, int meta2)
	{
		for(int x = sx; x <= ex; x++)
		{
			for(int y = sy; y <= ey; y++)
			{
				for(int z = sz; z <= ez; z++)
				{
					if(x == sx || x == ex || y == sy || y == ey || z == sz || z == ez)
					{
						this.world.setBlock(posX + x, posY + y, posZ + z, block2, meta2, 2);
					} else
					{
						this.world.setBlock(posX + x, posY + y, posZ + z, block1, meta1, 2);
					}
				}
			}
		}
	}
}
