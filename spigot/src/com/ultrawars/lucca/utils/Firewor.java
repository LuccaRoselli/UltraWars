package com.ultrawars.lucca.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Firewor {
	
	public Firewor(Location location){
		spawnFogos(location);
	}
	
	public static void spawnFogos(Location location){
	       Color c = pegaCor();
	       FireworkEffect.Type tipo = pegaTipo();
	       World w = location.getWorld();
	       Firework fw = (Firework)w.spawnEntity(location, EntityType.FIREWORK);
	       FireworkMeta fwm = fw.getFireworkMeta();
	       FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(c).withFade(c).with(tipo).trail(false).build();
	       fwm.setPower(2);
	       fwm.addEffect(effect);
	       fw.setFireworkMeta(fwm);
	     }
	     
	     private static Color pegaCor(){
	       Color[] cores = { Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW };
	       return cores[new java.util.Random().nextInt(cores.length)];
	     }
	     
	     private static FireworkEffect.Type pegaTipo(){
	       FireworkEffect.Type[] tipos = { FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.STAR };
	       return tipos[new java.util.Random().nextInt(tipos.length)];
	     }
}
