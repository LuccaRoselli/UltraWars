package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Enchanter extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.ENCHANTER);
		player.getInventory().addItem(
			new ItemCustom().type(Material.ENCHANTMENT_TABLE).build(), 
			new ItemCustom().type(Material.EXP_BOTTLE).amount(64).build(), 
			new ItemCustom().type(Material.BOOKSHELF).amount(8).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
