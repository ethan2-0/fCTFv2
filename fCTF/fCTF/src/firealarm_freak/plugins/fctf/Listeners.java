package firealarm_freak.plugins.fctf;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Wool;

import firealarm_freak.plugins.fctf.config.MapConfigManager;
import firealarm_freak.plugins.fctf.pvp.DeathManager;
import firealarm_freak.plugins.fctf.threads.FlagRestoreTimerBlue;
import firealarm_freak.plugins.fctf.threads.FlagRestoreTimerRed;
import firealarm_freak.plugins.fctf.threads.PostGameTimer;
import firealarm_freak.plugins.fctf.threads.PreGameTimer;

public class Listeners implements Listener{
	int teamassign = 0;
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (Game.maintenenceInProgress == false) {
			event.setJoinMessage(null);
			if (!PlayerManager.teamAPlayer.contains(event.getPlayer()) |! PlayerManager.teamBPlayer.contains(event.getPlayer())) {
				PlayerManager.playerstat.add(new fPlayer(event.getPlayer()));
				if (teamassign == 0) {
					PlayerManager.teamAPlayer.add(event.getPlayer());
					event.getPlayer().setDisplayName(ChatColor.RED + event.getPlayer().getName());
					teamassign = 1;
				} else if (teamassign == 1){
					PlayerManager.teamBPlayer.add(event.getPlayer());
					event.getPlayer().setDisplayName(ChatColor.BLUE + event.getPlayer().getName());
					teamassign = 0;
				}
			}
			if (Game.state == 2) {
				if (PlayerManager.teamAPlayer.contains(event.getPlayer())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "You are on " + ChatColor.RED + "Red" + ChatColor.GRAY + " team.");
					event.getPlayer().teleport(Game.spawnA);
				} else if (PlayerManager.teamBPlayer.contains(event.getPlayer())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "You are on " + ChatColor.BLUE + "Blue" + ChatColor.GRAY + " team.");
					event.getPlayer().teleport(Game.spawnB);
				}
			} else if (Game.state == 0) {
				Game.state = 1;
				new Thread(new PreGameTimer()).start();
				event.getPlayer().teleport(Game.spawn);
			} else if (Game.state == 1 || Game.state == 3) {
				event.getPlayer().teleport(Game.spawn);
			}
		} else {
			event.getPlayer().kickPlayer(ChatColor.RED + "Server maintenence is in progress! Try again later!");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDisconect(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		if (PlayerManager.checkPlayerTeam(event.getPlayer()) == true) {
			PlayerManager.teamAPlayer.remove(event.getPlayer());
		} else {
			PlayerManager.teamBPlayer.remove(event.getPlayer());
		}
		PlayerManager.playerstat.remove(PlayerManager.getFPlayerByPlayer(event.getPlayer()));
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Item i = event.getItem();
		if (i.getItemStack().getType().equals(Material.WOOL)) {
			if ((i.getItemStack().getData().getData() == DyeColor.BLUE.getData())) {
				if (PlayerManager.teamAPlayer.contains(event.getPlayer())) {
					if (Game.flagBHolder == "Home") {
						Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " stole " + ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag!");
						Game.flagBHolder = event.getPlayer().getName();
						FlagManager.flagBWI = FlagManager.flagSteal("B");
						PlayerManager.getFPlayerByPlayer(event.getPlayer()).flagSteals++;
						FlagManager.flagBI = null;
						Game.setCompassTargetPlayer(event.getPlayer());
					} else if (Game.flagBHolder == "On Ground") {
						Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " picked up " + ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag!");
						Game.flagBHolder = event.getPlayer().getName();
						FlagManager.flagBRestore = false;
						Game.setCompassTargetPlayer(event.getPlayer());
					}
				} else if (PlayerManager.teamBPlayer.contains(event.getPlayer())) {
					event.setCancelled(true);
					if (Game.flagBHolder == "Home" && Game.flagAHolder != "Home" && Game.flagAHolder == event.getPlayer().getName()) {
						Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + " has captured " + ChatColor.RED + "Red's" + ChatColor.LIGHT_PURPLE + " flag!");
						Game.flagAHolder = "Home";
						Game.teamBCaps++;
						PlayerManager.getFPlayerByPlayer(event.getPlayer()).flagCaps++;
						FlagManager.flagCapture("A");
						PlayerInventory inv = event.getPlayer().getInventory();
						Wool wool = new Wool(DyeColor.RED);
						ItemStack flag = wool.toItemStack(1);
						inv.remove(flag);
						Game.resetCompassTarget(event.getPlayer());
						if (Game.teamBCaps == Game.caps2Win) {
							Game.state = 3;
							new Thread(new PostGameTimer()).start();
						}
					} else if (Game.flagBHolder == "On Ground") {
						Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + ChatColor.GOLD + " has recoverd " + ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag!");
						FlagManager.flagBRestore = false;
						Game.flagBHolder = "Home";
						FlagManager.flagBRestore = false;
						Entity flag = i;
						FlagManager.flagBI = flag;
						Game.resetCompassTarget(event.getPlayer());
					}
				}
			} else if ((i.getItemStack().getData().getData() == DyeColor.RED.getData())) {
				if (PlayerManager.teamBPlayer.contains(event.getPlayer())) {
					if (Game.flagAHolder == "Home") {
						Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + ChatColor.GOLD + " stole " + ChatColor.RED + "Red's" + ChatColor.GOLD + " flag!");
						Game.flagAHolder = event.getPlayer().getName();
						PlayerManager.getFPlayerByPlayer(event.getPlayer()).flagSteals++;
						FlagManager.flagAWI = FlagManager.flagSteal("A");
						FlagManager.flagAI = null;	
						Game.setCompassTargetPlayer(event.getPlayer());
					} else if (Game.flagAHolder == "On Ground") {
						Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + ChatColor.GOLD + " picked up " + ChatColor.RED + "Red's" + ChatColor.GOLD + " flag!");
						Game.flagAHolder = event.getPlayer().getName();
						FlagManager.flagARestore = false;
						Game.setCompassTargetPlayer(event.getPlayer());
					}
				} else if (PlayerManager.teamAPlayer.contains(event.getPlayer())) {
					event.setCancelled(true);
					if (Game.flagAHolder == "Home" && Game.flagBHolder != "Home" && Game.flagBHolder == event.getPlayer().getName()) {
						Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + " has captured " + ChatColor.BLUE + "Blue's" + ChatColor.LIGHT_PURPLE + " flag!");
						Game.flagBHolder = "Home";
						Game.teamACaps++;
						PlayerManager.getFPlayerByPlayer(event.getPlayer()).flagCaps++;
						FlagManager.flagCapture("B");
						PlayerInventory inv = event.getPlayer().getInventory();
						Wool wool = new Wool(DyeColor.BLUE);
						ItemStack flag = wool.toItemStack(1);
						inv.remove(flag);
						Game.resetCompassTarget(event.getPlayer());
						if (Game.teamACaps == Game.caps2Win) {
							Game.state = 3;
							new Thread(new PostGameTimer()).start();
						}
					} else if (Game.flagAHolder == "On Ground") {
						Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.GOLD + " has recoverd " + ChatColor.RED + "Red's" + ChatColor.GOLD + " flag!");
						Game.flagAHolder = "Home";
						FlagManager.flagARestore = false;
						Entity flag = i;
						FlagManager.flagAI = flag;
						Game.resetCompassTarget(event.getPlayer());
					}
				}
			} else if ((i.getItemStack().getData().getData() == DyeColor.WHITE.getData())) {
				event.setCancelled(true);
			}
		} else {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Item i = event.getItemDrop();
		if (i.getItemStack().getType() == Material.WOOL) {
			if (i.getItemStack().getData().getData() == DyeColor.RED.getData()) {
				Bukkit.broadcastMessage(ChatColor.BLUE + event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + " has dropped " + ChatColor.RED + "Red's" + ChatColor.LIGHT_PURPLE + " flag!");
				FlagManager.flagAI = i;
				FlagManager.flagARestore = true;
				Game.flagAHolder = "On Ground";
				Thread timer = new Thread(new FlagRestoreTimerRed());
				timer.start();
			} else if (i.getItemStack().getData().getData() == DyeColor.BLUE.getData()) {
				Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.LIGHT_PURPLE + " has dropped " + ChatColor.BLUE + "Blue's" + ChatColor.LIGHT_PURPLE + " flag!");
				FlagManager.flagBI = i;
				FlagManager.flagBRestore = true;
				Game.flagBHolder = "On Ground";
				Thread timer = new Thread(new FlagRestoreTimerBlue());
				timer.start();
			}
		} else {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemDespawn(ItemDespawnEvent event) {
		if (event.getEntity().getItemStack().getType() == Material.WOOL) {
			Entity i = event.getEntity();
			UUID uuid = event.getEntity().getUniqueId();
			if (FlagManager.flagAI.getUniqueId() != null) {
				if (uuid.equals(FlagManager.flagAI.getUniqueId())) {
					event.setCancelled(true);
					i.setTicksLived(1);
				}
			}
			if (FlagManager.flagAWI != null) {
				if (uuid.equals(FlagManager.flagAWI.getUniqueId())) {
					event.setCancelled(true);
					i.setTicksLived(1);
				}
			}
			if (FlagManager.flagBI != null) {
				if (uuid.equals(FlagManager.flagBI.getUniqueId())) {
					event.setCancelled(true);
					i.setTicksLived(1);
				}
			}
			if (FlagManager.flagBWI != null) {
				if (uuid.equals(FlagManager.flagBWI.getUniqueId())) {
					event.setCancelled(true);
					i.setTicksLived(1);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (Game.flagAHolder == event.getPlayer().getName() || Game.flagBHolder == event.getPlayer().getName()) {
			Game.setCompassTargetPlayer(event.getPlayer());
		}
        Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (block.getType() == Material.REDSTONE_BLOCK) {
        	if (Game.flagBHolder == event.getPlayer().getName()) {
        		Game.flagBHolder = "Home";
    			FlagManager.flagCapture("B");
    			PlayerInventory inv = event.getPlayer().getInventory();
    			Wool wool = new Wool(DyeColor.BLUE);
    			ItemStack flag = wool.toItemStack(1);
    			inv.remove(flag);
    			Bukkit.broadcastMessage(ChatColor.BLUE + "Blue's" + ChatColor.GOLD + " flag has been restored.");
    			Game.resetCompassTarget(event.getPlayer());
        	} else if (PlayerManager.teamBPlayer.contains(event.getPlayer())) {
        		DeathManager.onPlayerDeath(event.getPlayer());
        	}
        } else if (block.getType() == Material.LAPIS_BLOCK) {
        	if (Game.flagAHolder == event.getPlayer().getName()) {
        		Game.flagAHolder = "Home";
    			FlagManager.flagCapture("A");
    			PlayerInventory inv = event.getPlayer().getInventory();
    			Wool wool = new Wool(DyeColor.RED);
    			ItemStack flag = wool.toItemStack(1);
    			inv.remove(flag);
    			Bukkit.broadcastMessage(ChatColor.RED + "Red's" + ChatColor.GOLD + " flag has been restored.");
    			Game.resetCompassTarget(event.getPlayer());
        	} else if (PlayerManager.teamAPlayer.contains(event.getPlayer())) {
        		DeathManager.onPlayerDeath(event.getPlayer());
        	}
        }
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFoodLevelDrop(FoodLevelChangeEvent event) {
		event.setFoodLevel(PlayerManager.getFPlayerByPlayer((Player) event.getEntity()).foodLevel); 
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getMaterial() == Material.COOKED_BEEF && event.getPlayer().getHealth() != 20) {
				Player p = event.getPlayer();
				double health = p.getHealth();
				if (health >= 12) {
					p.setHealth(20);
				} else {
					p.setHealth(health + 8);
				}
				PlayerInventory inv = p.getInventory();
				ItemStack steak = new ItemStack(Material.COOKED_BEEF, 1);
				inv.removeItem(steak);
				p.updateInventory();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerConsumeItem(PlayerItemConsumeEvent event) {
		ItemStack steak = new ItemStack(Material.COOKED_BEEF, event.getItem().getAmount());
		if (event.getItem().equals(steak)) {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClientPingServer(ServerListPingEvent event) {
		event.setMotd(ChatColor.RED + "Red: " + ChatColor.WHITE + Game.teamACaps + ChatColor.GOLD + " | " + ChatColor.BLUE + "Blue: " + ChatColor.WHITE + Game.teamBCaps + "\n" + ChatColor.GOLD + "Map: " + ChatColor.WHITE + MapConfigManager.mapName);
	}
}
