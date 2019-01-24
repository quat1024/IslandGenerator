package quaternary.islandgenerator.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import quaternary.islandgenerator.client.ClientHandler;
import quaternary.islandgenerator.config.IslandsConfig;
import quaternary.islandgenerator.world.WorldTypeIsland;

public class ServerProxy extends AbstractProxy {
	private static final String ALREADY_SET = "islandgenerator-already-set-default-worldtype";
	
	@Override
	public void init() {
		//TODO actually test this on a dedi server lmao
		//adapted a bit from Hexlands btw
		if(IslandsConfig.SET_AS_DEFAULT_WORLDTYPE) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			if(server instanceof DedicatedServer) {
				PropertyManager props = ((DedicatedServer)server).settings;
				if(! props.getBooleanProperty(ALREADY_SET, false)) {
					props.setProperty(ALREADY_SET, true);
					props.setProperty("level-type", WorldTypeIsland.NAME);
					props.setProperty("generator-settings", IslandsConfig.DEFAULT_WORLD_OPTIONS);
					props.saveProperties();
				}
			}
		}
	}
}
