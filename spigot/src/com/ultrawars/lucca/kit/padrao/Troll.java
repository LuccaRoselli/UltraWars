package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Troll extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.TROLL);
		player.getInventory().addItem(new ItemCustom().type(Material.WEB).build(),
				new ItemCustom().type(Material.LEATHER_HELMET).build(),
				new ItemCustom().type(Material.LEATHER_CHESTPLATE).build(), 
				new ItemCustom().type(Material.LEATHER_LEGGINGS).build(),
				new ItemCustom().type(Material.LEATHER_BOOTS).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
