package com.ultrawars.lucca.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.kit.Kit;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.enun.PlayerStage;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.storage.PlayerMySQL;
import com.ultrawars.lucca.utils.CuboidAPI;
import com.ultrawars.lucca.utils.EasyBoard;
import com.ultrawars.lucca.utils.ItemCustom;
import com.ultrawars.lucca.utils.LocaisSerialize;
import com.ultrawars.lucca.utils.StringManager;

public class PlayerGame {
	
	private Player player;
	private int kills = 0;
	private int assists = 0;
	private PlayerStage stage;
	private Kit kit;
	
	public PlayerGame(Player player) {
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public PlayerStage getPlayerStage(){
		return stage;
	}
	
	public Kit getKit(){
		return kit;
	}
	
	public void setPlayerStage(PlayerStage stage){
		this.stage = stage;
	}
	
	public int getKills(){
		return kills;
	}
	
	public int getAssists(){
		return assists;
	}
	
	public void setKills(int kills){
		this.kills = kills;
	}
	
	public void setAssists(int assists){
		this.assists = assists;
	}
	
	public void setKitPlayer(Kit kit){
		this.kit = kit;
	}
	
	public void sendShopMessage(){
        TextComponent antes = new TextComponent("§e§l(§e⛁§e§l) §6§lLOJA DE KITS §e§l(§e⛁§e§l)");
        TextComponent antes2 = new TextComponent("§e► §7Clique no nome do kit, para compra-lo!");
        TextComponent usocorreto = new TextComponent("§e§l► Lista: ");
        for (KitType t : KitType.values()){
        	String kitname = ChatColor.stripColor(t.getName());
        	String kitnamecolor = ChatColor.GRAY + kitname;
            TextComponent usocorreto2 = new TextComponent(kitnamecolor + "§a, ");
            usocorreto.setColor(ChatColor.RED);
            usocorreto2.setColor(ChatColor.GREEN);
            usocorreto2.setBold(Boolean.valueOf(true));
            usocorreto2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlayerMySQL.getCoin(player.getUniqueId()) >= t.getPrice() ? "§7Voce nao possui dinheiro para compra-lo! (§a⛁§7)" : "§7Voce possui dinheiro para compra-lo! (§c⛁§7)").create()));
            usocorreto2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sw buykit " + t.getID()));
            usocorreto.addExtra(usocorreto2);
        }
        player.spigot().sendMessage(antes);
        player.spigot().sendMessage(antes2);
        player.spigot().sendMessage(usocorreto);
	}
	
	@SuppressWarnings("deprecation")
	public void setPlayerInCage(int position){
		stage = PlayerStage.INCAGE;
		
		for(PotionEffect potion : player.getActivePotionEffects())
			player.removePotionEffect(potion.getType());
		
		if(kit != null)
			kit.runPlayer(player);
		
		if (player.getAllowFlight())
			player.setFlySpeed((float) 0.2);
		if (player.isOnGround())
			player.setWalkSpeed((float) 0.2);
		
		player.getInventory().clear();
		
		player.getInventory().setItem(0, new ItemCustom().type(Material.BLAZE_POWDER).name("&6Opções vips").build());
		player.getInventory().setItem(1, new ItemCustom().type(Material.BOW).name("&aSeletor de kits").build());
		player.getInventory().setItem(8, new ItemCustom().type(Material.BED).name("&cRetornar ao lobby").build());
		
		try{
			String serizaliado = Main.getLocsYml().getString("Ilhas." + String.valueOf(position));
			player.teleport(LocaisSerialize.getLocal(serizaliado, true));
		}catch(Exception e){
			Main.sendLog("Está faltando ilha para a posição " + position);
		}
	}
	
	public boolean isInsideWorldBorder(){
		if (Main.getLocsYml().isSet("Borda.1") && Main.getLocsYml().isSet("Borda.1")){
			if (CuboidAPI.inside(player, LocaisSerialize.getLocal(Main.getLocsYml().getString("Borda.1"), false), LocaisSerialize.getLocal(Main.getLocsYml().getString("Borda.2"), false))){
				return true;
			}
		}
		return false;
	}
	
	public boolean firstWalkInFeast(){
		if (Main.getLocsYml().isSet("Feast.Borda.1") && Main.getLocsYml().isSet("Feast.Borda.1")){
			if (CuboidAPI.inside(player, LocaisSerialize.getLocal(Main.getLocsYml().getString("Feast.Borda.1"), false), LocaisSerialize.getLocal(Main.getLocsYml().getString("Feast.Borda.2"), false))){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void backToIsland(int position){
		
		if (player.getAllowFlight())
			player.setFlySpeed((float) 0.2);
		if (player.isOnGround())
			player.setWalkSpeed((float) 0.2);
		try{
			String serizaliado = Main.getLocsYml().getString("Ilhas." + String.valueOf(position));
			player.teleport(LocaisSerialize.getLocal(serizaliado, true));
		}catch(Exception e){
			Main.sendLog("Está faltando ilha para a posição " + position);
		}
	}
	
	public boolean isSpectator(){
		if (stage == PlayerStage.SPEC){
			return true;
		}
		return false;
	}

	public void setPlayerSpec(){
		stage = PlayerStage.SPEC;
		player.spigot().respawn();
		
		for(PlayerGame pg : Main.getGame().getPlayers())
			if(pg.getPlayerStage() == PlayerStage.INGAME)
				pg.getPlayer().hidePlayer(this.player);
		
		
		player.getInventory().clear();
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 2));
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.getInventory().setItem(0, new ItemCustom().type(Material.COMPASS).name("&a&lTeleporte").addLore("&7Clique para espectar algum jogador").build());
				player.getInventory().setItem(4, new ItemCustom().type(Material.REDSTONE_COMPARATOR).name("&b&lConfiguração dos espectator").build());
				player.getInventory().setItem(8, new ItemCustom().type(Material.BED).name("&aRetornar ao lobby").build());
			}
		}.runTaskLaterAsynchronously(Main.getPlugin(), (20 * 1));
		player.setNoDamageTicks(Integer.MAX_VALUE);
		player.setAllowFlight(true);
		player.setFlying(true);
		try {player.teleport(LocaisSerialize.getLocal(Main.getLocsYml().getString("Espectador"), true));} catch (Exception e) {}
	}
	
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    String hour = format.format(now);
	
	public void sendScore(){
		final EasyBoard board = new EasyBoard(player, "§e§lSKYWARS") {
			
			@Override
			public void update() {
					setText("§9", 9);
					setText("§fPlayers: §a" + Main.getGame().getPlayers().size()+ "§f/§a" + Bukkit.getMaxPlayers(), 8);
					setText("§1", 7);
					if(Main.getGame().getTimeStart() != -1){
						setText("§fIniciando em", 6);
						setText("§a" + Main.getGame().getTimeStart() + "§fs", 5);
					}
					else {
						setText("§fAguardando...", 4);
					}
					setText("§2", 3);
					setText("§fServer: §a" + Config.server, 2);
					setText("§6", 1);
					setText("§e" + StringManager.SITE, 0);
					
			}
		};
		
        new BukkitRunnable() {
            @Override
            public void run() {
                board.update();
                if (Main.getGame().getStatus() == StatusGame.GAME){
                	board.removeScoreboard();
                	this.cancel();
                	sendOtherScore();
                }
            }
        }.runTaskTimer(Main.getPlugin(), 1L, 20L); // Deixe em 0L, 0L para atualizar muito rapido!
	}
	
	public void sendOtherScore(){
		final EasyBoard board = new EasyBoard(player, "§e§lSKYWARS") {
			
			@Override
			public void update() {
					setText("§7     "+hour, 12);
					setText("§1", 11);
					setText("§fProximo evento", 10);
					int minutes = (Main.getGame().getNextEvent() / 60);
					int seconds = (Main.getGame().getNextEvent() % 60);
					String display = Main.getGame().getRefil().getDisplay();
					if (display.equalsIgnoreCase("Sem eventos")){
						setText("§c" + display, 9);
					} else {
						if (String.valueOf(seconds).length() == 1){
						setText("§f" + display + ": §a" + minutes + ":0" + seconds, 9);
						} else {
							setText("§f" + display + ": §a" + minutes + ":" + seconds, 9);
						}
					}
					setText("§2", 8);
					setText("§fPlayers vivos: §a" + Main.getGame().filterPlayerGame(PlayerStage.INGAME).size(), 7);
					setText("§fKills: §a" + kills, 6);
					setText("§3", 5);
					setText("§fMapa: §a" + Config.mapa, 4);
					setText("§fMode: §cINSANO" , 3);
					setText("§4", 2);
					setText("§e" + StringManager.SITE, 1);
			}
		};
		
        new BukkitRunnable() {
            @Override
            public void run() {
                board.update();
            }
        }.runTaskTimer(Main.getPlugin(), 1L, 20L); // Deixe em 0L, 0L para atualizar muito rapido!
	}
}
