package com.ultrawars.lucca.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CuboidAPI {

	public static boolean inside(Player p, Location loc1, Location loc2) {
		if (loc1 == null || loc2 == null)
			return false;
		if (loc1.getWorld() != loc2.getWorld())
			return false;
		int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		int px = p.getLocation().getBlockX();
		int py = p.getLocation().getBlockY();
		int pz = p.getLocation().getBlockZ();
		if (loc1.getWorld() == p.getWorld()) {
			if (px >= x1 && px <= x2) {
				if (py >= y1 && py <= y2) {
					if (pz >= z1 && pz <= z2) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
