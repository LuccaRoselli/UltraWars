package com.ultrawars.lucca.gamechest;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.main.Main;
public class GameChestUtils {
	
	public static ArrayList<GameChest> chests1 = new ArrayList<GameChest>();
	
	public static void loadChests(){
		chests1 = new ArrayList<GameChest>();
		if (Main.getLocsYml().contains(Config.mapa + ".chests")) {
			for (String chestID : ((ConfigurationSection) Main.get(Config.mapa + ".chests"))
					.getKeys(false)) {
				Location loc = GameChest.loadLocation((ConfigurationSection) Main.get(
								Config.mapa + ".chests." + chestID + ".location"));

				if (loc.getBlock() == null
						|| !(loc.getBlock().getState() instanceof Chest)) {
					loc.getBlock().setType(Material.CHEST);
				}
				chests1.add(new GameChest((Chest) loc.getBlock().getState(),
						(int) Main.get(
								Config.mapa + ".chests." + chestID + ".tier")));

			}
		}
	}
	
	
	
	
	
	static ItemStack mush = new ItemStack(Material.MUSHROOM_SOUP);
	static ItemStack ironsword = new ItemStack(Material.IRON_SWORD);
	static ItemStack woodsword = new ItemStack(Material.WOOD_SWORD);
	static ItemStack goldsword = new ItemStack(Material.GOLD_SWORD);
	static ItemStack goldleggings = new ItemStack(Material.GOLD_LEGGINGS);
	static ItemStack goldhelmet = new ItemStack(Material.GOLD_HELMET);
	static ItemStack stonesword = new ItemStack(Material.STONE_SWORD);
	static ItemStack stonepic = new ItemStack(Material.STONE_PICKAXE);
	static ItemStack woodpic = new ItemStack(Material.WOOD_PICKAXE);
	static ItemStack woodaxe = new ItemStack(Material.WOOD_AXE);
	static ItemStack stoneaxe = new ItemStack(Material.STONE_AXE);
	static ItemStack chainb = new ItemStack(Material.CHAINMAIL_BOOTS);
	static ItemStack chainc = new ItemStack(Material.CHAINMAIL_HELMET);
	static ItemStack ironca = new ItemStack(Material.IRON_HELMET);
	static ItemStack chainchestplate = new ItemStack(
			Material.CHAINMAIL_CHESTPLATE);
	static ItemStack leatherh = new ItemStack(Material.LEATHER_HELMET);
	static ItemStack leatherchestplate = new ItemStack(
			Material.LEATHER_CHESTPLATE);
	static ItemStack leatherleggings = new ItemStack(Material.LEATHER_LEGGINGS);
	static ItemStack leatherboots = new ItemStack(Material.LEATHER_BOOTS);
	static ItemStack snowball8 = new ItemStack(Material.SNOW_BALL, 8);
	static ItemStack snowball4 = new ItemStack(Material.SNOW_BALL, 4);
	static ItemStack coblec15 = new ItemStack(Material.COBBLESTONE, 15);
	static ItemStack coblec16 = new ItemStack(Material.COBBLESTONE, 16);
	static ItemStack coblec13 = new ItemStack(Material.COBBLESTONE, 13);
	static ItemStack coblec17 = new ItemStack(Material.COBBLESTONE, 17);
	static ItemStack coblec31 = new ItemStack(Material.COBBLESTONE, 31);
	static ItemStack apple = new ItemStack(Material.APPLE);
	static ItemStack applec2 = new ItemStack(Material.APPLE);
	static ItemStack stonec16 = new ItemStack(Material.STONE, 16);
	static ItemStack cook = new ItemStack(Material.COOKIE);
	static ItemStack bread = new ItemStack(Material.BREAD);
	static ItemStack breadc3 = new ItemStack(Material.BREAD, 3);
	static ItemStack rawbeef = new ItemStack(Material.RAW_BEEF);
	static ItemStack rawbeefc3 = new ItemStack(Material.RAW_BEEF, 3);
	static ItemStack rawbeefc5 = new ItemStack(Material.RAW_BEEF, 5);
	static ItemStack rawbeefc8 = new ItemStack(Material.RAW_BEEF, 5);
	static ItemStack cobeef = new ItemStack(Material.COOKED_BEEF);
	static ItemStack cofishc3 = new ItemStack(Material.COOKED_FISH, 3);
	static ItemStack bflint = new ItemStack(Material.FLINT);
	static ItemStack flint = new ItemStack(Material.FLINT_AND_STEEL);
	static ItemStack enderpearl = new ItemStack(Material.FLINT_AND_STEEL);
	static ItemStack stickc4 = new ItemStack(Material.STICK, 4);
	@SuppressWarnings("deprecation")
	static ItemStack wood2c16 = new ItemStack(Material.getMaterial(5), 16,
			(short) 0, (byte) 2);
	@SuppressWarnings("deprecation")
	static ItemStack bwood1c8 = new ItemStack(Material.WOOD, 8, (short) 0,
			(byte) 1);
	static ItemStack bwood0c5 = new ItemStack(Material.WOOD, 5);
	@SuppressWarnings("deprecation")
	static ItemStack bwood3c4 = new ItemStack(Material.WOOD, 4, (short) 0,
			(byte) 3);
	@SuppressWarnings("deprecation")
	static ItemStack wood1c10 = new ItemStack(Material.getMaterial(5), 10,
			(short) 0, (byte) 1);
	@SuppressWarnings("deprecation")
	static ItemStack wood1c8 = new ItemStack(Material.getMaterial(5), 8,
			(short) 0, (byte) 1);
	static ItemStack dirtc32 = new ItemStack(Material.DIRT, 32);
	static ItemStack arrowc2 = new ItemStack(Material.ARROW, 2);
	static ItemStack arrowc3 = new ItemStack(Material.ARROW, 3);
	static ItemStack arrowc8 = new ItemStack(Material.ARROW, 8);
	@SuppressWarnings("deprecation")
	static ItemStack wood0c32 = new ItemStack(Material.getMaterial(5), 32);
	@SuppressWarnings("deprecation")
	static ItemStack wood0c8 = new ItemStack(Material.getMaterial(5), 8);
	@SuppressWarnings("deprecation")
	static ItemStack wood0c16 = new ItemStack(Material.getMaterial(5), 16);
	static ItemStack waterbucket = new ItemStack(Material.WATER_BUCKET);
	static ItemStack lavabucket = new ItemStack(Material.LAVA_BUCKET);
	static ItemStack bow = new ItemStack(Material.BOW);
	static ItemStack tntc3 = new ItemStack(Material.TNT, 3);

