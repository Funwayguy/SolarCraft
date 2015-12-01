package solarcraft.block.tile;

import java.util.ArrayList;
import java.util.List;
import solarcraft.TeleportWormhole;
import solarcraft.world.Planet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class TileEntityWormhole extends TileEntity
{
	public int dimension = 0;
	
	@Override
    public void updateEntity()
    {
		float size = 32F;
		double speed = 0.05D;
		
    	AxisAlignedBB bounds = this.getBlockType().getCollisionBoundingBoxFromPool(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
    	@SuppressWarnings("unchecked")
		List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, bounds.expand(size, size, size));
    	
    	for(Entity entity : list)
    	{
    		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isFlying)
    		{
    			continue;
    		}
    		
    		if(entity.getDistance(this.xCoord, this.yCoord, this.zCoord) < 2D && entity.timeUntilPortal <= 0)
    		{
    			if(entity.worldObj.provider.dimensionId == 0)
    			{
    				if(this.dimension == 0 || !Planet.planets.containsKey(this.dimension))
    				{
		    			ArrayList<Planet> planets = new ArrayList<Planet>(Planet.planets.values());
		    			
		    			if(planets.size() <= 0)
		    			{
		    				entity.timeUntilPortal = entity.getPortalCooldown();
		    				//entity.travelToDimension(0);
		    				TeleportWormhole.WarpEntityToDimension(entity, 0); // Warp to spawn
		    			} else
		    			{
		    				entity.timeUntilPortal = entity.getPortalCooldown();
		    				//entity.travelToDimension(planets.get(entity.worldObj.rand.nextInt(planets.size())).dimensionID);
		    				TeleportWormhole.WarpEntityToDimension(entity, planets.get(entity.worldObj.rand.nextInt(planets.size())).dimensionID);
		    			}
    				} else
    				{
	    				entity.timeUntilPortal = entity.getPortalCooldown();
	    				//entity.travelToDimension(Planet.planets.get(this.dimension).dimensionID);
	    				TeleportWormhole.WarpEntityToDimension(entity, Planet.planets.get(this.dimension).dimensionID);
    				}
    			} else
    			{
    				entity.timeUntilPortal = entity.getPortalCooldown();
    				//entity.travelToDimension(0);
    				TeleportWormhole.WarpEntityToDimension(entity, 0); // Warp to spawn
    			}
    		} else
    		{
	    		double scale = (size - entity.getDistance(this.xCoord, this.yCoord, this.zCoord))/size;
	    		
	    		Vec3 dir = Vec3.createVectorHelper(entity.posX - this.xCoord, entity.posY - this.yCoord, entity.posZ - this.zCoord);
	    		dir = dir.normalize();
	    		entity.addVelocity(dir.xCoord * -speed * scale, dir.yCoord * -speed * scale, dir.zCoord * -speed * scale);
    		}
    	}
    }
	
	@Override
	public void readFromNBT(NBTTagCompound tags)
	{
		this.dimension = tags.getInteger("portal_dimension");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tags)
	{
		tags.setInteger("portal_dimension", this.dimension);
	}
}
