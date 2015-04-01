package solarcraft.block.tile;

import java.util.ArrayList;
import solarcraft.block.IAirProvider;
import solarcraft.core.SolarCraft;
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

public class TileEntityAirEmitter extends TileEntity implements IInventory
{
	ItemStack airInput;
	public int airTime = 0;
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
		
		if(airTime > 0)
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
			
			airTime -= 1;
			
			if(airTime == 0)
			{
				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, SolarCraft.airEmitter);
			}
		}
		
		if(airTime <= 1 && this.getStackInSlot(0) != null && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
		{
			ItemStack stack = this.getStackInSlot(0);
			Item item = stack.getItem();
			
			if(item instanceof ItemBlock)
			{
				Block block = ((ItemBlock)item).field_150939_a;
				
				if(validMats.contains(block.getMaterial()))
				{
					this.decrStackSize(0, 1);
					airTime = 600;
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
}
