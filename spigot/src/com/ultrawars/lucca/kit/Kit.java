package com.ultrawars.lucca.kit;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public abstract class Kit {
	
	public static HashMap<UUID, KitType> playerKits = new HashMap<>();
	
	public abstract void runPlayer(Player player);
	
	public abstract void finishPlayer(Player player);

	
	
}
