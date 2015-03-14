package solarcraft.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import solarcraft.core.SolarCraft;
import solarcraft.handlers.ConfigHandler;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiInventoryConfig extends GuiConfig
{
	@SuppressWarnings({"rawtypes", "unchecked"})
	public GuiInventoryConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), SolarCraft.MODID, false, false, SolarCraft.NAME);
	}
}
