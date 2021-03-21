package kits;

import java.util.HashMap;

public enum Kit {
	Achilles, Anchor, Archer, Assassin, Barbarian, Beastmaster,
	Berserker, Cannibal, Copycat, Cultivator, Demoman, Fireman,
	Endermage, Gladiator, Grappler, Kangaroo, Lumberjack, Madman, 
	Monk, Ninja, Phantom, Pickpocket, Pyro,Snail, Stomper, Surprise,
	Thor, Urgal, Viking, Viper, Worm;

	private static final HashMap<String, Kit> map = new HashMap<>();

	public static void setKit(String playerName, Kit kit) {
		map.put(playerName, kit);
	}

	public static Kit getKit(String playerName) {
		return map.get(playerName);
	}

	public static boolean hasKit(String playerName) {
		return map.containsKey(playerName);
	}

}
