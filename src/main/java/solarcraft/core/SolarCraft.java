package solarcraft.core;

import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import solarcraft.core.proxies.CommonProxy;
import solarcraft.handlers.ConfigHandler;
import solarcraft.world.WorldProviderSpace;
import solarcraft.world.biomes.BiomeGenSpace;
import solarcraft.world.features.WorldGenSpaceStructure;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = SolarCraft.MODID, version = SolarCraft.VERSION, name = SolarCraft.NAME, guiFactory = "solarcraft.handlers.ConfigGuiFactory")
public class SolarCraft
{
    public static final String MODID = "solarcraft";
    public static final String VERSION = "SC_VER_KEY";
    public static final String NAME = "RouletteOres";
    public static final String PROXY = "solarcraft.core.proxies";
    public static final String CHANNEL = "SC_NET_CHAN";
	
	@Instance(MODID)
	public static SolarCraft instance;
	
	@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	
	public static BiomeGenBase spaceBiome;
	
	/**
	 * Purely used for returning faking filled slots
	 */
	public static Item locked;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	spaceBiome = new BiomeGenSpace(SC_Settings.spaceBiomeID);
    	BiomeDictionary.registerBiomeType(spaceBiome);
    	
    	DimensionManager.unregisterDimension(0);
    	DimensionManager.unregisterProviderType(0);
    	DimensionManager.registerProviderType(0, WorldProviderSpace.class, false);
    	DimensionManager.registerDimension(0, 0);
    	
    	GameRegistry.registerWorldGenerator(new WorldGenSpaceStructure(), 0);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
