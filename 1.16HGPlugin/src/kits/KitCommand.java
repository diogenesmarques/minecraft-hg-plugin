package kits;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import States.GameStates;
import net.md_5.bungee.api.ChatColor;

public class KitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(GameStates.getState() != GameStates.STARTING) {
			sender.sendMessage(ChatColor.DARK_RED + "The tournament has already started.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("kits")) {
			player.sendMessage(GameStates.getState() + "Available kits: "
					+ StringUtils.join(Arrays.stream(Kit.values()).map(Kit::name).collect(Collectors.toList()), ", "));
		} else if (label.equalsIgnoreCase("kit")) {
			if (args.length == 0) {
				return true;
			}
			Iterator<Kit> iterator = Arrays.asList(Kit.values()).iterator();
			while (iterator.hasNext()) {
				Kit k = iterator.next();
				if (args[0].equalsIgnoreCase(k.name())) {
					Kit.setKit(player.getName(), k);
					player.sendMessage(ChatColor.YELLOW + "You selected the " + k.toString() + " kit");
					break;
				} else {
					if (!iterator.hasNext()) {
						player.sendMessage(ChatColor.DARK_RED
								+ "There is no such kit. Use /kits for a list of the available kits");
					}
				}
			}
		}
		return true;
	}

}
