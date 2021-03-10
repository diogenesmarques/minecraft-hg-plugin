package plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Events.Events;
import States.Starting;

public class Main extends JavaPlugin {
	
	public static Plugin plugin;
	public static int timeToStart = 31;
	public static int invTime = 120;
	public static int minPlayers = 1;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Events(), this);
		plugin = this;
		Starting.timer();
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		Bukkit.unloadWorld(Bukkit.getWorld("world"), false);
		deleteWorld(new File("world"));
	}
	
	void deleteWorld(File folder) {
		File[] folderContent = folder.listFiles();
		if(folderContent != null) {
			for(File file : folderContent) {
				deleteWorld(file);
			}
		}
		folder.delete();
	}
}
