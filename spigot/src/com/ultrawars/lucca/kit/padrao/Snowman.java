package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Snowman extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.SWNOMAN);
		player.getInventory().addItem(new ItemCustom().type(Material.SNOW_BALL).amount(16).build(),
				new ItemCustom().type(Material.SNOW_BLOCK).build(),
				new ItemCustom().type(Material.IRON_SPADE).addEnchant(Enchantment.DURABILITY, 3).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
