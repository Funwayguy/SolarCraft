package solarcraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import solarcraft.block.tile.TileEntityAirVent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAirVent extends Block implements ITileEntityProvider, IAirProvider
{
	IIcon iconClosed;
	
	public BlockAirVent()
	{
		super(Material.iron);
		this.setHardness(5F);
        this.setBlockName("solarcraft.air_vent");
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return meta == 0? this.blockIcon : this.iconClosed;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon("solarcraft:panel_grate");
        this.iconClosed = register.registerIcon("solarcraft:panel_grate_closed");
    }

	@Override
	public int getAirSupply(World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile != null && tile instanceof TileEntityAirVent && tile.getBlockMetadata() == 0)
		{
			return MathHelper.clamp_int(((TileEntityAirVent)tile).airBuffer, 1, 16);
		}
		
		return 0;
	}

	@Override
	public void setAirSupply(World world, int x, int y, int z, int amount)
	{
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityAirVent();
	}

    /**
     * Called upon block activation (right click on the block.)
     */
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		if(world.isRemote)
		{
			return true;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) == 0? 1 : 0, 3);
		
		return true;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
    	if(rand.nextInt(5) == 0 && world.getBlockMetadata(x, y, z) == 0)
    	{
    		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				if(world.isAirBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
				{
					double randX = dir.offsetX != 0? dir.offsetX + 0.5D : rand.nextDouble();
					double randY = dir.offsetY != 0? dir.offsetY + 0.5D : rand.nextDouble();
					double randZ = dir.offsetZ != 0? dir.offsetZ + 0.5D : rand.nextDouble();
					
					world.spawnParticle("cloud", x + randX, y + randY, z + randZ, 0.5D * dir.offsetX, 0.5D * dir.offsetY, 0.5D * dir.offsetZ);
				}
			}
    	}
    }
}
