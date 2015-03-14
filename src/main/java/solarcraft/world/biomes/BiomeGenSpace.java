package solarcraft.world.biomes;

import solarcraft.world.decor.BiomeSpaceDecorator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
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
        this.topBlock = Blocks.obsidian;
        this.fillerBlock = Blocks.obsidian;
        
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
}
