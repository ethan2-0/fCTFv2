package firealarm_freak.plugins.fctf;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import firealarm_freak.plugins.fctf.config.MapConfigManager;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getLabel().equalsIgnoreCase("switch") && sender instanceof Player) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (PlayerManager.teamAPlayer.contains(p)) {
					PlayerManager.teamAPlayer.remove(p);
					PlayerManager.teamBPlayer.add(p);
					p.sendMessage(ChatColor.GRAY + "You are on " + ChatColor.BLUE + "Blue" + ChatColor.GRAY + " team.");
					((Player) sender).setDisplayName(ChatColor.BLUE + sender.getName() + ChatColor.WHITE);
					p.teleport(Game.spawnB);
					ClassManager.resetClass(p);
				} else {
					PlayerManager.teamBPlayer.remove(p);
					PlayerManager.teamAPlayer.add(p);
					((Player) sender).setDisplayName(ChatColor.RED + sender.getName() + ChatColor.WHITE);
					p.sendMessage(ChatColor.GRAY + "You are on " + ChatColor.RED + "Red" + ChatColor.GRAY + " team.");
					p.teleport(Game.spawnA);
					ClassManager.resetClass(p);
				}
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("start")) {
			FlagManager.flagAI = FlagManager.flagSpawn("A");
			FlagManager.flagBI = FlagManager.flagSpawn("B");
			FlagManager.flagAWI = null;
			FlagManager.flagBWI = null;
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("flags")) {
			sender.sendMessage(ChatColor.WHITE + "" + Game.teamACaps + " " + ChatColor.RED + Game.flagAHolder);
			sender.sendMessage(ChatColor.WHITE + "" + Game.teamBCaps + " " + ChatColor.BLUE + Game.flagBHolder);
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("stats")) {
			if (sender instanceof Player) {
				fPlayer p = PlayerManager.getFPlayerByPlayer((Player) sender);
				sender.sendMessage(p.kills + "");
				sender.sendMessage(p.killStreak + "");
				sender.sendMessage(p.deaths + "");
				sender.sendMessage(p.flagSteals + "");
				sender.sendMessage(p.flagCaps + "");
			} else {
				sender.sendMessage("This command can only be used in-game.");
			}
			return true;
		} else if (cmd.getLabel().equalsIgnoreCase("map")) {
			sender.sendMessage(MapConfigManager.mapName + " by " + MapConfigManager.mapAuthor);
			return true;
		} else {
			return false;
		}
	}
}
