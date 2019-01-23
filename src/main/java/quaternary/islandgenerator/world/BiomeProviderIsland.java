package quaternary.islandgenerator.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import quaternary.islandgenerator.world.genlayer.biome.GenLayerClipToDeepOcean;
import quaternary.islandgenerator.world.genlayer.biome.GenLayerSingleIsland;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BiomeProviderIsland extends BiomeProvider {
	public BiomeProviderIsland(World world, String settings) {
		super(world.getWorldInfo());
		this.settings = IslandWorldSettings.fromString(settings);
		
		WorldType type = world.getWorldType();
		
		//initial seed shape
		genBiomes = new GenLayerSingleIsland(world.getSeed());
		genBiomes = new GenLayerFuzzyZoom(1, genBiomes);
		
		GenLayer riverLayer = new GenLayerRiverInit(1, genBiomes);
		
		//biome placement
		genBiomes = new GenLayerAddSnow(50, genBiomes);
		genBiomes = type.getBiomeLayer(world.getSeed(), genBiomes, new ChunkGeneratorSettings.Factory().build());
		
		//expansion/shaping
		genBiomes = new GenLayerFuzzyZoom(2, genBiomes);
		genBiomes = new GenLayerZoom(4, genBiomes);
		genBiomes = new GenLayerShore(5, genBiomes);
		genBiomes = new GenLayerSmooth(6, genBiomes);
		
		genBiomes = new GenLayerBiomeEdge(10, genBiomes);
		
		genBiomes = new GenLayerZoom(7, genBiomes);
		genBiomes = new GenLayerShore(8, genBiomes);
		
		//river layer
		riverLayer = new GenLayerFuzzyZoom(3, riverLayer);
		riverLayer = new GenLayerFuzzyZoom(4, riverLayer);
		riverLayer = new GenLayerVoronoiZoom(5, riverLayer);
		riverLayer = new GenLayerZoom(6, riverLayer);
		riverLayer = new GenLayerRiver(9, riverLayer);
		riverLayer = new GenLayerSmooth(10, riverLayer);
		
		//finishing up
		genBiomes = new GenLayerRiverMix(6969, genBiomes, riverLayer);
		genBiomes = new GenLayerClipToDeepOcean(150, genBiomes);
		
		genBiomes.initWorldGenSeed(world.getSeed());
		
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
