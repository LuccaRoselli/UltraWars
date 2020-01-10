package com.ultrawars.lucca.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.ultrawars.lucca.commands.Commands;
import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.gamechest.GameChestUtils;
import com.ultrawars.lucca.listeners.All;
import com.ultrawars.lucca.listeners.CageListeners;
import com.ultrawars.lucca.listeners.GameListeners;
import com.ultrawars.lucca.listeners.SpecListeners;
import com.ultrawars.lucca.manager.Game;
import com.ultrawars.lucca.storage.MySQL;
import com.ultrawars.lucca.storage.PlayerMySQL;
import com.ultrawars.lucca.storage.exception.MySQLException;
import com.ultrawars.lucca.utils.UltraScroller;

public class Main extends JavaPlugin {

	public static Boolean alguemnofeast = false;
	public static Main plugin;
	public static MySQL mysql;
	public static Game game;
	public static String prefix = "§9[SkyWars] §7";
	
	public static FileConfiguration locsYml;
	
	public static Main getPlugin(){
		return plugin;
	}
	
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public void onEnable() {
		plugin = this;
		
		com.devlucca.br.constructors.Config c = new com.devlucca.br.constructors.Config(new File(getDataFolder(), "kits.yml"));
		try {
			c.createIfNotExist();
			c.set("Kits.Enchanter.Nome", "Enchanter");
			c.set("Kits.Enchanter.Descricao", Arrays.asList("&4Descricao legal", "&3eita cuzao"));
			c.set("Kits.Enchanter.Icone", "116");
			c.set("Kits.Enchanter.Itens", Arrays.asList("116-1", "384-24", "47-16"));
			c.save();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		saveDefaultConfig();
		Config.loadConfig();
		
		
		File locs = new File(getDataFolder(), "locs.yml");

		if (!locs.exists()) {
			try {
				locs.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		locsYml = new YamlConfiguration().loadConfiguration(locs);
		
		loadListeners();
		loadCommands();
		
		try {
			mysql = new MySQL(Config.ip, Config.porta, Config.database, Config.user, Config.senha);
			mysql.executeStatement("CREATE TABLE IF NOT EXISTS players_sw("+
	                "uuid VARCHAR(40) NOT NULL,"+
	                "coin INT NOT NULL,"+
	                "kits VARCHAR(500)," +
	                "rank VARCHAR(500))");
		} catch (MySQLException e) {
			e.printStackTrace();
		}
		
		try {
			for (World w : Bukkit.getWorlds()){
				w.setAutoSave(false);
				System.out.println("Mapa " + w.getName() + " descarregado.");
			}
		} catch (Exception e) {
			System.out.println("Nao foi possivel descarregar os mapas.");
		}
		
		game = new Game();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			game.addPlayerInGame(p);
			for(Player pp : Bukkit.getOnlinePlayers())
				p.showPlayer(pp);
		}
		if (getLocsYml().isSet(Config.mapa + ".chests")){
			GameChestUtils.loadChests();
			sendLog("Chests carregados!");
		} else {
			sendLog("Chests nao encontrados.");
		}
		UltraScroller.setup();
	}
	
	@SuppressWarnings("deprecation")
	public void onDisable() {
		HandlerList.unregisterAll();
		for(Player pls : Bukkit.getOnlinePlayers()){
			pls.kickPlayer("");
		}
		try {
			for (World w : Bukkit.getWorlds()){
				Bukkit.unloadWorld(w, false);
				System.out.println("Mapa " + w.getName() + " descarregado.");
			}
		} catch (Exception e) {
			System.out.println("Nao foi possivel descarregar os mapas.");
		}
	}
	
	public static void sendLog(String log){
		Bukkit.getLogger().info(log);
	}
	
	public static void sendLogColor(String log){
		Bukkit.getConsoleSender().sendMessage(log.replace("&", "§"));
	}
	
	public static FileConfiguration getLocsYml(){
		return locsYml;
	}
	
	public static boolean isWalkedOnFeast(){
		return alguemnofeast;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String path) {
		return (T) locsYml.get(path);
	}
	
	public static void saveLocsYml() {
		try {
			locsYml.save(new File(getPlugin().getDataFolder(), "locs.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MySQL getMySQL(){
		return mysql;
	}
	
	public static Game getGame(){
		return game;
	}
	
	private void loadListeners(){
		Bukkit.getPluginManager().registerEvents(new All(), this);
		Bukkit.getPluginManager().registerEvents(new GameListeners(),this);
		Bukkit.getPluginManager().registerEvents(new SpecListeners(), this);
		Bukkit.getPluginManager().registerEvents(new CageListeners(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMySQL(), this);
		
	}
	
	private void loadCommands(){
		getCommand("sw").setExecutor(new Commands());
	}
	
}
