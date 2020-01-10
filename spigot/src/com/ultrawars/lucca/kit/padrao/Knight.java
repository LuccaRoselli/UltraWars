package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Knight extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.KNIGHT);
		player.getInventory().addItem(new ItemCustom().type(Material.GOLD_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.DURABILITY, 5).build(), new ItemCustom().type(Material.GOLD_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
