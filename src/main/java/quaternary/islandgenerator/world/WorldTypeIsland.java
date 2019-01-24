package quaternary.islandgenerator.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.islandgenerator.config.IslandsConfig;
import quaternary.islandgenerator.gui.GuiCustomizeIslandWorld;

public class WorldTypeIsland extends WorldType {
	public static final String NAME = "islandgenerator";
	
	public WorldTypeIsland() {
		super(NAME);
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		if(generatorOptions.isEmpty()) generatorOptions = IslandsConfig.DEFAULT_WORLD_OPTIONS;
		
		return super.getChunkGenerator(world, generatorOptions); //TODO: this.
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		String settings = world.getWorldInfo().getGeneratorOptions();
		if(settings.isEmpty()) settings = IslandsConfig.DEFAULT_WORLD_OPTIONS;
		
		return new BiomeProviderIsland(world, settings);
	}
	
	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 0;
	}
	
	@Override
	public boolean isCustomizable() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onCustomizeButton(Minecraft mc, GuiCreateWorld cw) {
		mc.displayGuiScreen(new GuiCustomizeIslandWorld(cw, cw.chunkProviderSettingsJson));
	}
}
