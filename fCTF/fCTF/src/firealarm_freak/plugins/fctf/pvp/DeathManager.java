package firealarm_freak.plugins.fctf.pvp;

import org.bukkit.entity.Player;

import firealarm_freak.plugins.fctf.ClassManager;
import firealarm_freak.plugins.fctf.Game;
import firealarm_freak.plugins.fctf.PlayerManager;

public class DeathManager {
	public static void onPlayerDeath(Player p) {
    	if (PlayerManager.teamAPlayer.contains(p)) {
    		p.teleport(Game.spawnA);
    	} else {
    		p.teleport(Game.spawnB);
    	}
    	ClassManager.resetClass(p);
    	PlayerManager.getFPlayerByPlayer(p).deaths++;
    	PlayerManager.getFPlayerByPlayer(p).killStreak = 0;
	}
}