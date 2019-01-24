package quaternary.islandgenerator.world;

import quaternary.islandgenerator.IslandGenerator;

import java.util.HashMap;
import java.util.Map;

public class IslandWorldSettings {
	public IslandWorldSettings() {
	}
	
	public int islandSize;
	public boolean bigRivers;
	
	public static IslandWorldSettings fromString(String s) {
		Map<String, String> settingsMap = new HashMap<>();
		
		try {
			for(String entry : s.split("\\|")) {
				String[] entrySplit = entry.split("=");
				if(entrySplit.length == 2) {
					settingsMap.put(entrySplit[0], entrySplit[1]);
				}
			}
		} catch(Exception e) {
			IslandGenerator.LOGGER.error("Can't read island world type settings, using default", s, e);
			settingsMap.clear();
		}
		
		IslandWorldSettings settings = new IslandWorldSettings();
		settings.islandSize = Integer.valueOf(settingsMap.getOrDefault("islandSize", "5"));
		settings.bigRivers = Boolean.valueOf(settingsMap.getOrDefault("bigRivers", "false"));
		return settings;
	}
}
