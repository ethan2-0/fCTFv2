package firealarm_freak.plugins.fctf.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import firealarm_freak.plugins.fctf.FlagManager;
import firealarm_freak.plugins.fctf.Game;

public class FlagRestoreTimerRed implements Runnable {

	@Override
	public void run() {
		int seconds = 5;
		for(int i = seconds; i > 0; i--) {
			if (FlagManager.flagARestore == true) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "" + i + " seconds before " + ChatColor.RED + "Red's" + ChatColor.GOLD + " flag is restored.");
				try {
					Thread.sleep(1000);
				} catch (Exception e) { }
			} else {
				if (Game.flagAHolder == "Home") {
					FlagManager.flagRecover("A");
				}
				return;
			}
		}
		FlagManager.flagRestore("A");
		Bukkit.broadcastMessage(ChatColor.RED + "Red's" + ChatColor.GOLD + " flag has been restored.");
	}
}
