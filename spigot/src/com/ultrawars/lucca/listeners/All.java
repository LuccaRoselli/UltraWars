package com.ultrawars.lucca.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.ultrawars.lucca.config.Config;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.enun.PlayerRank;
import com.ultrawars.lucca.manager.enun.StatusGame;
import com.ultrawars.lucca.storage.PlayerMySQL;

public class All implements  Listener{
	
	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!PlayerRank.isRank(e.getPlayer().getUniqueId(), PlayerRank.NORMAL)) {
			e.setMessage(e.getMessage().replace("&", "§"));
		}
		e.setFormat(PlayerRank.valueOf(PlayerMySQL.getRank(p.getUniqueId())).getColor() + PlayerMySQL.getRank(p.getUniqueId()) + " §r" + PlayerRank.valueOf(PlayerMySQL.getRank(e.getPlayer().getUniqueId())).getSimpleColor() + p.getName() + "§e ➟ §7"+ e.getMessage());
	}
	
	@EventHandler
	public void cancelStartGame(PlayerQuitEvent e){
		e.setQuitMessage(null);
		if(Main.getGame().getPlayers().size() <= Config.min_players){
			
		}
	}
	
	@EventHandler
	public void cancelStartGame(PlayerKickEvent e){
		e.setLeaveMessage(null);
		if(Main.getGame().getPlayers().size() <= Config.min_players){
			
		}
	}
	
	@EventHandler
	public void motd(ServerListPingEvent e) {
		if(Main.getGame().getStatus().equals(StatusGame.CAGE)) {
			e.setMotd("§aENTRAR");
		}else{
			e.setMotd("§cJogando");
		}
	}
	
	@EventHandler
	public void cancelMob(CreatureSpawnEvent e){
		e.setCancelled(true);
	}
	
	
	
}
