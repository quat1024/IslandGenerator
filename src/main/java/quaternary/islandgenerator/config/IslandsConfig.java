package quaternary.islandgenerator.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.islandgenerator.IslandGenerator;

@Mod.EventBusSubscriber(modid = IslandGenerator.MODID)
public class IslandsConfig {
	public static int CURRENT_CONFIG_VERSION = 1;
	private static Configuration config;
	
	public static String DEFAULT_WORLD_OPTIONS;
	public static boolean SET_AS_DEFAULT_WORLDTYPE;
	
	public static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), String.valueOf(CURRENT_CONFIG_VERSION));
		config.load();
		
		readConfig();
	}
	
	private static void readConfig() {
		DEFAULT_WORLD_OPTIONS = config.getString("defaultWorldOptions", "general",
			"",
			"The default options string that will be used if nothing custom is specified.\n" +
			"Modpack makers: change this setting to change the default island settings."
		);
		
		SET_AS_DEFAULT_WORLDTYPE = config.getBoolean("setAsDefaultWorldtype", "general",
			false,
			"If true, the 'Island' worldtype will be preselected in the 'Create New World' menu.\n" +
			"Additionally, dedicated servers will be set to this world type by default."
		);
		
		if(config.hasChanged()) {
			config.save();
		}
	}
	
	@SubscribeEvent
	public static void changed(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(IslandGenerator.MODID)) {
			readConfig();
		}
	}
}
