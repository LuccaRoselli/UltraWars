package com.ultrawars.lucca.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.enun.PlayerRank;

public class PlayerMySQL implements Listener{

	private static String table = "players_sw";

	private boolean contaisInMySQL(UUID uuid) {
		return Main.getMySQL()	.resultSetBoolean("SELECT * FROM " + table + " WHERE uuid LIKE '%" + uuid.toString() + "%'");
	}

	private void setMySQL(UUID uuid) {
		Main.getMySQL().preparedStatement("INSERT INTO " + table + " (uuid,coin,kits,rank) " +
	                                      "VALUES('" + uuid.toString()+ "',0,'','" + PlayerRank.NORMAL.getDisplay() + "')");
	}
	
	public static String getRank(UUID uuid) {
		return (String) Main.getMySQL().resultSet("SELECT rank FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "rank");
	}

	public static int getCoin(UUID uuid) {
		return (Integer) Main.getMySQL().resultSet("SELECT coin FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "coin");
	}

	public static List<KitType> getKits(UUID uuid) {
		return formatKits((String) Main.getMySQL().resultSet("SELECT kits FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "kits"));
	}

	public static void setCoin(UUID uuid, int coin) {
		Main.getMySQL().executeStatement("UPDATE " + table + " SET coin='" + coin + "' WHERE uuid='" + uuid.toString() + "';");
	}

	public static void setRank(UUID uuid, PlayerRank rank) {
		Main.getMySQL().executeStatement("UPDATE " + table + " SET rank='" + rank.getDisplay() + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void setKits(UUID uuid, List<KitType> kits) {
		Main.getMySQL().executeStatement("UPDATE " + table + " SET kits='" + setKits(kits) + "' WHERE uuid='" + uuid.toString() + "';");
	}

	private static List<KitType> formatKits(String kits) {
		if (kits == null || kits == "")
			return new ArrayList<KitType>();

		List<KitType> list = new ArrayList<KitType>();
		String[] kitArgs = kits.split(",");

		try {
			for (String s : kitArgs) {
				list.add(KitType.getKit(Integer.valueOf(s)));
			}
		} catch (Exception e) {
		}

		return list;
	}

	private static String setKits(List<KitType> kits) {

		String line = "";

		for (KitType kit : kits) {

			if (line == "")
				line = kit.getID() + "";
			else
				line += "," + kit.getID();
		}
		return line;
	}

	@EventHandler
	public void joinSetInMySQL(PlayerJoinEvent e) {
		if (!contaisInMySQL(e.getPlayer().getUniqueId())){
			setMySQL(e.getPlayer().getUniqueId());
			System.out.println("MySQL criado para o jogador " + e.getPlayer().getName());
		} else {
			System.out.println("MySQL ja existe para o jogador " + e.getPlayer().getName());
		}
	}

}
