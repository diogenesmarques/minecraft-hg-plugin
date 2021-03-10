package kits;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class KitEvents implements Listener {

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
	public void archer(EntityDamageByEntityEvent event) {

	}
}
