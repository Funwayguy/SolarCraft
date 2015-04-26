package solarcraft.block.tile;

import solarcraft.block.IAirProvider;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityAirVent extends TileEntity implements IFluidHandler
{
	public int airBuffer = 0;
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		
		if(airBuffer >= SC_Settings.machineUsage && this.blockMetadata == 0)
		{
			if(this.getWorldObj().getTotalWorldTime()%10 == 0) // Culls a bit of the unnecessary processing
			{
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
	        	{
	        		int dx = this.xCoord + dir.offsetX;
	        		int dy = this.yCoord + dir.offsetY;
	        		int dz = this.zCoord + dir.offsetZ;
	        		
	        		Block block = worldObj.getBlock(dx, dy, dz);
	        		
	        		if(block == Blocks.air)
	        		{
	        			worldObj.setBlock(dx, dy, dz, SolarCraft.spaceAir, this.airBuffer/SC_Settings.machineUsage - 1, 3);
	        		} else if(block instanceof IAirProvider)
	        		{
	        			((IAirProvider)block).setAirSupply(this.worldObj, dx, dy, dz, this.airBuffer/SC_Settings.machineUsage);
	        		}
	        	}
			}
			
			airBuffer -= SC_Settings.machineUsage;
			
			if(airBuffer < SC_Settings.machineUsage)
			{
				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, SolarCraft.airVent);
			}
		}
	}
		
	
	@Override
    public void writeToNBT(NBTTagCompound tags)
    {
    	super.writeToNBT(tags);
    	tags.setInteger("Air", this.airBuffer);
    }
	
	@Override
    public void readFromNBT(NBTTagCompound tags)
    {
    	super.readFromNBT(tags);
    	this.airBuffer = tags.getInteger("Air");
    }
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		if(resource == null || resource.getFluid() != SolarCraft.LOX)
		{
			return 0;
		}
		
		int change = Math.min(resource.amount, (SC_Settings.machineUsage*16) - this.airBuffer);
		
		if(doFill && change != 0)
		{
			this.airBuffer += change;
		}
		
		return change;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return fluid == SolarCraft.LOX;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[]{new FluidTankInfo(this.airBuffer > 0? new FluidStack(SolarCraft.LOX, this.airBuffer) : null, (SC_Settings.machineUsage*16))};
	}
}
