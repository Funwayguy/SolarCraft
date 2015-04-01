package solarcraft.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import solarcraft.core.SolarCraft;
import solarcraft.handlers.EventHandler;
import solarcraft.handlers.GuiHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
		MinecraftForge.ORE_GEN_BUS.register(handler);
		NetworkRegistry.INSTANCE.registerGuiHandler(SolarCraft.instance, new GuiHandler());
	}
}
