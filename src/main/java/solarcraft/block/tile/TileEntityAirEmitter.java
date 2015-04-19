package solarcraft.block.tile;

import java.util.ArrayList;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import solarcraft.block.IAirProvider;
import solarcraft.core.SolarCraft;

public class TileEntityAirEmitter extends TileEntity implements IInventory, IEnergyReceiver, IFluidTank, IFluidHandler
{
	ItemStack airInput;
	public int airTime = 0;
	public int power = 0;
	static ArrayList<Material> validMats = new ArrayList<Material>();
	
	public TileEntityAirEmitter()
	{
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		
		// Calibration variables
		int powerUse = 50;
		int airUse = 10;
		int rechargeCost = 1000;
		
		if(airTime >= airUse && power >= powerUse && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
		{
			if(airTime%10 == 0) // Culls a bit of the unnecessary processing
			{
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
	        	{
	        		int dx = this.xCoord + dir.offsetX;
	        		int dy = this.yCoord + dir.offsetY;
	        		int dz = this.zCoord + dir.offsetZ;
	        		
	        		Block block = worldObj.getBlock(dx, dy, dz);
	        		
	        		if(block == Blocks.air)
	        		{
	        			worldObj.setBlock(dx, dy, dz, SolarCraft.spaceAir, 15, 3);
	        		} else if(block instanceof IAirProvider)
	        		{
	        			((IAirProvider)block).setAirSupply(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 15);
	        		}
	        	}
			}
			
			airTime -= airUse;
			power -= powerUse;
			
			if(airTime < airUse || power < powerUse)
			{
				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, SolarCraft.airEmitter);
			}
		}
		
		if(airTime <= airUse && this.getStackInSlot(0) != null && power >= rechargeCost)
		{
			ItemStack stack = this.getStackInSlot(0);
			Item item = stack.getItem();
			
			if(item instanceof ItemBlock)
			{
				Block block = ((ItemBlock)item).field_150939_a;
				
				if(validMats.contains(block.getMaterial()))
				{
					this.decrStackSize(0, 1);
					airTime = this.getCapacity();
					power -= rechargeCost;
				}
			}
		}
	}
	
	@Override
    public void readFromNBT(NBTTagCompound tags)
    {
    	super.readFromNBT(tags);
    	airInput = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("AirInput"));
    	airTime = tags.getInteger("airTime");
    }
	
    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
    	super.writeToNBT(tags);
    	tags.setInteger("airTime", airTime);
    	
    	if(airInput != null)
    	{
    		NBTTagCompound stackTags = new NBTTagCompound();
    		stackTags = airInput.writeToNBT(stackTags);
    		tags.setTag("AirInput", stackTags);
    	}
    }

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return slot == 0? airInput : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if(slot == 0 && airInput != null)
		{
			if(airInput.stackSize <= amount)
			{
				ItemStack item = airInput;
				airInput = null;
				this.markDirty();
				return item;
			} else
			{
				ItemStack item = airInput.splitStack(amount);
				
				if(airInput.stackSize <= 0)
				{
					airInput = null;
				}
				this.markDirty();
				return item;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return slot == 0? airInput : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if(slot == 0)
		{
			airInput = stack;
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName()
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}
	
	static
	{
		validMats.add(Material.grass);
		validMats.add(Material.plants);
		validMats.add(Material.leaves);
		validMats.add(Material.cactus);
		validMats.add(Material.vine);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection side)
	{
		return side != ForgeDirection.UP;
	}

	@Override
	public int getEnergyStored(ForgeDirection side)
	{
		return power;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection side)
	{
		return 10000;
	}

	@Override
	public int receiveEnergy(ForgeDirection side, int max, boolean simulate)
	{
		if(side == ForgeDirection.UP)
		{
			return 0;
		}
		
		int change = Math.min(this.getMaxEnergyStored(side) - power, max);
		
		if(!simulate && change != 0)
		{
			power += change;
		}
		
		return change;
	}

	@Override
	public FluidStack getFluid()
	{
		if(airTime <= 0)
		{
			return null;
		}
		return new FluidStack(SolarCraft.LOX, airTime);
	}

	@Override
	public int getFluidAmount()
	{
		return airTime;
	}

	@Override
	public int getCapacity()
	{
		return 6000;
	}

	@Override
	public FluidTankInfo getInfo()
	{
		return new FluidTankInfo(this);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(resource == null || resource.getFluid() != SolarCraft.LOX)
		{
			return 0;
		}
		
		int change = Math.min(this.getCapacity() - airTime, resource.amount);
		
		if(doFill && change != 0)
		{
			airTime += change;
		}
		
		return change;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		int change = Math.min(airTime, maxDrain);
		
		if(doDrain && change != 0)
		{
			airTime -= change;
		}
		
		return change > 0? new FluidStack(SolarCraft.LOX, change) : null;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return (from == ForgeDirection.UP)? 0 : this.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return (resource.getFluid() != SolarCraft.LOX)? null : this.drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return (from == ForgeDirection.UP)? null : this.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return fluid == SolarCraft.LOX && !(from == ForgeDirection.UP);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return fluid == SolarCraft.LOX && !(from == ForgeDirection.UP);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[]{this.getInfo()};
	}
}
