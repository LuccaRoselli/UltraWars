package com.ultrawars.lucca.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocaisSerialize {
	
	public static String setLocal(Location loc, boolean isPlayer)
	{
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		String linha = null;
		if(isPlayer){
			float yaw = loc.getYaw();
			float pitch = loc.getPitch();
			linha = world + " " + x + " " +  y + " " +  z + " " + yaw+ " " + pitch;
		}else{
			linha = world + " " + x + " " +  y + " " +  z;
		}
		return linha;
	}
	
	public static Location getLocal(String s, boolean isPlayer){
		String[] argumentos = s.split(" ");
		World world = Bukkit.getWorld(argumentos[0]);
		double x = Double.valueOf(argumentos[1]);
		double y = Double.valueOf(argumentos[2]);
		double z = Double.valueOf(argumentos[3]);
		Location loc = null;
		if(isPlayer){
			float yaw = Float.valueOf(argumentos[4]);
			float pitch = Float.valueOf(argumentos[5]);
			loc = new Location(world, x, y, z, yaw, pitch);
		}else{
			loc = new Location(world, x, y, z);
		}
		return loc;
	}
	
}
