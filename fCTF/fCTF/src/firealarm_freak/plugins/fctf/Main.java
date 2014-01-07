package firealarm_freak.plugins.fctf;

import org.bukkit.plugin.java.JavaPlugin;

import firealarm_freak.plugins.fctf.chat.ChatListeners;
import firealarm_freak.plugins.fctf.config.ConfigManager;
import firealarm_freak.plugins.fctf.pvp.PvpListeners;

public class Main extends JavaPlugin {
	public void onEnable() {
		getCommand("switch").setExecutor(new Commands());
		getCommand("start").setExecutor(new Commands());
		getCommand("flags").setExecutor(new Commands());
		getCommand("heavy").setExecutor(new ClassCommands());
		getCommand("stats").setExecutor(new Commands());
		getCommand("pyro").setExecutor(new ClassCommands());
		getCommand("map").setExecutor(new Commands());
		getCommand("ctf").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getServer().getPluginManager().registerEvents(new PvpListeners(), this);
		getServer().getPluginManager().registerEvents(new ChatListeners(), this);
		ConfigManager.plugin = this;
		this.saveDefaultConfig();
		MapManager.loadMap("world");
	}
	public void onDisable() {
		
	}
}
