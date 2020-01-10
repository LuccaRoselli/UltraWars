package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Fisherman extends Kit{

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.FARMER);
		player.getInventory().addItem(new ItemCustom().type(Material.FISHING_ROD).addEnchant(Enchantment.DURABILITY, 10).addEnchant(Enchantment.KNOCKBACK, 1).build(), new ItemCustom().type(Material.COOKED_FISH).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
