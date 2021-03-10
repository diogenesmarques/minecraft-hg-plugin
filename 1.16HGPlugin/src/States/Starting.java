package States;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import kits.Kit;
import plugin.Main;

public class Starting {
	
	public static void timer() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(GameStates.getState() == GameStates.STARTING) {
					
					Main.timeToStart--;
					
					if(Main.timeToStart % 20 == 0 || (Main.timeToStart <= 10 && Main.timeToStart > 0)) {
						Bukkit.broadcastMessage(ChatColor.RED + "The tournament will begin in " + Main.timeToStart + " seconds");
					}
					if(Main.timeToStart == 0) {
						if(Bukkit.getOnlinePlayers().size() < Main.minPlayers) {
							Main.timeToStart = 181;
						}else {
							Bukkit.broadcastMessage(ChatColor.RED + "The Tournament has begun!");
							Bukkit.broadcastMessage(ChatColor.RED + "There are " + Bukkit.getOnlinePlayers().size() + " Players participating");
							Bukkit.broadcastMessage(ChatColor.RED + "Everyone is invincible for 2 minutes");
							Bukkit.broadcastMessage(ChatColor.RED + "Good Luck!");
							
							for(Player player : Bukkit.getOnlinePlayers()) {
								player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 1);
								player.teleport(player.getWorld().getSpawnLocation());
								
								player.getInventory().addItem(new ItemStack(Material.COMPASS));
								
								
								if(Kit.getKit(player.getName()) == Kit.Archer) {
									
								}
							}
							GameStates.setState(GameStates.INVINCIBILITY);
							Invincibility.timer();
						}
					}
				}
			
			}
		}.runTaskTimer(Main.plugin, 0, 20);
	}

}
