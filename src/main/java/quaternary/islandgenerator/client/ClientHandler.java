package quaternary.islandgenerator.client;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.islandgenerator.IslandGenerator;
import quaternary.islandgenerator.config.IslandsConfig;
import quaternary.islandgenerator.world.WorldTypeIsland;

import java.lang.ref.WeakReference;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = IslandGenerator.MODID)
public class ClientHandler {
	private static WeakReference<GuiCreateWorld> alreadySetUi = new WeakReference<>(null);
	
	@SubscribeEvent
	public static void guiOpen(GuiOpenEvent e) {
		if(!IslandsConfig.SET_AS_DEFAULT_WORLDTYPE) return;
		
		GuiScreen ui = e.getGui();
		if(ui instanceof GuiCreateWorld) {
			GuiCreateWorld worldUi = (GuiCreateWorld) ui;
			
			if(IslandsConfig.SET_AS_DEFAULT_WORLDTYPE) {
				//don't set it on the same gui instance more than once
				//e.g. opening superflat customize menu and going back
				if(worldUi == alreadySetUi.get()) return;
				alreadySetUi = new WeakReference<>(worldUi);
				
				//find the index of the island worldtype
				int islandTypeIndex = -1;
				for(int i = 0; i < WorldType.WORLD_TYPES.length; i++) {
					WorldType type = WorldType.WORLD_TYPES[i];
					if(type instanceof WorldTypeIsland) {
						islandTypeIndex = i;
						break;
					}
				}
				
				worldUi.selectedIndex = islandTypeIndex;
				
				if(islandTypeIndex == -1) {
					IslandGenerator.LOGGER.info("cannot find the island world type on the gui");
					return;
				}
				
				if(worldUi.chunkProviderSettingsJson.isEmpty()) {
					worldUi.chunkProviderSettingsJson = IslandsConfig.DEFAULT_WORLD_OPTIONS;
				}
			}
		}
	}
}
