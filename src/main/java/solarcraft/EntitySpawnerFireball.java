package solarcraft;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DungeonHooks;

public class EntitySpawnerFireball extends EntityFireball
{
	
	public EntitySpawnerFireball(World world)
	{
		super(world);
        this.setSize(1F, 1F);
	}
	
	public EntitySpawnerFireball(World world, EntityLivingBase shooter, double x, double y, double z)
	{
		super(world, shooter, x, y, z);
        this.setSize(1F, 1F);
	}
	
	public EntitySpawnerFireball(World world, double x, double y, double z, double motX, double motY, double motZ)
	{
		super(world, x, y, z, motX, motY, motZ);
        this.setSize(1F, 1F);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if(this.worldObj.isRemote)
		{
			return;
		}
		
		this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 3.0F, false, true);

        this.worldObj.spawnParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
        
        int i = mop.blockX;
        int j = mop.blockY;
        int k = mop.blockZ;

        switch (mop.sideHit)
        {
            case 0:
                --j;
                break;
            case 1:
                ++j;
                break;
            case 2:
                --k;
                break;
            case 3:
                ++k;
                break;
            case 4:
                --i;
                break;
            case 5:
                ++i;
        }

        if (this.worldObj.isAirBlock(i, j, k))
        {
            this.worldObj.setBlock(i, j, k, Blocks.mob_spawner);
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)this.worldObj.getTileEntity(i, j, k);

            if (tileentitymobspawner != null)
            {
                tileentitymobspawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(this.worldObj.rand));
            }
        }
        
        this.setDead();
	}
}
