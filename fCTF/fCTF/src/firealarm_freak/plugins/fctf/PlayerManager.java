package firealarm_freak.plugins.fctf;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayerManager {
	public static ArrayList<fPlayer> playerstat = new ArrayList<fPlayer>();
	public static List<Player> teamAPlayer = new ArrayList<Player>();
	public static List<Player> teamBPlayer = new ArrayList<Player>();
	
	public static fPlayer getPlayerByUsername(String name) {
		for (int i = 0; i < playerstat.size(); i++) {
			if (playerstat.get(i).p.getName().equals(name)) {
				return playerstat.get(i);
			}
		}
		return null;
	}
	
	public static fPlayer getFPlayerByPlayer(Player p) {
	    return getPlayerByUsername(p.getName());
	}
	
	public static boolean checkPlayerTeam(Player p) {
		if (teamAPlayer.contains(p)) {
			return true;
		} else {
			return false;
		}
	}
}
