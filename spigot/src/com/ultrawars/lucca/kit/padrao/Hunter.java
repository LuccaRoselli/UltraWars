package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Hunter extends Kit{

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.HUNTER);
		player.getInventory().addItem(new ItemCustom().type(Material.BOW).build(), new ItemCustom().type(Material.ARROW).amount(10).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
