package com.ultrawars.lucca.gamechest;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class GameChest {

	private Chest chest;
	private int tier;

	public GameChest(Chest chest, int tier) {
		this.chest = chest;
		this.tier = tier;
	}

	public Chest getChest() {
		return chest;
	}

	public int getTier() {
		return tier;
	}

	ItemStack item1espada = new ItemStack(Material.WOOD_SWORD, 1);
	ItemStack item1espada2 = new ItemStack(Material.STONE_SWORD, 1);
	ItemStack itembloco = new ItemStack(Material.COBBLESTONE, 16);
	@SuppressWarnings("deprecation")
	ItemStack itembloco2 = new ItemStack(Material.getMaterial(5), 16);
	ItemStack itembaldelava = new ItemStack(Material.LAVA_BUCKET, 1);
	ItemStack itembaldeagua = new ItemStack(Material.WATER_BUCKET, 1);
	ItemStack itemarco = new ItemStack(Material.BOW, 1);
	ItemStack itembalflecha = new ItemStack(Material.ARROW, 2);
	ItemStack itemmacadourada = new ItemStack(Material.GOLDEN_APPLE, 1);
	ItemStack itemcapacete = new ItemStack(Material.LEATHER_HELMET, 1);
	ItemStack itembota = new ItemStack(Material.LEATHER_BOOTS, 1);
	ItemStack itemcalca = new ItemStack(Material.LEATHER_LEGGINGS, 1);
	ItemStack itempeitoral = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
	ItemStack itemsopa = new ItemStack(Material.MUSHROOM_SOUP, 1);
	ItemStack itemboladeneve = new ItemStack(Material.SNOW_BALL, 4);
	@SuppressWarnings("deprecation")
	ItemStack itemxp = new ItemStack(Material.getMaterial(384), 1);
	@SuppressWarnings("deprecation")
	ItemStack itembife = new ItemStack(Material.getMaterial(364), 1);

	ItemStack itemcapaceted = new ItemStack(Material.DIAMOND_HELMET, 1);
	ItemStack itembotsad = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
	ItemStack itemdiamante = new ItemStack(Material.DIAMOND, 1);
	ItemStack itempicareta = new ItemStack(Material.IRON_PICKAXE, 1);
	ItemStack itemmachado = new ItemStack(Material.IRON_AXE, 1);

	ItemStack ar = new ItemStack(Material.AIR, 1);
	ItemStack[] items2 = new ItemStack[] {itemcapaceted,
			itembotsad, itemdiamante, itempicareta, itemmachado, item1espada,
			item1espada2, itembloco, itembaldeagua,
			itembaldelava, itemarco, itembalflecha, itemmacadourada,
			itemcapacete, itembota, itemcalca, itempeitoral, itemboladeneve,
			itemxp, itembife, itemsopa, itemsopa, itemsopa,itemsopa, itemsopa };

	public void fillChest() {
		chest.getInventory().clear();
		
		if (tier == 1) {
			Random r = new Random();

			int numItems = r.nextInt(3) + 1;
			for (int i = 0; i < 8 + numItems; i++) {
				ItemStack material = null;
				material = GameChestUtils.chest1[r.nextInt(GameChestUtils.chest1.length)];
				ItemStack item = material;
				chest.getInventory().addItem(item);
			}
		}
		if (tier == 2) {
			Random r = new Random();

			int numItems = r.nextInt(3) + 1;
			for (int i = 0; i < 8 + numItems; i++) {
				ItemStack material = null;
				material = items2[r.nextInt(items2.length)];
				ItemStack item = material;
				chest.getInventory().addItem(item);
			}
		}
		if (tier == 3){
			Random r = new Random();

			int numItems = r.nextInt(3) + 1;
			for (int i = 0; i < 8 + numItems; i++) {
				ItemStack material = null;
				material = GameChestUtils.feastitems[r.nextInt(GameChestUtils.feastitems.length)];
				ItemStack item = material;
				chest.getInventory().addItem(item);
			}
		}
	}

	public void save(ConfigurationSection section) {
		section.set("location.world", chest.getLocation().getWorld().getName());
		section.set("location.x", chest.getLocation().getX());
		section.set("location.y", chest.getLocation().getY());
		section.set("location.z", chest.getLocation().getZ());
		section.set("tier", tier);
	}
	
	public static Location loadLocation(ConfigurationSection section) {
		Location loc = new Location(Bukkit.getServer().getWorld(
				section.getString("world")), section.getDouble("x"),
				section.getDouble("y"), section.getDouble("z"));
		loc.setPitch((float) section.getDouble(("yaw")));
		loc.setPitch((float) section.getDouble(("pitch")));
		return loc;
	}
}