package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Cannoneer extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.CANNONEER);
		
		player.getInventory().addItem(new ItemCustom().type(Material.TNT).amount(16).build(), new ItemCustom().type(Material.REDSTONE).amount(32).build(),
				new ItemCustom().type(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_FALL, 3).addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3).build(), new ItemCustom().type(Material.WATER_BUCKET).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}
}
