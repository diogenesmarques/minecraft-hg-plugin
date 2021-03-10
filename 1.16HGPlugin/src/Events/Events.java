package Events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import States.GameStates;
import net.md_5.bungee.api.ChatColor;

public class Events implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		event.setCancelled(
				GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.INVINCIBILITY);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.ENDING);
	}

	@EventHandler
	public void onPlayerHungerChange(FoodLevelChangeEvent event) {
		event.setCancelled(GameStates.getState() == GameStates.STARTING);
	}

	@EventHandler
	public void onPlayerInteractWithBlock(PlayerInteractEvent event) {
		event.setCancelled(GameStates.getState() == GameStates.STARTING);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(GameStates.getState() == GameStates.STARTING);
	}

	@EventHandler
	public void onPlayerFocusedEvent(EntityTargetEvent event) {
		event.setCancelled(
				GameStates.getState() == GameStates.STARTING || GameStates.getState() == GameStates.INVINCIBILITY);
	}

	@EventHandler
	public void onPlayerEatSoup(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().name().contains("RIGHT")) {
			if (player.getInventory().getItemInMainHand().getType() == Material.MUSHROOM_STEW) {
				double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				if (player.getHealth() < maxHealth) {
					double newHealth = player.getHealth() + 3;
					if (newHealth < maxHealth) {
						player.setHealth(newHealth);
					} else {
						player.setHealth(maxHealth);
					}
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL));
					player.updateInventory();
				} else {
					if (player.getFoodLevel() < 20) {
						int newFoodLevel = player.getFoodLevel() + 6;
						if (newFoodLevel < 20) {
							player.setFoodLevel(newFoodLevel);
						} else {
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

	@EventHandler
	public void onCompassRightClick(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
			if (event.getAction() != Action.PHYSICAL) {
				ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers().stream()
						.filter(s -> !s.equals(player)).filter(s -> s.getLocation().distance(player.getLocation()) > 10)
						.collect(Collectors.toList()));
				players.sort(Comparator.comparingInt(s -> (int) s.getLocation().distance(player.getLocation())));

				if (players.isEmpty()) {
					player.sendMessage(ChatColor.DARK_RED + "No player has been found");
					return;
				}
				Player target = players.get(0);
				player.setCompassTarget(target.getLocation());
				player.sendMessage(ChatColor.YELLOW + "Compass pointing towards " + target.getName() + ".");
			}
		}
	}

}
