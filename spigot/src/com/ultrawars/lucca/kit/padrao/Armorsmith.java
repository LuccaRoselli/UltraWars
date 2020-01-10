package com.ultrawars.lucca.kit.padrao;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.utils.ItemCustom;

public class Armorsmith extends Kit {
	
	
	public void runPlayer(Player player) {
		playerKits.put(player.getUniqueId(), KitType.ARMORSWITH);
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
		meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true);
		book.setItemMeta(meta);
		
		player.getInventory().addItem(new ItemCustom().type(Material.ANVIL).build(),
				new ItemCustom().type(Material.EXP_BOTTLE).amount(64).build(), 
				book);
	}

	public void finishPlayer(Player player) {
		playerKits.remove(player.getUniqueId());
	}
	

}
