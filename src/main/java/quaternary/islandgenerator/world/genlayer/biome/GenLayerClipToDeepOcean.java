package quaternary.islandgenerator.world.genlayer.biome;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerClipToDeepOcean extends GenLayer {
	public GenLayerClipToDeepOcean(int oceanRadius, GenLayer parent) {
		super(0);
		this.oceanRadiusSq = oceanRadius * oceanRadius;
		this.parent = parent;
	}
	
	int oceanRadiusSq;
	GenLayer parent;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] arr = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				int x = areaX + xOff;
				int y = areaY + yOff;
				int index = xOff + yOff * areaWidth;
				
				int biomeIdHere = arr[index];
				if(biomeIdHere == 7) continue; //river
				
				int distSq = x * x + y * y;
				if(distSq > oceanRadiusSq) {
					arr[index] = 24; //deep ocean
				}
			}
		}
		
		return arr;
	}
}
