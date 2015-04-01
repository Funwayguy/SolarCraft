package solarcraft.handlers;

import solarcraft.block.tile.TileEntityAirEmitter;
import solarcraft.client.GuiAirEmitter;
import solarcraft.inventory.ContainerAirEmitter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(ID == 0 && tile instanceof TileEntityAirEmitter)
		{
			return new ContainerAirEmitter(player, (TileEntityAirEmitter)tile);
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(ID == 0 && tile instanceof TileEntityAirEmitter)
		{
			return new GuiAirEmitter(player, (TileEntityAirEmitter)tile);
		}
		return null;
	}
}
