package firealarm_freak.plugins.fctf.threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import firealarm_freak.plugins.fctf.FlagManager;
import firealarm_freak.plugins.fctf.Game;
import firealarm_freak.plugins.fctf.PlayerManager;

public class PreGameTimer implements Runnable {

	@Override
	public void run() {
		for (int i = 15; i > 0; i--) {
			Bukkit.broadcastMessage(i + " seconds till game starts!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Bukkit.broadcastMessage("Game Starts Now!");
		Game.state = 2;
		for (Player p : PlayerManager.teamAPlayer) {
			p.teleport(Game.spawnA);
		}
		for (Player p : PlayerManager.teamAPlayer) {
			p.teleport(Game.spawnB);
		}
		FlagManager.flagSpawn("A");
		FlagManager.flagSpawn("B");
	}
}