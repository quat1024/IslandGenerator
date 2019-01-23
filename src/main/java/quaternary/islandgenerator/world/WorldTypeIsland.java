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
import quaternary.islandgenerator.gui.GuiCustomizeIslandWorld;

public class WorldTypeIsland extends WorldType {
	public WorldTypeIsland() {
		super("islandgenerator");
	}
	
	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		//todo maybe this is useful?
		return super.getChunkGenerator(world, generatorOptions);
	}
	
	@Override
	public BiomeProvider getBiomeProvider(World world) {
		String settings = world.getWorldInfo().getGeneratorOptions();
		return new BiomeProviderIsland(world, settings);
	}
	
	@Override
	public boolean isCustomizable() {
		return true;
	}
	
	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onCustomizeButton(Minecraft mc, GuiCreateWorld cw) {
		mc.displayGuiScreen(new GuiCustomizeIslandWorld(cw, cw.chunkProviderSettingsJson));
	}
}
