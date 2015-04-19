package solarcraft;

import net.minecraftforge.fluids.Fluid;

public class FluidOxygen extends Fluid
{
	public FluidOxygen()
	{
		super("oxygen");
		this.setTemperature(75);
		this.setDensity(1141);
		this.setGaseous(true);
	}
}
