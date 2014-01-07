package firealarm_freak.plugins.fctf;

import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

public class FlagManager {
	public static FlagManager instance;
	
	public static Entity flagAI;
	public static Entity flagAWI;
	
	public static Entity flagBI;
	public static Entity flagBWI;
	
	public static boolean flagARestore;
	public static boolean flagBRestore;
	
	public static Entity flagSpawn(String team) {
		if (team == "A") {
			Wool woolA = new Wool(DyeColor.RED);
			ItemStack flagA = woolA.toItemStack(1);
			Entity a = Game.flagA.getWorld().dropItem(Game.flagA, flagA);
		    a.setVelocity(new Vector(0, 0, 0));
		    return a;
		    
		} else {
			Wool woolB = new Wool(DyeColor.BLUE);
			ItemStack flagB = woolB.toItemStack(1);
			Entity b = Game.flagB.getWorld().dropItem(Game.flagB, flagB);
		    b.setVelocity(new Vector(0, 0, 0));	
		    return b;
		}
	}
	
	public static Entity flagSteal(String team) {
		if (team == "A") {
			Wool woolA = new Wool(DyeColor.WHITE);
			ItemStack flagA = woolA.toItemStack(1);
			Entity a = Game.flagA.getWorld().dropItem(Game.flagA, flagA);
		    a.setVelocity(new Vector(0, 0, 0));
		    return a;
		} else {
			Wool woolB = new Wool(DyeColor.WHITE);
			ItemStack flagB = woolB.toItemStack(1);
			Entity b = Game.flagB.getWorld().dropItem(Game.flagB, flagB);
		    b.setVelocity(new Vector(0, 0, 0));
		    return b;
		}
	}
	
	public static void flagCapture(String team) {
		if (team == "A") {
			for (Entity e : Game.flagA.getWorld().getEntities()) {
				if (e.getUniqueId()== flagAWI.getUniqueId()) {
					e.remove();
					flagAI = FlagManager.flagSpawn("A");
				}
			}
			flagAWI = null;
		} else {
			for (Entity e : Game.flagB.getWorld().getEntities()) {
				if (e.getUniqueId()== flagBWI.getUniqueId()) {
					e.remove();
					flagBI = FlagManager.flagSpawn("B");
				}
			}
			flagBWI = null;
		}
	}
	
	public static void flagRecover(String team) {
		if (team == "A") {
			flagAI.remove();
			for (Entity e : Game.flagA.getWorld().getEntities()) {
				if (e.getUniqueId()== flagAWI.getUniqueId()) {
					e.remove();
					flagAI = FlagManager.flagSpawn("A");
				}
			}
			flagAWI = null;
		} else {
			flagBI.remove();
			for (Entity e : Game.flagB.getWorld().getEntities()) {
				if (e.getUniqueId() == flagBWI.getUniqueId()) {
					e.remove();
					flagBI = FlagManager.flagSpawn("B");
				}
			}
			flagBWI = null;
		}
	}
	
	public static void flagRestore(String team) {
		if (team == "A") {
			FlagManager.flagAI.remove();
			for (Entity e : Game.flagA.getWorld().getEntities()) {
				if (e.getUniqueId()== flagAWI.getUniqueId()) {
					e.remove();
					flagAI = FlagManager.flagSpawn("A");
				}
			}
			FlagManager.flagAWI = null;
			Game.flagAHolder = "Home";
		} else {
			FlagManager.flagBI.remove();
			for (Entity e : Game.flagB.getWorld().getEntities()) {
				if (e.getUniqueId()== flagBWI.getUniqueId()) {
					e.remove();
					flagBI = FlagManager.flagSpawn("B");
				}
			}
			FlagManager.flagBWI = null;
			Game.flagBHolder = "Home";
		}
	}
}
