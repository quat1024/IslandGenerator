package quaternary.islandgenerator.world;

import quaternary.islandgenerator.IslandGenerator;

import java.util.HashMap;
import java.util.Map;

public class IslandWorldSettings {
	public IslandWorldSettings(int islandRadius) {
		this.islandRadius = islandRadius;
	}
	
	public final int islandRadius;
	
	public static IslandWorldSettings fromString(String s) {
		Map<String, String> settingsMap = new HashMap<>();
		
		try {
			for(String entry : s.split("\\|")) {
				String[] entrySplit = entry.split("=");
				settingsMap.put(entrySplit[0], entrySplit[1]);
			}
		} catch(Exception e) {
			IslandGenerator.LOGGER.error("Can't read island world type settings, using default", s, e);
			settingsMap.clear();
		}
		
		int islandRadius = Integer.valueOf(settingsMap.getOrDefault("radius", "100"));
		
		return new IslandWorldSettings(islandRadius);
	}
}
