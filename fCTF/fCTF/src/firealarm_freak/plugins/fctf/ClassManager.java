package firealarm_freak.plugins.fctf;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ClassManager {
	
	public static void resetClass(Player p) {		
		if (PlayerManager.getFPlayerByPlayer(p).pClass == "Heavy") {
	    	p.setFireTicks(0);
	    	p.setHealth(20);
	    	p.setFoodLevel(PlayerManager.getFPlayerByPlayer(p).foodLevel);
			PlayerInventory inv = p.getInventory();
			inv.clear();
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			ItemStack steak = new ItemStack(Material.COOKED_BEEF, 3);
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
			inv.addItem(sword);
			inv.addItem(steak);
			inv.addItem(compass);
			inv.setBoots(boot);
			inv.setLeggings(leg);
			inv.setChestplate(chest);
			inv.setHelmet(helm);
		} else if (PlayerManager.getFPlayerByPlayer(p).pClass == "Pyro") {
	    	p.setFireTicks(0);
	    	p.setHealth(20);
	    	p.setFoodLevel(15);
			PlayerInventory inv = p.getInventory();
			inv.clear();
			ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
			ItemStack steak = new ItemStack(Material.COOKED_BEEF, 5);
			ItemStack lighter = new ItemStack(Material.FLINT_AND_STEEL);
			ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
			ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
			inv.addItem(axe);
			inv.addItem(steak);
			inv.addItem(lighter);
			inv.setBoots(boot);
			inv.setLeggings(leg);
			inv.setChestplate(chest);
			inv.setHelmet(helm);
		}
	}
	
	public static void changeClass(Player p, Location teleLoc) {
		if (PlayerManager.getFPlayerByPlayer(p).pClass == "Heavy") {
	    	p.setFireTicks(0);
	    	p.setHealth(20);
	    	p.setFoodLevel(PlayerManager.getFPlayerByPlayer(p).foodLevel);
			PlayerInventory inv = p.getInventory();
			inv.clear();
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			ItemStack steak = new ItemStack(Material.COOKED_BEEF, 3);
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
			inv.addItem(sword);
			inv.addItem(steak);
			inv.addItem(compass);
			inv.setBoots(boot);
			inv.setLeggings(leg);
			inv.setChestplate(chest);
			inv.setHelmet(helm);
			p.teleport(teleLoc);
			p.sendMessage(ChatColor.GREEN + "You have selected the Heavy class.");
		} else if (PlayerManager.getFPlayerByPlayer(p).pClass == "Pyro") {
	    	p.setFireTicks(0);
	    	p.setHealth(20);
	    	p.setFoodLevel(PlayerManager.getFPlayerByPlayer(p).foodLevel);
			PlayerInventory inv = p.getInventory();
			inv.clear();
			ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
			ItemStack steak = new ItemStack(Material.COOKED_BEEF, 5);
			ItemStack lighter = new ItemStack(Material.FLINT_AND_STEEL);
			ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
			ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
			ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
			inv.addItem(axe);
			inv.addItem(steak);
			inv.addItem(lighter);
			inv.setBoots(boot);
			inv.setLeggings(leg);
			inv.setChestplate(chest);
			inv.setHelmet(helm);
			p.teleport(teleLoc);
			p.sendMessage(ChatColor.GREEN + "You have selected the Pyro class.");
		}
	}
}
