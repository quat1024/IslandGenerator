package quaternary.islandgenerator.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import quaternary.islandgenerator.world.genlayer.GenLayerConstant;
import quaternary.islandgenerator.world.genlayer.biome.GenLayerBiomeMix;
import quaternary.islandgenerator.world.genlayer.biome.GenLayerClipToDeepOcean;
import quaternary.islandgenerator.world.genlayer.biome.GenLayerSingleIsland;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BiomeProviderIsland extends BiomeProvider {
	public BiomeProviderIsland(World world, String settings_) {
		super(world.getWorldInfo());
		this.settings = IslandWorldSettings.fromString(settings_);
		int deepOceanRadius = settings.islandSize * settings.islandSize * 30;
		
		WorldType type = world.getWorldType();
		
		//initial seed shape
		genBiomes = new GenLayerSingleIsland(world.getSeed());
		genBiomes = new GenLayerFuzzyZoom(1, genBiomes);
		
		//expansion/shaping
		genBiomes = new GenLayerZoom(2, genBiomes);
		genBiomes = new GenLayerSmooth(3, genBiomes);
		genBiomes = new GenLayerShore(4, genBiomes); //do shores now (this makes them grow very big)
		genBiomes = GenLayerZoom.magnify(5, genBiomes, settings.islandSize);
		genBiomes = new GenLayerSmooth(6, genBiomes);
		
		GenLayer riverLayer = new GenLayerRiverInit(1, genBiomes);
		
		//biome layer
		GenLayer biomeLayer = new GenLayerConstant(1);
		biomeLayer = new GenLayerAddSnow(world.getSeed() + 100, biomeLayer);
		biomeLayer = type.getBiomeLayer(world.getSeed(), biomeLayer, new ChunkGeneratorSettings.Factory().build());
		biomeLayer = new GenLayerVoronoiZoom(2, biomeLayer);
		biomeLayer = new GenLayerZoom(3, biomeLayer);
		biomeLayer = new GenLayerSmooth(4, biomeLayer);
		biomeLayer = new GenLayerBiomeEdge(5, biomeLayer);
		
		//river layer
		riverLayer = new GenLayerFuzzyZoom(2, riverLayer);
		riverLayer = new GenLayerVoronoiZoom(4, riverLayer);
		riverLayer = new GenLayerZoom(5, riverLayer);
		riverLayer = new GenLayerRiver(6, riverLayer);
		if(settings.bigRivers) {
			riverLayer = new GenLayerZoom(7, riverLayer);
			riverLayer = new GenLayerFuzzyZoom(8, riverLayer);
		}
		riverLayer = new GenLayerSmooth(9, riverLayer);
		
		//finishing up
		genBiomes = new GenLayerBiomeMix(100, genBiomes, biomeLayer); //overlay biomes
		genBiomes = new GenLayerRiverMix(101, genBiomes, riverLayer); //overlay rivers
		genBiomes = new GenLayerClipToDeepOcean(deepOceanRadius / 4, genBiomes); //overlay deepocean
		
		genBiomes.initWorldGenSeed(world.getSeed());
		biomeLayer.initWorldGenSeed(world.getSeed());
		riverLayer.initWorldGenSeed(world.getSeed());
		
		//idk what this really does, but if you don't do it, shit fucks up real bad
		biomeIndexLayer = new GenLayerVoronoiZoom(69, genBiomes);
		biomeIndexLayer.initWorldGenSeed(world.getSeed());
	}
	
	IslandWorldSettings settings;
	
	@Override
	public List<Biome> getBiomesToSpawnIn() {
		//Yeah there's no like, "getvalues" method...
		return StreamSupport.stream(Biome.REGISTRY.spliterator(), false).collect(Collectors.toList());
	}
}
