package solarcraft.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
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
		
		if(SC_Settings.badAir && !event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayer)
		{
			int ex = MathHelper.floor_double(event.entityLiving.posX);
			int ey = MathHelper.floor_double(event.entityLiving.posY);
			int ez = MathHelper.floor_double(event.entityLiving.posZ);
			
			long airTime = event.entityLiving.getEntityData().getLong("SolarCraft_AirTime");
			
			if(event.entityLiving.worldObj.getBlock(ex, ey, ez) == SolarCraft.spaceAir || event.entityLiving.worldObj.getBlock(ex, ey + 1, ez) == SolarCraft.spaceAir)
			{
				airTime = event.entityLiving.worldObj.getTotalWorldTime();
				event.entityLiving.getEntityData().setLong("SolarCraft_AirTime", airTime);
			} else if(event.entityLiving.getRNG().nextInt(10) == 0 && event.entityLiving.worldObj.getTotalWorldTime() - airTime > 100)
			{
				event.entityLiving.attackEntityFrom(DamageSource.inWall, 2.0F);
			}
		}
	}
	
	@SubscribeEvent
	public void onTickEvent(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			for(int i = event.world.loadedEntityList.size() - 1; i >= 0; i--)
			{
				Object entry = event.world.loadedEntityList.get(i);
				
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
	
	@SubscribeEvent
	public void onGenerate(GenerateMinable event)
	{
		if(!SC_Settings.genOres)
		{
			event.setResult(Result.DENY);
		}
	}
}
