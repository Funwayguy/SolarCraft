package solarcraft.world;

import solarcraft.core.SC_Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderSpace extends WorldProvider
{
	public static Class<? extends WorldProvider> oldClass;
	WorldProvider oldProvider;
	
	public WorldProviderSpace()
	{
		try
		{
			oldProvider = oldClass.newInstance();
		} catch(Exception e)
		{
			
		}
	}
	
    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
    	if(oldProvider != null)
    	{
    		oldProvider.registerWorld(this.worldObj);
    		this.worldChunkMgr = oldProvider.worldChunkMgr;
    	} else
    	{
    		this.worldChunkMgr = new WorldChunkManagerSpace(this.worldObj);
    	}
        this.dimensionId = 0;
        this.hasNoSky = false;
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderSpace(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
    {
    	return super.calculateCelestialAngle(p_76563_1_, p_76563_3_);
    }

    /**
     * Returns array with sunrise/sunset colors
     */
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
        return SC_Settings.spaceSkybox? null : super.calcSunriseSunsetColors(p_76560_1_, p_76560_2_);
    }

    /**
     * Return Vec3D with biome specific fog color
     */
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
    	if(!SC_Settings.spaceSkybox)
    	{
    		return super.getFogColor(p_76562_1_, p_76562_2_);
    	} else if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.nightVision))
		{
			return Vec3.createVectorHelper(1D, 1D, 1D);
		} else
		{
			return Vec3.createVectorHelper(0D, 0D, 0D);
		}
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return !SC_Settings.spaceSkybox;
    }

    public int getActualHeight()
    {
        return 256;
    }

    public boolean canDoLightning(Chunk chunk)
    {
        return true;
    }

    public double getHorizon()
    {
        return SC_Settings.spaceSkybox? 0D : super.getHorizon();
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return true;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean isSurfaceWorld()
    {
        return true;
    }

    /**
     * the y level at which clouds are rendered.
     */
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 1.0F;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    @Override
    public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
    {
        return this.worldObj.getTopBlock(p_76566_1_, p_76566_2_).getMaterial().blocksMovement();
    }
    
    @Override
    public ChunkCoordinates getRandomizedSpawnPoint()
    {
        ChunkCoordinates chunkcoordinates = new ChunkCoordinates(0, this.worldObj.getTopSolidOrLiquidBlock(0, 0), 0);

        return chunkcoordinates;
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return new ChunkCoordinates(0, 127, 0);
    }

    public int getAverageGroundLevel()
    {
        return 127;
    }

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_)
    {
        return false;
    }
    
    /**
     * Gets the Star Brightness for rendering sky.
     * */
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return SC_Settings.spaceSkybox? 1F : super.getStarBrightness(par1);
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getDimensionName()
    {
        return SC_Settings.spaceDimName;
    }
	
	/**
	 * Creates the light to brightness table
	 */
	@Override
	protected void generateLightBrightnessTable()
	{
		float f = 0.0F;
		
		for (int i = 0; i <= 15; ++i)
		{
			float f1 = 1.0F - (float)i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}
}
