package solarcraft.handlers;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import solarcraft.core.SolarCraft;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class EventHandler
{
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(SolarCraft.MODID))
		{
			ConfigHandler.config.save();
			ConfigHandler.initConfigs();
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving.dimension == 0 && !event.entityLiving.onGround && !(event.entityLiving instanceof EntityFlying) && !(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).capabilities.isFlying))
		{
			event.entityLiving.addVelocity(0D, 0.07D, 0D);
			
			if(event.entityLiving.motionY > -1D)
			{
				event.entityLiving.fallDistance = 0F;
			}
		}
	}
	
	@SubscribeEvent
	public void onTickEvent(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			List<?> entities = event.world.loadedEntityList;
			
			for(Object entry : entities)
			{
				if(entry == null || !(entry instanceof Entity) || entry instanceof EntityLivingBase)
				{
					continue;
				}
				
				Entity entity = (Entity)entry;
				
				if(entity.dimension == 0 && !entity.onGround)
				{
					entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity isn't already too fast for ZeroG
					entity.addVelocity(0D, 0.037D, 0D); // Give some upward velocity to counter a portion of gravity
					entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity hasn't started going too fast
				}
			}
		}
	}
}
