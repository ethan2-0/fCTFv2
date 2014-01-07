package firealarm_freak.plugins.fctf;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import firealarm_freak.plugins.fctf.config.MapConfigManager;

public class MapManager {
	public static void loadMap(String world) {
		World w = Bukkit.getWorld(world);
		w.setAutoSave(false);
		Game.flagA = new Location(w, 0, 0, 0);
		Game.flagB = new Location(w, 0, 0, 0);
		Game.spawn = new Location(w, 0, 0, 0);
		Game.spawnA = new Location(w, 0, 0, 0);
		Game.spawnB = new Location(w, 0, 0, 0);
		MapConfigManager.loadMapConfig(world);
	}
	public static void unloadMap() {
		Game.flagA = null;
		Game.flagB = null;
		Game.spawn = null;
		Game.spawnA = null;
		Game.spawnB = null;
	}
}
