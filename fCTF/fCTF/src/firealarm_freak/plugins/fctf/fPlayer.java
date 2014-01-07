package firealarm_freak.plugins.fctf;

import org.bukkit.entity.Player;

public class fPlayer {
	Player p;
	public int kills = 0;
	public int killStreak;
	public int deaths = 0;
	public int flagSteals = 0;
	public int flagCaps = 0;
	public String pClass = "Heavy";
	public int pArmor = 20;
	public int foodLevel = 15;
	public fPlayer(Player p) {
		this.p = p;
	}
}
