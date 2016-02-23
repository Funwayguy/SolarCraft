package solarcraft.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.world.ExplosionEvent;
import solarcraft.EntitySpawnerFireball;
import solarcraft.block.BlockSpaceAir;
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
	public void onExplode(ExplosionEvent.Start event)
	{
		StackTraceElement[] e = new Exception().getStackTrace();
		
		if(6 < e.length && e[6].getClassName().equalsIgnoreCase(EntityLargeFireball.class.getName()))
		{
			event.explosion.isSmoking = true;
			event.explosion.isFlaming = true;
		}
	}
	
	long lastMeteor = 0L;
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		boolean ignoreThisEntity = false;

		for (String entityName : SC_Settings.ignoredEntites) {
			if (EntityList.getEntityString(entity).equals(entityName))
			{
				ignoreThisEntity = true;
				break;
			}
		}

		if(event.entityLiving.dimension == 0 && !event.entityLiving.onGround && !(event.entityLiving.isInWater() || event.entityLiving.handleLavaMovement()) && !(event.entityLiving instanceof EntityFlying) && !(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).capabilities.isFlying) && !ignoreThisEntity)
		{
			event.entityLiving.addVelocity(0D, SC_Settings.gravityFact * 0.07D, 0D);
			
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
		
		if(SC_Settings.meteorShowers && !event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.getTotalWorldTime() - lastMeteor >= (event.entityLiving.worldObj.isThundering()? 3 : 7) && event.entityLiving instanceof EntityPlayer)
		{
			lastMeteor = event.entityLiving.worldObj.getTotalWorldTime();
			
			if(event.entityLiving.worldObj.isRaining() && event.entityLiving.worldObj.provider.dimensionId == 0)
			{
				double spawnX = event.entityLiving.posX + (event.entityLiving.worldObj.rand.nextDouble() * 128D) - 64D;
				double spawnZ = event.entityLiving.posZ + (event.entityLiving.worldObj.rand.nextDouble() * 128D) - 64D;
				
				EntityFireball fireball = null;
				
				if(event.entityLiving.getRNG().nextInt(100) == 0 && event.entityLiving.worldObj.isThundering())
				{
					fireball = new EntitySpawnerFireball(event.entityLiving.worldObj, spawnX, 255, spawnZ, 0.1D, -2D, 0.1D);
				} else if(event.entityLiving.getRNG().nextInt(100) < 25)
				{
					fireball = new EntityLargeFireball(event.entityLiving.worldObj);
					fireball.motionX = 0.1D;
					fireball.motionY = -2D;
					fireball.motionZ = 0.1D;
					fireball.setPosition(spawnX, 255, spawnZ);
				} else
				{
					fireball = new EntitySmallFireball(event.entityLiving.worldObj, spawnX, 255, spawnZ, 0.1D, -2D, 0.1D);
				}
				
				event.entityLiving.worldObj.spawnEntityInWorld(fireball);
			}
		}
		
		if(!event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityLiving && !event.entityLiving.onGround && event.entityLiving.worldObj.provider.dimensionId == 0)
		{
			EntityLiving entityLiving = (EntityLiving)event.entityLiving;
			
			double speed = entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()/4D;
			
			if(entityLiving.getAttackTarget() != null)
			{
				double distX = entityLiving.getAttackTarget().posX - entityLiving.posX;
				double distZ = entityLiving.getAttackTarget().posZ - entityLiving.posZ;
				
				entityLiving.motionX = MathHelper.clamp_double(entityLiving.motionX + distX, -speed, speed);
				entityLiving.motionZ = MathHelper.clamp_double(entityLiving.motionZ + distZ, -speed, speed);
				
				if(!entityLiving.getNavigator().noPath())
				{
					entityLiving.getNavigator().clearPathEntity(); // If we don't clean out the path it will cause issues when we land somewhere other than the last pathing point
				}
			} else if(!entityLiving.getNavigator().noPath())
			{
				PathPoint point = entityLiving.getNavigator().getPath().getPathPointFromIndex(entityLiving.getNavigator().getPath().getCurrentPathIndex());
				
				if(point != null)
				{
					double distX = point.xCoord - entityLiving.posX;
					double distZ = point.zCoord - entityLiving.posZ;
					
					Vec3 motion = Vec3.createVectorHelper(MathHelper.clamp_double(entityLiving.motionX + distX, -speed, speed), entityLiving.motionY, MathHelper.clamp_double(entityLiving.motionZ + distZ, -speed, speed));
					entityLiving.motionX = motion.xCoord;
					entityLiving.motionY = motion.yCoord;
					entityLiving.motionZ = motion.zCoord;
				}
			}
		}
	}
	
	int tps = 0;
	
	@SubscribeEvent
	public void onTickEvent(TickEvent.WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			tps = (tps + 1)%5;
			
			if(tps == 0)
			{
				BlockSpaceAir.tps = 0;
			}
			
			for(int i = event.world.loadedEntityList.size() - 1; i >= 0; i--)
			{
				Object entry = event.world.loadedEntityList.get(i);
				
				if(entry == null || !(entry instanceof Entity) || entry instanceof EntityLivingBase)
				{
					continue;
				}
				
				Entity entity = (Entity)entry;

				boolean ignoreThisEntity = false;

				for (String entityName : SC_Settings.ignoredEntites) {
					if (EntityList.getEntityString(entity).equals(entityName))
						ignoreThisEntity = true;
				}

				if (ignoreThisEntity)
					continue;

				if(entity.dimension == 0 && !entity.onGround && !(entity.isInWater() || entity.handleLavaMovement()))
				{
					entity.motionY = MathHelper.clamp_double(entity.motionY, -3D, 3D); // Make sure entity isn't already too fast for ZeroG
					entity.addVelocity(0D, SC_Settings.gravityFact * 0.037D, 0D); // Give some upward velocity to counter a portion of gravity
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
