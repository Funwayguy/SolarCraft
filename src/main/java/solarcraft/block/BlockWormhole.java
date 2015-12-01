package solarcraft.block;

import java.util.Random;
import solarcraft.block.tile.TileEntityWormhole;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWormhole extends Block implements ITileEntityProvider
{
	public BlockWormhole()
	{
		super(Material.portal);
		this.setBlockTextureName("portal");
		this.setBlockName("solarcraft.wormhole");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
    {
    	TileEntity tile = world.getTileEntity(x, y, z);
    	
    	if(tile != null && tile instanceof TileEntityWormhole && stack.getTagCompound() != null)
    	{
    		((TileEntityWormhole)tile).dimension = stack.getTagCompound().getInteger("portal_dimension");
    	}
    }
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityWormhole();
	}
}
