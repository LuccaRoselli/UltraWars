package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Ecologist extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.ECOLOGIST);
		player.getInventory().addItem(new ItemStack[]{new ItemCustom().type(Material.IRON_AXE).build(),
				new ItemCustom().type(Material.WOOD).data((short)2).build()});
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

}
