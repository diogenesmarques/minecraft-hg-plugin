package Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import States.GameStates;

public class Events implements Listener{
	@EventHandler
	void onEntityDamage(EntityDamageEvent event) {
		if(GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.INVINCIBILITY) {
			event.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	void onBlockBreak(BlockBreakEvent event) {
		if(GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.ENDING) {
			event.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	void onPlayerHungerChange(FoodLevelChangeEvent event) {
		if(GameStates.getState() == GameStates.STARTING) {
			event.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	void onPlayerInteractWithBlock(PlayerInteractEvent event) {
		if(GameStates.getState() == GameStates.STARTING) {
			event.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	void onBlockPlace(BlockPlaceEvent event) {
		if(GameStates.getState() == GameStates.STARTING) {
			event.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	void onPlayerFocusedEvent(EntityTargetEvent event) {
		if(GameStates.getState() == GameStates.STARTING) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	void onPlayerEatSoup(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!(event.getEventName().contains("PHYSICAL"))) {
			if(player.getInventory().getItemInMainHand().getType() == Material.MUSHROOM_STEW) {
				if(player.getHealth() < player.getMaxHealth()) {
					double newHealth = player.getHealth() + 3.0;
					if(newHealth < player.getMaxHealth()) {
						player.setHealth(newHealth);
					}else {
						player.setHealth(player.getMaxHealth());
					}
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
					player.updateInventory();
				}else {
					if(player.getFoodLevel() < 20) {
						int newFoodLevel = player.getFoodLevel() + 6;
						if(newFoodLevel < 20) {
							player.setFoodLevel(newFoodLevel);
						}else {
							player.setFoodLevel(20);
						}
						player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
						player.updateInventory();
					}
				}
			}
			event.setCancelled(true);
		}
	}
	
}


















