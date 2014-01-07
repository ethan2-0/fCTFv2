package firealarm_freak.plugins.fctf;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Game {
	public static Game instance;
	
	//Game stats info.
	public static int teamACaps = 0;
	public static int teamBCaps = 0;
	
	public static int caps2Win = 0;
	public static int maxMatches = 2;
	
	public static int match = 0;
	public static int state = 0;
	public static boolean maintenenceInProgress = false;
	
	public static String flagAHolder = "Home";
	public static String flagBHolder = "Home";
	
	public static Location flagA;
	public static Location flagB;
	
	public static Location spawn;
	
	public static Location spawnA;
	public static Location spawnB;
	
	public static int teamASpawn = 0;
	public static int teamBSpawn = 0;
	
	public static int teamAWins = 0;
	public static int teamBWins = 0;
	
	public static void setCompassTargetPlayer(Player p) {
		if (PlayerManager.checkPlayerTeam(p) == true && Game.flagBHolder == p.getName()) {
			for (int i = 0; i < PlayerManager.teamBPlayer.size(); i++) {
				Player target = PlayerManager.teamBPlayer.get(i);
				target.setCompassTarget(p.getLocation());
			}
		} else if (PlayerManager.checkPlayerTeam(p) == false && Game.flagBHolder == p.getName()) {
			for (int i = 0; i < PlayerManager.teamAPlayer.size(); i++) {
				Player target = PlayerManager.teamAPlayer.get(i);
				target.setCompassTarget(p.getLocation());
			}
		}
	}
	
	public static void resetCompassTarget(Player p) {
		if (PlayerManager.checkPlayerTeam(p) == true) {
			for (int i = 0; i < PlayerManager.teamBPlayer.size(); i++) {
				Player target = PlayerManager.teamBPlayer.get(i);
				target.setCompassTarget(Game.flagB);
			}
		} else if (PlayerManager.checkPlayerTeam(p) == false) {
			for (int i = 0; i < PlayerManager.teamAPlayer.size(); i++) {
				Player target = PlayerManager.teamAPlayer.get(i);
				target.setCompassTarget(Game.flagA);
			}
		}
	}
}
