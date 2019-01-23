package quaternary.islandgenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.islandgenerator.world.WorldTypeIsland;

@Mod(modid = IslandGenerator.MODID, name = IslandGenerator.NAME, version = IslandGenerator.VERSION)
public class IslandGenerator {
	public static final String MODID = "islandgenerator";
	public static final String NAME = "Island Generator";
	public static final String VERSION = "GRADLE:VERSION";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static WorldTypeIsland ISLAND_WORLD_TYPE;
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		//sideeffecty constructor, this registers it
		ISLAND_WORLD_TYPE = new WorldTypeIsland();
	}
}
