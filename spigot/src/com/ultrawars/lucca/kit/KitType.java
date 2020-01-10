package com.ultrawars.lucca.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ultrawars.lucca.utils.ItemCustom;


public enum KitType {
	
	
	ARMORSWITH(0,"armorswith", new ItemCustom().type(Material.ANVIL).name("&cArmorswith").build(), 3000, 10),
	CANNONEER(1,"cannoneer", new ItemCustom().type(Material.TNT).name("&cCannoneer").build(), 7500, 11),
	FARMER(2, "farmer", new ItemCustom().type(Material.IRON_LEGGINGS).name("&cFarmer").build(), 5500, 12),
	SPELEOLOGIST(3, "speleologist", new ItemCustom().type(Material.IRON_AXE).name("&cSpeleologist").build(), 5500, 13),
	ENCHANTER(4, "enchanter", new ItemCustom().type(Material.ENCHANTMENT_TABLE).name("&cEnchanter").build(), 3000, 14),
	FISHERMAN(5, "fisherman", new ItemCustom().type(Material.FISHING_ROD).name("&cFisherman").build(), 5500, 15),
	HUNTER(6, "hunter", new ItemCustom().type(Material.BOW).name("&cHunter").build(), 5500, 16),
	ARMORER(7, "armorer", new ItemCustom().type(Material.GOLD_CHESTPLATE).name("&cArmorer").build(), 5500, 19),
	KNIGHT(8, "knight", new ItemCustom().type(Material.GOLD_SWORD).name("&cKnight").build(), 5500, 20),
	ECOLOGIST(9, "ecologist", new ItemCustom().type(Material.IRON_AXE).name("&cEcologist").build(), 3000, 21),
	PYRO(10, "pyro", new ItemCustom().type(Material.FLINT_AND_STEEL).name("&cPyro").build(), 7500, 22),
	SWNOMAN(11, "swnowman", new ItemCustom().type(Material.SNOW_BALL).name("&cSnowman").build(), 5000, 23),
	TROLL(12, "troll", new ItemCustom().type(Material.WEB).name("&cTroll").build(), 7000, 24);
	
	private int id;
	private String name;
	private ItemStack item;
	private int price;
	private int slot;
	
	KitType(int id, String name, ItemStack item, int price, int slot){
		this.id = id;
		this.name = name;
		this.item = item;
		this.price = price;
		this.slot = slot;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ItemStack getItem() {
		return item;
	}
	
	public int getPrice(){
		return price;
	}
	
	public int getSlot(){
		return slot;
	}

	public static KitType getKit(int id){
		for(KitType kt : KitType.values())
			if(kt.getID() == id)
				return kt;
		return null;
	}
	
	public static KitType getKit(ItemStack item){
		for(KitType kit : KitType.values())
			if(kit != null && kit.getItem().getType() ==  item.getType() && kit.getItem().getItemMeta().getDisplayName() == item.getItemMeta().getDisplayName())
				return kit;
		return null;
	}
	
	
}
