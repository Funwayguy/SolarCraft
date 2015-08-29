package solarcraft.world;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderSpace implements IChunkProvider
{
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    int[][] field_73203_h = new int[32][32];
    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    /** Holds Stronghold Generator */
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    /** Holds Village Generator */
    private MapGenVillage villageGenerator = new MapGenVillage();
    /** Holds Mineshaft Generator */
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();

    public ChunkProviderSpace(World p_i2007_1_, long p_i2007_2_, boolean genStructures)
    {
    	this.mapFeaturesEnabled = genStructures;
        this.endWorld = p_i2007_1_;
        this.endRNG = new Random(p_i2007_2_);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8); // Normally 8
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10); // Normally 10
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);

        NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5};
        noiseGens = TerrainGen.getModdedNoiseGenerators(p_i2007_1_, this.endRNG, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorOctaves)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
    }

    public void func_147420_a(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_, BiomeGenBase[] p_147420_4_)
    {
        byte b0 = 2;
        int k = b0 + 1;
        byte b1 = (byte)(SC_Settings.asteroidSize + 1);//33;
        int l = b0 + 1;
        this.densities = this.initializeNoiseField(this.densities, p_147420_1_ * b0, 0, p_147420_2_ * b0, k, b1, l);

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < SC_Settings.asteroidSize/*32*/; ++k1)
                {
                    double d0 = 0.25D;
                    double d1 = this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
                    double d2 = this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
                    double d3 = this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
                    double d4 = this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
                    double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
                    double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
                    double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
                    double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 4; ++l1)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 8; ++i2)
                        {
                            int j2 = i2 + i1 * 8 << 12 | 0 + j1 * 8 << 8 | k1 * 4 + l1;
                            short short1 = 256;
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 8; ++k2)
                            {
                                Block block = null;

                                if (d15 > 0.0D)
                                {
                                    block = Blocks.stone;
                                }

                                p_147420_3_[j2] = block;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    @Deprecated // Supply metadata to the below function.
    public void func_147421_b(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_)
    {
        replaceBiomeBlocks(p_147421_1_, p_147421_2_, p_147421_3_, p_147421_4_, new byte[p_147421_3_.length]);
    }
    public void replaceBiomeBlocks(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_, byte[] meta)
    {
    	if(!SC_Settings.genGrass)
    	{
    		return;
    	}
    	
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147421_1_, p_147421_2_, p_147421_3_, meta, p_147421_4_, this.endWorld);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                float f = (p_147421_1_ * 16) + l;
                float f1 = (p_147421_2_ * 16) + k;
                
                float spawnDist = MathHelper.sqrt_float(f * f + f1 * f1);
                
                BiomeGenBase biome = p_147421_4_[l + k * 16];
                
                if(spawnDist >= SC_Settings.scorchedArea && SC_Settings.genGrass)
                {
                	biome = p_147421_4_[l + k * 16];
                	biome.genTerrainBlocks(this.endWorld, this.endRNG, p_147421_3_, meta, p_147421_1_ * 16 + k, p_147421_2_ * 16 + l, 1D);
                } else
                {
                	biome = SolarCraft.spaceBiome;
                	biome.genTerrainBlocks(this.endWorld, this.endRNG, p_147421_3_, meta, p_147421_1_ * 16 + k, p_147421_2_ * 16 + l, 1D);
                	
                	continue;
                }
                
                // Cleanup blocks which would otherwise cause issues or just look out of place
                int i1 = (p_147421_1_ * 16 + k) & 15;
                int j1 = (p_147421_2_ * 16 + l) & 15;
                int k1 = p_147421_3_.length / 256;
                
                boolean flag = false; // In charge of marking out surface blocks such as grass
                boolean flag2 = false; // Flags blocks that could possibly fall to gravity
                int filler = 0;
                
                for (int l1 = 64; l1 >= 0; --l1)
                {
                    int i2 = (j1 * 16 + i1) * k1 + l1;
                    
                    // Allows top soil to be properly generated below old sea level
                    if(p_147421_3_[i2] == null || p_147421_3_[i2].getMaterial() == Material.air)
                    {
                    	flag = false;
                    } else if((p_147421_3_[i2] == biome.fillerBlock || p_147421_3_[i2] == Blocks.stone) && !flag)
                    {
                    	filler = 3;
                    	p_147421_3_[i2] = biome.topBlock;
                    	flag = true;
                    } else
                    {
                    	flag = true;
                    	
                    	if(filler > 0 && p_147421_3_[i2] != Blocks.air && p_147421_3_[i2] != null)
                    	{
                    		p_147421_3_[i2] = biome.fillerBlock;
                    		filler--;
                    	}
                    }
                    
                    // Stops gravity affected blocks pouring into the void and causing lag spikes
                    if(flag2 && (p_147421_3_[i2] == null || p_147421_3_[i2].getMaterial() == Material.air))
                    {
                    	p_147421_3_[i2] = Blocks.stone;
                    	flag2 = false;
                    } else if(p_147421_3_[i2] != null && p_147421_3_[i2] instanceof BlockFalling)
                    {
                    	flag2 = true;
                    } else
                    {
                    	flag2 = false;
                    }
                    
                    // Remove gravel basins and any bedrock
                    if(p_147421_3_[i2] == Blocks.bedrock)
                    {
                    	p_147421_3_[i2] = Blocks.air;
                    } else if(p_147421_3_[i2] == Blocks.gravel)
                    {
                    	p_147421_3_[i2] = biome.fillerBlock;
                    	meta[i2] = 1;
                    	
                    }// else if(SC_Settings.scorchedEarth && p_147421_3_[i2] != null && (p_147421_3_[i2].getMaterial() == Material.grass || p_147421_3_[i2].getMaterial() == Material.plants || p_147421_3_[i2].getMaterial() == Material.leaves || p_147421_3_[i2].getMaterial() == Material.ground))
                    //{
                    	//p_147421_3_[i2] = Blocks.stained_hardened_clay;
                    	//meta[i2] = 15;
                    //}
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
    {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
    {
        this.endRNG.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] meta = new byte[ablock.length];
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_73154_1_ * 4 - 2, p_73154_2_ * 4 - 2, 16, 16);
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        this.func_147420_a(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration);
        this.replaceBiomeBlocks(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration, meta);

        if (SC_Settings.genGrass && this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.endWorld, p_73154_1_, p_73154_2_, ablock);
            this.villageGenerator.func_151539_a(this, this.endWorld, p_73154_1_, p_73154_2_, ablock);
            this.strongholdGenerator.func_151539_a(this, this.endWorld, p_73154_1_, p_73154_2_, ablock);
            this.scatteredFeatureGenerator.func_151539_a(this, this.endWorld, p_73154_1_, p_73154_2_, ablock);
        }
        
        Chunk chunk = new Chunk(this.endWorld, ablock, meta, p_73154_1_, p_73154_2_);
        byte[] abyte = chunk.getBiomeArray();

        for (int k = 0; k < abyte.length; ++k)
        {
            float f = (p_73154_2_ * 16) + (k % 16);
            float f1 = (p_73154_1_ * 16) + ((k - (k%16))/16);
            
            float spawnDist = MathHelper.sqrt_float(f * f + f1 * f1);
            
            if(spawnDist >= SC_Settings.scorchedArea && SC_Settings.genGrass)
            {
        		abyte[k] = (byte)this.biomesForGeneration[k].biomeID;
            } else
            {
        		abyte[k] = (byte)SolarCraft.spaceBiome.biomeID;
            }
            
        	/*if(SC_Settings.genGrass)
        	{
        		abyte[k] = (byte)this.biomesForGeneration[k].biomeID;
        	} else
        	{
        		abyte[k] = (byte)SolarCraft.spaceBiome.biomeID;
        	}*/
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_)
    {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, p_73187_1_, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.noisefield;

        if (p_73187_1_ == null)
        {
            p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D); // Original
        //this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 * 2D, d1, d0 * 2D);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D); // Original
        //this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 * 2D, d1, d0 * 2D);
        d0 *= 2.0D;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;

        for (int i2 = 0; i2 < p_73187_5_; ++i2)
        {
            for (int j2 = 0; j2 < p_73187_7_; ++j2)
            {
                double d2 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d2 > 1.0D)
                {
                    d2 = 1.0D;
                }

                double d3 = this.noiseData5[l1] / 8000.0D;

                if (d3 < 0.0D)
                {
                    d3 = -d3 * 0.3D;
                }

                d3 = d3 * 3.0D - 2.0D;
                float f = (float)(i2 + p_73187_2_) / 0.1F;
                float f1 = (float)(j2 + p_73187_4_) / 0.1F;
                float spawnDist = MathHelper.sqrt_float(f * f + f1 * f1);
                
                float boundryMidDist = Math.abs(spawnDist - ((float)SC_Settings.scorchedArea + ((float)SC_Settings.scorchedBoundry/2F)));
                
                float spawnWeight = 100F - (spawnDist * SC_Settings.spawnIsland/10F);
                float f2 = MathHelper.clamp_float(spawnWeight, SC_Settings.asteroidWeight, 80F);//Percentage of the island's density at this position
                
                if(SC_Settings.scorchedBoundry > SC_Settings.spawnIsland && boundryMidDist <= SC_Settings.scorchedBoundry/2F + 4)
                {
                	f2 -= 100 * ((SC_Settings.scorchedBoundry/2F + 4) - boundryMidDist)/4F;
                }

                if (f2 > 80.0F)
                {
                    f2 = 80.0F;
                }

                if (f2 < -100.0F)
                {
                    f2 = -100.0F;
                }

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                d3 /= 8.0D;
                d3 = 0.0D;

                if (d2 < 0.0D)
                {
                    d2 = 0.0D;
                }

                d2 += 0.5D;
                d3 = d3 * (double)p_73187_6_ / 16.0D;
                ++l1;
                double d4 = (double)p_73187_6_ / 2.0D;

                for (int k2 = 0; k2 < p_73187_6_; ++k2)
                {
                    double d5 = 0.0D;
                    double d6 = ((double)k2 - d4) * 8.0D / d2;

                    if (d6 < 0.0D)
                    {
                        d6 *= -1.0D;
                    }

                    double d7 = this.noiseData2[k1] / 512.0D;
                    double d8 = this.noiseData3[k1] / 512.0D;
                    double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d9 < 0.0D)
                    {
                        d5 = d7;
                    }
                    else if (d9 > 1.0D)
                    {
                        d5 = d8;
                    }
                    else
                    {
                        d5 = d7 + (d8 - d7) * d9;
                    }

                    d5 -= 8.0D;
                    d5 += (double)f2;
                    byte b0 = 2;
                    double d10;

                    if (k2 > p_73187_6_ / 2 - b0)
                    {
                        //d10 = (double)((float)(k2 - (p_73187_6_ / 2 - b0)) / 64.0F);
                        d10 = (double)((float)(k2 - (p_73187_6_ / 2 - b0)) / 256.0F);

                        if (d10 < 0.0D)
                        {
                            d10 = 0.0D;
                        }

                        if (d10 > 1.0D)
                        {
                            d10 = 1.0D;
                        }

                        d5 = d5 * (1.0D - d10) + -3000.0D * d10;
                    }

                    b0 = 8;

                    if (k2 < b0)
                    {
                        d10 = (double)((float)(b0 - k2) / ((float)b0 - 1.0F));
                        d5 = d5 * (1.0D - d10) + -30.0D * d10;
                    }

                    p_73187_1_[k1] = d5;
                    ++k1;
                }
            }
        }

        return p_73187_1_;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int p_73149_1_, int p_73149_2_)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
    {
        BlockFalling.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, endWorld, endWorld.rand, p_73153_2_, p_73153_3_, false));

        if (SC_Settings.genGrass && this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generateStructuresInChunk(this.endWorld, this.endWorld.rand, p_73153_2_, p_73153_3_);
            this.villageGenerator.generateStructuresInChunk(this.endWorld, this.endWorld.rand, p_73153_2_, p_73153_3_);
            this.strongholdGenerator.generateStructuresInChunk(this.endWorld, this.endWorld.rand, p_73153_2_, p_73153_3_);
            this.scatteredFeatureGenerator.generateStructuresInChunk(this.endWorld, this.endWorld.rand, p_73153_2_, p_73153_3_);
        }

        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        
        if(SC_Settings.genGrass)
        {
        	BiomeGenBase biomegenbase = this.endWorld.getBiomeGenForCoords(k + 16, l + 16);
        	biomegenbase.decorate(this.endWorld, this.endWorld.rand, k, l);
        } else
        {
        	SolarCraft.spaceBiome.decorate(this.endWorld, this.endWorld.rand, k, l);
        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, endWorld, endWorld.rand, p_73153_2_, p_73153_3_, false));

        BlockFalling.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
    {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
    {
        BiomeGenBase biomegenbase = this.endWorld.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return p_73155_1_ == EnumCreatureType.monster && this.scatteredFeatureGenerator.func_143030_a(p_73155_2_, p_73155_3_, p_73155_4_) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : biomegenbase.getSpawnableList(p_73155_1_);
    }

    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
    {
        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int p_82695_1_, int p_82695_2_)
    {
        if (SC_Settings.genGrass && this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.endWorld, p_82695_1_, p_82695_2_, (Block[])null);
            this.villageGenerator.func_151539_a(this, this.endWorld, p_82695_1_, p_82695_2_, (Block[])null);
            this.strongholdGenerator.func_151539_a(this, this.endWorld, p_82695_1_, p_82695_2_, (Block[])null);
            this.scatteredFeatureGenerator.func_151539_a(this, this.endWorld, p_82695_1_, p_82695_2_, (Block[])null);
        }
    }
}