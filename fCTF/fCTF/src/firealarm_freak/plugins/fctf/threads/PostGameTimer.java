package firealarm_freak.plugins.fctf.threads;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import firealarm_freak.plugins.fctf.Game;

public class PostGameTimer implements Runnable{
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(Game.spawn);
		}
		for (int i = 15; i > 0; i--) {
			Bukkit.broadcastMessage(i + " seconds till game starts!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(Game.match == Game.maxMatches) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer("Server Restarting, come back in a moment...");
				Main.
			}
		} else {
			MapManager.unloadMap();
			
		}
	}
}
