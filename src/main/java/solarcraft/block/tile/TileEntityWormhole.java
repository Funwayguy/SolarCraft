package solarcraft.block.tile;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class TileEntityWormhole extends TileEntity
{
	@Override
    public void updateEntity()
    {
		float size = 32F;
		double speed = 0.01D;
		
    	AxisAlignedBB bounds = this.getBlockType().getCollisionBoundingBoxFromPool(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
    	List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, bounds.expand(size, size, size));
    	
    	for(Entity entity : list)
    	{
    		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isFlying)
    		{
    			continue;
    		}
    		
    		if(entity.getDistance(this.xCoord, this.yCoord, this.zCoord) < 2D)
    		{
    			entity.travelToDimension(1);
    		} else
    		{
	    		double scale = (size - entity.getDistance(this.xCoord, this.yCoord, this.zCoord))/size;
	    		
	    		Vec3 dir = Vec3.createVectorHelper(entity.posX - this.xCoord, entity.posY - this.yCoord, entity.posZ - this.zCoord);
	    		dir = dir.normalize();
	    		entity.addVelocity(dir.xCoord * -speed * scale, dir.yCoord * -speed * scale, dir.zCoord * -speed * scale);
    		}
    	}
    }
}
