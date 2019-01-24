package quaternary.islandgenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.islandgenerator.config.IslandsConfig;
import quaternary.islandgenerator.proxy.AbstractProxy;
import quaternary.islandgenerator.world.WorldTypeIsland;

@Mod(modid = IslandGenerator.MODID, name = IslandGenerator.NAME, version = IslandGenerator.VERSION)
public class IslandGenerator {
	public static final String MODID = "islandgenerator";
	public static final String NAME = "Island Generator";
	public static final String VERSION = "GRADLE:VERSION";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	@SidedProxy(
		clientSide = "quaternary.islandgenerator.proxy.ClientProxy",
		serverSide = "quaternary.islandgenerator.proxy.ServerProxy"
	)
	public static AbstractProxy PROXY;
	
	
	public static WorldTypeIsland ISLAND_WORLD_TYPE;
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		IslandsConfig.preinit(e);
		
		//sideeffecty constructor, this registers it
		ISLAND_WORLD_TYPE = new WorldTypeIsland();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		PROXY.init();
	}
}
