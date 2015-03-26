package solarcraft.world.biomes;

import java.util.Random;
import org.apache.logging.log4j.Level;
import solarcraft.core.SC_Settings;
import solarcraft.core.SolarCraft;
import solarcraft.world.decor.BiomeSpaceDecorator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenSpace extends BiomeGenBase
{

	public BiomeGenSpace(int id)
	{
        super(id);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();

        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 5, 1, 1));
        
        this.topBlock = Blocks.stained_hardened_clay;
        this.fillerBlock = Blocks.stained_hardened_clay;
        
        this.setColor(8421631);
        this.setBiomeName("Space");
        this.setDisableRain();
        this.theBiomeDecorator = new BiomeSpaceDecorator();
	}

    /**
     * takes temperature, returns color
     */
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float p_76731_1_)
    {
        return 0;
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blockMap, byte[] metaMap, int posX, int posZ, double stoneNoise)
    {
        int i1 = posX & 15;
        int j1 = posZ & 15;
        int k1 = blockMap.length / 256;
        
        int bottom = 0;
        
        while(bottom < 256 && (blockMap[(j1 * 16 + i1) * k1 + bottom] == Blocks.air || blockMap[(j1 * 16 + i1) * k1 + bottom] == null))
        {
        	bottom++;
        }
        
        boolean flag = false; // Has passed top layer
        int top = 255;
        
        for (int l1 = 255; l1 >= bottom; --l1)
        {
            int i2 = (j1 * 16 + i1) * k1 + l1;
            
            if(blockMap[i2] == Blocks.stone)
            {
            	if(!flag)
            	{
            		flag = true;
            		top = l1;
            	}
            	
            	String layerName = SC_Settings.cakeLayers[MathHelper.floor_float((float)(top - l1)/(float)(top - bottom)*(SC_Settings.cakeLayers.length - 1))];
            	String[] splitName = layerName.split(":");
            	
            	Block layer = Blocks.air;
            	byte meta = 0;
            	
            	if(splitName.length >= 3)
            	{
            		layer = Block.getBlockFromName(splitName[0] + ":" + splitName[1]);
            		try
            		{
            			meta = (byte)(Integer.parseInt(splitName[2])%16);
            		} catch(NumberFormatException e)
            		{
            			meta = 0;
            		}
            	}
            	
            	blockMap[i2] = layer != null? layer : Blocks.air;
            	metaMap[i2] = meta;
            }
        }
    }
}
