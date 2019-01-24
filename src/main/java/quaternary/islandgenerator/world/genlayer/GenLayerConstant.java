package quaternary.islandgenerator.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.Arrays;

public class GenLayerConstant extends GenLayer {
	public GenLayerConstant(int constant) {
		super(0);
		this.constant = constant;
	}
	
	private int constant;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] arr = IntCache.getIntCache(areaWidth * areaHeight);
		Arrays.fill(arr, constant);
		return arr;
	}
}
