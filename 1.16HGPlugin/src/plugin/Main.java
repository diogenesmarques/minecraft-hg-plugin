package plugin;

import java.io.File;
import java.lang.reflect.Constructor;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Events.Events;
import States.Starting;
import kits.KitCommand;
import kits.KitEvents;
import net.minecraft.server.v1_16_R2.RegionFileCache;

public class Main extends JavaPlugin {

	public static Plugin plugin;
	public static int timeToStart = 11;
	public static int invTime = 11;
	public static int minPlayers = 1;

	@Override
	public void onEnable() {
		plugin = this;
		Starting.timer();
		getCommand("kits").setExecutor(new KitCommand());
		getCommand("kit").setExecutor(new KitCommand());
		getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getPluginManager().registerEvents(new KitEvents(), this);
	}

	@Override
	public void onDisable() {
		World world = Bukkit.getWorld("world");
		Bukkit.unloadWorld(world, false);
		try {
			Class<RegionFileCache> region = RegionFileCache.class;
			Constructor<RegionFileCache> constructor = region.getDeclaredConstructor(File.class, boolean.class);
			constructor.setAccessible(true);
			RegionFileCache regionFileCache = constructor.newInstance(new File(world.getWorldFolder(), "region/r.0.0.mca").getParentFile(), true);
			regionFileCache.close();
			regionFileCache.a();
			FileUtils.deleteDirectory(world.getWorldFolder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
