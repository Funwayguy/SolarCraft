package solarcraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderPlanet extends WorldProvider
{
	@Override
	public boolean canRespawnHere()
	{
		return false;
	}
	
    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
		Planet planet = Planet.planets.get(this.dimensionId);
		
		if(planet == null || planet.biomeID < 0 || planet.biomeID >= BiomeGenBase.getBiomeGenArray().length)
		{
			this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, BiomeGenBase.plains.rainfall);
		} else
		{
			BiomeGenBase biome = BiomeGenBase.getBiome(planet.biomeID);
			biome = biome != null? biome : BiomeGenBase.plains;
			this.worldChunkMgr = new WorldChunkManagerHell(biome, biome.rainfall);
		}
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
		Planet planet = Planet.planets.get(this.dimensionId);
		
		if(planet == null || planet.biomeID < 0 || planet.biomeID >= BiomeGenBase.getBiomeGenArray().length)
		{
			return terrainType.getChunkGenerator(worldObj, field_82913_c);
		} else
		{
			BiomeGenBase biome = BiomeGenBase.getBiome(planet.biomeID);
			biome = biome != null? biome : BiomeGenBase.plains;
			
			if(biome == BiomeGenBase.hell)
			{
		        return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
			} else if(biome == BiomeGenBase.sky)
			{
		        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
			} else
			{
		        return terrainType.getChunkGenerator(worldObj, field_82913_c);
			}
		}
    }
    
	@Override
	public String getDimensionName()
	{
		Planet planet = Planet.planets.get(this.dimensionId);
		return planet == null? "Unknown Planet" : planet.name;
	}
}
