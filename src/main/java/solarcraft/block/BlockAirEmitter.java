package solarcraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import solarcraft.block.tile.TileEntityAirEmitter;
import solarcraft.core.SolarCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAirEmitter extends BlockContainer implements IAirProvider
{
	public IIcon iconTop;
	public IIcon iconBottom;
	
	public BlockAirEmitter()
	{
		super(Material.iron);
		this.setBlockName("solarcraft.air_emitter");
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setHardness(5F);
	}

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon("solarcraft:panel_emitter_side");
        this.iconTop = register.registerIcon("solarcraft:panel_emitter_top");
        this.iconBottom = register.registerIcon("solarcraft:panel_generic");
    }

    /**
     * Called upon block activation (right click on the block.)
     */
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		player.openGui(SolarCraft.instance, 0, world, x, y, z);
        return true;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityAirEmitter();
	}

	@Override
	public int getAirSupply(World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityAirEmitter)
		{
			return ((TileEntityAirEmitter)tile).airTime > 0? 16 : 0;
		} else
		{
			return 0;
		}
	}

	@Override
	public void setAirSupply(World world, int x, int y, int z, int amount)
	{
	}
	
}
