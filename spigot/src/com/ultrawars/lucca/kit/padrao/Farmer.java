package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Farmer extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.FARMER);
		player.getInventory().addItem(new ItemCustom().type(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 3).build(), new ItemCustom().type(Material.EGG).amount(64).build(), new ItemCustom().type(Material.GOLDEN_APPLE).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
