package firealarm_freak.plugins.fctf.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import firealarm_freak.plugins.fctf.PlayerManager;

@SuppressWarnings("deprecation")
public class ChatListeners implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		event.setCancelled(true);
		if (PlayerManager.checkPlayerTeam(event.getPlayer()) == true) {
			for (int i = 0; i < PlayerManager.teamAPlayer.size(); i++) {
				Player p = PlayerManager.teamAPlayer.get(i);
				p.sendMessage(ChatColor.RED + "<" + ChatColor.WHITE + event.getPlayer().getName() + ChatColor.RED + "> " + ChatColor.WHITE + event.getMessage());
			}
		} else {
			for (int i = 0; i < PlayerManager.teamBPlayer.size(); i++) {
				Player p = PlayerManager.teamBPlayer.get(i);
				p.sendMessage(ChatColor.BLUE + "<" + ChatColor.WHITE + event.getPlayer().getName() + ChatColor.BLUE + "> " + ChatColor.WHITE + event.getMessage());
			}
		}
	}
}