package com.ultrawars.lucca.config;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.ultrawars.lucca.main.Main;

public class Config {
	
	public static World world;
	public static int min_players;
	public static String mapa;
	public static String server;
	
	
	public static String ip;
	public static int porta;
	public static String database;
	public static String user;
	public static String senha;
	
	
	public static void loadConfig(){
		try{
			ip = Main.getPlugin().getConfig().getString("MySQL.IP");
			porta = Main.getPlugin().getConfig().getInt("MySQL.Porta");
			database = Main.getPlugin().getConfig().getString("MySQL.Database");
			user = Main.getPlugin().getConfig().getString("MySQL.User");
			senha = Main.getPlugin().getConfig().getString("MySQL.Senha");
			
			
			world = Bukkit.getWorld(Main.getPlugin().getConfig().getString("world"));
			min_players = Main.getPlugin().getConfig().getInt("min_players");
			mapa = Main.getPlugin().getConfig().getString("mapa");
			server = Main.getPlugin().getConfig().getString("server");
		}catch(Exception e){
			throw new IllegalArgumentException("Config invalid");
		}
	}
	
}
