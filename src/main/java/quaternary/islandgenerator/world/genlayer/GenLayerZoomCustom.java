package quaternary.islandgenerator.world.genlayer;

import com.google.common.base.Preconditions;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerZoomCustom extends GenLayer {
	public GenLayerZoomCustom(long seed, int numerator, int denomerator, GenLayer parent) {
		super(seed);
		Preconditions.checkArgument(numerator < denomerator, "can't scale down with genlayerzoomcustom");
		this.numerator = numerator;
		this.denominator = denomerator;
		this.parent = parent;
	}
	
	private int numerator;
	private int denominator;
	private GenLayer parent;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] arr = IntCache.getIntCache(areaWidth * areaHeight);
		
		//Sample a smaller region than the output region
		int shrunkAreaWidth = (areaWidth * numerator) / denominator;
		int shrunkAreaHeight = (areaHeight * numerator) / denominator;
		int shrunkAreaX = areaX + ((areaWidth - shrunkAreaWidth) / 2);
		int shrunkAreaY = areaY + ((areaHeight - shrunkAreaHeight) / 2);
		int[] parentArr = parent.getInts(shrunkAreaX, shrunkAreaY, shrunkAreaWidth, shrunkAreaHeight);
		
		//For every unit in the output region,
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				int x = areaX + xOff;
				int y = areaY + yOff;
				int index = xOff + yOff * areaWidth;
				initChunkSeed(x, y);
				
				//Find the corresponding index into the parent array
				int shrunkXOff = (xOff * numerator) / denominator;
				int shrunkYOff = (yOff * numerator) / denominator;
				int parentIndex = shrunkXOff + shrunkYOff * shrunkAreaWidth;
				
				//Set
				arr[index] = parentArr[parentIndex];
			}
		}
		
		return arr;
	}
}
