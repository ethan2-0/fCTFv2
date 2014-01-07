package firealarm_freak.plugins.fctf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommands  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getLabel().equalsIgnoreCase("heavy")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				PlayerManager.getFPlayerByPlayer(p).pClass = "Heavy";
				PlayerManager.getFPlayerByPlayer(p).pArmor = 20;
				PlayerManager.getFPlayerByPlayer(p).foodLevel = 15;
				if (PlayerManager.teamAPlayer.contains(p)) {
					ClassManager.changeClass(p, Game.spawnA);
				} else {
					ClassManager.changeClass(p, Game.spawnB);
				}
			} else {
				sender.sendMessage("This command can only be used in-game.");
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("pyro")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				PlayerManager.getFPlayerByPlayer(p).pClass = "Pyro";
				PlayerManager.getFPlayerByPlayer(p).pArmor = 7;
				PlayerManager.getFPlayerByPlayer(p).foodLevel = 15;
				if (PlayerManager.teamAPlayer.contains(p)) {
					ClassManager.changeClass(p, Game.spawnA);
				} else {
					ClassManager.changeClass(p, Game.spawnB);
				}
			} else {
				sender.sendMessage("This command can only be used in-game.");
			}
			return true;
		} else {
			return false;
		}
	}
	
}
