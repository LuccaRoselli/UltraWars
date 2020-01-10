package com.ultrawars.lucca.manager.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.ultrawars.lucca.gamechest.GameChest;
import com.ultrawars.lucca.gamechest.GameChestUtils;
import com.ultrawars.lucca.main.Main;
import com.ultrawars.lucca.manager.Game;
import com.ultrawars.lucca.manager.PlayerGame;
import com.ultrawars.lucca.manager.enun.RefilLevel;
import com.ultrawars.lucca.utils.Title;

public class TaskRefil extends BukkitRunnable {
	
	private int minutes;
	private Game game;
	
	public TaskRefil(Game game, int minutes) {
		this.game = game;
		this.minutes = minutes;
	}
	
	public void run() {
		
				
		if(minutes == 0){
			restart();
		}
		
		game.setNextEvent(minutes);
		minutes --;
	}

	public void restart(){
		switch (game.getRefil()) {
		case UM:
			for (GameChest chest : GameChestUtils.chests1) {
				if (chest != null){
					chest.fillChest();
				}
			}
			for(PlayerGame pg : Main.getGame().getPlayers()){
				if (pg.getPlayer().isOnline()){
					Title title=  new Title("§b§l" + game.getRefil().getDisplay(),"§7Todos os baús foram refillados.");
					title.send(pg.getPlayer());
				}
			}
			game.setNextEvent(180);
			game.setRefil(RefilLevel.DOIS);
			game.stopTaskRefil();
			game.startRefil();
			break;
		case DOIS:
			for (GameChest chest : GameChestUtils.chests1) {
				if (chest != null){
					chest.fillChest();
				}
			}
			for(PlayerGame pg : Main.getGame().getPlayers()){
				if (pg.getPlayer().isOnline()){
					Title title=  new Title("§b§l" + game.getRefil().getDisplay(),"§7Todos os baús foram refillados.");
					title.send(pg.getPlayer());
				}
			}
			game.setNextEvent(180);
			game.setRefil(RefilLevel.TRES);
			game.stopTaskRefil();
			game.startRefil();
			break;
		case TRES:
			for (GameChest chest : GameChestUtils.chests1) {
				if (chest != null){
					chest.fillChest();
				}
			}
			for(PlayerGame pg : Main.getGame().getPlayers()){
				if (pg.getPlayer().isOnline()){
					Title title=  new Title("§b§l" + game.getRefil().getDisplay(),"§7Todos os baús foram refillados.");
					title.send(pg.getPlayer());
				}
			}
			game.setNextEvent(2);
			game.setRefil(RefilLevel.FINAL);
			game.stopTaskRefil();
			game.startRefil();
			break;
		case FINAL:
			game.stopTaskRefil();
			game.setNextEvent(0);
			break;
		default:
			break;
		}
		
	}
	
}
