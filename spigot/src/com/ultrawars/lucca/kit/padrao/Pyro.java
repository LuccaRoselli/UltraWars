package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Pyro extends Kit {

	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.PYRO);
		player.getInventory().addItem(new ItemCustom().type(Material.FLINT_AND_STEEL).build(), 
				new ItemCustom().type(Material.LAVA_BUCKET).build(), 
				new ItemCustom().type(Material.LAVA_BUCKET).build(), 
				new ItemCustom().type(Material.LAVA_BUCKET).build(), 
				new ItemCustom().type(Material.LAVA_BUCKET).build(),
				new ItemCustom().type(Material.IRON_CHESTPLATE).build());
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}

	
}
