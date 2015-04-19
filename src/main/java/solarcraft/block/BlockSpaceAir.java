package solarcraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;

public class BlockSpaceAir extends Block implements IAirProvider
{
    public BlockSpaceAir()
    {
        super(Material.air);
        this.setTickRandomly(true);
        this.setBlockName("solarcraft.space_air");
		this.setBlockTextureName("glass");
    }
    
    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
    	super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
        return 15;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	
    	if(SC_Settings.airInterval > 0)
    	{
    		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    	}
    }
    
    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate(World world)
    {
        return SC_Settings.airInterval;
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	int curAir = this.getAirSupply(world, x, y, z);
    	int maxNear = 0;
    	
    	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
    	{
    		int dx = x + dir.offsetX;
    		int dy = y + dir.offsetY;
    		int dz = z + dir.offsetZ;
    		
    		Block block = world.getBlock(dx, dy, dz);
    		
    		if(block == Blocks.leaves || block == Blocks.leaves2)
    		{
    			maxNear = 16;
    			break;
    		} else if(block instanceof IAirProvider)
    		{
    			int tmpAir = ((IAirProvider)block).getAirSupply(world, dx, dy, dz);
    			
    			if(tmpAir > maxNear)
    			{
    				maxNear = tmpAir;
    			}
    		}
    	}
    	
    	if(curAir != maxNear - 1)
    	{
    		this.setAirSupply(world, x, y, z, maxNear - 1);
    	} else if(curAir > 1) // Air supply changed so we need to update the surrounding air. NOTE: Only used to create new air blocks. Existing ones can update themselves
		{
    		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        	{
        		int dx = x + dir.offsetX;
        		int dy = y + dir.offsetY;
        		int dz = z + dir.offsetZ;
        		
        		Block block = world.getBlock(dx, dy, dz);
        		
        		if(block == Blocks.air)
        		{
        			world.setBlock(dx, dy, dz, SolarCraft.spaceAir, curAir - 1, 2);
        		}
        	}
		}
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return SC_Settings.debugAir? 0 : -1;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in 
     * @param par1 block metaData 
     * @param par2 whether the player right-clicked while holding a boat
     */
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	if(SC_Settings.airInterval > 0)
    	{
    		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    	}
    }

	@Override
	public int getAirSupply(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) + 1;
	}

	@Override
	public void setAirSupply(World world, int x, int y, int z, int amount)
	{
		amount = MathHelper.clamp_int(amount, 0, 16);
		
		if(amount <= 0)
		{
			world.setBlockToAir(x, y, z);
		} else
		{
			//world.setBlock(x, y, z, SolarCraft.spaceAir, amount - 1, 3);
			world.setBlockMetadataWithNotify(x, y, z, amount - 1, 3);
			if(SC_Settings.airInterval > 0)
	    	{
	    		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	    	}
		}
	}
}