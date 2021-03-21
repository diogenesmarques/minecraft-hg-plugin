package kits;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KitEvents implements Listener {

	public static void givePlayerKitItems(Player player) {

		if (Kit.getKit(player.getName()) == Kit.Archer) {
			player.getInventory().addItem(new ItemStack(Material.BOW, 1));
			player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
		}

		if (Kit.getKit(player.getName()) == Kit.Beastmaster) {
			player.getInventory().addItem(new ItemStack(Material.WOLF_SPAWN_EGG, 3));
			player.getInventory().addItem(new ItemStack(Material.BONE, 4));
		}
		
		if(Kit.getKit(player.getName()) == Kit.Fireman) {
			player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));
		}
		
	}

	@EventHandler
	public void stomper(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (Kit.getKit(player.getName()) != Kit.Stomper)
				return;

			if (event.getCause() == DamageCause.FALL) {
				event.setDamage(4);
				for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
					if (entity instanceof Damageable) {
						if (entity instanceof Player && ((Player) entity).isSneaking())
							return;

						((Damageable) entity).damage(player.getFallDistance(), player);
						entity.setVelocity(entity.getLocation().toVector().subtract(player.getLocation().toVector())
								.setY(1).multiply(Math.random() * 2));
					}
				}

			}
		}
	}

	@EventHandler
	public void anchor(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (Kit.getKit(player.getName()) == Kit.Anchor || (event.getDamager() instanceof Player
					&& Kit.getKit(event.getDamager().getName()) == Kit.Anchor)) {
				player.setVelocity(new Vector(0, 0, 0));
			}
		}
	}

	@EventHandler
	public void beastmaster(EntityDeathEvent event) {
		EntityDamageEvent edmge = event.getEntity().getLastDamageCause();
		EntityDamageByEntityEvent edmgbee = (EntityDamageByEntityEvent) edmge;

		if (!(event.getEntity() instanceof Player) || !(edmgbee.getDamager() instanceof Player))
			return;

		if (Kit.getKit(edmgbee.getDamager().getName()) == Kit.Beastmaster) {
			((Player) edmgbee.getDamager()).getInventory().addItem(new ItemStack(Material.WOLF_SPAWN_EGG, 1));
		}

	}

	@EventHandler
	public void cannibal(EntityDamageByEntityEvent event) {
		if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
			if (Kit.getKit(event.getDamager().getName()) == Kit.Cannibal) {
				((Player) event.getDamager()).setFoodLevel(((Player) event.getDamager()).getFoodLevel() + 2);
				((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));
			}
		}
	}
	
	@EventHandler
	public void achilles(EntityDamageByEntityEvent event) {
		if((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
			if(Kit.getKit(event.getEntity().getName()) == Kit.Achilles) {
				if(((Player) event.getDamager()).getInventory().getItemInMainHand().toString().contains("WOODEN")) {
					event.setDamage(event.getDamage() + 4);
				}else {
					event.setDamage(event.getDamage() - 3);
				}
			}
		}
	}
	
	@EventHandler
	public void fireman(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(Kit.getKit(event.getEntity().getName()) == Kit.Fireman) {
			if((event.getCause().toString().contains("FIRE")) || (event.getCause().toString().contains("LAVA"))) {
				event.setCancelled(true);
			}
		}
	}

}













