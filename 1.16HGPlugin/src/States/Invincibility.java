package States;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import plugin.Main;

public class Invincibility {
	public static void timer() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(GameStates.getState() == GameStates.INVINCIBILITY) {
					Main.invTime--;
					
					if(Main.invTime % 20 == 0 || (Main.invTime <= 10 && Main.invTime > 0)) {
						Bukkit.broadcastMessage(ChatColor.RED + "Invincibility wears off in " + Main.invTime + " seconds");
					}
					if(Main.invTime == 0) {
						Bukkit.broadcastMessage(ChatColor.RED + "You are no longer invincible!");
						GameStates.setState(GameStates.INGAME);
					}
				}
			}
			
			
		}.runTaskTimer(Main.plugin, 0, 20);
	}
}
