package solarcraft.block;

import java.util.Random;
import solarcraft.block.tile.TileEntityWormhole;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
