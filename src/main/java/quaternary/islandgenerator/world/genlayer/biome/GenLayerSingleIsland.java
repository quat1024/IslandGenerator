package quaternary.islandgenerator.world.genlayer.biome;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerSingleIsland extends GenLayer {
	public GenLayerSingleIsland(long baseSeed) {
		super(baseSeed);
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] arr = IntCache.getIntCache(areaWidth * areaHeight);
		
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				int x = areaX + xOff;
				int y = areaY + yOff;
				int index = xOff + yOff * areaWidth; 
				
				if(x == 0 && y == 0) {
					//force at least one block on the island center
					arr[index] = 1;
				} else {
					
					int distSq = x * x + y * y;
					if(distSq >= 5) {
						//too far away
						arr[index] = 0;
					} else {
						//generate a clump of blocks centered around 0, 0
						int chance = distSq == 0 ? 1 : distSq;
						initChunkSeed(x, y);
						
						arr[index] = nextInt(chance) == 0 ? 1 : 0;
					}
				}
			}
		}
		
		return arr;
	}
}
