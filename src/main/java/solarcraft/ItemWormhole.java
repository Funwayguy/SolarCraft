package solarcraft;

import java.util.List;
import solarcraft.world.Planet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemWormhole extends ItemBlock
{
	public ItemWormhole(Block block)
	{
		super(block);
	}

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	list.add(new ItemStack(item));
    	
    	for(Planet planet : Planet.planets.values())
    	{
    		ItemStack stack = new ItemStack(item);
    		NBTTagCompound tags = new NBTTagCompound();
    		tags.setInteger("portal_dimension", planet.dimensionID);
    		stack.setTagCompound(tags);
    		list.add(stack);
    	}
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
    	Planet planet = stack.stackTagCompound == null? null : Planet.planets.get(stack.stackTagCompound.getInteger("portal_dimension"));
    	
    	if(planet == null || planet.dimensionID == 0)
    	{
    		list.add("Planet: Random");
    	} else
    	{
    		list.add("Planet: " + planet.name);
    	}
    }
}
