package solarcraft.world.features;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class StructureStation extends StructureBase
{
	public StructureStation(World world, int posX, int posY, int posZ)
	{
		super(world, posX, posY, posZ);
	}

	@Override
	public void Build()
	{
		this.BuildSmallTunnel(0, 0, -60, 120, 0); // Main hull line
		this.BuildSmallTunnel(-30, 0, -2, 31, 1); // Room split A
		this.BuildSmallTunnel(3, 0, -2, 31, 1); // Room split B
		
		this.BuildSolarArray(-2, 0, -25, 6, 3); // Solar array Back_R1
		this.BuildSolarArray(4, 0, -25, 6, 1); // Solar array Back_L1
		
		this.BuildSolarArray(-2, 0, -50, 6, 3); // Solar array Back_R2
		this.BuildSolarArray(4, 0, -50, 6, 1); // Solar array Back_L2
		
		this.BuildSolarArray(-2, 0, 23, 6, 3); // Solar array Front_R1
		this.BuildSolarArray(4, 0, 23, 6, 1); // Solar array Front_L1
		
		this.BuildSolarArray(-2, 0, 48, 6, 3); // Solar array FrontBack_R2
		this.BuildSolarArray(4, 0, 48, 6, 1); // Solar array Front_L2
		
		this.BuildRoom(-2, 0, 62, 1); // Front Room
		this.BuildRoom(-2, 0, -70, 4); // Back Room
		
		for(int i = 0; i < 3; i++)
		{
			this.BuildRoom(-10 - (i * 11), 0, 4, 1);
			this.BuildRoom(6 + (i * 11), 0, 4, 1);
			
			this.BuildRoom(-10 - (i * 11), 0, -12, 4);
			this.BuildRoom(6 + (i * 11), 0, -12, 4);
		}
		
		this.BuildRoom(-40, 0, -4, 2);
		this.BuildRoom(36, 0, -4, 8);
	}
	
	public void BuildSolarArray(int x, int y, int z, int segments, int rotation)
	{
		rotation = rotation%4;
		
		if(rotation >= 2)
		{
			x -= rotation%2 == 1? 8 : 0;
			z -= rotation%2 == 0? 8 : 0;
		}
		
		if(rotation%2 == 0)
		{
			for(int i = 0; i < segments; i++)
			{
				if(rotation >= 2)
				{
					this.Fill(x + 0, y + 0, z + 1, x + 1, y + 0, z + 9, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				} else
				{
					this.Fill(x + 0, y + 0, z + 0, x + 1, y + 0, z + 8, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				}
				
				this.Fill(x - 10, y + 1, z + 3, x + 11, y + 1, z + 6, Blocks.stained_glass, Blocks.stained_glass, 11, 11);
				this.Fill(x - 10, y + 1, z + 2, x + 11, y + 1, z + 2, Blocks.redstone_lamp, Blocks.redstone_lamp, 0, 0);
				this.Fill(x - 10, y + 1, z + 7, x + 11, y + 1, z + 7, Blocks.redstone_lamp, Blocks.redstone_lamp, 0, 0);
				this.Fill(x - 10, y + 2, z + 2, x + 11, y + 2, z + 2, Blocks.redstone_wire, Blocks.redstone_wire, 0, 0);
				this.Fill(x - 10, y + 2, z + 7, x + 11, y + 2, z + 7, Blocks.redstone_wire, Blocks.redstone_wire, 0, 0);
				this.Fill(x - 10, y + 1, z + 1, x + 11, y + 1, z + 1, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				this.Fill(x - 10, y + 1, z + 8, x + 11, y + 1, z + 8, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				
				if(rotation >= 2)
				{
					this.Fill(x + 0, y + 1, z + 9, x + 1, y + 1, z + 9, Blocks.stone_slab, Blocks.stone_slab, 0, 0);
				} else
				{
					this.Fill(x + 0, y + 1, z + 0, x + 1, y + 1, z + 0, Blocks.stone_slab, Blocks.stone_slab, 0, 0);
				}
				
				this.Fill(x + 0, y + 1, z + 1, x + 1, y + 1, z + 1, Blocks.double_stone_slab, Blocks.double_stone_slab, 0, 0);
				this.Fill(x + 0, y + 1, z + 8, x + 1, y + 1, z + 8, Blocks.double_stone_slab, Blocks.double_stone_slab, 0, 0);
				
				this.Fill(x + 0, y + 2, z + 2, x + 1, y + 2, z + 2, Blocks.daylight_detector, Blocks.daylight_detector, 0, 0);
				this.Fill(x + 0, y + 2, z + 7, x + 1, y + 2, z + 7, Blocks.daylight_detector, Blocks.daylight_detector, 0, 0);
				
				if(rotation >= 2)
				{
					z -= 9;
				} else
				{
					z += 9;
				}
			}
		} else
		{
			for(int i = 0; i < segments; i++)
			{
				if(rotation >= 2)
				{
					this.Fill(x + 1, y + 0, z + 0, x + 9, y + 0, z + 1, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				} else
				{
					this.Fill(x + 0, y + 0, z + 0, x + 8, y + 0, z + 1, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				}
				
				this.Fill(x + 3, y + 1, z - 10, x + 6, y + 1, z + 11, Blocks.stained_glass, Blocks.stained_glass, 11, 11);
				this.Fill(x + 2, y + 1, z - 10, x + 2, y + 1, z + 11, Blocks.redstone_lamp, Blocks.redstone_lamp, 0, 0);
				this.Fill(x + 7, y + 1, z - 10, x + 7, y + 1, z + 11, Blocks.redstone_lamp, Blocks.redstone_lamp, 0, 0);
				this.Fill(x + 2, y + 2, z - 10, x + 2, y + 2, z + 11, Blocks.redstone_wire, Blocks.redstone_wire, 0, 0);
				this.Fill(x + 7, y + 2, z - 10, x + 7, y + 2, z + 11, Blocks.redstone_wire, Blocks.redstone_wire, 0, 0);
				this.Fill(x + 1, y + 1, z - 10, x + 1, y + 1, z + 11, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				this.Fill(x + 8, y + 1, z - 10, x + 8, y + 1, z + 11, Blocks.stone_slab, Blocks.stone_slab, 0 | 8, 0 | 8);
				
				if(rotation >= 2)
				{
					this.Fill(x + 9, y + 1, z + 0, x + 9, y + 1, z + 1, Blocks.stone_slab, Blocks.stone_slab, 0, 0);
				} else
				{
					this.Fill(x + 0, y + 1, z + 0, x + 0, y + 1, z + 1, Blocks.stone_slab, Blocks.stone_slab, 0, 0);
				}
				
				this.Fill(x + 1, y + 1, z + 0, x + 1, y + 1, z + 1, Blocks.double_stone_slab, Blocks.double_stone_slab, 0, 0);
				this.Fill(x + 8, y + 1, z + 0, x + 8, y + 1, z + 1, Blocks.double_stone_slab, Blocks.double_stone_slab, 0, 0);
				
				this.Fill(x + 2, y + 2, z + 0, x + 2, y + 2, z + 1, Blocks.daylight_detector, Blocks.daylight_detector, 0, 0);
				this.Fill(x + 7, y + 2, z + 0, x + 7, y + 2, z + 1, Blocks.daylight_detector, Blocks.daylight_detector, 0, 0);
				
				if(rotation >= 2)
				{
					x -= 9;
				} else
				{
					x += 9;
				}
			}
		}
	}
	
	/**
	 * Generates a space station room with the given bitwise set of doors (0 - 63 inclusive)
	 * @param x
	 * @param y
	 * @param z
	 * @param doors
	 */
	public void BuildRoom(int x, int y, int z, int doors)
	{
		this.Fill(x + 1, y + 0, z + 0, x + 6, y + 2, z + 0, Blocks.quartz_block, Blocks.quartz_block, 1, 1); // Wall
		this.Fill(x + 1, y + 3, z + 0, x + 6, y + 3, z + 0, Blocks.stone_slab, Blocks.stone_slab, 7, 7); // Wall Top
		this.Fill(x + 0, y + 0, z + 1, x + 0, y + 2, z + 6, Blocks.quartz_block, Blocks.quartz_block, 1, 1); // Wall
		this.Fill(x + 0, y + 3, z + 1, x + 0, y + 3, z + 6, Blocks.stone_slab, Blocks.stone_slab, 7, 7); // Wall Top
		this.Fill(x + 1, y + 0, z + 7, x + 6, y + 2, z + 7, Blocks.quartz_block, Blocks.quartz_block, 1, 1); // Wall
		this.Fill(x + 1, y + 3, z + 7, x + 6, y + 3, z + 7, Blocks.stone_slab, Blocks.stone_slab, 7, 7); // Wall Top
		this.Fill(x + 7, y + 0, z + 1, x + 7, y + 2, z + 6, Blocks.quartz_block, Blocks.quartz_block, 1, 1); // Wall
		this.Fill(x + 7, y + 3, z + 1, x + 7, y + 3, z + 6, Blocks.stone_slab, Blocks.stone_slab, 7, 7); // Wall Top
		
		this.Fill(x + 1, y - 1, z + 1, x + 6, y - 1, z + 6, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8); // Floor
		
		this.Fill(x + 1, y + 3, z + 1, x + 6, y + 3, z + 6, Blocks.quartz_block, Blocks.quartz_block, 0, 0); // Roof Base 1
		this.Fill(x + 2, y + 3, z + 2, x + 5, y + 3, z + 5, Blocks.air, Blocks.air, 0, 0); // Roof Base 2
		
		this.Fill(x + 2, y + 4, z + 2, x + 5, y + 4, z + 5, Blocks.glowstone, Blocks.glowstone, 0, 0); // Roof Light 1
		this.Fill(x + 2, y + 4, z + 3, x + 5, y + 4, z + 4, Blocks.quartz_block, Blocks.quartz_block, 0, 0); // Roof Light 2
		this.Fill(x + 3, y + 4, z + 2, x + 4, y + 4, z + 5, Blocks.quartz_block, Blocks.quartz_block, 0, 0); // Roof Light 3
		
		doors = doors%64; // Make sure the value of doors
		
		if((doors & 1) != 1)
		{
			this.Fill(x + 2, y + 1, z + 0, x + 5, y + 1, z + 0, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15); // Window
		} else
		{
			this.Fill(x + 3, y + 0, z + 0, x + 4, y + 1, z + 0, Blocks.air, Blocks.air, 0, 0); // Doorway
			this.Fill(x + 3, y - 1, z + 0, x + 4, y - 1, z + 0, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8); // Doorway Path
			
			// Build side tunnel
			this.BuildSmallTunnel(x + 2, y, z - 1, -3, 0);
		}
		
		if((doors & 2) != 2)
		{
			this.Fill(x + 7, y + 1, z + 2, x + 7, y + 1, z + 5, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15); // Window
		} else
		{
			this.Fill(x + 7, y + 0, z + 3, x + 7, y + 1, z + 4, Blocks.air, Blocks.air, 0, 0); // Doorway
			this.Fill(x + 7, y - 1, z + 3, x + 7, y - 1, z + 4, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8); // Doorway Path
			
			// Build side tunnel
			this.BuildSmallTunnel(x + 8, y, z + 2, 5, 1);
		}
		
		if((doors & 4) != 4)
		{
			this.Fill(x + 2, y + 1, z + 7, x + 5, y + 1, z + 7, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15); // Window
		} else
		{
			this.Fill(x + 3, y + 0, z + 7, x + 4, y + 1, z + 7, Blocks.air, Blocks.air, 0, 0); // Doorway
			this.Fill(x + 3, y - 1, z + 7, x + 4, y - 1, z + 7, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8); // Doorway Path
			
			// Build side tunnel
			this.BuildSmallTunnel(x + 2, y, z + 8, 3, 0);
		}
		
		if((doors & 8) != 8)
		{
			this.Fill(x + 0, y + 1, z + 2, x + 0, y + 1, z + 5, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15); // Window
		} else
		{
			this.Fill(x + 0, y + 0, z + 3, x + 0, y + 1, z + 4, Blocks.air, Blocks.air, 0, 0); // Doorway
			this.Fill(x + 0, y - 1, z + 3, x + 0, y - 1, z + 4, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8); // Doorway Path
			
			// Build side tunnel
			this.BuildSmallTunnel(x - 1, y, z + 2, -3, 1);
		}
		
		if((doors & 16) != 16) // Up
		{
			this.Fill(x + 3, y + 4, z + 3, x + 4, y + 4, z + 4, Blocks.stained_glass, Blocks.stained_glass, 15, 15); // Roof Glass 2
		} else
		{
			this.Fill(x + 3, y + 4, z + 3, x + 4, y + 4, z + 4, Blocks.air, Blocks.air, 0, 0); // Roof Hole 2
		}
		
		if((doors & 32) == 32) // Down
		{
			this.Fill(x + 2, y - 1, z + 2, x + 5, y - 1, z + 5, Blocks.stone_slab, Blocks.stone_slab, 7, 7); // Step
			this.Fill(x + 3, y - 1, z + 3, x + 4, y - 1, z + 4, Blocks.air, Blocks.air, 7 | 8, 7 | 8); // Hole
		}
	}
	
	public void BuildSmallTunnel(int x, int y, int z, int l, int r)
	{
		if(r%2 == 0)
		{
			if(l > 0)
			{
				this.Fill(x + 1, y - 1, z + 0, x + 2, y - 1, z + (l - 1), Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8);
				this.Fill(x + 0, y + 0, z + 0, x + 3, y + 2, z + (l - 1), Blocks.quartz_block, Blocks.quartz_block, 0, 0);
				this.Fill(x + 0, y + 1, z + 0, x + 3, y + 1, z + (l - 1), Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15);
				this.Fill(x + 1, y + 3, z + 0, x + 2, y + 3, z + (l - 1), Blocks.stone_slab, Blocks.stone_slab, 7, 7);
				this.Fill(x + 1, y + 0, z + 0, x + 2, y + 2, z + (l - 1), Blocks.air, Blocks.air, 0, 0); // Hollow
			} else if(l < 0)
			{
				this.Fill(x + 1, y - 1, z + l + 1, x + 2, y - 1, z + 0, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8);
				this.Fill(x + 0, y + 0, z + l + 1, x + 3, y + 2, z + 0, Blocks.quartz_block, Blocks.quartz_block, 0, 0);
				this.Fill(x + 0, y + 1, z + l + 1, x + 3, y + 1, z + 0, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15);
				this.Fill(x + 1, y + 3, z + l + 1, x + 2, y + 3, z + 0, Blocks.stone_slab, Blocks.stone_slab, 7, 7);
				this.Fill(x + 1, y + 0, z + l + 1, x + 2, y + 2, z + 0, Blocks.air, Blocks.air, 0, 0); // Hollow
			}
		} else
		{
			if(l > 0)
			{
				this.Fill(x + 0, y - 1, z + 1, x + (l - 1), y - 1, z + 2, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8);
				this.Fill(x + 0, y + 0, z + 0, x + (l - 1), y + 2, z + 3, Blocks.quartz_block, Blocks.quartz_block, 0, 0);
				this.Fill(x + 0, y + 1, z + 0, x + (l - 1), y + 1, z + 3, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15);
				this.Fill(x + 0, y + 3, z + 1, x + (l - 1), y + 3, z + 2, Blocks.stone_slab, Blocks.stone_slab, 7, 7);
				this.Fill(x + 0, y + 0, z + 1, x + (l - 1), y + 2, z + 2, Blocks.air, Blocks.air, 0, 0); // Hollow
			} else if(l < 0)
			{
				this.Fill(x + l + 1, y - 1, z + 1, x + 0, y - 1, z + 2, Blocks.stone_slab, Blocks.stone_slab, 7 | 8, 7 | 8);
				this.Fill(x + l + 1, y + 0, z + 0, x + 0, y + 2, z + 3, Blocks.quartz_block, Blocks.quartz_block, 0, 0);
				this.Fill(x + l + 1, y + 1, z + 0, x + 0, y + 1, z + 3, Blocks.stained_glass_pane, Blocks.stained_glass_pane, 15, 15);
				this.Fill(x + l + 1, y + 3, z + 1, x + 0, y + 3, z + 2, Blocks.stone_slab, Blocks.stone_slab, 7, 7);
				this.Fill(x + l + 1, y + 0, z + 1, x + 0, y + 2, z + 2, Blocks.air, Blocks.air, 0, 0); // Hollow
			}
		}
	}
}
