package com.ultrawars.lucca.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.Game;
import com.ultrawars.lucca.manager.PlayerGame;
import com.ultrawars.lucca.manager.enun.PlayerStage;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.utils.ActionBar;
import com.ultrawars.lucca.utils.StringManager;

public class GameListeners implements Listener {

	@EventHandler
	public void death(PlayerDeathEvent e) {
		if (Main.getGame().getStatus() == StatusGame.GAME) {
			e.setDeathMessage(null);
			Player p = e.getEntity();
			Player killer = p.getKiller();

			if (killer != null) {
				PlayerGame killerg = Main.getGame().getPlayer(killer);
				killerg.setKills((killerg.getKills() + 1));
				Main.getGame().bc(StringManager.PREFIX + "§c" + p.getName() + "  §7morreu para §a" + killer.getName());
				/*
				if(hasAssist(p)){
					addAssist(assist.get(p), p);
					System.out.println("assistencia add");
				}
				*/
			}else{
				PlayerGame pg = new PlayerGame(p);
				if (!pg.isSpectator()){
					Main.getGame().bc(StringManager.PREFIX + "§e" + p.getName() + "  §7morreu.");
				}
			}
			
			if (!new PlayerGame(p).isSpectator()){
			Main.getGame().getPlayer(p).setPlayerSpec();
			}

			checkWin();
			
			for(PlayerGame pg : Main.getGame().getPlayers())
				new ActionBar().sendActionbar(pg.getPlayer(), "§eHá §c" + Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() + " §eplayers na partida");
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if (!new PlayerGame(e.getPlayer()).isInsideWorldBorder()){
			if (Main.getGame().getStatus() == StatusGame.GAME){
				e.getPlayer().sendMessage(StringManager.PREFIX + "§cVoce saiu da borda do mundo! Retornando para sua ilha.");
				new PlayerGame(e.getPlayer()).backToIsland(Game.skys.get(e.getPlayer().getUniqueId()));
			}
		}
		if (!Main.isWalkedOnFeast()){
			if (Main.getGame().getStatus() == StatusGame.GAME){
				if (new PlayerGame(e.getPlayer()).firstWalkInFeast()){
					Main.alguemnofeast = true;
					Bukkit.broadcastMessage(StringManager.PREFIX + "O jogador §a" + e.getPlayer().getName() + " §7foi o primeiro a chegar no feast!");
					e.getPlayer().getWorld().strikeLightningEffect(e.getPlayer().getLocation());
					e.getPlayer().getWorld().strikeLightningEffect(e.getPlayer().getLocation());
					for (PlayerGame j : Main.getGame().filterPlayerGame(PlayerStage.INGAME))
						j.getPlayer().playSound(j.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e){
		if (e.getEntity().getType() == EntityType.PLAYER && e.getCause() == DamageCause.VOID){
			PlayerGame p = new PlayerGame((Player) e.getEntity());
			switch (Main.getGame().getStatus()) {
			case CAGE:
				p.setPlayerInCage(Game.skys.get(e.getEntity().getUniqueId()));
				break;
			case GAME:
				p.getPlayer().damage(20.0D);
				break;
			default:
				break;
			}
		}
	}
	
	/*
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
	  if(event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)){
		  Player player = (Player)event.getEntity();
		  Player damager = (Player)event.getDamager();
		  setAssist(damager, player);
	  }
	}
	
	public HashMap<Player, Player> assist = new HashMap<Player, Player>();
	
	public void setAssist(Player assistador, Player alvo){
		assist.put(alvo, assistador);
	}
	
	public boolean hasAssist(Player alvo){
		if (assist.containsKey(alvo)){
			if (!assist.get(alvo).equals(alvo)){
				if (assist.get(alvo).isOnline()){
					return true;
				}
			}
		}
		return false;
	}
	
	public void addAssist(Player assists, Player alvo){
		PlayerGame assistencia = Main.getGame().getPlayer(assists);
		assistencia.setAssists((assistencia.getAssists() + 1));
		assist.remove(alvo);
		assist.remove(assists);
	}
	
	*/
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onBlockCanBuild(BlockCanBuildEvent e){
		if(Main.getGame().getStatus().equals(StatusGame.GAME)){
			if(!e.isBuildable()){
				 for (Player target : Bukkit.getOnlinePlayers()) {
					 if(Main.getGame().getPlayer(target).getPlayerStage() == PlayerStage.SPEC){
						 Location loc = target.getLocation();
				          if ((loc.getX() > loc.getBlockX() - 1) && (loc.getX() < loc.getBlockX() + 1) && (loc.getZ() > loc.getBlockZ() - 1) && (loc.getZ() < loc.getBlockZ() + 1) &&  (loc.getY() > loc.getBlockY() - 2) && (loc.getY() < loc.getBlockY() + 1)) {
				        	  target.teleport(e.getBlock().getLocation().add(0.0D, 5.0D, 0.0D));
				        	  e.setBuildable(true);
				          }else{
				        	  e.setBuildable(false);
				          }
					 }
				 }
			}
		}
	}

	@EventHandler
	public void kick(PlayerKickEvent e) {
		if (Main.getGame().getStatus() == StatusGame.GAME){
			Main.getGame().getPlayer(e.getPlayer()).setPlayerStage(PlayerStage.OFFLINE);
			Main.getGame().bc(StringManager.PREFIX + "" + e.getPlayer().getName() + "  §esaiu ");
		}
		
		checkWin();
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		if (Main.getGame().getStatus() == StatusGame.GAME){
			Main.getGame().getPlayer(e.getPlayer()).setPlayerStage(PlayerStage.OFFLINE);
			Main.getGame().bc(StringManager.PREFIX + "" + e.getPlayer().getName() + "  §esaiu ");
		}
		
		checkWin();
	}

	private void checkWin() {
		if (Main.getGame().filterPlayerGame(PlayerStage.INGAME).size() == 1)
			Main.getGame().finishGame(Main.getGame().filterPlayerGame(PlayerStage.INGAME).get(0).getPlayer());
	}
}
