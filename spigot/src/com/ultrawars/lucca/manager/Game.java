package com.ultrawars.lucca.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.ultrawars.lucca.gamechest.GameChest;
import com.ultrawars.lucca.gamechest.GameChestUtils;
import com.ultrawars.lucca.kit.KitType;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.enun.PlayerStage;
import com.ultrawars.lucca.manager.enun.RefilLevel;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.manager.task.StartGame;
import com.ultrawars.lucca.manager.task.TaskRefil;
import com.ultrawars.lucca.storage.PlayerMySQL;
import com.ultrawars.lucca.utils.Firewor;
import com.ultrawars.lucca.utils.LocaisSerialize;
import com.ultrawars.lucca.utils.StringBukkit;
import com.ultrawars.lucca.utils.StringManager;
import com.ultrawars.lucca.utils.Title;

public class Game {
	
	public static HashMap<UUID, Integer> skys = new HashMap<>();
	
	
	private BukkitTask taskContage = null;
	private BukkitTask tastRefil = null;
	private HashMap<UUID, PlayerGame> playersByUUID = new HashMap<>();
	private List<PlayerGame> players = new ArrayList<>();
	private int timeStart = -1;
	private StatusGame status = StatusGame.CAGE;
	private RefilLevel refil = RefilLevel.UM;
	private int nextEvent;
	
	
	public Game() {
		this.nextEvent = 0;
	}
	
	public void addPlayerInGame(Player player){
		PlayerGame pla = new PlayerGame(player);
		if(!players.contains(pla))
			players.add(pla);
		else
			pla.setPlayerStage(PlayerStage.INCAGE);
		
		if(!playersByUUID.containsKey(player.getUniqueId()))
			playersByUUID.put(player.getUniqueId(), pla);
		
		Title t = new Title("&eSkyWars", "&cModo insano");
		
		t.send(player);
		for(String s : Main.getLocsYml().getConfigurationSection("Ilhas").getKeys(false)){
			if(!skys.containsValue(Integer.valueOf(s))){
				skys.put(player.getUniqueId(), Integer.valueOf(s));
				pla.setPlayerInCage(Integer.valueOf(s));
			}
		}
	}
	
	public void removePlayerGame(Player player){
		if(playersByUUID.containsKey(player.getUniqueId())){
			PlayerGame pla = playersByUUID.get(player.getUniqueId());
			
			if(players.contains(pla))
				players.remove(pla);
			
			playersByUUID.remove(player.getUniqueId());
		}
		
		if(!skys.containsKey(player.getUniqueId()))
			skys.remove(player.getUniqueId());
	}
	
	public boolean containsPlayer(UUID uuid){
		return (playersByUUID.containsKey(uuid));
	}
	
	public PlayerGame getPlayer(Player player){
		if(playersByUUID.containsKey(player.getUniqueId()))
			return (playersByUUID.get(player.getUniqueId()));
		else
			return null;
	}
	
	public List<PlayerGame> filterPlayerGame(PlayerStage stage){
		List<PlayerGame> list = new ArrayList<>();
		for(PlayerGame pg : getPlayers())
			if(pg.getPlayerStage() == stage)
				list.add(pg);
		
		return list;
	}
	
	public StatusGame getStatus(){
		return status;
	}
	
	public void setStatus(StatusGame status){
		this.status = status;
	}
	
	public RefilLevel getRefil(){
		return refil;
	}
	
	public void setRefil(RefilLevel refil){
		this.refil = refil;
	}
	
	public void finishGame(final Player winner){
		if (Main.getGame().getStatus() != StatusGame.FINAL){
			Main.getGame().setStatus(StatusGame.FINAL);
			try{
				Location local = LocaisSerialize.getLocal(Main.getLocsYml().getString("Espectador"), true);
				winner.teleport(local.subtract(0, 0, 3));
				winner.setNoDamageTicks(Integer.MAX_VALUE);
				winner.setAllowFlight(true);
				winner.setFlying(true);
			}catch(Exception e){
				Main.sendLog("[Erro] Local dos espectador ainda não setado" );
			}
			
			OfflinePlayer player1 = null;
			int totaKills1 = 0;
			
			OfflinePlayer player2 = null;
			int totaKills2 = 0;
			
			OfflinePlayer player3 = null;
			int totaKills3 = 0;
			
			for(PlayerGame pg : getPlayers()){
				if(pg.getKills() > totaKills1){
					player1 = pg.getPlayer();
					totaKills1 = pg.getKills();
				}
				else if(pg.getKills() > totaKills2){
					player2 = pg.getPlayer();
					totaKills2 = pg.getKills();
				}
				else if(pg.getKills() > totaKills3){
					player3 = pg.getPlayer();
					totaKills1 = pg.getKills();
				}
			}
			
			
			Title titleNoWinner = new Title("§cFIM DO JOGO","§7Você não foi o vitorioso desta vez");
			
			bc("§a§l–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚");
			bc("");
			for(PlayerGame pg : getPlayers()){
				if(pg.getPlayer().isOnline() && pg.getPlayer() != winner){
					titleNoWinner.send(pg.getPlayer());
				}
				
				if(pg.getPlayer().isOnline()){
					if(winner != null)
						StringBukkit.sendCenteredMessage(pg.getPlayer(), "§eGanhador - §f" + winner.getName());
					pg.getPlayer().sendMessage("");
					if(player1 != null)
						StringBukkit.sendCenteredMessage(pg.getPlayer(), "§e1° Killer §7- §f" + player1.getName() + " - " + totaKills1);
					if(player2 != null)
						StringBukkit.sendCenteredMessage(pg.getPlayer(), "§62° Killer §7- §f" + player2.getName() + " - " + totaKills2);
					if(player3 != null)
						StringBukkit.sendCenteredMessage(pg.getPlayer(), "§c3° Killer §7- §f" + player3.getName() + " - " + totaKills3);
				}
			}
			
			bc("");
			bc("§a§l–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚");
			
			if(winner != null){
				Title title=  new Title("§6§lVITORIA!","§7Você foi o ultimo jogador a sobreviver.");
				title.send(winner);
				
				Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
					public void run() {
						new Firewor(winner.getLocation());
					}
				}, 0L, (20 * 2));
			}
			
