package solarcraft.block;

import net.minecraft.world.World;

public interface IAirProvider
{
	public int getAirSupply(World world, int x, int y, int z);
	
	public void setAirSupply(World world, int x, int y, int z, int amount);
}
