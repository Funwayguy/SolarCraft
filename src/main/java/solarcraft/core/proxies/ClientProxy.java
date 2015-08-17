package solarcraft.core.proxies;

import net.minecraft.client.renderer.entity.RenderFireball;
import solarcraft.EntitySpawnerFireball;
import solarcraft.block.tile.TileEntityWormhole;
import solarcraft.client.TileEntityWormholeRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWormhole.class, new TileEntityWormholeRenderer());
    	RenderingRegistry.registerEntityRenderingHandler(EntitySpawnerFireball.class, new RenderFireball(4.0F));
	}
}
