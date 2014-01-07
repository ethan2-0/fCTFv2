package firealarm_freak.plugins.fctf.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import firealarm_freak.plugins.fctf.Game;

public class MapConfigManager {
	public static File mapDataFile = null;
	
	public static String mapName;
	public static String mapAuthor;

	public static void loadMapConfig(String map) {
		if (mapDataFile == null) {
			mapDataFile = new File(map + "/Map-Config.ctf");
		}
		if (!mapDataFile.exists()) {
			try {
				mapDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				PrintWriter pw = new PrintWriter(mapDataFile);
				pw.write("#CTF Map Config File\r\n");
				pw.write("Map.Name:\r\n");
				pw.write("Map.Author:\r\n");
				pw.write("Map.Caps2Win:\r\n");
				pw.write("Map.Spawn:\r\n\n");
				pw.write("Red.Flag:\r\n");
				pw.write("Red.Spawn:\r\n");
				pw.write("Red.Spawn.Block:\r\n\n");
				pw.write("Blue.Flag:\r\n");
				pw.write("Blue.Spawn:\r\n");
				pw.write("Blue.Spawn.Block:");
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			mapName = getValueByNode("Map.Name");
			mapAuthor = getValueByNode("Map.Author");
			Game.caps2Win = Integer.parseInt(getValueByNode("Map.Caps2Win"));
			
			String flagLocA = getValueByNode("Red.Flag");
			String[] coordsA = flagLocA.split(", ");
			Game.flagA.setX(Double.parseDouble(coordsA[0]));
			Game.flagA.setY(Double.parseDouble(coordsA[1]) + 1);
			Game.flagA.setZ(Double.parseDouble(coordsA[2]));
			
			String flagLocB = getValueByNode("Blue.Flag");
			String[] coordsB = flagLocB.split(", ");
			Game.flagB.setX(Double.parseDouble(coordsB[0]));
			Game.flagB.setY(Double.parseDouble(coordsB[1]) + 1);
			Game.flagB.setZ(Double.parseDouble(coordsB[2]));
			
			String spawnLocA = getValueByNode("Red.Spawn");
			String[] coordsC = spawnLocA.split(", ");
			Game.spawnA.setX(Double.parseDouble(coordsC[0]));
			Game.spawnA.setY(Double.parseDouble(coordsC[1]));
			Game.spawnA.setZ(Double.parseDouble(coordsC[2]));
			
			String spawnLocB = getValueByNode("Blue.Spawn");
			String[] coordsD = spawnLocB.split(", ");
			Game.spawnB.setX(Double.parseDouble(coordsD[0]));
			Game.spawnB.setY(Double.parseDouble(coordsD[1]));
			Game.spawnB.setZ(Double.parseDouble(coordsD[2]));
			
			String spawnLoc = getValueByNode("Map.Spawn");
			String[] coordsE = spawnLoc.split(", ");
			Game.spawn.setX(Double.parseDouble(coordsE[0]));
			Game.spawn.setY(Double.parseDouble(coordsE[1]));
			Game.spawn.setZ(Double.parseDouble(coordsE[2]));
			
			Game.teamASpawn = Integer.parseInt(getValueByNode("Red.Spawn.Block"));
			Game.teamBSpawn = Integer.parseInt(getValueByNode("Blue.Spawn.Block"));
		}
	}
	
	public static String getValueByNode(String node) {
		String result = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapDataFile));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(":") && line.startsWith(node)) {
					int index = line.indexOf(":");
					index = index + 2;
					if (line.length() < index) {
						result = null;
					} else {
						result = line.substring(index);
					}
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