	static ItemStack[] chest1 = new ItemStack[] { woodsword, chainb, chainc,
			snowball8, bread, wood2c16, wood0c32, waterbucket,stonesword, woodaxe,
			leatherchestplate, wood0c16, wood1c10, wood1c10, rawbeef, flint,stoneaxe, cobeef,
			leatherleggings, leatherboots, bwood1c8, enderpearl, waterbucket,bow, arrowc3,
			chainchestplate, chainb, apple, coblec16, woodpic, lavabucket,woodsword, arrowc8, rawbeef,
			chainchestplate, coblec13, wood0c8, lavabucket,stoneaxe, breadc3, apple,
			snowball4, waterbucket, ironca, bwood3c4,goldsword, mush, mush, mush,
			dirtc32, stoneaxe, tntc3, waterbucket,stonepic, leatherh,
			leatherchestplate, flint, rawbeefc3, arrowc2, coblec15,woodsword, wood1c8,
			enderpearl, goldleggings, goldhelmet, rawbeef, apple, bwood0c5,
			cofishc3,woodsword, bflint, coblec17,
			rawbeefc5, lavabucket, leatherboots,ironsword, enderpearl,
			leatherh, applec2, stonec16, cook,ironsword, leatherboots,
			chainc, coblec31, snowball8, rawbeefc8, lavabucket };
	
	static ItemStack cobbleston3e = new ItemStack(Material.COBBLESTONE, 31);
	static ItemStack darmour = new ItemStack(Material.DIAMOND_HELMET, 1);
	static ItemStack darmour1 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
	static ItemStack darmour2 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
	static ItemStack darmour3 = new ItemStack(Material.DIAMOND_BOOTS, 1);
	static ItemStack dsword1 = new ItemStack(Material.DIAMOND_SWORD, 1);
	static ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE, 4);
	static ItemStack goldenApple10 = new ItemStack(Material.GOLDEN_APPLE, 7);
	static ItemStack dpick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
	static ItemStack daxe = new ItemStack(Material.DIAMOND_AXE, 1);
	static ItemStack dbow = new ItemStack(Material.BOW, 1);
	static ItemStack arrow = new ItemStack(Material.ARROW, 10);
	static ItemStack arrow20 = new ItemStack(Material.ARROW, 21);
	static ItemStack carrot = new ItemStack(Material.CARROT, 6);
	static ItemStack ender = new ItemStack(Material.ARROW, 2);
	
	static ItemStack[] feastitems = new ItemStack[] { cobbleston3e, darmour, darmour1, darmour2, 
		darmour3, dsword1, goldenApple, goldenApple10, dpick, daxe, dbow, arrow, arrow20, carrot, ender};
}

