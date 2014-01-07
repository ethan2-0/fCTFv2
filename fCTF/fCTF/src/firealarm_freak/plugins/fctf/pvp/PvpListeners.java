package firealarm_freak.plugins.fctf.pvp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import firealarm_freak.plugins.fctf.PlayerManager;

public class PvpListeners implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(EntityDamageEvent event) {
		Entity player = event.getEntity();
        if (player instanceof Player) {
        	Player p = (Player) player;
        	double damage = event.getDamage();
            double pHealth = p.getHealth();
            DamageCause cause = event.getCause();
            if (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.CONTACT || cause == DamageCause.PROJECTILE || cause == DamageCause.LIGHTNING || cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION) {
                if ((1 - PlayerManager.getFPlayerByPlayer(p).pArmor * 0.04) * damage >= pHealth) {
                	event.setCancelled(true);
                	DeathManager.onPlayerDeath(p);
                }
            } else {
                if (damage >= pHealth) {
                	event.setCancelled(true);
                	DeathManager.onPlayerDeath(p);
                	if (PlayerManager.checkPlayerTeam(p) == true) {
                		p.sendMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + " died");
                	} else {
                		p.sendMessage(ChatColor.BLUE + p.getName() + ChatColor.WHITE + " died");
                	}
                }
            }
        }
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerDeathMessage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			double damage = event.getDamage();
			double pHealth = p.getHealth();
			if (PlayerManager.checkPlayerTeam(p) == true) {
				if (event.getDamager() instanceof Player) {
					Player damager = (Player) event.getDamager();
					ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
					if (PlayerManager.checkPlayerTeam(damager) == true) {
						event.setCancelled(true);
					} else if (damager.getItemInHand().equals(axe)) {
						p.sendMessage("You were killed by " + ChatColor.BLUE + damager.getName() + "(/" + PlayerManager.getFPlayerByPlayer(damager).pClass + ")");
						damager.sendMessage("You killed " + ChatColor.RED + p.getName() + "(/" + PlayerManager.getFPlayerByPlayer(p).pClass + ")");
						PlayerManager.getFPlayerByPlayer(damager).kills++;
						PlayerManager.getFPlayerByPlayer(damager).killStreak++;
						DeathManager.onPlayerDeath(p);
					}
				} else if ((1 - PlayerManager.getFPlayerByPlayer(p).pArmor * 0.04) * damage >= pHealth) {
					if (event.getDamager() instanceof Player) {
						Player damager = (Player) event.getDamager();
						p.sendMessage("You were killed by " + ChatColor.BLUE + damager.getName() + "(/" + PlayerManager.getFPlayerByPlayer(damager).pClass + ")");
						damager.sendMessage("You killed " + ChatColor.RED + p.getName() + "(/" + PlayerManager.getFPlayerByPlayer(p).pClass + ")");
						PlayerManager.getFPlayerByPlayer(damager).kills++;
						PlayerManager.getFPlayerByPlayer(damager).killStreak++;
					}
				}
			} else {
				if (event.getDamager() instanceof Player) {
					Player damager = (Player) event.getDamager();
					ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
					if (PlayerManager.checkPlayerTeam(damager) == false) {
						event.setCancelled(true);
					} else if (damager.getItemInHand().equals(axe)) {
						p.sendMessage("You were killed by " + ChatColor.RED + damager.getName() + "(/" + PlayerManager.getFPlayerByPlayer(damager).pClass + ")");
						damager.sendMessage("You killed " + ChatColor.BLUE + p.getName() + "(/" + PlayerManager.getFPlayerByPlayer(p).pClass + ")");
						PlayerManager.getFPlayerByPlayer(damager).kills++;
						PlayerManager.getFPlayerByPlayer(damager).killStreak++;
						DeathManager.onPlayerDeath(p);
					}
				} else if ((1 - PlayerManager.getFPlayerByPlayer(p).pArmor * 0.04) * damage >= pHealth) {
					if (event.getDamager() instanceof Player) {
						Player damager = (Player) event.getDamager();
						p.sendMessage("You were killed by " + ChatColor.RED + damager.getName() + "(/" + PlayerManager.getFPlayerByPlayer(damager).pClass + ")");
						damager.sendMessage("You killed " + ChatColor.BLUE + p.getName() + "(/" + PlayerManager.getFPlayerByPlayer(p).pClass + ")");
						PlayerManager.getFPlayerByPlayer(damager).kills++;
						PlayerManager.getFPlayerByPlayer(damager).killStreak++;
					}
				}
			}
		}
	}
}