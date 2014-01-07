package firealarm_freak.plugins.fctf.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import firealarm_freak.plugins.fctf.FlagManager;
import firealarm_freak.plugins.fctf.Game;

public class FlagRestoreTimerBlue implements Runnable {
	
	@Override
	public void run() {
		int seconds = 5;
		for(int i = seconds; i > 0; i--) {
			if (FlagManager.flagBRestore == true) {
				Bukkit.broadcastMessage(ChatColor.GOLD + "" + i + " seconds before " + ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag is restored.");
				try {
					Thread.sleep(1000);
				} catch (Exception e) { }
			} else {
				if (Game.flagBHolder == "Home") {
					FlagManager.flagRecover("B");
				}
				return;
			}
		}
		FlagManager.flagRestore("B");
		Bukkit.broadcastMessage(ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag has been restored.");
	}
}
