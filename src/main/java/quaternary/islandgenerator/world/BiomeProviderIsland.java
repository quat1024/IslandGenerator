package quaternary.islandgenerator.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.IntCache;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BiomeProviderIsland extends BiomeProvider {
	public BiomeProviderIsland(World world, String settings) {
		super(world.getWorldInfo());
		this.settings = IslandWorldSettings.fromString(settings);
		
		genBiomes = new GenLayerShore(2, new GenLayer(1) {
			@Override
			public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
				int[] arr = IntCache.getIntCache(areaHeight * areaWidth);
				
				//System.out.printf("%s %s %s %s\n", areaX, areaY, areaWidth, areaHeight);
				
				for(int xOffset = 0; xOffset < areaWidth; xOffset++) {
					for(int zOffset = 0; zOffset < areaHeight; zOffset++) {
						initChunkSeed(areaX + xOffset, areaY + zOffset);
						int index = xOffset + zOffset * areaWidth;
						
						int x = areaX + xOffset;
						int z = areaY + zOffset;
						
						int distSq = x * x + z * z;
						
						if(distSq > 100 * 100) arr[index] = 24; //deep ocean
						else if(distSq > 75 * 75) arr[index] = 0; //ocean
						else if(distSq > 50 * 50) arr[index] = 16; //beach
						else arr[index] = 23; //jungle edge
					}
				}
				
				return arr;
			}
		});
		
		//what is this for ???
		biomeIndexLayer = genBiomes;
	}
	
	IslandWorldSettings settings;
	
	@Override
	public List<Biome> getBiomesToSpawnIn() {
		//Yeah there's no like, "getvalues" method...
		return StreamSupport.stream(Biome.REGISTRY.spliterator(), false).collect(Collectors.toList());
	}
}