			bc(StringManager.INFO + "§cServer reiniciando em 20 segundos");
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
				
				public void run() {
					Bukkit.shutdown();
				}
			}, (20 * 20));
		} else {
			System.out.println("EOQQQQQQ MALUCO");
		}
	}
	
	public static void buyKit(Player p, Integer kitid){
		KitType kitClicked = KitType.getKit(kitid);
		
		if(kitClicked == null){
			return;
		}
		
		if(!PlayerMySQL.getKits(p.getUniqueId()).contains(kitClicked)){
			
			if(kitClicked.getPrice() > PlayerMySQL.getCoin(p.getUniqueId())){
				p.sendMessage(StringManager.ERROR + "Você não possui coin suficiente para comprar esse kit");
				p.closeInventory();
				return;
			}
			
			int value = PlayerMySQL.getCoin(p.getUniqueId()) - kitClicked.getPrice();
			PlayerMySQL.setCoin(p.getUniqueId(), value);
			
			List<KitType> kits =  PlayerMySQL.getKits(p.getUniqueId());
			kits.add(kitClicked);
			PlayerMySQL.setKits(p.getUniqueId(), kits);

			p.closeInventory();
			p.sendMessage(StringManager.INFO + "Você comprou o kit " + kitClicked.getName());
		}else{
			p.sendMessage(StringManager.ERROR + "Você ja tem o kit " + kitClicked.getName());
		}
	}
	
	public void startGame(){
		
		Title insane = new Title("&c&lMODO INSANO");
		
		for(PlayerGame pg : players){
			
			pg.getPlayer().getLocation().add(0, -1, 0).getBlock().setType(Material.AIR);
			
			insane.send(pg.getPlayer());
			
			pg.setPlayerStage(PlayerStage.INGAME);
			
			pg.getPlayer().getInventory().clear();
			pg.getPlayer().closeInventory();
			
			if(pg.getKit() != null)
				pg.getKit().runPlayer(pg.getPlayer());
			
			pg.getPlayer().setGameMode(GameMode.SURVIVAL);
			for (GameChest chest : GameChestUtils.chests1) {
				chest.fillChest();
			}
			pg.getPlayer().sendMessage("§a§l–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚");
			StringBukkit.sendCenteredMessage(pg.getPlayer(), "§f§lSkyWars");
			pg.getPlayer().sendMessage("");
			StringBukkit.sendCenteredMessage(pg.getPlayer(), "§e§LColete recursos e equipamentos na sua ilha");
			StringBukkit.sendCenteredMessage(pg.getPlayer(), "§e§Lpara poder matar qualquer outro jogador.");
			StringBukkit.sendCenteredMessage(pg.getPlayer(), "§e§LVa ate a ilha central para encontrar");
			StringBukkit.sendCenteredMessage(pg.getPlayer(), "§e§Lbaús e items especiais.");
			pg.getPlayer().sendMessage("");
			pg.getPlayer().sendMessage("§a§l–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚–‚");
		}
		
		this.status = StatusGame.GAME;
		startRefil();
	}
	
	public void setNextEvent(int time){
		this.nextEvent = time;
	}
	
	public int getNextEvent(){
		return nextEvent;
	}
	
	public void setTimeStart(int time){
		this.timeStart = time;
	}
	
	public int getTimeStart(){
		return timeStart;
	}
	
	@SuppressWarnings("deprecation")
	public void startRefil(){
		tastRefil = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new TaskRefil(this, 180), 0L, 20L);
	}
	
	public void stopTaskRefil(){
		tastRefil.cancel();
	}
	
	@SuppressWarnings("deprecation")
	public void startContage(){
		taskContage = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new StartGame(10, this), 0L, 20L);
	}
	
	public void stopTaskContage(){
		taskContage.cancel();
	}
	
	public void bc(String message){
		for(PlayerGame pg : players)
			pg.getPlayer().sendMessage(message);
	}
	
	public List<PlayerGame> getPlayers(){
		return players;
	}
	
}
