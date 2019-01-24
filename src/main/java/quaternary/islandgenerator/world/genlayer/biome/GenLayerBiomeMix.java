package quaternary.islandgenerator.world.genlayer.biome;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeMix extends GenLayer {
	public GenLayerBiomeMix(long seed, GenLayer shape, GenLayer biome) {
		super(seed);
		this.shape = shape;
		this.biome = biome;
	}
	
	private GenLayer shape;
	private GenLayer biome;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] arr = IntCache.getIntCache(areaWidth * areaHeight);
		
		int[] shapeArray = shape.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] biomeArray = biome.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int i = 0; i < arr.length; i++) {
			arr[i] = shapeArray[i] == 1 ? biomeArray[i] : shapeArray[i];
		}
		
		return arr;
	}
}
